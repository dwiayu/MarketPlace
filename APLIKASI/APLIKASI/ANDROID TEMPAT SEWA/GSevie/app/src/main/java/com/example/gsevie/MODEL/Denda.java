package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class Denda {

    @SerializedName("id_denda")
    private  String id_denda;
    @SerializedName("id_sewa")
    private String id_sewa;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;
    @SerializedName("keterangan")
    private String keterangan;


    public String getId_denda() {
        return id_denda;
    }

    public void setId_denda(String id_denda) {
        this.id_denda = id_denda;
    }

    public String getId_sewa() {
        return id_sewa;
    }

    public void setId_sewa(String id_detail) {
        this.id_sewa = id_sewa;
    }

    public String getJumlah_denda() {
        return jumlah_denda;
    }

    public void setJumlah_denda(String jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Denda(String id_denda, String id_sewa, String jumlah_denda, String keterangan){
        this.id_denda= id_denda;
        this.id_sewa= id_sewa;
        this.jumlah_denda= jumlah_denda;
        this.keterangan= keterangan;

    }


}
