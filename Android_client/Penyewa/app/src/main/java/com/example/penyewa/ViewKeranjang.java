package com.example.penyewa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.penyewa.DataHelper.MyDataHelper;

import static com.example.penyewa.DataHelper.Constant.TB_CART;

public class ViewKeranjang extends AppCompatActivity {
    protected Cursor cursor; //digunakan untuk mengakses data
    MyDataHelper dbHelper;//memanggil dbHelper pada kelas MyDataHelper
    TextView tvIdKeranjang, tvIdUser, tvIdKostum,tvIdTempat, tvIdAlamat,namakostum, namatempat,alamat, jumlahstok,harga,
            jumlahPesanan,
    totalBayar;
    Button kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_keranjang);

        dbHelper = new MyDataHelper(this);
        tvIdKeranjang = (TextView) findViewById(R.id.kjId);
        tvIdUser= (TextView)findViewById(R.id.id_user);
        tvIdKostum = (TextView)findViewById(R.id.idKostum);
        tvIdTempat = (TextView) findViewById(R.id.id_tempat);
        tvIdAlamat = (TextView) findViewById(R.id.id_alamat);
        namakostum = (TextView) findViewById(R.id.namaKostum);
        namatempat= (TextView) findViewById(R.id.namaTempat);
        alamat= (TextView) findViewById(R.id.alamat);
        jumlahstok=(TextView) findViewById(R.id.jumlahKostum);
        harga =(TextView) findViewById(R.id.hargaKostum);
        jumlahPesanan= (TextView) findViewById(R.id.jml);
        totalBayar=(TextView) findViewById(R.id.totalBayar);
        kembali =(Button) findViewById(R.id.btKembali);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntet= new Intent(getApplicationContext(),KeranjangActivity.class);
                startActivity(mIntet);
            }
        });

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT *FROM "+TB_CART +" WHERE nama_kostum = '"+getIntent().getStringExtra("nama_kostum")+"'",null);
        cursor.moveToFirst();

        //menampilkan data yang ada isinya (lebih dari 0)
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            tvIdKeranjang.setText((cursor.getString(0)));
            tvIdUser.setText(cursor.getString(1));
            tvIdKostum.setText(cursor.getString(2));
            tvIdTempat.setText(cursor.getString(3));
            tvIdAlamat.setText(cursor.getString(4));
            namakostum.setText(cursor.getString(5));
            namatempat.setText(cursor.getString(6));
            alamat.setText(cursor.getString(7));
            jumlahstok.setText(cursor.getString(8));
            harga.setText(cursor.getString(9));
            jumlahPesanan.setText(cursor.getString(10));
            totalBayar.setText(cursor.getString(11));

        }
    }

}
