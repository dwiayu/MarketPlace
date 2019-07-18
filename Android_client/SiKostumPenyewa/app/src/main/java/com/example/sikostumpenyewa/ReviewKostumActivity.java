package com.example.sikostumpenyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumpenyewa.Adapter.ReviewAdapter;
import com.example.sikostumpenyewa.MODEL.GetKomentar;
import com.example.sikostumpenyewa.MODEL.GetTempat;
import com.example.sikostumpenyewa.MODEL.Komentar;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIInterface;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewKostumActivity extends AppCompatActivity {
    Context mContext;
    TextView id_kostum;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    TextView nama_tempat, alamat_tempat,no_hp,email, slogan,deskripsi,status_tempat,namaFoto;
    ImageView foto_tempat;
    String url_photo,photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_kostum);
        mContext = getApplicationContext();
        id_kostum= (TextView) findViewById(R.id.id_kostum);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        nama_tempat = (TextView) findViewById(R.id.namatempat);
        alamat_tempat =(TextView) findViewById(R.id.alamat);
        no_hp= (TextView) findViewById(R.id.nohp);
        email =(TextView)findViewById(R.id.email);
        slogan = (TextView) findViewById(R.id.slogan);
        deskripsi=(TextView) findViewById(R.id.deskripsi);
        status_tempat =(TextView) findViewById(R.id.status);
        foto_tempat =(ImageView) findViewById(R.id.fototempat);

        tampilKomentar();
    }
    public  void tampilKomentar(){
        final Intent mIntent= getIntent();
        id_kostum.setText(mIntent.getStringExtra("id_kostum"));

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_kostum = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (mIntent.getStringExtra("id_kostum")));
        Call<GetKomentar> mKomentar = mApiInterface.getReview(reqid_kostum);
        mKomentar.enqueue(new Callback<GetKomentar>() {
            @Override
            public void onResponse(Call<GetKomentar> call, Response<GetKomentar> response) {
                Log.d("Get Komentar", response.body().getStatus());
                List<Komentar> daftarReview = response.body().getResult();
                mAdapter = new ReviewAdapter(daftarReview);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKomentar> call, Throwable t) {

            }
        });
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (mIntent.getStringExtra("id_kostum")));
        Call<GetTempat> mTempat = mApiInterface.getDetailTempat(reqid);
        mTempat.enqueue(new Callback<GetTempat>() {
            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {

                nama_tempat.setText(response.body().getResult().get(0).getNama_tempat());
                alamat_tempat.setText(response.body().getResult().get(0).getAlamat());
                no_hp.setText(response.body().getResult().get(0).getNo_hp());
                email.setText(response.body().getResult().get(0).getEmail());
                slogan.setText(response.body().getResult().get(0).getSlogan_tempat());
                deskripsi.setText(response.body().getResult().get(0).getDeskripsi_tempat());
                status_tempat.setText(response.body().getResult().get(0).getStatus_tempat());
                url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
                photoName = response.body().getResult().get(0).getFoto_tempat();

                if (photoName != null){
                    Glide.with(getApplicationContext()).load(url_photo).into(foto_tempat);
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.tempat_icon).into(foto_tempat);
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
