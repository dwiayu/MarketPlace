package com.example.penyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.penyewa.REST.APIInterface;

public class DetailSewa extends AppCompatActivity {
    Context mContext;
    TextView tvIdDetail, tvNamaTempat, tvAlamatTempat, tvTgl,tStatus,tHarga;
    APIInterface mAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sewa);
        mContext= getApplicationContext();

        tvIdDetail=(TextView) findViewById(R.id.tvIdDetail);
        tvNamaTempat =(TextView) findViewById(R.id.tvNama_tempat);
        tvAlamatTempat =(TextView) findViewById(R.id.alamatTempat);
        tvTgl =(TextView) findViewById(R.id.tglTransaksi);
        tStatus =(TextView) findViewById(R.id.statusSewa);
        tHarga =(TextView) findViewById(R.id.hgKostum);


        final Intent mIntent= getIntent();
        tvIdDetail.setText(mIntent.getStringExtra("id_sewa"));
        tvNamaTempat.setText(mIntent.getStringExtra("nama_tempat"));
        tvAlamatTempat.setText(mIntent.getStringExtra("alamat_tempat"));
        tStatus.setText(mIntent.getStringExtra("status_sewa"));
        tHarga.setText(mIntent.getStringExtra("harga_kostum"));
    }
}
