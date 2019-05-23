package com.example.sikostum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sikostum.Adapter.KomentarAdapter;
import com.example.sikostum.Adapter.RiwayatAdapter;
import com.example.sikostum.MODEL.GetKomentar;
import com.example.sikostum.MODEL.Komentar;
import com.example.sikostum.MODEL.Pemesanan;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKomentar extends AppCompatActivity {
    String id_user;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_komentar);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilKomentar();
    }
    public void tampilKomentar(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        retrofit2.Call<GetKomentar> mKomentar= mApiInterface.getKomentar(reqid_user);
        mKomentar.enqueue(new Callback<GetKomentar>() {
            @Override
            public void onResponse(retrofit2.Call<GetKomentar> call, Response<GetKomentar> response) {
                Log.d("Get Pemesanan", response.body().getStatus());
                List<Komentar> daftarKomentar= response.body().getResult();
                mAdapter = new KomentarAdapter(daftarKomentar);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(retrofit2.Call<GetKomentar> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
