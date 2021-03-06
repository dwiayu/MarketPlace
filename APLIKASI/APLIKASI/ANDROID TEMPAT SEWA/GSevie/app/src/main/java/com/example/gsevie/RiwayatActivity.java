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
import android.widget.Toast;

import com.example.gsevie.Adapter.SelesaiAdapter;
import com.example.gsevie.Adapter.ValidAdapter;
import com.example.gsevie.MODEL.GetPemesanan;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {
    String id_tempat;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    public SelesaiAdapter myAdapter;
    List<Pemesanan> listPesan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Riwayat");
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_tempat = SaveSharedPreferences.getId(getApplicationContext());
        tampilPemesanan();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(RiwayatActivity.this, MenuBerandaActivity.class));
        finish();
    }


    private void tampilPemesanan(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetPemesanan> mPemesananCall = mApiInterface.getSelesai();
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);

        mPemesananCall.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                simpleProgressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                listPesan = response.body().getResult();
                myAdapter = new SelesaiAdapter(listPesan);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
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
                ArrayList<Pemesanan> dataFilter= new ArrayList<>();
                for( Pemesanan data : listPesan){
                    String kode_sewa= data.getKode_sewa().toLowerCase();
                    String nama_user = data.getNama_user().toLowerCase();
                    String tgl_sewa = data.getTgl_sewa().toLowerCase();
                    String tgl_kembali = data.getTgl_kembali().toLowerCase();
                    if(kode_sewa.contains(s.toLowerCase()) || nama_user.contains(s.toLowerCase()) || tgl_sewa.contains(s.toLowerCase()) || tgl_kembali.contains(s.toLowerCase())){
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
