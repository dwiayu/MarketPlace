package com.example.gseviepenyewa.DataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataHelper extends SQLiteOpenHelper {
    public MyDataHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_TB_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(" DROP TABLE IF EXISTS "+ Constant.TB_CART);
//        onCreate(db);
        if(oldVersion<1){
            db.execSQL(" DROP TABLE IF EXISTS "+ Constant.TB_CART);
            onCreate(db);
        }
    }

}
