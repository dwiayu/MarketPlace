package com.example.sikostum;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sikostum.Adapter.AlamatAdapter;
import com.example.sikostum.Adapter.KostumAdapter;
import com.example.sikostum.MODEL.Alamat;
import com.example.sikostum.MODEL.GetKostum;
import com.example.sikostum.MODEL.Kostum;
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

public class DaftarKostum extends AppCompatActivity {
    Handler mHandler;
    Button tambahKostum;
    APIInterface mApiInterface;
    private FloatingActionButton fab;
    String id_user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kostum);
        fab=(FloatingActionButton) findViewById(R.id.tambahKostum);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilKostum();
//        this.mHandler= new Handler();
//        m_Runnable.run();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(),InsertKostumActivity.class);
                startActivity(mIntent);
            }
        });
    }
//    private final Runnable m_Runnable = new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(DaftarKostum.this,"in runnable ",Toast.LENGTH_SHORT).show();
//            DaftarKostum.this.mHandler.postDelayed(m_Runnable,20000);
//        }
//    };
    public void tampilKostum(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetKostum> mKostumCall = mApiInterface.tampilKostum(reqid_user);
        mKostumCall.enqueue(new Callback<GetKostum>() {
            @Override
            public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                Log.d("Get Kostum", response.body().getStatus());
                List<Kostum> daftarKostum = response.body().getResult();
                mAdapter = new KostumAdapter(daftarKostum);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKostum> call, Throwable t) {
                Log.d("Get Kostum", t.getMessage());
            }
        });
    }
}
