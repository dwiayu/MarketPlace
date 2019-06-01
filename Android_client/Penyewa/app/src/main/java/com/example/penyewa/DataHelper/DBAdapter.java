package com.example.penyewa.DataHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.penyewa.MODEL.Keranjang;
import com.example.penyewa.MODEL.TampilKeranjang;

import java.util.ArrayList;
import java.util.List;

import static com.example.penyewa.DataHelper.Constant.ID_KERANJANG;
import static com.example.penyewa.DataHelper.Constant.TB_CART;

public class DBAdapter {
    private static final String TAG = "LOL";
    Context c;
    SQLiteDatabase db;
    MyDataHelper helper;



    public DBAdapter(Context ctx) {
        this.c = ctx;
        helper = new MyDataHelper(c);
    }

    //OPEN DB
    public DBAdapter openDB() {
        try {
            db = helper.getReadableDatabase();
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public long insertDataKeranjang(
//            String id_keranjang,
            String id_user,
            String id_kostum,
            String id_tempat,
            String id_alamat,
            String nama_kostum,
            String nama_tempat,
            String alamat,
            String jumlah_kostum,
            String harga_kostum,
            String jumlah_sewa,
            String sub_harga) {

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.A, id_user);
            contentValues.put(Constant.B, id_kostum);
            contentValues.put(Constant.C, id_tempat);
            contentValues.put(Constant.D, id_alamat);
            contentValues.put(Constant.E, nama_kostum);
            contentValues.put(Constant.F, nama_tempat);
            contentValues.put(Constant.G, alamat);
            contentValues.put(Constant.H, jumlah_kostum);
            contentValues.put(Constant.I, harga_kostum);
            contentValues.put(Constant.J, jumlah_sewa);
            contentValues.put(Constant.K, sub_harga);


            return db.insert(Constant.TB_CART, null, contentValues);

        } catch (SQLException er) {
            er.printStackTrace();
        }
        return 0;
    }

    //CLOSE
    public void close() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Cursor getAllCart() {
        String[] columns = {
                ID_KERANJANG,
                Constant.A,
                Constant.B,
                Constant.C,
                Constant.D,
                Constant.E,
                Constant.F,
                Constant.G,
                Constant.H,
                Constant.I,
                Constant.J,
                Constant.K
        };
        return db.query(Constant.TB_CART, columns, null, null, null, null, null);
    }

    public List<TampilKeranjang> getData() {
        List<TampilKeranjang> data = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + TB_CART +  ";"  , null);
        Cursor cursor = db.rawQuery("select * from " + TB_CART +  ";"  , null);
        StringBuffer stringBuffer = new StringBuffer();
        TampilKeranjang tampilKeranjang = null;
        while (cursor.moveToNext()) {
            tampilKeranjang = new TampilKeranjang();
           // String id_keranjang = cursor.getString(cursor.getColumnIndexOrThrow())
//            String id_keranjang = cursor.getString(cursor.getColumnIndexOrThrow("id_keranjang"));
            String nama_kostum = cursor.getString(cursor.getColumnIndexOrThrow("nama_kostum"));
            String nama_tempat = cursor.getString(cursor.getColumnIndexOrThrow("nama_tempat"));
            String alamat = cursor.getString(cursor.getColumnIndexOrThrow("alamat"));
            String jumlah_kostum = cursor.getString(cursor.getColumnIndexOrThrow("jumlah_kostum"));
            String harga_kostum = cursor.getString(cursor.getColumnIndexOrThrow("harga_kostum"));
            String jumlah_sewa = cursor.getString(cursor.getColumnIndexOrThrow("jumlah_sewa"));
            String sub_harga = cursor.getString(cursor.getColumnIndexOrThrow("sub_harga"));

//            tampilKeranjang.setId_kerajang(id_keranjang);
            tampilKeranjang.setNama_kostum(nama_kostum);
            tampilKeranjang.setNama_tempat(nama_tempat);
            tampilKeranjang.setAlamat(alamat);
            tampilKeranjang.setJumlah_kostum(jumlah_kostum);
            tampilKeranjang.setHarga_kostum(harga_kostum);
            tampilKeranjang.setJumlah_sewa(jumlah_sewa);
            tampilKeranjang.setSub_harga(sub_harga);

            data.add(tampilKeranjang);

        }
        for (TampilKeranjang mo : data) {
            Log.i("Hellomo", "" + mo.getAlamat());
        }
        return data;
    }


    public boolean Delete(long rowId)
    {

        this.openDB();
        return db.delete(Constant.TB_CART, Constant.ID_KERANJANG + "=" + rowId, null) > 0;
    }
    public void deleteCallDetail(String id_keranjang) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Constant.TB_CART, "id_keranjang='" + id_keranjang + "'", null);
        db.close();
    }
    public void deleteAllCart() {
        db.execSQL("delete from " + Constant.TB_CART);
    }
}
