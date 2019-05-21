package com.example.sikostum;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostum.MODEL.Alamat;
import com.example.sikostum.MODEL.GetAlamat;
import com.example.sikostum.MODEL.GetTempat;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class EditTempatActivity extends AppCompatActivity {
    TextView tvIdTempat,tvNamaTempat, noRekening, sloganTempat, deskripsiTempat;
    Spinner spinnerAlamat, spinnerStatus;
    ImageView fotoTem;
    Button pilihGambar, btSimpanTem;
APIInterface mApiInterface;
    String fileNamePhoto;
    String imagePath = "";
    Context mContext;
    String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tempat);

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);
    mContext= getApplicationContext();
        ActionBar menu = getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
        tvIdTempat=(TextView) findViewById(R.id.tvIdTempatS);
        tvNamaTempat= (TextView) findViewById(R.id.nama);
         noRekening = (TextView) findViewById(R.id.norek);
         sloganTempat =(TextView) findViewById(R.id.slogan);
         deskripsiTempat =(TextView) findViewById(R.id.deskripsi);
         spinnerAlamat =(Spinner) findViewById(R.id.alamat);
         fotoTem =(ImageView) findViewById(R.id.fotoTem);
         spinnerStatus=(Spinner) findViewById(R.id.status);
         pilihGambar = (Button) findViewById(R.id.buttonPilihGambar);
         btSimpanTem =(Button) findViewById(R.id.buttonEdit);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

         final Intent mIntent = getIntent();
        tvIdTempat.setText(mIntent.getStringExtra("id_tempat"));
        tvNamaTempat.setText(mIntent.getStringExtra("nama_tempat"));
        noRekening.setText(mIntent.getStringExtra("no_rekening"));
        sloganTempat.setText(mIntent.getStringExtra("slogan_tempat"));
        deskripsiTempat.setText(mIntent.getStringExtra("deskripsi_tempat"));
//        spinnerAlamat.setOnItemSelectedListener(mIntent.getStringExtra("alamat_tempat"));
//        spinnerAlamat.setSelection(Integer.parseInt(mIntent.getStringExtra("alamat_tempat"))-1);
        fileNamePhoto = mIntent.getStringExtra("foto_tempat");
        imagePath = mIntent.getStringExtra("foto_tempat_url");
        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(imagePath).into(fotoTem);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_person_black_24dp).into(fotoTem);
        }
        imagePath = mIntent.getStringExtra("foto_tempat_url");
//        spinnerStatus.setSelection(Integer.parseInt(mIntent.getStringExtra("status"))-1);
        initSpinnerAlamat();
        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mintaPermissions();
            }
        });
        spinnerAlamat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName =parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Kamu memilih alamat"+selectedName,Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btSimpanTem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!imagePath.contains("uploads/" + fileNamePhoto)) &&
                        (imagePath.length()>0)){
                    //File creating from selected URL
                    File file = new File(imagePath);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("foto_tempat", file.getName(),
                            requestFile);
                }
                RequestBody reqid_tempat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (tvIdTempat
                                        .getText().toString().isEmpty()) ?
                                        "" : tvIdTempat.getText().toString());
                RequestBody reqid_alamat=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (spinnerAlamat.getSelectedItem().toString()));
                RequestBody reqnama_toko=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (tvNamaTempat.getText().toString().isEmpty())?"":tvNamaTempat.getText().toString());
                RequestBody reqno_rekening =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (noRekening.getText().toString().isEmpty()?"":noRekening.getText().toString()));
                RequestBody reqslogan_tempat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (sloganTempat.getText().toString().isEmpty()?"":sloganTempat.getText().toString()));
                RequestBody reqdeskripsi_tempat=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (deskripsiTempat.getText().toString().isEmpty()?"":deskripsiTempat.getText().toString()));
                RequestBody reqstatus=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (spinnerStatus.getSelectedItem().toString()));
                Call<GetTempat>callUpdate= mApiInterface.putTempat(body,reqid_tempat,reqid_alamat,reqnama_toko,reqno_rekening,
                        reqslogan_tempat,reqdeskripsi_tempat,reqstatus);
                callUpdate.enqueue(new Callback<GetTempat>() {
                    @Override
                    public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditTempatActivity.this, "Gagal Edit Identitas", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditTempatActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myIdentitasIntent = new Intent(getApplicationContext(),TempatSewaActivity.class);
                            startActivity(myIdentitasIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTempat> call, Throwable t) {

                    }
                });
            }
        });

    }
    private void initSpinnerAlamat(){

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));
//        RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
//                (tvIdAlamatU.getText().toString().isEmpty()) ? "" : tvIdAlamatU.getText().toString());
        Call<GetAlamat> mAlamat=mApiInterface.getAlm(reqid_user);
        mAlamat.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                if(response.body().getStatus().equals("success")){
                    List<Alamat> alamatItem = response.body().getResult();
                    List<String> listSpinner= new ArrayList<String>();
                    for(int i=0; i<alamatItem.size();i++){
//                        listSpinner.add(alamatItem.get(i).getId_alamat());
                        String namaAlamat = alamatItem.get(i).getAlamat();
                        listSpinner.add(alamatItem.get(i).getIdAlamat()+"-"+namaAlamat);


                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAlamat.setAdapter(adapter);
                }else{

                    Toast.makeText(mContext,"Gagal mengambil data alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAlamat> call, Throwable t) {
                Toast.makeText(mContext,"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mintaPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // Cek apakah semua permission yang diperlukan sudah diijinkan
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(),
                                    "Semua permissions diijinkan!", Toast.LENGTH_SHORT).show();
                            tampilkanFotoDialog();
                        }

                        // Cek apakah ada permission yang tidak diijinkan
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // Info user untuk mengubah setting permission
                            tampilkanSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void tampilkanFotoDialog() {
        AlertDialog.Builder fotoDialog = new AlertDialog.Builder(this);
        fotoDialog.setTitle("Select Action");
        // Isi opsi dialog
        String[] fotoDialogItems = {
                "Pilih foto dari gallery",
                "Ambil dari kamera"};
        fotoDialog.setItems(fotoDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pilihan) {
                        switch (pilihan) {
                            case 0:
                                pilihDariGallery();
                                break;
                            case 1:
                                ambilDariCamera();
                                break;
                        }
                    }
                });
        fotoDialog.show();
    }

    public void pilihDariGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 13);
    }

    private void ambilDariCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, 16);
    }

    private void tampilkanSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditTempatActivity.this);
        builder.setTitle("Butuh Permission");
        builder.setMessage("Aplikasi ini membutuhkan permission khusus, mohon ijin.");
        builder.setPositiveButton("BUKA SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                bukaSettings();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    private void bukaSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        // Jika request berasal dari Gallery
        if (requestCode == 13) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imagePath = simpanImage(bitmap);
                    Toast.makeText(mContext, "Foto berhasil di-load!", Toast.LENGTH_SHORT).show();

                    Glide.with(mContext).load(new File(imagePath)).into(fotoTem);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }
            }

            // Jika request dari Camera
        } else if (requestCode == 16) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imagePath = simpanImage(thumbnail);
            Toast.makeText(mContext, "Foto berhasil di-load dari Camera!", Toast.LENGTH_SHORT)
                    .show();

            Glide.with(mContext).load(new File(imagePath)).into(fotoTem);
        }

    }
    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/praktikum");

        // Buat direktori untuk penyimpanan
        if (!lokasiImage.exists()) {
            lokasiImage.mkdirs();
        }

        try {
            // Untuk penamaan file
            File f = new File(lokasiImage, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();

            // Operasi file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.d("PRAKTIKUM", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("PRAKTIKUM", "error");
            e1.printStackTrace();
        }
        return "";
    }

}
