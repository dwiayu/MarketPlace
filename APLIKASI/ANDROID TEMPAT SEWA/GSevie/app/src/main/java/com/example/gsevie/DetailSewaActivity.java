package com.example.gsevie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.Adapter.DetailSewaAdapter;
import com.example.gsevie.MODEL.Data;
import com.example.gsevie.MODEL.GetDenda;
import com.example.gsevie.MODEL.GetPemesanan;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.MODEL.Region;
import com.example.gsevie.MODEL.UniqueCode;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.REST.APIWilayah;
import com.example.gsevie.REST.WilayahInterface;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSewaActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ImageView buktiSewa, fotoUser;
    TextView id_sewa,totalBayar, namaPenyewa, emailPenyewa, noHpPenyewa, alamatPenyewa, desaPenyewa, kecPenyewa, kotaPenyewa, provPenyewa;
    List<Pemesanan> daftarDetail = new ArrayList<>();
    String total_harga;
    Button valid,denda;
    String url_photo, photoName;
    String code = "";
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sewa);
        id_sewa = (TextView) findViewById(R.id.tIdSewa);
        totalBayar = (TextView) findViewById(R.id.totalBayar);
        mContext = getApplicationContext();
        valid =(Button) findViewById(R.id.btValid);
        denda = (Button) findViewById(R.id.btDenda);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        buktiSewa = (ImageView) findViewById(R.id.buktiBayar);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fotoUser = (ImageView) findViewById(R.id.fotoUser);
        namaPenyewa = (TextView) findViewById(R.id.namaPenyewa);
        emailPenyewa = (TextView) findViewById(R.id.emailPenyewa);
        noHpPenyewa = (TextView) findViewById(R.id.noHpPenyewa);
        alamatPenyewa = (TextView) findViewById(R.id.alamatPenyewa);
        desaPenyewa = (TextView) findViewById(R.id.desaPenyewa);
        kecPenyewa = (TextView) findViewById(R.id.kecPenyewa);
        kotaPenyewa = (TextView) findViewById(R.id.kotaPenyewa);
        provPenyewa = (TextView) findViewById(R.id.provPenyewa);


        final Intent mIntent = getIntent();
        id_sewa.setText(mIntent.getStringExtra("id_sewa"));
        tampilDetail();
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
                final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
                simpleProgressBar.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.VISIBLE);
                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_sewa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        id_sewa.getText().toString());
                Call<GetPemesanan> putBukti= mApiInterface.putSelesai(reqid_sewa);
                putBukti.enqueue(new Callback<GetPemesanan>() {
                    @Override
                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                        simpleProgressBar.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Sewa Kostum Selesai",Toast.LENGTH_LONG).show();
                        Intent mIntent=  new Intent(getApplicationContext(),SewaActivity.class);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onFailure(Call<GetPemesanan> call, Throwable t) {

                    }
                });
            }
        });
        denda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog= new Dialog(DetailSewaActivity.this);
                dialog.setContentView(R.layout.layout_denda);
                dialog.setTitle("Input Denda");
                final EditText text = (EditText) dialog.findViewById(R.id.tv_denda);
                final EditText keterangan = (EditText) dialog.findViewById(R.id.tv_ket);
                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressBar simpleProgressBar= (ProgressBar) dialog.findViewById(R.id.simpleProgressBarr);
                        final LinearLayout progressLayout = (LinearLayout) dialog.findViewById(R.id.progressLayout);
                        simpleProgressBar.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.VISIBLE);
                        if(text.getText().toString().length()==0 || keterangan.getText().toString().length()==0){
                            simpleProgressBar.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Data tidak boleh kosong!",Toast.LENGTH_LONG).show();
                        }else{
                            final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                            RequestBody reqid_sewa =MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    id_sewa.getText().toString());
                            RequestBody reqdenda = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    text.getText().toString());
                            RequestBody reqketerangan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    keterangan.getText().toString());
                            Call<GetDenda>mDenda = mApiInterface.inputDenda(reqid_sewa,reqdenda,reqketerangan);
                            mDenda.enqueue(new Callback<GetDenda>() {
                                @Override
                                public void onResponse(Call<GetDenda> call, Response<GetDenda> response) {

                                    RequestBody reqid_sewa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                            id_sewa.getText().toString());
                                    Call<GetPemesanan> putBukti= mApiInterface.putSelesai(reqid_sewa);
                                    putBukti.enqueue(new Callback<GetPemesanan>() {
                                        @Override
                                        public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                                            simpleProgressBar.setVisibility(View.GONE);
                                            progressLayout.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"Sewa Kostum Selesai",Toast.LENGTH_LONG).show();
                                            Intent mIntent=  new Intent(getApplicationContext(),SewaActivity.class);
                                            startActivity(mIntent);
                                        }

                                        @Override
                                        public void onFailure(Call<GetPemesanan> call, Throwable t) {

                                        }
                                    });

                                    Toast.makeText(mContext,"Input denda berhasil",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(mContext,SewaActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<GetDenda> call, Throwable t) {
                                    Toast.makeText(mContext,"gagal Input Denda",Toast.LENGTH_LONG).show();

                                }
                            });
                        }


                    }
                });
                dialog.show();
            }
        });

        //tampil api alamat

        final WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();
        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {

                code = "MeP7c5ne" + response.body().getUniqueCode();
                Call<Data> call2 = apiService.getProvinceList(code);

                call2.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        final Intent mIntent= getIntent();

                        mApiInterface = APIClient.getClient().create(APIInterface.class);
                        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (mIntent.getStringExtra("id_sewa")));

                        Call<GetPemesanan> mPemesananCall = mApiInterface.getBukti(reqid_detail);
                        mPemesananCall.enqueue(new Callback<GetPemesanan>() {
                            @Override
                            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                                for (int i = 0; i < daftarProvinsi.size(); i++) {
                                    if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getProvinsi()) ){
                                        provPenyewa.setText("Provinsi "+capitalize(daftarProvinsi.get(i).name));

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                            }
                        });



                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });


                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_sewa")));

                Call<GetPemesanan> mPemesananCall = mApiInterface.getBukti(reqid_detail);
                mPemesananCall.enqueue(new Callback<GetPemesanan>() {
                    @Override
                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                        final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(response.body().getResult().get(0).getProvinsi()));
                        call3.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();


                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPemesanan> mPemesananCall = mApiInterface.getBukti(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPemesanan>() {
                                    @Override
                                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getKota())) {
                                                kotaPenyewa.setText(capitalize(daftarProvinsi.get(i).getName()));
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }


                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {

                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });



                final Intent Intent= getIntent();

                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (Intent.getStringExtra("id_sewa")));

                Call<GetPemesanan> PemesananCall = mApiInterface.getBukti(detail);
                PemesananCall.enqueue(new Callback<GetPemesanan>() {
                    @Override
                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                        Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(response.body().getResult().get(0).getKota()));
                        call4.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();

                                final Intent mIntent= getIntent();

                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPemesanan> mPemesananCall = mApiInterface.getBukti(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPemesanan>() {
                                    @Override
                                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {

                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getKecamatan()) ){
                                                kecPenyewa.setText("Kecamatan "+capitalize(daftarProvinsi.get(i).getName()));


                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }


                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {

                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });

                final Intent Intent2= getIntent();

                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody detail2 = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (Intent2.getStringExtra("id_sewa")));

                Call<GetPemesanan> PemesananCall2 = mApiInterface.getBukti(detail2);
                PemesananCall2.enqueue(new Callback<GetPemesanan>() {
                    @Override
                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                        Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(response.body().getResult().get(0).getKecamatan()));
                        call5.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();

                                final Intent mIntent= getIntent();
                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPemesanan> mPemesananCall = mApiInterface.getBukti(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPemesanan>() {
                                    @Override
                                    public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {

                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getDesa())){
                                                desaPenyewa.setText("Kelurahan "+capitalize(daftarProvinsi.get(i).getName()));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }


                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {

                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<GetPemesanan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });


    }
    public void tampilDetail(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_sewa =MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_sewa.getText().toString());
        Call<GetPemesanan> mDetailCall = mApiInterface.getDetailPinjam(reqid_sewa);
        mDetailCall.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                Log.d("Get Kostum", response.body().getStatus());
                daftarDetail = response.body().getResult();
                mAdapter = new DetailSewaAdapter(daftarDetail);
                mRecyclerView.setAdapter(mAdapter);
                getSumofAllitems();
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getSumofAllitems(){
        int total_sum=0;
        for(int i=0;i<daftarDetail.size();i++){
            Pemesanan detailItem=daftarDetail.get(i);
            int jumlah = Integer.parseInt(detailItem.getJumlah());//getPrice() is a getter method in your POJO class.
            int harga = Integer.parseInt(detailItem.getHarga_kostum());
            int result = jumlah*harga;
            total_sum+= result;
            total_harga = String.valueOf(total_sum);
        }
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(total_harga);
        totalBayar.setText("Total Pemesanan : "+formatRupiah.format(harga));
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_sewa =MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_sewa.getText().toString());
        Call<GetPemesanan>mBukti = mApiInterface.getBukti(reqid_sewa);
        mBukti.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if(response.body().getStatus().equals("success")){
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getBukti_sewa();
                    photoName = response.body().getResult().get(0).getBukti_sewa();
                    if (photoName.equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.shopping).into(buktiSewa);
                    }else{
                        Glide.with(getApplicationContext()).load(url_photo).into(buktiSewa);
                    }

                    if (response.body().getResult().get(0).getFoto_user().equals("")){
                        Glide.with(getApplicationContext()).load(R.drawable.user).into(fotoUser);
                    }else{
                        Glide.with(getApplicationContext()).load(APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_user()).into(fotoUser);

                    }
                    namaPenyewa.setText(response.body().getResult().get(0).getNama_user());
                    emailPenyewa.setText(response.body().getResult().get(0).getEmail());
                    noHpPenyewa.setText(response.body().getResult().get(0).getNo_hp());
                    alamatPenyewa.setText(response.body().getResult().get(0).getAlamat());
                    valid.setVisibility(View.VISIBLE);
                    denda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {

            }
        });
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
}
