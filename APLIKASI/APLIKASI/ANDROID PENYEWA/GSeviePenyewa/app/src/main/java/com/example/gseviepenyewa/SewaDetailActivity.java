package com.example.gseviepenyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gseviepenyewa.Adapter.PemesananDetailAdapter;
import com.example.gseviepenyewa.Adapter.SewaDetailAdapter;
import com.example.gseviepenyewa.MODEL.Data;
import com.example.gseviepenyewa.MODEL.GetPesan;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.MODEL.Region;
import com.example.gseviepenyewa.MODEL.UniqueCode;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.REST.APIWilayah;
import com.example.gseviepenyewa.REST.WilayahInterface;

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

public class SewaDetailActivity extends AppCompatActivity {
    Context mContext;
    TextView tvIdDetail, alamat, desa, kecamatan, kota, provinsi;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String url_photo, photoName;
    TextView totalBayar;
    String totalPesan;
    String code = "";

    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    List<Pesan> tampilPesan= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_detail);

        tvIdDetail=(TextView) findViewById(R.id.id_sewa);

        tampilPesan= new ArrayList<Pesan>();
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        totalBayar = (TextView) findViewById(R.id.totalhargapesan);

        alamat = (TextView) findViewById(R.id.txtAlamat);
        desa = (TextView) findViewById(R.id.txtDesa);
        kecamatan = (TextView) findViewById(R.id.txtKecamatan);
        kota = (TextView) findViewById(R.id.txtKota);
        provinsi = (TextView) findViewById(R.id.txtProvinsi);

        tampilPemesanan();

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
                        tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

                        mApiInterface = APIClient.getClient().create(APIInterface.class);
                        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (mIntent.getStringExtra("id_sewa")));

                        Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
                        mPemesananCall.enqueue(new Callback<GetPesan>() {
                            @Override
                            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                                for (int i = 0; i < daftarProvinsi.size(); i++) {
                                    if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getProvinsi()) ){
                                        provinsi.setText("Provinsi "+capitalize(daftarProvinsi.get(i).name));

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GetPesan> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                            }
                        });



                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Intent mIntent= getIntent();
                tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_sewa")));

                Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
                mPemesananCall.enqueue(new Callback<GetPesan>() {
                    @Override
                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                        final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(response.body().getResult().get(0).getProvinsi()));
                        call3.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();

                                final Intent mIntent= getIntent();
                                tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPesan>() {
                                    @Override
                                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getKota())) {
                                                kota.setText(capitalize(daftarProvinsi.get(i).getName()));
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPesan> call, Throwable t) {
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
                    public void onFailure(Call<GetPesan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });



                final Intent Intent= getIntent();
                tvIdDetail.setText(Intent.getStringExtra("id_sewa"));

                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (Intent.getStringExtra("id_sewa")));

                Call<GetPesan> PemesananCall = mApiInterface.detailSewa(detail);
                PemesananCall.enqueue(new Callback<GetPesan>() {
                    @Override
                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                        Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(response.body().getResult().get(0).getKota()));
                        call4.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();

                                final Intent mIntent= getIntent();
                                tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPesan>() {
                                    @Override
                                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {

                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getKecamatan()) ){
                                                kecamatan.setText("Kecamatan "+capitalize(daftarProvinsi.get(i).getName()));


                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPesan> call, Throwable t) {
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
                    public void onFailure(Call<GetPesan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });

                final Intent Intent2= getIntent();
                tvIdDetail.setText(Intent2.getStringExtra("id_sewa"));

                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody detail2 = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (Intent2.getStringExtra("id_sewa")));

                Call<GetPesan> PemesananCall2 = mApiInterface.detailSewa(detail2);
                PemesananCall2.enqueue(new Callback<GetPesan>() {
                    @Override
                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                        Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(response.body().getResult().get(0).getKecamatan()));
                        call5.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                final List<Region> daftarProvinsi = response.body().getData();

                                final Intent mIntent= getIntent();
                                tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

                                mApiInterface = APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (mIntent.getStringExtra("id_sewa")));

                                Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
                                mPemesananCall.enqueue(new Callback<GetPesan>() {
                                    @Override
                                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {

                                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                                            if (daftarProvinsi.get(i).id == Long.valueOf(response.body().getResult().get(0).getDesa())){
                                                desa.setText("Kelurahan "+capitalize(daftarProvinsi.get(i).getName()));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPesan> call, Throwable t) {
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
                    public void onFailure(Call<GetPesan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });

    }

    public void getSumofAllitems(){
        int total_sum=0;
        for(int i=0;i<tampilPesan.size();i++){
            Pesan pesan_items=tampilPesan.get(i);
            int harga = Integer.parseInt(pesan_items.getHarga_kostum());//getPrice() is a getter method in your POJO class.
            int jumlah = Integer.parseInt(pesan_items.getJumlah());
            int result = harga*jumlah;
            total_sum += result;
            totalPesan = String.valueOf(total_sum);
        }
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(totalPesan);

        totalBayar.setText(formatRupiah.format(harga));
    }
    private void tampilPemesanan(){
        final Intent mIntent= getIntent();
        tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                (mIntent.getStringExtra("id_sewa")));

        Call<GetPesan> mPemesananCall = mApiInterface.detailSewa(reqid_detail);
        mPemesananCall.enqueue(new Callback<GetPesan>() {
            @Override
            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                alamat.setText(response.body().getResult().get(0).getAlamat());
                Log.d("Get Pemesanan", response.body().getStatus());
                tampilPesan = response.body().getResult();
                mAdapter = new SewaDetailAdapter(tampilPesan);
                mRecyclerView.setAdapter(mAdapter);
                getSumofAllitems();
            }

            @Override
            public void onFailure(Call<GetPesan> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
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
