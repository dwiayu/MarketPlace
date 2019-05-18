package com.example.sikostum;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sikostum.Adapter.AlamatAdapter;
import com.example.sikostum.MODEL.Alamat;
import com.example.sikostum.MODEL.GetAlamat;
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

public class ListAlamat extends AppCompatActivity {
    private FloatingActionButton fab;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    APIInterface mAPIInterface;
    Context mContext;
    Button btGet;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alamat);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mContext =getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet = (Button) findViewById(R.id.btGet);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        final APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);
        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"), SaveSharedPreferences.getId(getApplicationContext()));


               Call<GetAlamat> mAlamatCall = mAPIInterface.getAlamat(reqid_user);
                mAlamatCall.enqueue(new Callback<GetAlamat>() {
                    @Override
                    public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                        Log.d("Get Alamat", response.body().getStatus());
                        List<Alamat> listAlamat = response.body().getResult();
                        mAdapter = new AlamatAdapter(listAlamat);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<GetAlamat> call, Throwable t) {
                        Log.d("Get Alamat", t.getMessage());
                    }
                });
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertAlamat.class);
                startActivity(intent);
            }
        });
    }
}
