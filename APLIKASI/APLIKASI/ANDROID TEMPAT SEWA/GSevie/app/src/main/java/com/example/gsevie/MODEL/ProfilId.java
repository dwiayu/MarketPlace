package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class ProfilId {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @SerializedName("email")
    private String email;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("foto_user")
    private String foto_user;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;


    public ProfilId(String id_user, String nama, String jenis_kelamin, String email, String no_hp, String foto_user, String username, String password) {
        this.id_user = id_user;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.no_hp = no_hp;
        this.foto_user = foto_user;
        this.username = username;
        this.password = password;
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

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
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
