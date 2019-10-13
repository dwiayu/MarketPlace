package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pesan {
    @SerializedName("id_sewa")
    private String id_sewa;
    @SerializedName("kode_sewa")
    private String kode_Sewa;
    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;
    @SerializedName("tgl_sewa")
    private String tgl_sewa;
    @SerializedName("tgl_kembali")
    private String tgl_kembali;
    @SerializedName("bukti_sewa")
    private String bukti_sewa;
    @SerializedName("id_detail")
    private String id_detail;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("status_detail")
    private String status_detail;
    @SerializedName("id_kostum")
    private String id_kostum;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("jumlah_kostum")
    private String jumlah_kostum;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("deskripsi_kostum")
    private String deskripsi_kostum;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("jumlahPesan")
    private String jumlahPesan;
    @SerializedName("jumlahVerifikasi")
    private String jumlahVerifikasi;
    @SerializedName("jumlahSewa")
    private String jumlahSewa;
    @SerializedName("jumlahKembali")
    private String jumlahKembali;
    @SerializedName("id_alamat")
    private String id_alamat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kota")
    private String kota;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("desa")
    private String desa;
    @SerializedName("id_denda")
    private String id_denda;
    @SerializedName("denda")
    private String denda;
    @SerializedName("keterangan")
    private  String keterangan;
    private String total;
    @SerializedName("jumlahTerlambat")
    private String jumlahTerlambat;


    public Pesan(String tgl_kembali, String bukti_sewa, String id_detail, String jumlah, String status_detail, String id_kostum, String nama_kostum, String jumlah_kostum, String harga_kostum, String deskripsi_kostum, String foto_kostum, String jumlahPesan, String jumlahVerifikasi, String jumlahSewa, String jumlahKembali, String id_alamat, String alamat, String provinsi, String kota, String kecamatan, String desa, String total) {
        this.tgl_kembali = tgl_kembali;
        this.bukti_sewa = bukti_sewa;
        this.id_detail = id_detail;
        this.jumlah = jumlah;
        this.status_detail = status_detail;
        this.id_kostum = id_kostum;
        this.nama_kostum = nama_kostum;
        this.jumlah_kostum = jumlah_kostum;
        this.harga_kostum = harga_kostum;
        this.deskripsi_kostum = deskripsi_kostum;
        this.foto_kostum = foto_kostum;
        this.jumlahPesan = jumlahPesan;
        this.jumlahVerifikasi = jumlahVerifikasi;
        this.jumlahSewa = jumlahSewa;
        this.jumlahKembali = jumlahKembali;
        this.id_alamat = id_alamat;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.total = total;
    }


    public String getId_sewa() {
        return id_sewa;
    }

    public void setId_sewa(String id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getKode_Sewa() {
        return kode_Sewa;
    }

    public void setKode_Sewa(String kode_Sewa) {
        this.kode_Sewa = kode_Sewa;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
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

    public String getBukti_sewa() {
        return bukti_sewa;
    }

    public void setBukti_sewa(String bukti_sewa) {
        this.bukti_sewa = bukti_sewa;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public void setStatus_detail(String status_detail) {
        this.status_detail = status_detail;
    }

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getJumlah_kostum() {
        return jumlah_kostum;
    }

    public void setJumlah_kostum(String jumlah_kostum) {
        this.jumlah_kostum = jumlah_kostum;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getDeskripsi_kostum() {
        return deskripsi_kostum;
    }

    public void setDeskripsi_kostum(String deskripsi_kostum) {
        this.deskripsi_kostum = deskripsi_kostum;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public String getJumlahPesan() {
        return jumlahPesan;
    }

    public void setJumlahPesan(String jumlahPesan) {
        this.jumlahPesan = jumlahPesan;
    }

    public String getJumlahVerifikasi() {
        return jumlahVerifikasi;
    }

    public void setJumlahVerifikasi(String jumlahVerifikasi) {
        this.jumlahVerifikasi = jumlahVerifikasi;
    }

    public String getJumlahSewa() {
        return jumlahSewa;
    }

    public void setJumlahSewa(String jumlahSewa) {
        this.jumlahSewa = jumlahSewa;
    }

    public String getJumlahKembali() {
        return jumlahKembali;
    }

    public void setJumlahKembali(String jumlahKembali) {
        this.jumlahKembali = jumlahKembali;
    }

    public String getId_alamat() {
        return id_alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
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
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    public String getId_denda() {
        return id_denda;
    }

    public void setId_denda(String id_denda) {
        this.id_denda = id_denda;
    }

    public String getJumlahTerlambat() {
        return jumlahTerlambat;
    }

    public void setJumlahTerlambat(String jumlahTerlambat) {
        this.jumlahTerlambat = jumlahTerlambat;
    }
}
