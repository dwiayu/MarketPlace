package com.example.sikostumpenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pesan {
    @SerializedName("id_sewa")
    private String id_sewa;
    @SerializedName("id_log")
    private String id_log;
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
    @SerializedName("tgl_sewa")
    private String tgl_sewa;
    @SerializedName("tgl_kembali")
    private String tgl_kembali;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("jumlahMasuk")
    private String jumlahMasuk;
    @SerializedName("jumlahValid")
    private String jumlahValid;
    @SerializedName("jumlahSewa")
    private String jumlahSewa;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kota")
    private String kota;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("desa")
    private String desa;
    private String total;

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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi= provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota= kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan= kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa= desa;
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

    public String getTgl_sewa() {
        return tgl_sewa;
    }

    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getId_log() {
        return id_log;
    }

    public void setId_log(String id_log) {
        this.id_log = id_log;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public String getJumlahMasuk() {
        return jumlahMasuk;
    }

    public void setJumlahMasuk(String jumlahMasuk) {
        this.jumlahMasuk = jumlahMasuk;
    }

    public String getJumlahValid() {
        return jumlahValid;
    }

    public void setJumlahValid(String jumlahValid) {
        this.jumlahValid = jumlahValid;
    }

    public String getJumlahSewa() {
        return jumlahSewa;
    }

    public void setJumlahSewa(String jumlahSewa) {
        this.jumlahSewa = jumlahSewa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Pesan(String id_detail, String status_log, String id_sewa, String nama_tempat, String alamat, String nama_kostum, String tgl_transaksi, String status_sewa,
                 String harga_kostum, String bukti_sewa, String tgl_sewa, String tgl_kembali, String jumlah,
                 String id_log, String foto_kostum, String jumlahMasuk, String provinsi, String kota, String kecamatan, String desa, String jumlahValid, String jumlahSewa, String total){
        this.id_detail=id_detail;
        this.status_log= status_log;
        this.id_sewa = id_sewa;
        this.nama_tempat= nama_tempat;
        this.tgl_transaksi= tgl_transaksi;
        this.status_sewa= status_sewa;
        this.harga_kostum= harga_kostum;
        this.nama_kostum = nama_kostum;
        this.alamat= alamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.bukti_sewa= bukti_sewa;
        this.tgl_sewa = tgl_sewa;
        this.tgl_kembali = tgl_kembali;
        this.jumlah= jumlah;
        this.id_log= id_log;
        this.foto_kostum = foto_kostum;
        this.jumlahMasuk= jumlahMasuk;
        this.jumlahValid= jumlahValid;
        this.jumlahSewa= jumlahSewa;
        this.total = total;
    }

}
