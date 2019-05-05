package com.example.sikostum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostum.MODEL.GetLogin;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.PreferencesUtility;
import com.example.sikostum.Utils.SaveSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Html.fromHtml;

public class activity_login extends AppCompatActivity {
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
        if(SaveSharedPreferences.getLoggedStatusTS(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), beranda.class);
            startActivity(intent);
            finish();
        }else if(SaveSharedPreferences.getLoggedStatusPY(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), Penyewa.class);
            startActivity(intent);
            finish();
        }

//        if(this.isLoggedIn()) {
//            Intent intent = new Intent(this.getApplicationContext(), beranda.class);
//            this.startActivity(intent);
//        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Cek form login
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Lakukan login
                    doLogin(username, password);
                } else {
                    // Notif user
                    Toast.makeText(getApplicationContext(),
                            "Isikan username dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }
    private boolean isLoggedIn()
    {
        // Cek apakah ada shared pref login
        SharedPreferences pref = getSharedPreferences("SiKostumLoginData", MODE_PRIVATE);
        return pref.getBoolean("isloggedin", false);

    }
    private void openHome()
    {
        Intent intent = new Intent(this.getApplicationContext(), beranda.class);
        this.startActivity(intent);
    }
    private void openPenyewa(){
        Intent intent = new Intent(this.getApplicationContext(), Penyewa.class);
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
        Call loginCall = apiInterface.loginToko(username, password);

        loginCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // Jika request sukses
                if(response.isSuccessful()){

                    // Buat object model GetLogin dari response
                    GetLogin loginobject = (GetLogin) response.body();
                    String id_user = loginobject.getResult();

                    // Jika status = success (sesuai respon dari REST server)
                    if(loginobject.getStatus().equals("Tempat Sewa")){

                        // Simpan data email user ke sharedpref
                        SaveSharedPreferences.setLoggedInTS(getApplicationContext(), true);
                        SaveSharedPreferences.setId(getApplicationContext(),id_user);
                        // Buka layar home
                        openHome();

                    }
                    else if(loginobject.getStatus().equals("Penyewa")){
                        SaveSharedPreferences.setLoggedInPY(getApplicationContext(), true);
                        SaveSharedPreferences.setId(getApplicationContext(),id_user);
                        openPenyewa();

                    }else {
                        Toast.makeText(activity_login.this,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity_login.this,
                            "Error! Coba lagi!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Jika gagal, beri notif
                Toast.makeText(activity_login.this, "Gagal:" + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Belum memiliki Akun ? </font><font color='#03A9F4'>Daftar</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_login.this, daftar.class);
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
