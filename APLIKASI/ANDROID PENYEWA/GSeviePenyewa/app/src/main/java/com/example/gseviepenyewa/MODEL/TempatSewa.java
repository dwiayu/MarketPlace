package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class TempatSewa {
    @SerializedName("id_tempat")
    private String id_tempat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("no_rekening")
    private String no_rekening;
    @SerializedName("slogan_tempat")
    private String slogan_tempat;
    @SerializedName("deskripsi_tempat")
    private String deskripsi_tempat;
    @SerializedName("foto_tempat")
    private String foto_tempat;
    @SerializedName("status_tempat")
    private  String status_tempat;
    @SerializedName("no_hp")
    private String no_hpTempat;
    @SerializedName("email")
    private String emailTempat;


    public TempatSewa(String id_tempat, String alamat, String nama_tempat, String no_rekening, String slogan_tempat, String deskripsi_tempat, String foto_tempat, String status_tempat, String no_hpTempat, String emailTempat) {
        this.id_tempat = id_tempat;
        this.alamat = alamat;
        this.nama_tempat = nama_tempat;
        this.no_rekening = no_rekening;
        this.slogan_tempat = slogan_tempat;
        this.deskripsi_tempat = deskripsi_tempat;
        this.foto_tempat = foto_tempat;
        this.status_tempat = status_tempat;
        this.no_hpTempat = no_hpTempat;
        this.emailTempat = emailTempat;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getFoto_tempat() {
        return foto_tempat;
    }

    public void setFoto_tempat(String foto_tempat) {
        this.foto_tempat = foto_tempat;
    }

    public String getStatus_tempat() {
        return status_tempat;
    }

    public void setStatus_tempat(String status_tempat) {
        this.status_tempat = status_tempat;
    }

    public String getNo_hpTempat() {
        return no_hpTempat;
    }

    public void setNo_hpTempat(String no_hpTempat) {
        this.no_hpTempat = no_hpTempat;
    }

    public String getEmailTempat() {
        return emailTempat;
    }

    public void setEmailTempat(String emailTempat) {
        this.emailTempat = emailTempat;
    }
}
