package com.example.penyewa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.penyewa.MODEL.GetEditProfil;
import com.example.penyewa.REST.APIClient;
import com.example.penyewa.REST.APIInterface;
import com.example.penyewa.Utils.SaveSharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class EditProfilActivity extends AppCompatActivity {
    EditText edt_nama, edt_noHp,edt_email, edt_username, edt_password, edtidUser;
    RadioButton rp,rl;
    RadioGroup rg;
    Button camera, edit;
    String id_user;
    ImageView foto_user;
    String imagePath="";
    String jenisKel;
    Context mContext;
    String fileNamePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);

        edt_nama = (EditText) findViewById(R.id.nama);
        foto_user= (ImageView) findViewById(R.id.foto_user);
        edt_noHp = (EditText) findViewById(R.id.no_hp);
        edt_email =(EditText) findViewById(R.id.email);
        edt_username = (EditText) findViewById(R.id.username);
        edt_password = (EditText) findViewById(R.id.password);
        camera = (Button) findViewById(R.id.buttonPilihGambar);
        edit = (Button) findViewById(R.id.buttonEdit) ;

        id_user = SaveSharedPreferences.getId(getApplicationContext());

        Intent mIntent = getIntent();

        edt_nama .setText(mIntent.getStringExtra("nama"));
        edt_noHp.setText(mIntent.getStringExtra("no_hp"));
        edt_email.setText(mIntent.getStringExtra("email"));
        edt_username .setText(mIntent.getStringExtra("username"));
        edt_password.setText(mIntent.getStringExtra("password"));
        imagePath= mIntent.getStringExtra("foto_user_url");
        fileNamePhoto = mIntent.getStringExtra("foto_user");

        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(imagePath).into(foto_user);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_person_black_24dp).into(foto_user);
        }

        imagePath = mIntent.getStringExtra("foto_user_url");
        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MultipartBody.Part body = null;
                if ((!imagePath.contains("uploads/" + fileNamePhoto)) &&
                        (imagePath.length()>0)){
                    //File creating from selected URL
                    File file = new File(imagePath);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("foto_user", file.getName(),
                            requestFile);
                }
                RequestBody reqid_user =
                        MultipartBody.create(parse("multipart/form-data"),
                                id_user);
                RequestBody reqnama =
                        MultipartBody.create(parse("multipart/form-data"),
                                (edt_nama.getText().toString().isEmpty())?
                                        "" : edt_nama.getText().toString());
//                RequestBody reqjenis_kelamin =
//                        MultipartBody.create(MediaType.parse("multipart/form-data"),
//                                (jenisKel));
                RequestBody reqno_hp =
                        MultipartBody.create(parse("multipart/form-data"),
                                (edt_noHp.getText().toString().isEmpty())?
                                        "" : edt_noHp.getText().toString());
                RequestBody reqemail =
                        MultipartBody.create(parse("multipart/form-data"),
                                (edt_email.getText().toString().isEmpty())?
                                        "" : edt_email.getText().toString());
                RequestBody requsername =
                        MultipartBody.create(parse("multipart/form-data"),
                                (edt_username.getText().toString().isEmpty())?
                                        "" : edt_username.getText().toString());
                RequestBody reqpassword =
                        MultipartBody.create(parse("multipart/form-data"),
                                (edt_password.getText().toString().isEmpty())?
                                        "" : edt_password.getText().toString());


                Call<GetEditProfil> callUpdate = mApiInterface.postEditProfil(body,reqid_user, reqnama,
                        reqno_hp, reqemail, requsername, reqpassword);

                callUpdate.enqueue(new Callback<GetEditProfil>() {
                    @Override
                    public void onResponse(Call<GetEditProfil> call, Response<GetEditProfil> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditProfilActivity.this, "Gagal Edit Profil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditProfilActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myProfileIntent = new Intent(getApplicationContext(), BerandaActivity.class);
                            startActivity(myProfileIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetEditProfil> call, Throwable t) {
                        //Log.d("Update Retrofit ", t.getMessage());
                        Toast.makeText(EditProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih foto untuk " +
                        "di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });



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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfilActivity.this);
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
        if (resultCode == RESULT_OK && requestCode == 10) {
            if (data == null) {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                //Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
                Glide.with(getApplicationContext()).load(new File(imagePath)).into(foto_user);
                cursor.close();
            } else {
                Toast.makeText(getApplicationContext(), "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }

    }

    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/kostum");

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

            Log.d("Foto", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}
