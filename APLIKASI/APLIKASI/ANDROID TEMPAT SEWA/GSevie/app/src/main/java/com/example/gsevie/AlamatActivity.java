package com.example.gsevie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gsevie.Adapter.AlamatAdapter;
import com.example.gsevie.MODEL.Alamat;
import com.example.gsevie.MODEL.GetAlamat;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamatActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    TextView id_user;
    List<Alamat> daftarAlamat= new ArrayList<>();
    String code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);

        mContext = getApplicationContext();
        id_user = (TextView) findViewById(R.id.id_user);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final Intent mIntent = getIntent();
        id_user.setText(mIntent.getStringExtra("id_user"));
        tampilAlamat();
    }

    public void tampilAlamat(){
        final Intent mIntent = getIntent();
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"), mIntent.getStringExtra("id_user"));
        Call<GetAlamat> mAlamatCall = mApiInterface.getAlamat(reqid_user);
        mAlamatCall.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                Log.d("Get Alamat", response.body().getStatus());
                List<Alamat> daftarAlamat = response.body().getResult();
                mAdapter = new AlamatAdapter(daftarAlamat);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetAlamat> call, Throwable t) {
                Log.d("Get Alamat", t.getMessage());
            }
        });

    }
}
