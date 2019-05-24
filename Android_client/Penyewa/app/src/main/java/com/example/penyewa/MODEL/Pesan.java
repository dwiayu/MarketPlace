package com.example.penyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pesan {
    @SerializedName("id_sewa")
    private String id_sewa;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("id_detail")
    private String id_detail;
    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;
    @SerializedName("status_sewa")
    private String status_sewa;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("status_log")
    private  String status_log;
    @SerializedName("bukti_sewa")
    private String bukti_sewa;

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getStatus_sewa() {
        return status_sewa;
    }

    public void setStatus_sewa(String status_sewa) {
        this.status_sewa = status_sewa;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_sewa() {
        return id_sewa;
    }

    public void setId_sewa(String id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getStatus_log() {
        return status_log;
    }

    public void setStatus_log(String status_log) {
        this.status_log = status_log;
    }

    public String getBukti_sewa() {
        return bukti_sewa;
    }

    public void setBukti_sewa(String bukti_sewa) {
        this.bukti_sewa = bukti_sewa;
    }

    public Pesan(String id_detail, String status_log, String id_sewa, String nama_tempat, String alamat, String nama_kostum, String tgl_transaksi, String status_sewa,
                 String harga_kostum, String bukti_sewa){
        this.id_detail=id_detail;
        this.status_log= status_log;
        this.id_sewa = id_sewa;
        this.nama_tempat= nama_tempat;
        this.tgl_transaksi= tgl_transaksi;
        this.status_sewa= status_sewa;
        this.harga_kostum= harga_kostum;
        this.nama_kostum = nama_kostum;
        this.alamat= alamat;
        this.bukti_sewa= bukti_sewa;

    }
}
