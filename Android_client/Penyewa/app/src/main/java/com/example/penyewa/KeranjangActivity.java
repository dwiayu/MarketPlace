package com.example.penyewa;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.penyewa.Adapter.KeranjangAdapter;
import com.example.penyewa.DataHelper.Constant;
import com.example.penyewa.DataHelper.DBAdapter;
import com.example.penyewa.DataHelper.MyDataHelper;
import com.example.penyewa.MODEL.Keranjang;
import com.example.penyewa.MODEL.KostumAll;
import com.example.penyewa.MODEL.Pesan;
import com.example.penyewa.MODEL.TampilKeranjang;
import com.example.penyewa.Utils.SaveSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.penyewa.DataHelper.Constant.TB_CART;

public class KeranjangActivity extends AppCompatActivity {
    MyDataHelper dbHelper;
    KeranjangAdapter keranjangAdapter;
    ListView ListView01;
    DBAdapter dbAdapter;
//    RecyclerView rv;
    ListView rv;
    Button btPesan;
    TextView totalBayar;
    List<TampilKeranjang> tampilKeranjang;
    protected Cursor cursor;
    public static KeranjangActivity layarutama;
    int totalKeranjang;
    String arrr;
    int ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        rv = findViewById(R.id.rvCart);
        btPesan = (Button) findViewById(R.id.btPesanKostum);
        tampilKeranjang = new ArrayList<TampilKeranjang>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(KeranjangActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv.setLayoutManager(layoutManager);
        dbAdapter = new DBAdapter(this);
        layarutama = this;
//        tampilKeranjang();
        TampilkanList();
        btPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PesanKostum.class);
                startActivity(intent);
            }
        });
    }

//    private void tampilKeranjang() {
//
//        dbAdapter = new DBAdapter(KeranjangActivity.this);
//        tampilKeranjang = dbAdapter.getData();
//        keranjangAdapter = new KeranjangAdapter(tampilKeranjang);
//
//        Log.i("GetData", "" + tampilKeranjang);
//        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rv.setLayoutManager(reLayoutManager);
//        rv.setAdapter(keranjangAdapter);
//
//
//
//
//        }
    public void TampilkanList() {
        dbHelper = new MyDataHelper(this);
        //untuk membaca database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//menampilkan semua dari tabel sinetrons
        cursor = db.rawQuery("select * from " + TB_CART +  ";"  , null);
        final String[] databaru = new String[cursor.getCount()];

        cursor.moveToFirst();

        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            databaru[cc] = cursor.getString(5);


        }
        //menyimpan listview dengan ariabel ListView01
        rv =  findViewById(R.id.rvCart);
        rv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, databaru));
        rv.setSelected(true);
        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Memberikan action ketika kita mengklik judul
            public void onItemClick(AdapterView v, View arg1,int arg2, long arg3){
                final String selection = databaru[arg2];
                //yang akan ditampilkan jika kita klik judul sietron
                final CharSequence[] dialogitem = {"Detai Keranjang","Hapus Keranjang"};
//memberikan dialog ketika kita enyentuh judul
                AlertDialog.Builder builder = new AlertDialog.Builder(KeranjangActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    //pilihan untuk masing-masing pilihan
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0://jika kita pilih "lihat sinetron" maka akan menuju ke kelas ViewMovie
                                Intent intent0 = new Intent(getApplicationContext(), ViewKeranjang.class);
                                intent0.putExtra("nama_kostum", selection);
                                startActivity(intent0);
                                break;

                            case 1://jika memilih hapus sinetron maka akan menjalankan QUERY DELETE FROM...
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("DELETE FROM "+TB_CART +" WHERE nama_kostum ='" + selection + "'");
                                TampilkanList();
                                break;
                        }


                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)rv.getAdapter()).notifyDataSetInvalidated();
        cursor.close();
    }


    }



