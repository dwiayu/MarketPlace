package com.example.gseviepenyewa;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gseviepenyewa.Adapter.KostumAllAdapter;
import com.example.gseviepenyewa.MODEL.GetKostumAll;
import com.example.gseviepenyewa.MODEL.KostumAll;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostumActivity extends AppCompatActivity {
    String id_user;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    public KostumAllAdapter myAdapter;

    List<KostumAll> listKostum= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kostum);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cari kostum");
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        id_user = SaveSharedPreferences.getId(getApplicationContext());

        tampilAllKostum();
    }
    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(KostumActivity.this, MenuBerandaActivity.class));
        finish();
    }
    public void tampilAllKostum() {
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetKostumAll> mKostumCall = mApiInterface.getKostumAll();
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);

        mKostumCall.enqueue(new Callback<GetKostumAll>() {
            @Override
            public void onResponse(Call<GetKostumAll> call, Response<GetKostumAll> response) {
                simpleProgressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                Log.d("Get Pemesanan", response.body().getStatus());
                listKostum= response.body().getResult();
                myAdapter = new KostumAllAdapter(listKostum, mContext);
                SaveSharedPreferences.setKeranjang(getApplicationContext(),listKostum);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<GetKostumAll> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.keranjang_action:
                Intent intent = new Intent(this, KeranjangActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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
                ArrayList<KostumAll> dataFilter= new ArrayList<>();
                for( KostumAll data : listKostum){
                    String nama_kostum = data.getNama_kostum().toLowerCase();
                    String kategori_kostum = data.getNama_kategori().toLowerCase();
                    String harga = data.getHarga_kostum().toLowerCase();


                    if(nama_kostum.contains(s.toLowerCase()) || kategori_kostum.contains(s.toLowerCase()) || harga.contains(s.toLowerCase())  ){
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
