package com.example.penyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.penyewa.MODEL.GetPendaftaran;
import com.example.penyewa.REST.APIClient;
import com.example.penyewa.REST.APIInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {
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


                APIInterface mAPInterface = APIClient.getClient().create(APIInterface.class);
                MultipartBody.Part body = null;

                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ed_nama.getText().toString().isEmpty()) ? "" : ed_nama.getText().toString());
                RequestBody reqJenisKelamin = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        jenisKel);

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
                            openLogin();

                        }
                    }

                    @Override
                    public void onFailure(Call<GetPendaftaran> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Gagal", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void openLogin()
    {
        Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
        this.startActivity(intent);
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewLogin);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Sudah Memiliki Akun ? </font><font color='#03A9F4'>Login</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
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
}
