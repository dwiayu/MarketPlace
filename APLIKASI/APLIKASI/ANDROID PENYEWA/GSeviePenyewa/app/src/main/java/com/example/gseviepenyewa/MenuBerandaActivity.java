package com.example.gseviepenyewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.GetProfilId;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuBerandaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView nama,email;
    ImageView foto;
    String id_user;
    APIInterface mAPIInterface;
    String url_photo, photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_beranda);

        id_user =SaveSharedPreferences.getId(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = BerandaFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_menu_beranda);

        nama = (TextView) headerView.findViewById(R.id.nama_user);
        foto =(ImageView) headerView.findViewById(R.id.imageView);
        email = (TextView) headerView.findViewById(R.id.email);
        getData();

    }

    public void getData(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqId_user= MultipartBody.create(MediaType.parse("multipart/form-data"),(id_user));
        Call<GetProfilId> mUser = mAPIInterface.getMyProfile(reqId_user);
        mUser.enqueue(new Callback<GetProfilId>() {
            @Override
            public void onResponse(Call<GetProfilId> call, Response<GetProfilId> response) {
                if(response.body().getStatus().equals("success")){
                    nama.setText(response.body().getResult().get(0).getNama());
                    email.setText(response.body().getResult().get(0).getEmail());
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_user();
                    photoName = response.body().getResult().get(0).getFoto_user();

                    if (photoName.equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.user).into(foto);
                    }else{
                        Glide.with(getApplicationContext()).load(url_photo).into(foto);
                    }
                }else{
                    Toast.makeText(MenuBerandaActivity.this, "Gagal medapatkan data user", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetProfilId> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beranda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            Toast.makeText(MenuBerandaActivity.this, "Beranda",Toast.LENGTH_LONG).show();
            fragmentClass = BerandaFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        } else if (id == R.id.nav_sewa) {
            Toast.makeText(MenuBerandaActivity.this, "Pemesanan",Toast.LENGTH_LONG).show();
            fragmentClass = SewaFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        } else if (id == R.id.nav_tentang) {
            fragmentClass = TentangFragment.class;
            Toast.makeText(MenuBerandaActivity.this, "Tentang",Toast.LENGTH_LONG).show();
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        } else if (id == R.id.nav_logout) {
            SaveSharedPreferences.setLoggedInPY(getApplicationContext(), false);
            SaveSharedPreferences.setId(getApplicationContext(),"");
            Intent intent = new Intent(MenuBerandaActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
