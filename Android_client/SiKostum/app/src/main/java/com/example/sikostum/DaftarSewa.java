package com.example.sikostum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sikostum.Adapter.PemesananAdapter;
import com.example.sikostum.Adapter.SewaAdapter;
import com.example.sikostum.MODEL.GetPemesanan;
import com.example.sikostum.MODEL.Pemesanan;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarSewa extends AppCompatActivity {
    String id_user;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_sewa);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilSewa();
    }
    private void tampilSewa(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetPemesanan> mPemesananCall = mApiInterface.getSewa(reqid_user);
        mPemesananCall.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                Log.d("Get Pemesanan", response.body().getStatus());
                List<Pemesanan> daftarSewa= response.body().getResult();
                mAdapter = new SewaAdapter(daftarSewa);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                Log.d("Get Kostum", t.getMessage());
            }
        });

    }
}