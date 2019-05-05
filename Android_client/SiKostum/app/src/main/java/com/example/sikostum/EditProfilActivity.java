package com.example.sikostum;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostum.MODEL.GetEditProfil;
import com.example.sikostum.MODEL.GetProfilId;
import com.example.sikostum.MODEL.ProfilId;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;

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

import static java.security.AccessController.getContext;

public class EditProfilActivity extends AppCompatActivity {
    EditText edt_nama, edt_noHp,edt_email, edt_username, edt_password, edtidUser;
    RadioButton rp,rl;
    RadioGroup rg;
    Button camera, edit;
    int id_user;
    ImageView foto_user;
    String image_path="";
    String jenisKel;
    Context mContext;

    
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


        edtidUser = (EditText) findViewById(R.id.id_user);
        edt_nama = (EditText) findViewById(R.id.nama);
//        rg = (RadioGroup) findViewById(R.id.radioGroupGender);
//        rp = (RadioButton) findViewById(R.id.radioPerempuan);
//        rl = (RadioButton) findViewById(R.id.radioLelaki);
        foto_user= (ImageView) findViewById(R.id.foto_user);
        edt_noHp = (EditText) findViewById(R.id.no_hp);
        edt_email =(EditText) findViewById(R.id.email);
        edt_username = (EditText) findViewById(R.id.username);
        edt_password = (EditText) findViewById(R.id.password);
        camera = (Button) findViewById(R.id.buttonPilihGambar);
        edit = (Button) findViewById(R.id.buttonEdit) ;
        id_user =Integer.parseInt(SaveSharedPreferences.getId(getApplicationContext()));


        Intent mIntent = getIntent();
//        id_user = mIntent.getStringExtra("id_user");
//        edtidUser.setText(mIntent.getStringExtra("id_user"));
        edt_nama .setText(mIntent.getStringExtra("nama"));
        edt_noHp.setText(mIntent.getStringExtra("no_hp"));
        edt_email.setText(mIntent.getStringExtra("email"));
        edt_username .setText(mIntent.getStringExtra("username"));
        edt_password.setText(mIntent.getStringExtra("password"));
        image_path= mIntent.getStringExtra("foto_user");


        if (mIntent.getStringExtra("foto_user") != null) {
            Glide.with(mContext).load(APIClient
                    .BASE_URL + mIntent.getStringExtra("foto_user")).into(foto_user);
        }
        else {
//            Glide.with(getContext().)
            Glide.with(getApplicationContext()).load(R.drawable.ic_person_black_24dp).into(foto_user);
        }
        image_path = mIntent.getStringExtra("foto_user");

        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
//        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
//        sView.setVerticalScrollBarEnabled(false);
//        sView.setHorizontalScrollBarEnabled(false);
//
//        ActionBar menu = getSupportActionBar();
//        menu.setDisplayHomeAsUpEnabled(true);
//        menu.setDisplayShowHomeEnabled(true);
//
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                int selectedId = rg.getCheckedRadioButtonId();
////                final RadioButton radioButton = (RadioButton) findViewById(selectedId);
//                if(selectedId == rl.getId()){
//                    jenisKel = "L";
//                }
//                else{
//                    jenisKel ="P";
//                }
                MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
//                if ((!image_path.contains("uploads/" )) &&
//                        (image_path.length() > 0)) {
                    //File creating from selected URL
//                    File file = new File(image_path);

                    // create RequestBody instance from file
//                    RequestBody requestFile = RequestBody.create(
//                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
//                    body = MultipartBody.Part.createFormData("foto_user", file.getName(),
//                            requestFile);
//                }
//                RequestBody reqid_user =
////                        MultipartBody.create(MediaType.parse("multipart/form-data"),
////                                (id_user));
                RequestBody reqnama =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edt_nama.getText().toString().isEmpty())?
                                        "" : edt_nama.getText().toString());
//                RequestBody reqjenis_kelamin =
//                        MultipartBody.create(MediaType.parse("multipart/form-data"),
//                                (jenisKel));
                RequestBody reqno_hp =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edt_noHp.getText().toString().isEmpty())?
                                        "" : edt_noHp.getText().toString());
                RequestBody reqemail =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edt_email.getText().toString().isEmpty())?
                                        "" : edt_email.getText().toString());
                RequestBody requsername =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edt_username.getText().toString().isEmpty())?
                                        "" : edt_username.getText().toString());
                RequestBody reqpassword =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edt_password.getText().toString().isEmpty())?
                                        "" : edt_password.getText().toString());
                Call<GetEditProfil> callUpdate = mApiInterface.postEditProfil(body,  reqnama,
                        reqno_hp, reqemail, requsername, reqpassword);
                callUpdate.enqueue(new Callback<GetEditProfil>() {
                    @Override
                    public void onResponse(Call<GetEditProfil> call, Response<GetEditProfil> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditProfilActivity.this, "Gagal Edit Profil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditProfilActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myProfileIntent = new Intent(getApplicationContext(), ProfilFragment.class);
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
                mintaPermissions();
            }
        });


    }

    private void mintaPermissions() {

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
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        // Jika request berasal dari Gallery
        if (requestCode == 13) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    image_path = simpanImage(bitmap);
                    Toast.makeText(mContext, "Foto berhasil di-load!", Toast.LENGTH_SHORT).show();

                    Glide.with(mContext).load(new File(image_path)).into(foto_user);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }
            }

            // Jika request dari Camera
        } else if (requestCode == 16) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            image_path = simpanImage(thumbnail);
            Toast.makeText(mContext, "Foto berhasil di-load dari Camera!", Toast.LENGTH_SHORT)
                    .show();

            Glide.with(mContext).load(new File(image_path)).into(foto_user);
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
            Log.d("PRAKTIKUM", "erroraaaaa");
            e1.printStackTrace();
        }
        return "";
    }



}
