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

import com.example.gsevie.Adapter.KategoriAdapter;
import com.example.gsevie.MODEL.GetKategori;
import com.example.gsevie.MODEL.Kategori;
import com.example.gsevie.MODEL.Kostum;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    public KategoriAdapter myAdapter;
    List<Kategori> listKategori= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Kategori");
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        fab=(FloatingActionButton) findViewById(R.id.tambahKategori);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        tampilKategori();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(),InsertKategoriActivity.class);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(KategoriActivity.this, MenuBerandaActivity.class));
        finish();
    }


    private void tampilKategori(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        Call<GetKategori> mKomentarCall = mApiInterface.lihatKategori();
        mKomentarCall.enqueue(new Callback<GetKategori>() {
            @Override
            public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                simpleProgressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                Log.d("Get Kategori", response.body().getStatus());
                listKategori= response.body().getResult();
                myAdapter = new KategoriAdapter(listKategori);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<GetKategori> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah", Toast.LENGTH_SHORT).show();
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
                ArrayList<Kategori> dataFilter= new ArrayList<>();
                for( Kategori data : listKategori){
                    String kategori = data.getKategori().toLowerCase();
                    if(kategori.contains(s.toLowerCase()) ){
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
