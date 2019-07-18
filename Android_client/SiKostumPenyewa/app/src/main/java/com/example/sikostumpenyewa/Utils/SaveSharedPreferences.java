package com.example.sikostumpenyewa.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sikostumpenyewa.MODEL.KostumAll;
import com.google.gson.Gson;

import java.util.List;

import static com.example.sikostumpenyewa.Utils.PreferencesUtility.LOGGED_IN_PY;
import static com.example.sikostumpenyewa.Utils.PreferencesUtility.id;
import static com.example.sikostumpenyewa.Utils.PreferencesUtility.keranjangList;


public class SaveSharedPreferences {
    static String nama;
    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }



    public static void setLoggedInPY(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PY, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatusPY(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PY, false);
    }
    public static void setKeranjang(Context context, List<KostumAll> list) {
        keranjangList = list;
        Gson gson = new Gson();
        String json = gson.toJson(keranjangList);
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("keranjang",json );
        editor.apply();
    }

    public static String getKeranjang(Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(keranjangList);
        return getPreferences(context).getString("keranjang",json);
    }

    public static void setId(Context context, String name) {
        nama = name;
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(id, name);
        editor.apply();
    }

    public static String getId(Context context) {
        return getPreferences(context).getString(id, nama);
    }
}
