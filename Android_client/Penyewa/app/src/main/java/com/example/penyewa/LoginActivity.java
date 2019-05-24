package com.example.penyewa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.penyewa.MODEL.GetLogin;
import com.example.penyewa.REST.APIClient;
import com.example.penyewa.REST.APIInterface;
import com.example.penyewa.Utils.SaveSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initCreateAccountTextView();

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        // Check if UserResponse is Already Logged In
        if(SaveSharedPreferences.getLoggedStatusPY(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
            startActivity(intent);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //cek login
                if (!username.isEmpty() && !password.isEmpty()){
                    // Lakukan login
                    doLogin(username, password);
                }else {
                    // Notif user
                    Toast.makeText(getApplicationContext(),
                            "Isikan username dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private boolean isLoggedIn() {
        // Cek apakah ada shared pref login
        SharedPreferences pref = getSharedPreferences("SiKostumLoginData", MODE_PRIVATE);
        return pref.getBoolean("isloggedin", false);
    }

    private void openHome()
    {
        Intent intent = new Intent(this.getApplicationContext(), BerandaActivity.class);
        this.startActivity(intent);
    }

    private void saveLogin(String username, String id_user)
    {
        // Simpan data login ke shared pref
        SharedPreferences sharedpref = getSharedPreferences("SiKostumLoginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();

        // Simpan isloggedin true berupa boolean
        editor.putBoolean("isloggedin", true);
        // Simpan data lainnya berupa string
        editor.putString("username", username);
        editor.putString("id_user", id_user);
        editor.apply();
    }

    // Untuk proses login menggunakan REST
    private void doLogin(final String username,final String password){
        // Panggil request Api
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call loginCall = apiInterface.loginPenyewa(username, password);

        loginCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // Jika request sukses
                if (response.isSuccessful()) {

                    // Buat object model GetLogin dari response
                    GetLogin loginobject = (GetLogin) response.body();
                    String id_user = loginobject.getResult();

                    // Jika status = success (sesuai respon dari REST server)
                    if (loginobject.getStatus().equals("Penyewa")) {

                        // Simpan data email user ke sharedpref
                        SaveSharedPreferences.setLoggedInPY(getApplicationContext(), true);
                        SaveSharedPreferences.setId(getApplicationContext(), id_user);
                        // Buka layar home
                        openHome();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error! Coba lagi!",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                // Jika gagal, beri notif
                Toast.makeText(LoginActivity.this, "Gagal:" + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


            private void initCreateAccountTextView() {
                TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
                textViewCreateAccount.setText(fromHtml("<font color='#000000'>Belum memiliki Akun ? </font><font color='#03A9F4'>Daftar</font>"));
                textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
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