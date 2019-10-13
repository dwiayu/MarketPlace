package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class KostumAll {
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
    private String nama_kategori;
   @SerializedName("id_komentar")
   private String id_komentar;
   @SerializedName("komentar")
   private String komentar;
   @SerializedName("id_tempat")
   private String id_tempat;
   @SerializedName("status_tempat")
   private String status_tempat;
   @SerializedName("nama")
   private String nama;

    private String jml;
    private String id_user;
    private String id_kerajang;
    private Long id_cart;
    private String sub_harga;


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

    public String getJml() {
        return jml;
    }

    public void setJml(String jml) {
        this.jml = jml;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kerajang() {
        return id_kerajang;
    }

    public void setId_kerajang(String id_kerajang) {
        this.id_kerajang = id_kerajang;
    }

    public Long getId_cart() {
        return id_cart;
    }

    public void setId_cart(Long id_cart) {
        this.id_cart = id_cart;
    }

    public String getSub_harga() {
        return sub_harga;
    }

    public void setSub_harga(String sub_harga) {
        this.sub_harga = sub_harga;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getStatus_tempat() {
        return status_tempat;
    }

    public void setStatus_tempat(String status_tempat) {
        this.status_tempat = status_tempat;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
