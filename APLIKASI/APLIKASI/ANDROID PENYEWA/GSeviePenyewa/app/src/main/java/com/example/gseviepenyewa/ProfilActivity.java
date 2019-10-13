package com.example.gseviepenyewa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.GetProfilId;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {
    TextView nama, jenis_kelamin, email, noHp, username, password;
    ImageView foto;
    String id_user;
    APIInterface mAPIInterface;
    String url_photo, photoName;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        id_user = SaveSharedPreferences.getId(getApplicationContext());
        nama = (TextView) findViewById(R.id.namaUser);
        jenis_kelamin = (TextView) findViewById(R.id.jenisKelamin);
        email = (TextView) findViewById(R.id.emailUser);
        noHp = (TextView) findViewById(R.id.noHp);
        username = (TextView) findViewById(R.id.usernameUser);
        password = (TextView) findViewById(R.id.passwordUser);
        foto = (ImageView) findViewById(R.id.fotoUser);
        edit = (Button) findViewById(R.id.buttonEditUser);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent mIntent = new Intent(getApplicationContext(), EditProfilActivity.class);
            mIntent.putExtra("id_user", id_user);
            mIntent.putExtra("nama", nama.getText().toString());
            mIntent.putExtra("jenis_kelamin", jenis_kelamin.getText().toString());
            mIntent.putExtra("email", email.getText().toString());
            mIntent.putExtra("no_hp", noHp.getText().toString());
            mIntent.putExtra("username", username.getText().toString());
            mIntent.putExtra("password", password.getText().toString());
            mIntent.putExtra("foto_user", photoName);
            mIntent.putExtra("foto_user_url", url_photo);
            startActivity(mIntent);

            }
        });

        getData();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(ProfilActivity.this, MenuBerandaActivity.class));
        finish();
    }

    public void getData(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqId_user= MultipartBody.create(MediaType.parse("multipart/form-data"),(id_user));
        Call<GetProfilId> mUser = mAPIInterface.getMyProfile(reqId_user);
        mUser.enqueue(new Callback<GetProfilId>() {
            @Override
            public void onResponse(Call<GetProfilId> call, Response<GetProfilId> response) {
                if(response.body().getStatus().equals("success")){
                    nama.setText(response.body().getResult().get(0).getNama());
                    if (response.body().getResult().get(0).getJenis_kelamin().equals("P")){
                        jenis_kelamin.setText("Perempuan");
                    }else{
                        jenis_kelamin.setText("Laki-Laki");
                    }

                    email.setText(response.body().getResult().get(0).getEmail());
                    noHp.setText(response.body().getResult().get(0).getNo_hp());
                    username.setText(response.body().getResult().get(0).getUsername());
                    password.setText(response.body().getResult().get(0).getPassword());
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_user();
                    photoName = response.body().getResult().get(0).getFoto_user();

                    if (photoName.equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.user).into(foto);
                    }else{
                        Glide.with(getApplicationContext()).load(url_photo).into(foto);
                    }
                }else{
                    Toast.makeText(ProfilActivity.this, "Gagal medapatkan data user", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetProfilId> call, Throwable t) {

            }
        });
    }
}
