package com.example.sikostum;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostum.MODEL.GetTempat;
import com.example.sikostum.MODEL.Tempat;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TempatSewaActivity extends AppCompatActivity {
    private Button editTempat;
    Button insertTempat;
    APIInterface mAPIInterface;
    TextView tvIdTempat,tvNorek, tvNamaToko, tvAlamat,tvSlogan, tvDeskripsi,tvStatus, tvIzin;
    String id_user;
    String url_photo, photoName;
    ImageView fotoT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_sewa);

        //inisial tombol
        editTempat= (Button)findViewById(R.id.buttonEditTempat);
        insertTempat= (Button) findViewById(R.id.buttonInsTempat);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tvIdTempat = (TextView) findViewById(R.id.tvIdToko);
        fotoT = (ImageView) findViewById(R.id.fotoT);
        tvNamaToko =(TextView) findViewById(R.id.tvNamaTempat);
        tvAlamat=(TextView) findViewById(R.id.tvAlamatTem);
        tvSlogan =(TextView) findViewById(R.id.tvSloganTem);
        tvDeskripsi =(TextView) findViewById(R.id.tvDeskripsiTem);
        tvStatus = (TextView) findViewById(R.id.tvStatusTem);
        tvNorek=(TextView) findViewById(R.id.tvNoRek);
        tvIzin = (TextView) findViewById(R.id.tvIzinTem);
        getData();
        // function tombol
        editTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), EditTempatActivity.class);
                mIntent.putExtra("id_tempat", tvIdTempat.getText().toString()) ;
                mIntent.putExtra("nama_tempat", tvNamaToko.getText().toString());
                mIntent.putExtra("alamat_tempat", tvAlamat.getText().toString());
                mIntent.putExtra("no_rekening", tvNorek.getText().toString());
                mIntent.putExtra("slogan_tempat", tvSlogan.getText().toString());
                mIntent.putExtra("deskripsi_tempat", tvDeskripsi.getText().toString());
                mIntent.putExtra("status",tvStatus.getText().toString());
                mIntent.putExtra("foto_tempat", photoName);
                mIntent.putExtra("foto_tempat_url", url_photo);
                startActivity(mIntent);
            }
        });
        insertTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(),InsertTempatSewa.class);
                startActivity(mIntent);
            }
        });

        ScrollView sView = (ScrollView) findViewById(R.id.kelola_tempat);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
    }
    public void getData(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));
        retrofit2.Call<GetTempat> mTempatCall = mAPIInterface.getTempat(reqid_user);
        mTempatCall.enqueue(new retrofit2.Callback<GetTempat>() {
            @Override
            public void onResponse(retrofit2.Call<GetTempat> call, retrofit2.Response<GetTempat> response) {
                Log.d("My Tempat Sewa", response.body().getStatus());
                if(response.body().getStatus().equals("success")){
                    insertTempat.setEnabled(false);
                    editTempat.setEnabled(true);
                    tvIdTempat.setText(response.body().getResult().get(0).getId_tempat());
                    tvNamaToko.setText(response.body().getResult().get(0).getNama_tempat());
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
                    photoName = response.body().getResult().get(0).getFoto_tempat();
                    if (photoName != null){
                        Glide.with(getApplicationContext()).load(url_photo).into(fotoT);
                    } else {
                        Glide.with(getApplicationContext()).load(R.drawable.kostum_icon).into(fotoT);
                    }
                    tvAlamat.setText(response.body().getResult().get(0).getAlamat());
                    tvSlogan.setText(response.body().getResult().get(0).getSlogan_tempat());
                    tvDeskripsi.setText(response.body().getResult().get(0).getDeskripsi_tempat());
                    tvStatus.setText(response.body().getResult().get(0).getStatus_tempat());
                    tvIzin.setText(response.body().getResult().get(0).getIzin());
                    tvNorek.setText(response.body().getResult().get(0).getNo_rekening());
                }else if(response.body(). getStatus().equals("kosong")){
                    insertTempat.setEnabled(true);
                    editTempat.setEnabled(false);
                    tvIdTempat.setText("kosong");
                    tvNamaToko.setText("kosong");
                    Glide.with(getApplicationContext()).load(R.drawable.kostum_icon).into(fotoT);
                    tvAlamat.setText("kosong");
                    tvSlogan.setText("kosong");
                    tvDeskripsi.setText("kosong");
                    tvStatus.setText("kosong");
                    tvIzin.setText("kosong");
                }  else{
                    Toast.makeText(TempatSewaActivity.this, "Gagal mendapatkan foto Toko", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetTempat> call, Throwable t) {

            }
        });
    }
}
