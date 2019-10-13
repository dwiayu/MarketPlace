package com.example.gseviepenyewa.REST;


import com.example.gseviepenyewa.MODEL.GetAlamat;
import com.example.gseviepenyewa.MODEL.GetDeleteAlamat;
import com.example.gseviepenyewa.MODEL.GetEditProfil;
import com.example.gseviepenyewa.MODEL.GetKeranjang;
import com.example.gseviepenyewa.MODEL.GetKomentar;
import com.example.gseviepenyewa.MODEL.GetKostumAll;
import com.example.gseviepenyewa.MODEL.GetLogin;
import com.example.gseviepenyewa.MODEL.GetPendaftaran;
import com.example.gseviepenyewa.MODEL.GetPesan;
import com.example.gseviepenyewa.MODEL.GetProfilId;
import com.example.gseviepenyewa.MODEL.GetSewa;
import com.example.gseviepenyewa.MODEL.GetTempat;

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
    //Login Penyewa
    @FormUrlEncoded
    @POST("Penyewa/login/login")
    Call<GetLogin> loginPenyewa(
            @Field("username") String username,
            @Field("password") String password
    );
    //login with email
    @FormUrlEncoded
    @POST("Penyewa/login/loginEmail")
    Call<GetLogin>postEmail(
            @Field("email") String email
    );

    //insert pendaftaran penyewa
    @Multipart
    @POST("Penyewa/pendaftaran/all")
    Call<GetPendaftaran> postPendaftaran(
            @Part MultipartBody.Part file,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("email") RequestBody email,
            @Part("no_hp") RequestBody noHp,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    //TAMPIL Profil user
    @Multipart
    @POST("Penyewa/profil/myProfil")
    Call<GetProfilId> getMyProfile(
            @Part("id_user") RequestBody id_user
    );

    //edit profil user
    @Multipart
    @POST("Penyewa/profil/myedit")
    Call<GetEditProfil>postEditProfil(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("no_hp") RequestBody no_hp,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );


    //Tampil Profil Tempat Sewa
    @GET("Penyewa/TempatSewa/myTempat")
    Call<GetTempat>getTempatSewa(
    );

    //Alamat
    @Multipart
    @POST("Penyewa/Alamat/myAlamat")
    Call<GetAlamat> getAlamat(
            @Part("id_user") RequestBody id_user

    );

    @Multipart
    @POST("Penyewa/Alamat/all")
    Call<GetAlamat>postAlamat(

            @Part("id_user") RequestBody id_user,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("provinsi") RequestBody provinsi,
            @Part("kota") RequestBody kota,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("desa") RequestBody desa,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("Penyewa/Alamat/editalamat")
    Call<GetAlamat>putAlamat(
            @Part("id_alamat") RequestBody id_alamat,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("provinsi") RequestBody provinsi,
            @Part("kota") RequestBody kota,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("desa") RequestBody desa
    );
    @Multipart
    @POST("Penyewa/Alamat/deletemy")
    Call<GetDeleteAlamat>deleteAlamat(
            @Part("id_alamat") RequestBody id_alamat
    );

    //cek alamat
    @Multipart
    @POST("Penyewa/Alamat/cekAlamat")
    Call<GetAlamat>cekAlamat(
            @Part("id_user") RequestBody id_user
    );

    //insert sewa
    @Multipart
    @POST("cart/keranjang/sewa")
    Call<GetSewa>postSewa(
            @Part("id_user") RequestBody id_user,
            @Part("id_alamat") RequestBody id_alamat,
            @Part("tgl_sewa") RequestBody tgl_sewa
    );

    //insert detail
    @Multipart
    @POST("cart/keranjang/detail")
    Call<GetSewa>postDetail(
            @Part("id_sewa") RequestBody id_sewa,
            @Part("id_kostum") RequestBody id_kostum,
            @Part("jumlah") RequestBody jumlah

    );

    //cek stok
    @Multipart
    @POST("Cart/keranjang/cekStok")
    Call<GetKostumAll>cekKostum(
            @Part("id_kostum") RequestBody id_kostum
    );

    // mendapatkan data semua kostum
    @GET("Penyewa/Kostum/allKostum")
    Call<GetKostumAll>getKostumAll(
    );

    //notif pemesanan
    @Multipart
    @POST("Penyewa/pesan/countPesan")
    Call<GetPesan>hitungPesan(
            @Part("id_user") RequestBody id_user
    );
    //notif verifikasi
    @Multipart
    @POST("Penyewa/pesan/countVerifikasi")
    Call<GetPesan>hitungVerifikasi(
            @Part("id_user") RequestBody id_user
    );
    //notif sewa
    @Multipart
    @POST("Penyewa/pesan/countSewa")
    Call<GetPesan>hitungSewa(
            @Part("id_user") RequestBody id_user
    );

    //notif riwayat
    @Multipart
    @POST("Penyewa/pesan/countRiwayat")
    Call<GetPesan>hitungRiwayat(@Part("id_user") RequestBody id_user);

    //list pemesanan
    @Multipart
    @POST("Penyewa/Pesan/Pesan")
    Call<GetPesan>getPesan(
            @Part("id_user") RequestBody id_user
    );

    //list verifikasi
    @Multipart
    @POST("Penyewa/Pesan/Verifikasi")
    Call<GetPesan>getVerifikasi(
            @Part("id_user") RequestBody id_user
    );

    //list sewa
    @Multipart
    @POST("Penyewa/Pesan/Sewa")
    Call<GetPesan>getSewa(@Part("id_user") RequestBody id_user);

    //list riwayat
    @Multipart
    @POST("Penyewa/Pesan/Riwayat")
    Call<GetPesan>getRiwayat(@Part("id_user") RequestBody id_user);

    //list detail pemesanan
    @Multipart
    @POST("Penyewa/pesan/detailPesan")
    Call<GetPesan>detailPesan(
            @Part("id_sewa") RequestBody id_sewa
    );
    //list detail verifikasi
    @Multipart
    @POST("Penyewa/pesan/detailVerifikasi")
    Call<GetPesan>detailVerifikasi(
            @Part("id_sewa") RequestBody id_sewa
    );
    //list detail sewa
    @Multipart
    @POST("Penyewa/pesan/detailSewa")
    Call<GetPesan>detailSewa(
            @Part("id_sewa") RequestBody id_sewa
    );
    //list detail riwayat
    @Multipart
    @POST("Penyewa/pesan/detailRiwayat")
    Call<GetPesan>detailRiwayat(
            @Part("id_sewa") RequestBody id_sewa
    );

    //lihat denda
    @Multipart
    @POST("Penyewa/pesan/tampilDenda")
    Call<GetPesan>getDenda(
            @Part("id_sewa") RequestBody id_sewa
    );

    //tambah komentar
    @Multipart
    @POST("Penyewa/komentar/tambahKomentar")
    Call<GetKomentar>postKomentar(
            @Part ("id_user") RequestBody id_user,
            @Part ("id_detail") RequestBody id_detail,
            @Part("komentar") RequestBody komentar
    );

    //tampilKomentar
    @Multipart
    @POST("Penyewa/komentar/tampilKomentar")
    Call<GetKomentar>getKomentar(
            @Part("id_detail") RequestBody id_detail
    );

    //tampil review
    @Multipart
    @POST("Penyewa/komentar/tampilReview")
    Call<GetKomentar>getReview(
            @Part("id_kostum") RequestBody id_kostum
    );
    //upload buktisewa
    @Multipart
    @POST("Penyewa/sewa/updateSewa")
    Call<GetPesan>putBuktiSewa(
            @Part MultipartBody.Part file,
            @Part("id_sewa") RequestBody id_sewa

    );

    //tempat sewa tutup
    @GET("Penyewa/tempatSewa/tempatTutup")
    Call<GetTempat>getTutup();


}

