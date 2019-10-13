package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class Tempat {
    @SerializedName("id_tempat")
    private String id_tempat;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("no_rekening")
    private String no_rekening;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("email")
    private String email;
    @SerializedName("foto_tempat")
    private String foto_tempat;
    @SerializedName("slogan_tempat")
    private String slogan_tempat;
    @SerializedName("deskripsi_tempat")
    private String deskripsi_tempat;
    @SerializedName("status_tempat")
    private  String status_tempat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;


    public Tempat(String id_tempat, String nama_tempat, String no_rekening, String no_hp, String email, String foto_tempat, String slogan_tempat, String deskripsi_tempat, String status_tempat, String alamat, String username, String password) {
        this.id_tempat = id_tempat;
        this.nama_tempat = nama_tempat;
        this.no_rekening = no_rekening;
        this.no_hp = no_hp;
        this.email = email;
        this.foto_tempat = foto_tempat;
        this.slogan_tempat = slogan_tempat;
        this.deskripsi_tempat = deskripsi_tempat;
        this.status_tempat = status_tempat;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto_tempat() {
        return foto_tempat;
    }

    public void setFoto_tempat(String foto_tempat) {
        this.foto_tempat = foto_tempat;
    }

    public String getSlogan_tempat() {
        return slogan_tempat;
    }

    public void setSlogan_tempat(String slogan_tempat) {
        this.slogan_tempat = slogan_tempat;
    }

    public String getDeskripsi_tempat() {
        return deskripsi_tempat;
    }

    public void setDeskripsi_tempat(String deskripsi_tempat) {
        this.deskripsi_tempat = deskripsi_tempat;
    }

    public String getStatus_tempat() {
        return status_tempat;
    }

    public void setStatus_tempat(String status_tempat) {
        this.status_tempat = status_tempat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
