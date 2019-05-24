package com.example.penyewa.MODEL;

import com.google.gson.annotations.SerializedName;

public class KostumAll {
@SerializedName("id_kostum")
    private String id_kostum;
@SerializedName("id_tempat")
    private  String id_tempat;
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

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getId_alamat() {
        return id_alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
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

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public KostumAll(String id_kostum, String id_alamat, String id_tempat, String nama_tempat,
                     String alamat, String jumlah_kostum, String harga_kostum, String foto_kostum){
    this.id_kostum = id_kostum;
    this.id_alamat = id_alamat;
    this.id_tempat = id_tempat;
    this.jumlah_kostum= jumlah_kostum;
    this.harga_kostum= harga_kostum;
    this.foto_kostum= foto_kostum;
    this.alamat= alamat;
    this.nama_tempat= nama_tempat;


}
}

