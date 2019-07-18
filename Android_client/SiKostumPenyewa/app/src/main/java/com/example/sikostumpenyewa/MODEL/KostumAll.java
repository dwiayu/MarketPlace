package com.example.sikostumpenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class KostumAll {
    @SerializedName("id_kostum")
    private String id_kostum;
    @SerializedName("id_tempat")
    private  String id_tempat;
    @SerializedName("komentar")
    private String komentar;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public KostumAll(String nama, String komentar) {
        this.nama = nama;
        this.komentar= komentar;

    }

    @SerializedName("nama")
    private String nama;
    @SerializedName("id_alamat")
    private String id_alamat;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("jumlah_kostum")
    private String jumlah_kostum;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("id_kategori")
    private String id_kategori;
    @SerializedName("deskripsi_kostum")
    private String deskripsi_kostum;
    @SerializedName("status_tempat")
    private String status_tempat;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kota")
    private String kota;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("desa")
    private String desa;


    private String jml;
    private String id_user;
    private String id_kerajang;
    private Long id_cart;
    private String sub_harga;
    public Long getId_cart() {
        return id_cart;
    }

    public void setId_cart(Long id_cart) {
        this.id_cart = id_cart;
    }
    public String getId_kostum() {
        return id_kostum;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public String getId_alamat() {
        return id_alamat;
    }
    public void setId_alamat(String id_alamat){
        this.id_alamat= id_alamat;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }
    public void setId_kostum(String id_kostum){
        this.id_kostum= id_kostum;
    }
    public String getNama_tempat() {
        return nama_tempat;
    }
    public void setId_tempat(String id_tempat){
        this.id_tempat= id_tempat;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat){
        this.alamat= alamat;
    }

    public String getJumlah_kostum() {
        return jumlah_kostum;
    }
    public void setJumlah_kostum(String jumlah_kostum){
        this.jumlah_kostum= jumlah_kostum;
    }
    public String getHarga_kostum() {
        return harga_kostum;
    }
    public void setHarga_kostum(String harga_kostum){
        this.harga_kostum= harga_kostum;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }
    public  void setFoto_kostum(String foto_kostum){
        this.foto_kostum=foto_kostum;
    }
    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori){
        this.id_kategori=id_kategori;
    }

    public String getDeskripsi_kostum() {
        return deskripsi_kostum;
    }

    public void setDeskripsi_kostum(String deskripsi_kostum) {
        this.deskripsi_kostum = deskripsi_kostum;
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

    public String getJml(){
        return jml;
    }
    public String getSub_harga(){
        return sub_harga;
    }
    public String getId_user(){
        return  id_user;
    }
    public String getId_kerajang(){
        return  id_kerajang;
    }
    public KostumAll(String id_keranjang,String id_user,String id_kostum, String id_tempat, String id_alamat, String status_tempat,
                     String nama_kostum, String nama_tempat, String provinsi, String kota, String kecamatan, String desa, String alamat,String jumlah_kostum,String harga_kostum,  String jml,String sub_harga){
        this.id_kerajang=id_keranjang;
        this.id_user= id_user;
        this.id_kostum = id_kostum;
        this.id_tempat = id_tempat;
        this.id_alamat = id_alamat;
        this.nama_kostum= nama_kostum;
        this.nama_tempat= nama_tempat;
        this.status_tempat = status_tempat;
        this.alamat= alamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.jumlah_kostum= jumlah_kostum;
        this.harga_kostum= harga_kostum;
        this.jml=jml;
        this.sub_harga= sub_harga;
    }

    public String getStatus_tempat() {
        return status_tempat;
    }

    public void setStatus_tempat(String status_tempat) {
        this.status_tempat = status_tempat;
    }
}
