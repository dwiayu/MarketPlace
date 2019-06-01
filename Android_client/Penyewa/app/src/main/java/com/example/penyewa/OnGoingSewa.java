package com.example.penyewa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.penyewa.DataHelper.DBAdapter;
import com.example.penyewa.Utils.SaveSharedPreferences;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class OnGoingSewa extends AppCompatActivity {
    private static final String TAG = "LOL";
    DBAdapter dbAdapter;
    TextView txIdUser,txIdKostum, txIdTempat,txIdAlamat, txNamaTempat, txNamaKostum, txAlamat,
    txHargaKostum, txJumlah,txDeskripsi,subHarga,tglKembali, tglSewa;
    int totalHarga;
    EditText jumlahSewa;
    Button tambahKeranjang,batal;
    String id_user, fileNamePhoto;
    ImageView fotoKostum;
    String imagePath= "";



    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_sewa);

        mContext = getApplicationContext();
        tambahKeranjang = (Button) findViewById(R.id.tambahKerannjang);
        txIdUser =(TextView) findViewById(R.id.txIdUser);
        txIdKostum = (TextView) findViewById(R.id.id_kostum);
        txIdTempat =(TextView) findViewById(R.id.tIdTempatSewa);
        txIdAlamat=(TextView) findViewById(R.id.txIdAlamat);
        txNamaKostum =(TextView) findViewById(R.id.txNamaKostum);
        txNamaTempat =(TextView) findViewById(R.id.txNamaTempatSew);
        txAlamat =(TextView) findViewById(R.id.txNamaAlamat);
        txHargaKostum =(TextView) findViewById(R.id.txHargaKostum);
        txJumlah =(TextView) findViewById(R.id.txJumlah);

        jumlahSewa =(EditText) findViewById(R.id.valueJumlahPesan);
        subHarga=(TextView) findViewById(R.id.subHg);


        id_user = SaveSharedPreferences.getId(getApplicationContext());
        final Intent mIntent = getIntent();
//        //txIdUser.setText(mIntent.getStringExtra("id_user"));
        txIdUser.setText(id_user);
         txIdKostum.setText(mIntent.getStringExtra("id_kostum"));
        txIdTempat.setText(mIntent.getStringExtra("idTempat"));
        txIdAlamat.setText(mIntent.getStringExtra("id_alamat"));
        txNamaKostum.setText(mIntent.getStringExtra("nama_kostum"));
        txNamaTempat.setText(mIntent.getStringExtra("namaTempat"));
        txAlamat.setText(mIntent.getStringExtra("alamat"));
        txHargaKostum.setText(mIntent.getStringExtra("harga_kostum"));
        txJumlah.setText(mIntent.getStringExtra("jumlah_kostum"));



        tambahKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String id_user = SaveSharedPreferences.getId(getApplicationContext());
                final Intent mIntent = getIntent();
//                id_user = SaveSharedPreferences.getId(getApplicationContext());
                //String id_keranjang= UUID.randomUUID().toString();


//
                String id_kostum = txIdKostum.getText().toString();
                String id_tempat= txIdTempat.getText().toString();
                String id_alamat = txIdAlamat.getText().toString();
                String nama_kostum = txNamaKostum.getText().toString();
                String nama_tempat= txNamaTempat.getText().toString();
                String alamat= txAlamat.getText().toString();
                String jumlah_kostum= txJumlah.getText().toString();
                String harga_kostum= txHargaKostum.getText().toString();
                String jumlah_sewa = jumlahSewa.getText().toString();
                totalHarga =(Integer.parseInt(jumlah_sewa)*Integer.parseInt(harga_kostum));
                subHarga.setText(String.valueOf(totalHarga));
                String sub_harga = subHarga.getText().toString();


                if (Integer.parseInt(jumlah_sewa) > Integer.parseInt(jumlah_kostum) || Integer.parseInt(jumlah_sewa) < 1){
                    Toast.makeText(OnGoingSewa.this, "Jumlah lol", Toast.LENGTH_SHORT).show();
                } else {
                    addKeranjang(id_user,id_kostum,id_tempat,id_alamat,nama_kostum,nama_tempat,alamat,jumlah_kostum
                            ,harga_kostum,jumlah_sewa,sub_harga);

                }
            }
        });

    }

    public   void addKeranjang(final String id_user, final String id_kostum, final String id_tempat ,final String id_alamat,
                               final String nama_kostum,
                               final String nama_tempat, final String alamat,final String jumlah_kostum,final String harga_kostum,
                               final String jumlah_sewa,
                               final String sub_harga){

        new AlertDialog.Builder(this)
                .setTitle(" Konfirmasi")
                .setMessage(" Tambahkan kedaftar Keranjang?")
                .setIcon(R.drawable.bungaa)
                .setCancelable(false)
                .setPositiveButton("Iya ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dbAdapter = new DBAdapter(OnGoingSewa.this);
                        dbAdapter.openDB();
                        long hasil = 0;
                        hasil = dbAdapter.insertDataKeranjang(id_user,id_kostum,id_tempat,
                                id_alamat,nama_kostum,nama_tempat,alamat,
                                jumlah_kostum,harga_kostum,jumlah_sewa,sub_harga);
                        Log.d(TAG, "onClick: hasil->"+hasil);
                        if (hasil > 0) {
                            Toast.makeText(OnGoingSewa.this, "Behasil menambahkan ke daftar Keranjang ", Toast.LENGTH_SHORT).show();

                            Intent a = new Intent(OnGoingSewa.this, BerandaActivity
                                    .class);
                            startActivity(a);
                        } else {
                            Toast.makeText(OnGoingSewa.this, "Gagal", Toast.LENGTH_SHORT).show();

                        }
                    }})
                            .setNegativeButton("Batal", null).show();
                }

         }

