package com.example.gseviepenyewa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.GetTempat;
import com.example.gseviepenyewa.MODEL.TempatSewa;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TempatSewaActivity extends AppCompatActivity {
    TextView namaTempat, sloganTempat, deskripsiTempat, noRekening, noHpTempat, emailTempat, statusTempat, alamatTempat;
    ImageView foto;
    Button maps;
    APIInterface mAPIInterface;
    String url_photo, photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_sewa);

        namaTempat = (TextView) findViewById(R.id.namaTempat);
        sloganTempat = (TextView) findViewById(R.id.sloganTempat);
        deskripsiTempat = (TextView) findViewById(R.id.deskripsiTempat);
        noRekening = (TextView) findViewById(R.id.noRekening);
        noHpTempat = (TextView) findViewById(R.id.noHpTempat);
        emailTempat = (TextView) findViewById(R.id.emailTempat);
        statusTempat = (TextView) findViewById(R.id.statusTempat);
        alamatTempat = (TextView) findViewById(R.id.alamatTempat);
        foto = (ImageView) findViewById(R.id.fotoTempat);
        maps = (Button) findViewById(R.id.btn_navigasi);
        getData();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(TempatSewaActivity.this, MenuBerandaActivity.class));
        finish();
    }

    public void getData() {
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetTempat> mTempat = mAPIInterface.getTempatSewa();
        mTempat.enqueue(new Callback<GetTempat>() {
            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                if(response.body().getStatus().equals("success")) {

                namaTempat.setText(response.body().getResult().get(0).getNama_tempat());
                sloganTempat.setText(response.body().getResult().get(0).getSlogan_tempat());
                deskripsiTempat.setText(response.body().getResult().get(0).getDeskripsi_tempat());
                noRekening.setText(response.body().getResult().get(0).getNo_rekening());
                noHpTempat.setText(response.body().getResult().get(0).getNo_hpTempat());
                emailTempat.setText(response.body().getResult().get(0).getEmailTempat());
                statusTempat.setText(response.body().getResult().get(0).getStatus_tempat());
                alamatTempat.setText(response.body().getResult().get(0).getAlamat());
                final String googleMaps = "com.google.android.apps.maps";
                final String alamat = response.body().getResult().get(0).getAlamat() ;
                maps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Uri gmmIntentUri = Uri.parse("google.navigation:q="+alamat);
                        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage(googleMaps);
                        v.getContext().startActivity(mapIntent);
                    }
                });


                url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
                photoName = response.body().getResult().get(0).getFoto_tempat();

                    if (photoName.equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.splash_user).into(foto);
                    }else{
                        Glide.with(getApplicationContext()).load(url_photo).into(foto);
                    }
                }else{
                    Toast.makeText(TempatSewaActivity.this, "Gagal mendapatkan data tempat sewa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {

            }
        });
    }
}
