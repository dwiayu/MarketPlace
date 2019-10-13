package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Sewa {
    @SerializedName("id_sewa")
    private  String id_sewa;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_kostum")
    private String id_kostum;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("id_detail")
    private String id_detail;
    @SerializedName("id_alamat")
    private String id_alamat;
    @SerializedName("tanggal_sewa")
    private String tanggal_sewa;
    @SerializedName("tanggal_kembali")
    private String tanggal_kembali;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_alamat() {
        return id_alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public String getTanggal_sewa() {
        return tanggal_sewa;
    }

    public void setTanggal_sewa(String tanggal_sewa) {
        this.tanggal_sewa = tanggal_sewa;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    public String getId_sewa() {
        return id_sewa;
    }

    public void setId_sewa(String id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public Sewa(String id_sewa, String id_user, String id_kostum, String jumlah, String id_detail, String id_alamat, String tanggal_sewa, String tanggal_kembali) {
        this.id_sewa = id_sewa;
        this.id_user = id_user;
        this.id_kostum = id_kostum;
        this.jumlah = jumlah;
        this.id_detail = id_detail;
        this.id_alamat = id_alamat;
        this.tanggal_sewa = tanggal_sewa;
        this.tanggal_kembali = tanggal_kembali;
    }
}
