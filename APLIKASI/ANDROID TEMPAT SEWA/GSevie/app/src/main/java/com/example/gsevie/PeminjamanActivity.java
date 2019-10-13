package com.example.gsevie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gsevie.Adapter.KostumAdapter;
import com.example.gsevie.Adapter.PeminjamanAdapter;
import com.example.gsevie.MODEL.GetKostum;
import com.example.gsevie.MODEL.Kostum;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    private FloatingActionButton fab;
    String id_tempat;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    public PeminjamanAdapter myAdapter;
    List<Kostum> listKostum= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Peminjaman");
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_tempat = SaveSharedPreferences.getId(getApplicationContext());
        tampilKostum();
    }


    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(PeminjamanActivity.this, MenuBerandaActivity.class));
        finish();
    }


    public void tampilKostum(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        Call<GetKostum> mKostumCall = mApiInterface.tampilKostum();
        mKostumCall.enqueue(new Callback<GetKostum>() {
            @Override
            public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                simpleProgressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                Log.d("Get Kostum", response.body().getStatus());
                listKostum= response.body().getResult();
                myAdapter = new PeminjamanAdapter(listKostum);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<GetKostum> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
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
                ArrayList<Kostum> dataFilter= new ArrayList<>();
                for( Kostum data : listKostum){
                    String nama_kostum= data.getNama_kostum().toLowerCase();
                    String kategori = data.getKategori().toLowerCase();
                    String harga = data.getHarga_kostum().toLowerCase();
                    String deskripsi= data.getDeskripsi_kostum().toLowerCase();
                    if(nama_kostum.contains(s.toLowerCase()) || kategori.contains(s.toLowerCase()) || harga.contains(s.toLowerCase()) || deskripsi.contains(s.toLowerCase())){
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
