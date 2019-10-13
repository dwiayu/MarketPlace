package com.example.gsevie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gsevie.Adapter.RiwayatOffAdapter;
import com.example.gsevie.MODEL.GetPesanOff;
import com.example.gsevie.MODEL.Kostum;
import com.example.gsevie.MODEL.PesanOff;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatOfflineActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    TextView datakosong;

    public RiwayatOffAdapter myAdapter;
    List<PesanOff> listRiwayat= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_offline);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Riwayat");
        setSupportActionBar(toolbar);

        datakosong =(TextView) findViewById(R.id.datakosong);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        tampilRiwayatOff();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(RiwayatOfflineActivity.this, MenuBerandaActivity.class));
        finish();
    }


    private void tampilRiwayatOff(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<GetPesanOff>mPesanOff = mApiInterface.getRiwayatOff();
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        mPesanOff.enqueue(new Callback<GetPesanOff>() {
            @Override
            public void onResponse(retrofit2.Call<GetPesanOff> call, Response<GetPesanOff> response) {
                if(response.body().getStatus().equals("success")){
                    simpleProgressBar.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    listRiwayat= response.body().getResult();
                    myAdapter = new RiwayatOffAdapter(listRiwayat);
                    mRecyclerView.setAdapter(myAdapter);
                }else{
                    datakosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<GetPesanOff> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cari sesuatu....");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange( String s) {
                ArrayList<PesanOff> dataFilter= new ArrayList<>();
                for( PesanOff data : listRiwayat){
                    String nama_kostum= data.getNama_kostum().toLowerCase();
                    String nama_user = data.getNama().toLowerCase();
                    String tgl_sewa = data.getTgl_sewa().toLowerCase();
                    String tgl_kembali= data.getTgl_kembali().toLowerCase();
                    if(nama_kostum.contains(s.toLowerCase()) || nama_user.contains(s.toLowerCase()) || tgl_sewa.contains(s.toLowerCase()) || tgl_kembali.contains(s.toLowerCase())){
                        dataFilter.add(data);
                    }

                }
                myAdapter.setFilter(dataFilter);
                return true;
            }
        });
        searchItem.setActionView(searchView);
        return true;
    }
}
