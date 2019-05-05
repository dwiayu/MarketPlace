package com.example.sikostum;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostum.MODEL.GetPendaftaran;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;


public class daftar extends AppCompatActivity  {
    Context mContext;
    EditText ed_nama, ed_hp, ed_email, ed_username, ed_password;
    RadioGroup mRadioGroup;
    RadioButton lk, pr;
    String jenisKel;

    Button daftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        initCreateAccountTextView();

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        mContext = getApplicationContext();
        ed_nama = (EditText) findViewById(R.id.editTextNama);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        ed_hp =(EditText) findViewById(R.id.editTextNoHp);
        ed_email = (EditText) findViewById(R.id.editTextEmail);
        ed_username = (EditText) findViewById(R.id.editTextusername);
        ed_password = (EditText) findViewById(R.id.editTextPassword);
        daftar = (Button) findViewById(R.id.buttonDaftar);
        lk = (RadioButton) findViewById(R.id.radioLelaki);
        pr = (RadioButton) findViewById(R.id.radioPerempuan);

//






        daftar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
//                final RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if(selectedId == pr.getId()){
                    jenisKel = "L";
                }
                else{
                    jenisKel ="P";
                }
//                final String mRadioGroup = jenisKel;
//                Log.d("JK", radioButton.getText().toString());

                APIInterface mAPInterface = APIClient.getClient().create(APIInterface.class);
                MultipartBody.Part body = null;

                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ed_nama.getText().toString().isEmpty()) ? "" : ed_nama.getText().toString());
                RequestBody reqJenisKelamin = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        jenisKel);
//                RequestBody reqJenisKelamin = MultipartBody.create(MediaType.parse("multipart/form-data"),
//                        radioButton.getText().toString());
                RequestBody reqEmail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ed_email.getText().toString().isEmpty()) ? "" : ed_email.getText().toString());
                RequestBody reqNoHp = MultipartBody.create(MediaType.parse("mulitpart/form-data"),
                        (ed_hp.getText().toString().isEmpty()) ?"" : ed_hp.getText().toString());

                RequestBody reqUsername= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ed_username.getText().toString().isEmpty()) ? "": ed_username.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ed_password.getText().toString().isEmpty()) ? "" : ed_password.getText(). toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetPendaftaran> mPendaftaranCall = mAPInterface.postPendaftaran(body, reqNama,reqJenisKelamin,reqEmail, reqNoHp,
                        reqUsername, reqPassword, reqAction);
                mPendaftaranCall.enqueue(new Callback<GetPendaftaran>() {
                    @Override
                    public void onResponse(Call<GetPendaftaran> call, Response<GetPendaftaran> response) {
                        if (response.body().getStatus().equals("failed")) {
                            Toast.makeText(getApplicationContext(),"Gagal Daftar", Toast.LENGTH_LONG).show();

                        }
                        else{
                            String detail = "\n" +
                                    "id_user = " + response.body().getResult().get(0).getIdUser() + "\n" +
                                    "nama = " + response.body().getResult().get(0).getNama() + "\n" +
                                    "jenis_kelamin = " + response.body().getResult().get(0).getJenisKelamin() +
                                    "no_hp = " +response.body().getResult().get(0).getNoHp()+
                                    "foto_user = "+response.body().getResult().get(0).getFotoUser()+
                                    "email = " +response.body().getResult().get(0).getEmail()+
                                    "username = "+response.body().getResult().get(0).getUsername()+
                                    "password = "+response.body().getResult().get(0).getPassword()+
                                    "\n";
                            Toast.makeText(getApplicationContext(),"Pendaftaran Berhasil", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetPendaftaran> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Gagal", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
//        bt_foto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent galleryIntent = new Intent();
//                galleryIntent.setType("image/*");
//                galleryIntent.setAction(Intent.ACTION_PICK);
//                Intent intentChoose = Intent.createChooser(
//                        galleryIntent,
//                        "Pilih foto untuk di-upload");
//                startActivityForResult(intentChoose, 10);
//            }
//        });
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewLogin);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Sudah Memiliki Akun ? </font><font color='#03A9F4'>Login</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(daftar.this, activity_login.class);
                startActivity(intent);
            }
        });
    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode ==10){
//            if (data==null){
//                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imagePath =cursor.getString(columnIndex);
//
//                Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
////                Glide.with(mContext).load(new File(imagePath)).into(mImageView);
//                cursor.close();
//            }else{
//                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }



}


