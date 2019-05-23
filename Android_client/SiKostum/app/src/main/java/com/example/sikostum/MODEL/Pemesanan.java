package com.example.sikostum.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pemesanan {
    @SerializedName("id_log")
    private String id_log;
    @SerializedName("id_detail")
    private  String id_detail;
    @SerializedName("id_tempat")
    private String id_tempat;
    @SerializedName("nama")
    private  String nama_user;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("status_log")
    private String status_log;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getStatus_log() {
        return status_log;
    }

    public void setStatus_log(String status_log) {
        this.status_log = status_log;
    }

    public String getId_log() {
        return id_log;
    }

    public void setId_log(String id_log) {
        this.id_log = id_log;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getJumlah_denda() {
        return jumlah_denda;
    }

    public void setJumlah_denda(String jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
    }

    public Pemesanan(String id_detail, String jumlah_denda, String id_log, String id_tempat, String nama_user, String tgl_transaksi, String nama_kostum, String jumlah,
                     String status_log, String alamat){
        this.id_tempat = id_tempat;
        this.jumlah_denda=jumlah_denda;
        this.id_log=id_log;
        this.nama_user = nama_user;
        this.tgl_transaksi = tgl_transaksi;
        this.nama_kostum = nama_kostum;
        this.jumlah = jumlah;
        this.status_log = status_log;
        this.alamat = alamat;
        this.id_detail= id_detail;

    }
}
