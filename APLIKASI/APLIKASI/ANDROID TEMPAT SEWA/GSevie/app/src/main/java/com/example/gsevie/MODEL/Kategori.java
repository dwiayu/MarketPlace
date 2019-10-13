package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class Kategori {

    @SerializedName("id_kategori")
    public String id_kategori;
    @SerializedName("kategori")
    public String kategori;

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }


    public String getId() {
        return id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Kategori(String id_kategori, String kategori){
        this.id_kategori= id_kategori;
        this.kategori = kategori;


    }


    @Override
    public String toString() {
        return this.kategori;
    }

}
