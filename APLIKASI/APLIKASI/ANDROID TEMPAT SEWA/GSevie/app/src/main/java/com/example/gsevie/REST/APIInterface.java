package com.example.gsevie.REST;


import com.example.gsevie.MODEL.GetAlamat;
import com.example.gsevie.MODEL.GetDenda;
import com.example.gsevie.MODEL.GetKategori;
import com.example.gsevie.MODEL.GetKomentar;
import com.example.gsevie.MODEL.GetKostum;
import com.example.gsevie.MODEL.GetLogin;
import com.example.gsevie.MODEL.GetPemesanan;
import com.example.gsevie.MODEL.GetPesanOff;
import com.example.gsevie.MODEL.GetProfilId;
import com.example.gsevie.MODEL.GetTempat;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @FormUrlEncoded
    @POST("TempatSewa/Login/login")
    Call<GetLogin> loginToko(
            @Field("username") String username,
            @Field("password") String password);

    //login with email
    @FormUrlEncoded
    @POST("TempatSewa/login/loginEmail")
    Call<GetLogin>postEmail(
            @Field("email") String email
    );

    //TAMPIL Profil
    @Multipart
    @POST("TempatSewa/profil/myProfil")
    Call<GetTempat> getMyProfile(
            @Part("id_tempat") RequestBody id_tempat
    );

    //tampil tempat sewa
    @Multipart
    @POST("TempatSewa/TempatSewa/tampilTempat")
    Call<GetTempat>getTempat(
            @Part("id_tempat") RequestBody id_tempat
    );

    //update tempat
    @Multipart
    @POST("TempatSewa/TempatSewa/updateTempat")
    Call<GetTempat>putTempat(
            @Part MultipartBody.Part file,
            @Part("id_tempat") RequestBody id_tempat,
            @Part("nama_tempat") RequestBody nama_tempat,
            @Part("no_rekening") RequestBody no_rekening,
            @Part("no_hp") RequestBody nohp,
            @Part("email") RequestBody email,
            @Part("slogan_tempat") RequestBody slogan_tempat,
            @Part("deskripsi_tempat") RequestBody deskripsi_tempat,
            @Part("status_tempat") RequestBody status_tempat,
            @Part("alamat") RequestBody alamat,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    //tampil all user
    @GET("TempatSewa/Profil/allUser")
    Call<GetProfilId>getAllUser();

    //tampil komentar
    @GET("TempatSewa/Pemesanan/cekKomentar")
    Call<GetKomentar>lihatKomentar();

    //tampil kategori
    @GET("TempatSewa/kostum/tampilKategori")
    Call<GetKategori>lihatKategori();

    //tampil kostum
    @GET("TempatSewa/Kostum/tampilKostum")
    Call<GetKostum>tampilKostum(
    );

    //insert kostum
    @Multipart
    @POST("TempatSewa/Kostum/insertKostum")
    Call<GetKostum>postKostum(
            @Part MultipartBody.Part file,
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );

    //update kostum
    @Multipart
    @POST("TempatSewa/Kostum/updateKostum")
    Call<GetKostum>putKostum(
            @Part MultipartBody.Part file,
            @Part("id_kostum") RequestBody id_kostum,
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );
    //hapus kostum
    @Multipart
    @POST("TempatSewa/Kostum/hapusKostum")
    Call<GetKostum> deleteKostum(
            @Part("id_kostum") RequestBody id_kostum
    );
    //cek id kostum sebelum hapus
    @Multipart
    @POST("TempatSewa/Kostum/cekKostum")
    Call<GetKostum>cekKostum(
            @Part("id_kostum") RequestBody id_kostum
    );

    //get kategori
    @GET("TempatSewa/Kostum/getKategori")
    Call<GetKategori>getKategori();

    //insert kategori
    @Multipart
    @POST("TempatSewa/Kostum/insertKategori")
    Call<GetKategori>postKategori(
            @Part("kategori") RequestBody kategori
    );

    //hapus kategori
    @Multipart
    @POST("TempatSewa/Kostum/hapusKategori")
    Call<GetKategori>deleteKategori(
            @Part("id_kategori") RequestBody id_kategori
    );

    //update Katgeori
    @Multipart
    @POST("TempatSewa/Kostum/updateKategori")
    Call<GetKategori>putKategori(
            @Part("id_kategori") RequestBody id_kategori,
            @Part("kategori") RequestBody kategori
    );

    //update offline 'kembali'
    @Multipart
    @POST("TempatSewa/Pemesanan/kembaliOff")
    Call<GetPesanOff>putOff(
            @Part("id_pemesanan") RequestBody id_pemesanan,
            @Part("denda") RequestBody denda,
            @Part("keterangan") RequestBody keterangan

    );

    //tampil riwayat off
    @GET("TempatSewa/Pemesanan/riwayatOff")
    Call<GetPesanOff>getRiwayatOff();

    //tampil jumlah pesan
    @GET("TempatSewa/Pemesanan/countPesan")
    Call<GetPemesanan>getJumlahPesan();

    //tampil jumlah verifikasi
    @GET("TempatSewa/Pemesanan/countVerifikasi")
    Call<GetPemesanan>getJumlahVerifikasi();

    //tampil jumlah sewa
    @GET("TempatSewa/Pemesanan/countSewa")
    Call<GetPemesanan>getJumlahSewa();

    //tampil jumlah riwayat
    @GET("TempatSewa/Pemesanan/countRiwayat")
    Call<GetPemesanan>getJumlahRiwayat();

    //tampil jumlah pinjam
    @GET("TempatSewa/Pemesanan/countPinjam")
    Call<GetPesanOff>getJumlahPinjam();

    //tampil jumlah kembali
    @GET("TempatSewa/Pemesanan/countKembali")
    Call<GetPesanOff>getJumlahKembali();

    //input offline
    @Multipart
    @POST("TempatSewa/Pemesanan/pesanOff")
    Call<GetPesanOff>postOff(
            @Part("id_kostum") RequestBody id_kostum,
            @Part("jumlah") RequestBody jumlah,
            @Part("nama") RequestBody nama,
            @Part("no_hp") RequestBody no_hp,
            @Part("alamat") RequestBody alamat,
            @Part("tgl_sewa") RequestBody tgl_sewa
    );

    //tampil offline
    @GET("TempatSewa/Pemesanan/tampilOff")
    Call<GetPesanOff> tampilOff();

    //Pemesanan
    @GET("TempatSewa/Pemesanan/tampilPemesanan")
    Call<GetPemesanan>getPemesanan();

    //tampil pemesanan valid
    @GET("TempatSewa/Pemesanan/sewaValid")
    Call<GetPemesanan>getValid();

    //tampil pemesanan 'ambil'
    @GET("TempatSewa/Pemesanan/sewaPinjam")
    Call<GetPemesanan>getPinjam();

    //tampil riwayat selesai
    @GET("TempatSewa/Pemesanan/tampilSelesai")
    Call<GetPemesanan>getSelesai();

    //tampil bukti
    @Multipart
    @POST("TempatSewa/Pemesanan/tampilBukti")
    Call<GetPemesanan>getBukti(
            @Part("id_sewa") RequestBody id_sewa
    );

    //get detail pemesanan
    @Multipart
    @POST("TempatSewa/Pemesanan/tampilDetail")
    Call<GetPemesanan>getDetail(
            @Part("id_sewa") RequestBody id_sewa
    );

    //verifikasi buktisewa(valid)
    @Multipart
    @POST("TempatSewa/Pemesanan/updateBukti")
    Call<GetPemesanan>putBukti(
            @Part("id_sewa") RequestBody id_sewa
    );

    //verifikasi buktisewa(tidak valid)
    @Multipart
    @POST("TempatSewa/Pemesanan/updateBatal")
    Call<GetPemesanan>updateBatal(
            @Part("id_sewa") RequestBody id_sewa
    );

    //tampil Riwayat detail selesai
    @Multipart
    @POST("TempatSewa/Pemesanan/detailSelesai")
    Call<GetPemesanan>detailSelesai(
            @Part("id_sewa") RequestBody id_sewa
    );

    //tampil detail ambil
    @Multipart
    @POST("TempatSewa/Pemesanan/detailPinjam")
    Call<GetPemesanan>getDetailPinjam(
            @Part("id_sewa") RequestBody id_sewa
    );

    //input denda
    @Multipart
    @POST("TempatSewa/Pemesanan/inputDenda")
    Call<GetDenda>inputDenda(
            @Part("id_sewa") RequestBody id_sewa,
            @Part("denda") RequestBody denda,
            @Part ("keterangan") RequestBody keterangan
    );

    //update status menjadi 'selesai'
    @Multipart
    @POST("TempatSewa/Pemesanan/updateSelesai")
    Call<GetPemesanan>putSelesai(
            @Part("id_sewa") RequestBody id_sewa
    );

    //update status menjadi pinjam
    @Multipart
    @POST("TempatSewa/Pemesanan/updatePinjam")
    Call<GetPemesanan>putPinjam(
            @Part("id_sewa") RequestBody id_Sewa
    );

    //tampil detail valid
    @Multipart
    @POST("TempatSewa/Pemesanan/detailValid")
    Call<GetPemesanan>getDetailValid(
            @Part("id_sewa") RequestBody id_sewa
    );

    //tampil kostum berdasarkan id
    @Multipart
    @POST("TempatSewa/Kostum/kostumGambar")
    Call<GetKostum>getGambar(
            @Part("id_kostum") RequestBody id_kostum
    );

    //lihat denda
    @Multipart
    @POST("TempatSewa/pemesanan/tampilDenda")
    Call<GetPemesanan>getDenda(
            @Part("id_sewa") RequestBody id_sewa
    );

    //get Alamat
    //Alamat
    @Multipart
    @POST("TempatSewa/Alamat/myAlamat")
    Call<GetAlamat>getAlamat(
            @Part("id_user") RequestBody id_user

    );
}
