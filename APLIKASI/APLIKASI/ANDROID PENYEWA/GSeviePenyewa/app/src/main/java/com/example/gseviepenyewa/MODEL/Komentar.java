package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    @SerializedName("id_komentar")
    private String id_komentar;
    @SerializedName("komentar")
    private String komentar;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("foto_user")
    private String foto_user;
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
    @SerializedName("id_kategori")
    private String id_kategori;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("id_sewa")
    private String id_sewa;
    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("id_detail")
    private String id_detail;


    public Komentar(String id_komentar, String komentar, String id_detail, String id_user, String nama, String jumlah, String jumlah_denda, String foto_user, String id_kostum, String nama_kostum, String jumlah_kostum, String harga_kostum, String deskripsi_kostum, String foto_kostum, String id_kategori, String kategori, String id_sewa, String tgl_transaksi) {
        this.id_komentar = id_komentar;
        this.id_detail = id_detail;
        this.komentar = komentar;
        this.id_user = id_user;
        this.jumlah = jumlah;
        this.jumlah_denda = jumlah_denda;
        this.nama = nama;
        this.foto_user = foto_user;
        this.id_kostum = id_kostum;
        this.nama_kostum = nama_kostum;
        this.jumlah_kostum = jumlah_kostum;
        this.harga_kostum = harga_kostum;
        this.deskripsi_kostum = deskripsi_kostum;
        this.foto_kostum = foto_kostum;
        this.id_kategori = id_kategori;
        this.kategori = kategori;
        this.id_sewa = id_sewa;
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(String id_komentar) {
        this.id_komentar = id_komentar;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
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

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId_sewa() {
        return id_sewa;
    }

    public void setId_sewa(String id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getJumlah_denda() {
        return jumlah_denda;
    }

    public void setJumlah_denda(String jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
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
}
