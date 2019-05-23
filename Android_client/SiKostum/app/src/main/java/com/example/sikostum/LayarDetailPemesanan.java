package com.example.sikostum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sikostum.REST.APIInterface;

public class LayarDetailPemesanan extends AppCompatActivity {
    Context mContext;
    APIInterface mApiInterface;
    TextView tvNamaKostum,tvNamaUser,tvAlamat,tvJumlahKostum,tvHargaKostum,tvStatus,tvTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_detail_pemesanan);
        mContext = getApplicationContext();

        tvNamaKostum =(TextView) findViewById(R.id.tvNamaKostum);
        tvNamaUser =(TextView) findViewById(R.id.tvNamaUser);
        tvAlamat =(TextView) findViewById(R.id.tvAlamatUser);
        tvJumlahKostum =(TextView)findViewById(R.id.tvJmlh);
        tvHargaKostum =(TextView) findViewById(R.id.tvHg);
        tvStatus =(TextView) findViewById(R.id.tvStatus_log);
        tvTanggal=(TextView) findViewById(R.id.tvTglTrans);
        final Intent mIntent = getIntent();
        tvNamaKostum.setText(mIntent.getStringExtra("nama_kostum"));
        tvNamaUser.setText(mIntent.getStringExtra("nama_user"));
        tvJumlahKostum.setText(mIntent.getStringExtra("jumlah"));
        tvAlamat.setText(mIntent.getStringExtra("alamat"));
        tvHargaKostum.setText(mIntent.getStringExtra("harga_kostum"));
        tvStatus.setText(mIntent.getStringExtra("status_log"));
        tvTanggal.setText(mIntent.getStringExtra("tgl_transaksi"));
    }
}
