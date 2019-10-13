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

import com.example.gseviepenyewa.Adapter.RiwayatAdapter;
import com.example.gseviepenyewa.Adapter.SewaAdapter;
import com.example.gseviepenyewa.MODEL.GetPesan;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {
    String id_user;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    public RiwayatAdapter myAdapter;
    List<Pesan> listPesan = new ArrayList<>();
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
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilPemesanan();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(RiwayatActivity.this, MenuBerandaActivity.class));
        finish();
    }



    public void tampilPemesanan(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetPesan> mPemesananCall = mApiInterface.getRiwayat(reqid_user);

        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);

        mPemesananCall.enqueue(new Callback<GetPesan>() {
            @Override
            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                if(response.body(). getStatus().equals("success")){
                    simpleProgressBar.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    listPesan = response.body().getResult();
                    myAdapter = new RiwayatAdapter(listPesan);
                    mRecyclerView.setAdapter(myAdapter);
                }
                else{


                }

            }

            @Override
            public void onFailure(Call<GetPesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_riwayat, menu);

        MenuItem searchItem = menu.findItem(R.id.search_riwayat);
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
                ArrayList<Pesan> dataFilter= new ArrayList<>();
                for( Pesan data : listPesan){
                    String kode_sewa= data.getKode_Sewa().toLowerCase();
                    String tgl_sewa = data.getTgl_sewa().toLowerCase();
                    String tgl_kembali = data.getTgl_kembali().toLowerCase();
                    if(kode_sewa.contains(s.toLowerCase()) || tgl_sewa.contains(s.toLowerCase()) || tgl_kembali.contains(s.toLowerCase())){
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
