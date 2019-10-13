package com.example.gseviepenyewa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.DataHelper.DBAdapter;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;

import java.text.NumberFormat;
import java.util.Locale;

public class OnGoingSewaActivity extends AppCompatActivity {

    private static final String TAG = "LOL";
    DBAdapter dbAdapter;
    TextView txIdUser,txIdKostum,  txNamaKostum,
            txHargaKostum,txHargaKostum1, txJumlah,subHarga, subHarga1;
    int totalHarga;
    EditText jumlahSewa;
    Button tambahKeranjang;
    String id_user, fileNamePhoto;
    ImageView fotoKostum;
    String imagePath= "";
    APIInterface mApiInterface;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_sewa);
        mContext = getApplicationContext();
        tambahKeranjang = (Button) findViewById(R.id.tambahKerannjang);
        txIdUser =(TextView) findViewById(R.id.txIdUser);
        txIdKostum = (TextView) findViewById(R.id.id_kostum);
        txNamaKostum =(TextView) findViewById(R.id.txNamaKostum);
        txHargaKostum =(TextView) findViewById(R.id.txHargaKostum);
        txHargaKostum1 = (TextView) findViewById(R.id.txHargaKostum1);
        txJumlah =(TextView) findViewById(R.id.txJumlah);
        fotoKostum =(ImageView) findViewById(R.id.foto_kostum);
        jumlahSewa =(EditText) findViewById(R.id.valueJumlahPesan);
        subHarga=(TextView) findViewById(R.id.subHg);
        subHarga1=(TextView) findViewById(R.id.subHg1);

        Locale localeID= new Locale("in","ID");
        NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

        id_user = SaveSharedPreferences.getId(getApplicationContext());
        final Intent mIntent = getIntent();
        txIdUser.setText(id_user);
        txIdKostum.setText(mIntent.getStringExtra("id_kostum"));
        txNamaKostum.setText(mIntent.getStringExtra("nama_kostum"));
        final NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(mIntent.getStringExtra("harga_kostum"));
        txHargaKostum.setText(formatRupiah.format(harga));
        txHargaKostum1.setText(mIntent.getStringExtra("harga_kostum"));
        txJumlah.setText(mIntent.getStringExtra("jumlah_kostum"));
        fileNamePhoto = mIntent.getStringExtra("foto_kostum");
        imagePath = mIntent.getStringExtra("foto_kostum_url");
        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(APIClient.BASE_URL+"uploads/"+fileNamePhoto).into(fotoKostum);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.splash_user).into(fotoKostum);
        }
        imagePath = mIntent.getStringExtra("foto_kostum_url");



            tambahKeranjang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(jumlahSewa.getText().toString().length()==0){
                        Toast.makeText(OnGoingSewaActivity.this, "Jumlah Sewa harus di isi", Toast.LENGTH_SHORT).show();
                    }else{

                        String id_kostum = txIdKostum.getText().toString();
                        String nama_kostum = txNamaKostum.getText().toString();
                        String jumlah_kostum= txJumlah.getText().toString();
                        String harga_kostum= txHargaKostum1.getText().toString();
                        String jumlah_sewa = jumlahSewa.getText().toString();
                        totalHarga =(Integer.parseInt(jumlah_sewa)*Integer.parseInt(harga_kostum));
                        subHarga1.setText(formatRupiah.format(totalHarga));
                        subHarga.setText(String.valueOf(totalHarga));
                        String sub_harga = subHarga.getText().toString();

                        if (Integer.parseInt(jumlah_sewa) > Integer.parseInt(jumlah_kostum) || Integer.parseInt(jumlah_sewa) < 1){
                            Toast.makeText(OnGoingSewaActivity.this, "Jumlah Stok Kostum tidak tersedia", Toast.LENGTH_SHORT).show();
                        } else {
                            addKeranjang(id_user,id_kostum,nama_kostum,jumlah_kostum,harga_kostum,jumlah_sewa,sub_harga);

                        }
                    }

                }
            });
        }



    public   void addKeranjang(final String id_user, final String id_kostum,
                               final String nama_kostum, final String jumlah_kostum,final String harga_kostum,
                               final String jumlah_sewa,
                               final String sub_harga){
        totalHarga =(Integer.parseInt(jumlah_sewa)*Integer.parseInt(harga_kostum));
        subHarga.setText(String.valueOf(totalHarga));
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(this);
                alertDialog.setTitle(" Konfirmasi");
                alertDialog.setMessage("Tambahkan kedaftar Keranjang ?")
                .setIcon(R.drawable.splash_user)
                .setCancelable(false)
                .setPositiveButton("Iya ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dbAdapter = new DBAdapter(OnGoingSewaActivity.this);
                        dbAdapter.openDB();
                        long hasil = 0;
                        hasil = dbAdapter.insertDataKeranjang(id_user,id_kostum,nama_kostum,jumlah_kostum,harga_kostum,jumlah_sewa,sub_harga);
                        Log.d(TAG, "onClick: hasil->"+hasil);
                        if (hasil > 0) {
                            Toast.makeText(OnGoingSewaActivity.this, "Behasil menambahkan ke daftar Keranjang ", Toast.LENGTH_SHORT).show();

                            Intent a = new Intent(OnGoingSewaActivity.this, KostumActivity
                                    .class);
                            startActivity(a);
                        } else {
                            Toast.makeText(OnGoingSewaActivity.this, "Gagal", Toast.LENGTH_SHORT).show();

                        }
                    }})
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

    }
}
