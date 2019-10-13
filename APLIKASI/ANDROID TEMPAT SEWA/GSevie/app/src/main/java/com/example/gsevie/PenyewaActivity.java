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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gsevie.Adapter.PenyewaAdapter;
import com.example.gsevie.MODEL.GetProfilId;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.MODEL.ProfilId;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenyewaActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    public PenyewaAdapter myAdapter;
    List<ProfilId> listPenyewa= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Penyewa");
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        tampilPenyewa();
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(PenyewaActivity.this, MenuBerandaActivity.class));
        finish();
    }


    private void tampilPenyewa(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        Call<GetProfilId> mUser = mApiInterface.getAllUser();
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        mUser.enqueue(new Callback<GetProfilId>() {
            @Override
            public void onResponse(Call<GetProfilId> call, Response<GetProfilId> response) {
                Log.d("Get User", response.body().getStatus());
                simpleProgressBar.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                listPenyewa= response.body().getResult();
                myAdapter = new PenyewaAdapter(listPenyewa);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<GetProfilId> call, Throwable t) {
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
                ArrayList<ProfilId> dataFilter= new ArrayList<>();
                for( ProfilId data : listPenyewa){
                    String nama= data.getNama().toLowerCase();
                    String jenis_kelamin = data.getJenis_kelamin().toLowerCase();
                    String email = data.getEmail().toLowerCase();
                    String no_hp = data.getNo_hp().toLowerCase();
                    if(nama.contains(s.toLowerCase()) || jenis_kelamin.contains(s.toLowerCase()) || email.contains(s.toLowerCase()) || no_hp.contains(s.toLowerCase())){
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
