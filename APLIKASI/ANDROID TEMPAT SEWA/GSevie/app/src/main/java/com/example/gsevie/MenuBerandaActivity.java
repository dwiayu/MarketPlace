package com.example.gsevie;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.gsevie.MODEL.GetTempat;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.Utils.SaveSharedPreferences;

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
String id_tempat;
APIInterface mAPIInterface;
String url_photo, photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_beranda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_tempat =SaveSharedPreferences.getId(getApplicationContext());

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

        nama = (TextView) headerView.findViewById(R.id.nama_tempat);
        foto =(ImageView) headerView.findViewById(R.id.imageView);
        email = (TextView) headerView.findViewById(R.id.email);
        getData();
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
    public void getData(){
      mAPIInterface = APIClient.getClient().create(APIInterface.class);
      RequestBody reqId_tempat =MultipartBody.create(MediaType.parse("multipart/form-data"),(id_tempat));
      Call<GetTempat>mTempat = mAPIInterface.getMyProfile(reqId_tempat);
      mTempat.enqueue(new Callback<GetTempat>() {
          @Override
          public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
              if(response.body().getStatus().equals("success")){
                nama.setText(response.body().getResult().get(0).getNama_tempat());
                email.setText(response.body().getResult().get(0).getEmail());
                  url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
                  photoName = response.body().getResult().get(0).getFoto_tempat();

                  if (photoName.equals("")){
                      Glide.with(getApplicationContext()).load(R.drawable.user).into(foto);
                  }else{
                      Glide.with(getApplicationContext()).load(url_photo).into(foto);
                  }
              }else{
                  Toast.makeText(MenuBerandaActivity.this, "Gagal medapatkan data tempat sewa", Toast.LENGTH_SHORT).show();

              }
          }

          @Override
          public void onFailure(Call<GetTempat> call, Throwable t) {

          }
      });
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
        } else if (id == R.id.nav_kostum) {
            Toast.makeText(MenuBerandaActivity.this, "Kostum",Toast.LENGTH_LONG).show();
            fragmentClass = KostumFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        }else if (id == R.id.nav_sewa) {
            Toast.makeText(MenuBerandaActivity.this, "Pemesanan",Toast.LENGTH_LONG).show();
            fragmentClass = SewaFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        } else if (id == R.id.nav_offline) {
            Toast.makeText(MenuBerandaActivity.this, "Sewa Offline",Toast.LENGTH_LONG).show();
            fragmentClass = PesanFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        }else if (id == R.id.nav_tentang) {
            Toast.makeText(MenuBerandaActivity.this, "Tentang",Toast.LENGTH_LONG).show();
            fragmentClass = TentangFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
        } else if (id == R.id.nav_logout) {
            SaveSharedPreferences.setLoggedInTS(getApplicationContext(), false);
            SaveSharedPreferences.setId(getApplicationContext(),"");
            Intent intent = new Intent(MenuBerandaActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout(){
        SaveSharedPreferences.setLoggedInTS(getApplicationContext(), false);
        SaveSharedPreferences.setId(getApplicationContext(),"");

    }
}
