package com.example.gsevie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.GetTempat;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class GaleriSevieActivity extends AppCompatActivity {
    private Button editTempat;
    Button insertTempat;
    APIInterface mAPIInterface;
    TextView idTempat, idAlamat, namaTempat, noRekening, alamatTempat,sloganTempat,
            deskripsiTempat,statusTempat,username, password,no_hp, email ;
    String id_tempat;
    String url_photo, photoName;
    ImageView fotoTempat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri_sevie);

        //inisial tombol
        editTempat =(Button)findViewById(R.id.buttonEditTempat);
        insertTempat = (Button) findViewById(R.id.buttonInsertTempat);
        id_tempat = SaveSharedPreferences.getId(getApplicationContext());
        idTempat =(TextView)findViewById(R.id.idTempat);
        noRekening = (TextView) findViewById(R.id.noRekening);
        fotoTempat = (ImageView) findViewById(R.id.fotoTempat);
        namaTempat = (TextView) findViewById(R.id.namaTempat);
        alamatTempat = (TextView) findViewById(R.id.alamatTempat);
        sloganTempat = (TextView) findViewById(R.id.sloganTempat);
        deskripsiTempat = (TextView) findViewById(R.id.deskripsiTempat);
        statusTempat = (TextView) findViewById(R.id.statusTempat);
        idAlamat = (TextView) findViewById(R.id.idAlamatku);
        no_hp =(TextView) findViewById(R.id.noHp);
        email = (TextView) findViewById(R.id.email);
        username =(TextView) findViewById(R.id.username);
        password =(TextView) findViewById(R.id.password);
        getTempat();
        editTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), EditTempatActivity.class);
                mIntent.putExtra("id_tempat", idTempat.getText().toString());
                mIntent.putExtra("nama_tempat", namaTempat.getText().toString());
                mIntent.putExtra("no_rekening", noRekening.getText().toString());
                mIntent.putExtra("alamat_tempat", alamatTempat.getText().toString());
                mIntent.putExtra("username", username.getText().toString());
                mIntent.putExtra("password", password.getText().toString());
                mIntent.putExtra("email", email.getText().toString());
                mIntent.putExtra("no_hp", no_hp.getText().toString());
                mIntent.putExtra("slogan_tempat", sloganTempat.getText().toString());
                mIntent.putExtra("deskripsi_tempat", deskripsiTempat.getText().toString());
                mIntent.putExtra("status", statusTempat.getText().toString());
                mIntent.putExtra("foto_tempat", photoName);
                mIntent.putExtra("foto_tempat_url", url_photo);
                startActivity(mIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(GaleriSevieActivity.this, MenuBerandaActivity.class));
        finish();
    }


    public void getTempat(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),(id_tempat));
        retrofit2.Call<GetTempat> mTempatCall = mAPIInterface.getTempat(reqid_user);
        mTempatCall.enqueue(new retrofit2.Callback<GetTempat>(){

            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                Log.d("My Tempat Sewa", response.body().getStatus());
                if (response.body().getStatus().equals("success")){
                    idTempat.setText(response.body().getResult().get(0).getId_tempat());
                    namaTempat.setText(response.body().getResult().get(0).getNama_tempat());
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
                    photoName = response.body().getResult().get(0).getFoto_tempat();
                    if (photoName.equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.galeri_icon).into(fotoTempat);
                    }else{
                        Glide.with(getApplicationContext()).load(url_photo).into(fotoTempat);
                    }
                    noRekening.setText(response.body().getResult().get(0).getNo_rekening());
                    idAlamat.setText(response.body().getResult().get(0).getAlamat());
                    alamatTempat.setText(response.body().getResult().get(0).getAlamat());
                    sloganTempat.setText(response.body().getResult().get(0).getSlogan_tempat());
                    deskripsiTempat.setText(response.body().getResult().get(0).getDeskripsi_tempat());
                    statusTempat.setText(response.body().getResult().get(0).getStatus_tempat());
                    no_hp.setText(response.body().getResult().get(0).getNo_hp());
                    email.setText(response.body().getResult().get(0).getEmail());
                    username.setText(response.body().getResult().get(0).getUsername());
                    password.setText(response.body().getResult().get(0).getPassword());
                }else if(response.body().getStatus().equals("kosong")){
                    idTempat.setText(" ");
                    namaTempat.setText("Nama Tempat Sewa ");
                    Glide.with(getApplicationContext()).load(R.drawable.galeri_icon).into(fotoTempat);


                }else{
                    Toast.makeText(GaleriSevieActivity.this, "Gagal medapatkan data tempat sewa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {

            }
        });
    }
}
