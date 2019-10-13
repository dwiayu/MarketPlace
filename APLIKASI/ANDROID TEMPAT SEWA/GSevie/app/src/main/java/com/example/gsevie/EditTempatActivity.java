package com.example.gsevie;


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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.GetTempat;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    TextView idTempat, namaTempat, noRekening, noHp, email, slogan, deskripsi, alamat, username, password;
    Spinner spinnerStatus;
    ImageView fotoTempat;
    Button pilihGambar, btnSimpan;
    APIInterface mApiInterface;
    String fileNamePhoto;
    String imagePath = "";
    Context mContext;
    String id_tempat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tempat);

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);
        mContext= getApplicationContext();

        idTempat =(TextView) findViewById(R.id.idTempat);
        namaTempat = (TextView) findViewById(R.id.namaTempat);
        noRekening = (TextView) findViewById(R.id.noRekening);
        noHp = (TextView) findViewById(R.id.noHp);
        email = (TextView) findViewById(R.id.email);
        slogan = (TextView) findViewById(R.id.sloganTempat);
        deskripsi = (TextView) findViewById(R.id.deskripsiTempat);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        alamat = (TextView) findViewById(R.id.alamat);
        username = (TextView) findViewById(R.id.uname);
        password = (TextView) findViewById(R.id.passwd);
        fotoTempat = (ImageView) findViewById(R.id.fotoTempat);
        pilihGambar = (Button) findViewById(R.id.buttonPilihGambar);
        btnSimpan = (Button) findViewById(R.id.buttonEdit);

        id_tempat=  SaveSharedPreferences.getId(getApplicationContext());

        final Intent mIntent = getIntent();
        idTempat.setText(mIntent.getStringExtra("id_tempat"));
        namaTempat.setText(mIntent.getStringExtra("nama_tempat"));
        noRekening.setText(mIntent.getStringExtra("no_rekening"));
        slogan.setText(mIntent.getStringExtra("slogan_tempat"));
        alamat.setText(mIntent.getStringExtra("alamat_tempat"));
        noHp.setText(mIntent.getStringExtra("no_hp"));
        email.setText(mIntent.getStringExtra("email"));
        username.setText(mIntent.getStringExtra("username"));
        password.setText(mIntent.getStringExtra("password"));
        deskripsi.setText(mIntent.getStringExtra("deskripsi_tempat"));
        fileNamePhoto = mIntent.getStringExtra("foto_tempat");
        imagePath = mIntent.getStringExtra("foto_tempat_url");
        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(imagePath).into(fotoTempat);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.galeri_icon).into(fotoTempat);
        }
        imagePath = mIntent.getStringExtra("foto_tempat_url");
        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mintaPermissions();
            }
        });
        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                RequestBody reqid_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"), id_tempat);
                RequestBody reqnama_toko =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (namaTempat.getText().toString().isEmpty()) ? "" : namaTempat.getText().toString());
                RequestBody reqno_rekening =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (noRekening.getText().toString().isEmpty() ? "" : noRekening.getText().toString()));
                RequestBody reqNohp =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (noHp.getText().toString().isEmpty()?"" : noHp.getText().toString()));
                RequestBody reqEmail =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (email.getText().toString().isEmpty()? "" :email.getText().toString()));
                RequestBody reqslogan_tempat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (slogan.getText().toString().isEmpty() ? "" : slogan.getText().toString()));
                RequestBody reqdeskripsi_tempat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (deskripsi.getText().toString().isEmpty() ? "" : deskripsi.getText().toString()));
                RequestBody reqstatus =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (spinnerStatus.getSelectedItem().toString()));
                RequestBody reqalamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (alamat.getText().toString().isEmpty()) ? "" :alamat.getText().toString());
                RequestBody reqUsername =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (username.getText().toString().isEmpty() ?"": username.getText().toString()));
                RequestBody reqPassword =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (password.getText().toString().isEmpty()?"":password.getText().toString()));
                Call<GetTempat> callUpdate = mApiInterface.putTempat(body, reqid_tempat, reqnama_toko, reqno_rekening, reqNohp, reqEmail, reqslogan_tempat, reqdeskripsi_tempat, reqstatus, reqalamat, reqUsername, reqPassword);
                callUpdate.enqueue(new Callback<GetTempat>() {
                    @Override
                    public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                        if (response.body().getStatus().equals("success")) {
                            Toast.makeText(EditTempatActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myIdentitasIntent = new Intent(getApplicationContext(), GaleriSevieActivity.class);
                            startActivity(myIdentitasIntent);
                        } else {
                            Toast.makeText(EditTempatActivity.this, "Gagal Edit Tempat Sewa", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<GetTempat> call, Throwable t) {

                    }
                });


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

                    Glide.with(mContext).load(new File(imagePath)).into(fotoTempat);

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

            Glide.with(mContext).load(new File(imagePath)).into(fotoTempat);
        }

    }
    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/tempat_sewa");

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

            Log.d("tempat_sewa", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("tempat_sewa", "error");
            e1.printStackTrace();
        }
        return "";
    }
}
