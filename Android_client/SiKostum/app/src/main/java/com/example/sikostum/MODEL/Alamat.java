package com.example.sikostum.MODEL;

import com.google.gson.annotations.SerializedName;

public class Alamat {
    @SerializedName("id_alamat")
    private  String id_alamat;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("label_alamat")
    private String label_alamat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("kota")
    private String kota;
    private String action;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getLabel_alamat() {
        return label_alamat;
    }

    public void setLabel_alamat(String label_alamat) {
        this.label_alamat = label_alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId_alamat() {
        return id_alamat;
    }
    public String getAlamatIn(){
        return id_alamat;
    }
    public void setAlamatIn(String alamatIn){
        this.id_alamat = alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public Alamat(String id_alamat, String id_user, String label_alamat, String alamat, String kota, String action){
        this.id_user = id_user;
        this.label_alamat = label_alamat;
        this.alamat = alamat;
        this.id_alamat =alamat;
        this.kota = kota;
        this.action = action;
        this.id_alamat = id_alamat;
        this.setId_alamat(id_alamat);
        this.setAlamat(alamat);

    }
    public String getIdAlamat(){
        return id_alamat;
    }
    public  void setIdAlamat(String id_alamat ){
        this.id_alamat=id_alamat;
    }
    @Override
    public String toString(){
        return alamat;
    }
    public void setName(String alamat){this.alamat=alamat;}
}
