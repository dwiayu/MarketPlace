package com.example.gseviepenyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.Adapter.ReviewKostumAdapter;
import com.example.gseviepenyewa.MODEL.GetKomentar;
import com.example.gseviepenyewa.MODEL.Komentar;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_kostum);

        mContext = getApplicationContext();
        id_kostum= (TextView) findViewById(R.id.id_kostum);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

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
                mAdapter = new ReviewKostumAdapter(daftarReview);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKomentar> call, Throwable t) {

            }
        });

    }
}
