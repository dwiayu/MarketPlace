package com.example.gseviepenyewa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gseviepenyewa.Adapter.KeranjangAdapter;
import com.example.gseviepenyewa.DataHelper.DBAdapter;
import com.example.gseviepenyewa.MODEL.Alamat;
import com.example.gseviepenyewa.MODEL.GetAlamat;
import com.example.gseviepenyewa.MODEL.GetKostumAll;
import com.example.gseviepenyewa.MODEL.GetSewa;
import com.example.gseviepenyewa.MODEL.TampilKeranjang;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.GRAY;
import static okhttp3.MediaType.parse;

public class KeranjangActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    APIInterface mApiInterface;
    String id_user, id_sewa;
    TextView tvTglSewa,ketAlamat;
    Button sewa,btTglSewa;
    public static KeranjangAdapter keranjangAdapter;
    DBAdapter dbAdapter;
    Context mContext;
    Spinner spinnerAlamat;
    Alamat alamatTempat;

    RecyclerView rv;
    static TextView totalBayar;
    List<TampilKeranjang> tampilKeranjang = new ArrayList<>();
    protected Cursor cursor;
    public  static KeranjangActivity layarutama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tvTglSewa = (TextView) findViewById(R.id.tvTglSewa);
        mContext= getApplicationContext();
        ketAlamat = (TextView) findViewById(R.id.alamatkosong);
        sewa = (Button) findViewById(R.id.pemesanan);
        spinnerAlamat = (Spinner) findViewById(R.id.spinnerAlamat);
        rv = findViewById(R.id.rvCart);
        btTglSewa= (Button) findViewById(R.id.btTglSewa);
        tampilKeranjang = new ArrayList<TampilKeranjang>();
        id_user = SaveSharedPreferences.getId(getApplicationContext());



        final LinearLayoutManager layoutManager = new LinearLayoutManager(KeranjangActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        dbAdapter = new DBAdapter(this);
        layarutama= this;
        totalBayar = (TextView) findViewById(R.id.totalKeranjang);
        tampilKeranjang();
        initSpinnerAlamat();
        getSumofAllitems();
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =
                MultipartBody.create(parse("multipart/form-data"),
                        id_user);
        Call<GetAlamat> mCekTempat = mApiInterface.cekAlamat(reqid_user);
        mCekTempat.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                if(response.body().getStatus().equals("success")){
                    sewa.setEnabled(false);
                    sewa.setBackgroundColor(GRAY);
                    ketAlamat.setVisibility(View.VISIBLE);
                }else{
                    sewa.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<GetAlamat> call, Throwable t) {

            }
        });
        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
                final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
                simpleProgressBar.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.VISIBLE);
                alamatTempat = (Alamat) spinnerAlamat.getSelectedItem();
                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_user =
                        MultipartBody.create(parse("multipart/form-data"),
                                id_user);

                RequestBody reqid_alamat =MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (String.valueOf(alamatTempat.getId())));
                RequestBody reqTgl_sewa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        tvTglSewa.getText().toString());
                Call<GetSewa>mSewaCall = mApiInterface.postSewa(reqid_user,reqid_alamat,reqTgl_sewa);
                mSewaCall.enqueue(new Callback<GetSewa>() {
                    @Override
                    public void onResponse(Call<GetSewa> call, Response<GetSewa> response) {
                        if (response.body().getStatus().equals("success")){
                            simpleProgressBar.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            id_sewa=response.body().getResult().get(0).getId_sewa();
                            insertDetail(id_sewa);
                        }
                        else if(response.body().getStatus().equals("failed")){
                            simpleProgressBar.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            Toast.makeText(mContext,"Masukkan Tanggal Sewa",Toast.LENGTH_SHORT).show();

                        }
                        else{
//
                            sewa.setEnabled(false);
                            sewa.setBackgroundColor(GRAY);
                            ketAlamat.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<GetSewa> call, Throwable t) {
                        Log.d("Insert Sewa", t.getMessage());
                    }
                });
            }
        });
        btTglSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(KeranjangActivity.this, KostumActivity.class));
        finish();
    }

    public  void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();



        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvTglSewa.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    public void getSumofAllitems(){
        int total_sum=0;
        Locale localeID= new Locale("in","ID");
        NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        final NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        for(int i=0;i<tampilKeranjang.size();i++){
            TampilKeranjang kostum_items=tampilKeranjang.get(i);
            int result = Integer.parseInt(kostum_items.getSub_harga());//getPrice() is a getter method in your POJO class.
            total_sum+= result;
        }

        totalBayar.setText("Total Pembayaran : "+formatRupiah.format(total_sum)+" ");
    }
    public void initSpinnerAlamat(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =
                MultipartBody.create(parse("multipart/form-data"),
                        id_user);
        Call<GetAlamat> mAlamat=mApiInterface.getAlamat(reqid_user);
        mAlamat.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                if(response.body().getStatus().equals("success")){
                    final List<Alamat> alamatItem = response.body().getResult();
                    List<String> listSpinner= new ArrayList<String>();
                    for(int i=0; i<alamatItem.size();i++){
                        listSpinner.add(alamatItem.get(i).getAlamat());
                    }
                    final ArrayAdapter<Alamat> adapter = new ArrayAdapter<>(KeranjangActivity.this,
                            android.R.layout.simple_spinner_item, alamatItem);
                    spinnerAlamat.setAdapter(adapter);

                    spinnerAlamat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!spinnerAlamat.getSelectedItem().toString().equals(getString(R.string.txt_pilih))) {
                                String selectAlamat = alamatItem.get(position).getId();
                                Toast.makeText(mContext, "Kamu memilih alamat" + selectAlamat, Toast.LENGTH_SHORT);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else{

                    Toast.makeText(mContext,"Gagal mengambil data alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAlamat> call, Throwable t) {
                Toast.makeText(mContext,"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void tampilKeranjang(){
        if(dbAdapter.lihatKeranjang()){
            sewa.setVisibility(View.VISIBLE);
            btTglSewa.setVisibility(View.VISIBLE);
        }

        dbAdapter= new DBAdapter(KeranjangActivity.this);
        tampilKeranjang = dbAdapter.getData();

        keranjangAdapter= new KeranjangAdapter(this,tampilKeranjang,dbAdapter);

        Log.i("GetData",""+tampilKeranjang);
        RecyclerView.LayoutManager reLayoutManager= new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(reLayoutManager);
        rv.setAdapter(keranjangAdapter);

    }


    public void sewa(View view) {
        alamatTempat = (Alamat) spinnerAlamat.getSelectedItem();
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =
                MultipartBody.create(parse("multipart/form-data"),
                        id_user);

        RequestBody reqid_alamat =MultipartBody.create(MediaType.parse("multipart/form-data"),
                (String.valueOf(alamatTempat.getId())));
        RequestBody reqTgl_sewa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                tvTglSewa.getText().toString());
        Call<GetSewa>mSewaCall = mApiInterface.postSewa(reqid_user,reqid_alamat,reqTgl_sewa);
        mSewaCall.enqueue(new Callback<GetSewa>() {
            @Override
            public void onResponse(Call<GetSewa> call, Response<GetSewa> response) {
                if (response.body().getStatus().equals("success")){
                    id_sewa=response.body().getResult().get(0).getId_sewa();
                    insertDetail(id_sewa);
                }
                else
                {
                    Toast.makeText(KeranjangActivity.this, "Lengkapi data alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSewa> call, Throwable t) {
                Log.d("Insert Sewa", t.getMessage());
            }
        });

    }

    private void insertDetail(String id_sewa) {
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        final RequestBody reqid_sewa= MultipartBody.create(parse("multipart/form-data"),
                id_sewa);
        for(int i=0; i<tampilKeranjang.size();i++){
            final RequestBody reqJumlah =MultipartBody.create(parse("multipart/form-data"),
                    tampilKeranjang.get(i).getJumlah_sewa());
            final RequestBody reqKostum=MultipartBody.create(parse("multipart/form-data"),
                    tampilKeranjang.get(i).getId_kostum());
            final String jumlah= tampilKeranjang.get(i).getJumlah_sewa();
            final int jumlahku= Integer.parseInt(jumlah);
            mApiInterface = APIClient.getClient().create(APIInterface.class);
            Call<GetKostumAll> mCekJumlah = mApiInterface.cekKostum(reqKostum);
            mCekJumlah.enqueue(new Callback<GetKostumAll>() {
                @Override
                public void onResponse(Call<GetKostumAll> call, Response<GetKostumAll> response) {
                    if(Integer.parseInt(response.body().getResult().get(0).getJumlah_kostum()) >= jumlahku){
                        Call<GetSewa> mDetail=mApiInterface.postDetail(reqid_sewa,reqKostum,reqJumlah);
                        mDetail.enqueue(new Callback<GetSewa>() {
                            @Override
                            public void onResponse(Call<GetSewa> call, Response<GetSewa> response) {

                            }

                            @Override
                            public void onFailure(Call<GetSewa> call, Throwable t) {
                                Toast.makeText(KeranjangActivity.this, "Gagal insert!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Jumlah kostum"+response.body().getResult().get(0).getNama_kostum()+" tidak tersedia",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GetKostumAll> call, Throwable t) {

                }
            });




        }
        final DBAdapter db=new DBAdapter(getApplication());
        db.openDB();
        db.deleteAllCart();
        Toast.makeText(KeranjangActivity.this, "Sukses insert kostum!", Toast.LENGTH_SHORT).show();
        Intent mIntent = new Intent(KeranjangActivity.this, KeranjangActivity.class);
        startActivity(mIntent);
    }

    public void pesan(View view) {
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =
                MultipartBody.create(parse("multipart/form-data"),
                        id_user);

        RequestBody reqid_alamat =MultipartBody.create(MediaType.parse("multipart/form-data"),
                (spinnerAlamat.getSelectedItem().toString()));
        RequestBody reqTgl_sewa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                tvTglSewa.getText().toString());
        Call<GetSewa>mSewaCall = mApiInterface.postSewa(reqid_user,reqid_alamat,reqTgl_sewa);
        mSewaCall.enqueue(new Callback<GetSewa>() {
            @Override
            public void onResponse(Call<GetSewa> call, Response<GetSewa> response) {
                if (response.body().getStatus().equals("success")){
                    id_sewa=response.body().getResult().get(0).getId_sewa();
                    insertDetail(id_sewa);
                }
                else
                {
                    Toast.makeText(KeranjangActivity.this, "Lengkapi data alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSewa> call, Throwable t) {
                Log.d("Insert Sewa", t.getMessage());
            }
        });

    }
}
