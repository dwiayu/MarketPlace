package com.example.gsevie.MODEL;

import com.google.gson.annotations.SerializedName;

public class PesanOff {
    @SerializedName("id_pemesanan")
    private String id_pemesanan;
    @SerializedName("id_kostum")
    private  String id_kostum;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("jumlah")
    private  String jumlah;
    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tgl_sewa")
    private String tgl_sewa;
    @SerializedName("status_pesan")
    private String  status_pesan;
    @SerializedName("tgl_kembali")
    private String tgl_kembali;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("jumlahPinjam")
    private String jumlahPinjam;
    @SerializedName("jumlahKembali")
    private String jumlahKembali;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("denda")
    private String denda;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("jumlahTerlambat")
    private String jumlahTerlambat;

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_sewa() {
        return tgl_sewa;
    }

    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getStatus_pesan() {
        return status_pesan;
    }

    public void setStatus_pesan(String status_pesan) {
        this.status_pesan = status_pesan;
    }

    public String getId_pemesanan() {
        return id_pemesanan;
    }

    public void setId_pemesanan(String id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public PesanOff(String id_pemesanan, String nama_kostum, String id_kostum, String jumlah, String nama,
                    String alamat, String tgl_sewa, String tgl_kembali, String status_pesan, String foto_kostum,
                    String jumlahPinjam, String jumlahKembali) {
        this.id_pemesanan= id_pemesanan;
        this.nama_kostum = nama_kostum;
        this.id_kostum = id_kostum;
        this.jumlah = jumlah;
        this.nama = nama;
        this.alamat = alamat;
        this.tgl_sewa = tgl_sewa;
        this.tgl_kembali= tgl_kembali;
        this.status_pesan = status_pesan;
        this.foto_kostum= foto_kostum;
        this.jumlahPinjam = jumlahPinjam;
        this.jumlahKembali = jumlahKembali;

    }

    public String getJumlahPinjam() {
        return jumlahPinjam;
    }

    public void setJumlahPinjam(String jumlahPinjam) {
        this.jumlahPinjam = jumlahPinjam;
    }

    public String getJumlahKembali() {
        return jumlahKembali;
    }

    public void setJumlahKembali(String jumlahKembali) {
        this.jumlahKembali = jumlahKembali;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJumlahTerlambat() {
        return jumlahTerlambat;
    }

    public void setJumlahTerlambat(String jumlahTerlambat) {
        this.jumlahTerlambat = jumlahTerlambat;
    }
}
