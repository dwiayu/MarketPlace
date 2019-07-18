package com.example.sikostumpenyewa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sikostumpenyewa.Adapter.KostumAllAdapter;
import com.example.sikostumpenyewa.MODEL.GetKostumAll;
import com.example.sikostumpenyewa.MODEL.KostumAll;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIInterface;
import com.example.sikostumpenyewa.Utils.SaveSharedPreferences;

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
        startActivity(new Intent(KostumActivity.this, BerandaActivity.class));
        finish();
    }
    public void tampilAllKostum() {
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetKostumAll> mKostumCall = mApiInterface.getKostumAll();
        mKostumCall.enqueue(new Callback<GetKostumAll>() {
            @Override
            public void onResponse(Call<GetKostumAll> call, Response<GetKostumAll> response) {
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
                    String nama_tempat= data.getNama_tempat().toLowerCase();
                    String alamat = data.getAlamat().toLowerCase();

                    if(nama_kostum.contains(s.toLowerCase()) || nama_tempat.contains(s.toLowerCase()) || alamat.contains(s.toLowerCase())  ){
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
