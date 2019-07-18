package com.example.sikostumpenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    @SerializedName("id_komentar")
    private String id_komentar;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("id_kostum")
    private String id_kostum;
    @SerializedName("id_detail")
    private String id_detail;
    @SerializedName("komentar")
    private String komentar;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("nama_kostum")
    private String nama_kostum;

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }
    public String getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(String id_komentar) {
        this.id_komentar = id_komentar;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getDenda() {
        return jumlah_denda;
    }

    public void setDenda(String jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
    }

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan= keterangan;
    }

    public Komentar(String id_komentar, String id_user, String id_detail, String komentar, String jumlah_denda, String alamat,
                    String nama_tempat, String nama_kostum, String id_kostum, String nama, String keterangan, String foto_kostum){
        this.id_komentar= id_komentar;
        this.id_user = id_user;
        this.id_detail= id_detail;
        this.komentar= komentar;
        this.jumlah_denda = jumlah_denda;
        this.alamat= alamat;
        this.nama_tempat= nama_tempat;
        this.nama_kostum = nama_kostum;
        this.id_kostum = id_kostum;
        this.nama= nama;
        this.keterangan = keterangan;
        this.foto_kostum = foto_kostum;
    }
}
