package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    @SerializedName("tgl_transaksi")
    private  String tgl_transaksi;
    @SerializedName("nama")
    private String nama;
    @SerializedName("nama_kostum")
    private  String nama_kostum;
    @SerializedName("komentar")
    private  String komentar;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("foto_user")
    private String foto_user;



    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }


    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public Komentar(String tgl_transaksi, String nama, String nama_kostum,
                    String komentar, String foto_kostum){
        this.tgl_transaksi= tgl_transaksi;
        this.nama= nama;
        this.nama_kostum= nama_kostum;
        this.komentar= komentar;
        this.foto_kostum= foto_kostum;


    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
    }
}
