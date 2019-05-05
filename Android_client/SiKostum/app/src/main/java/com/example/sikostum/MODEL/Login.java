package com.example.sikostum.MODEL;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenis_kelamin")
    private String jenisKelamin;
    @SerializedName("email")
    private String email;
    @SerializedName("no_hp")
    private String noHp;
    @SerializedName("foto_user")
    private String fotoUser;

    public Login(String idUser, String nama, String jenisKelamin, String email, String noHp, String fotoUser){
        this.idUser= idUser;
        this.nama= nama;
        this.jenisKelamin=jenisKelamin;
        this.email=email;
        this.noHp=noHp;
        this.fotoUser=fotoUser;

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(String fotoUser) {
        this.fotoUser = fotoUser;
    }



}

