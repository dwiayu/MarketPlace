package com.example.sikostumpenyewa.REST;

import com.example.sikostumpenyewa.MODEL.GetAlamat;
import com.example.sikostumpenyewa.MODEL.GetDeleteAlamat;
import com.example.sikostumpenyewa.MODEL.GetEditProfil;
import com.example.sikostumpenyewa.MODEL.GetIdentitas;
import com.example.sikostumpenyewa.MODEL.GetKomentar;
import com.example.sikostumpenyewa.MODEL.GetKostumAll;
import com.example.sikostumpenyewa.MODEL.GetLogin;
import com.example.sikostumpenyewa.MODEL.GetPendaftaran;
import com.example.sikostumpenyewa.MODEL.GetPesan;
import com.example.sikostumpenyewa.MODEL.GetProfilId;
import com.example.sikostumpenyewa.MODEL.GetSewa;
import com.example.sikostumpenyewa.MODEL.GetTempat;

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

    //Tampil Profil Penyewa
    @Multipart
    @POST("Penyewa/profil/myProfil")
    Call<GetProfilId> getMyProfile(
            @Part("id_user") RequestBody id_user
    );
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

    //Identitas
    @Multipart
    @POST("Penyewa/Identitasku/myidentitas")
    Call<GetIdentitas>getIdentitas(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("Penyewa/Identitasku/all")
    Call<GetIdentitas>postIdentitas(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("action") RequestBody action
    );
    @Multipart
    @POST("Penyewa/Identitasku/editidentitas")
    Call<GetIdentitas>putIdentitas(
            @Part MultipartBody.Part file,
            @Part("id_identitas") RequestBody id_identitas
    );

    //Alamat
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

    //list pesan
    @Multipart
        @POST("Penyewa/Pesan/getPesan")
        Call<GetPesan>getPesan(
            @Part("id_user") RequestBody id_user
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

    //tampil detail pesan
    @Multipart
    @POST("Penyewa/pesan/detailPesan")
    Call<GetPesan>getDetail(
            @Part("id_sewa") RequestBody id_sewa
    );
    @Multipart
        @POST("Penyewa/Pesan/getVerifikasi")
        Call<GetPesan>getVer(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
        @POST("Penyewa/Pesan/getAmbilSewa")
    Call<GetPesan>getAmbilSewa(
            @Part("id_user") RequestBody id_user
    );
    //update ambil
    @Multipart
    @POST("Penyewa/pesan/updateAmbil")
    Call<GetPesan>putAmbil(
            @Part("id_log") RequestBody id_log
    );
    //riwayat selessai
    @Multipart
    @POST("Penyewa/komentar/getRiwayat")
    Call<GetKomentar>getRiwayat(
            @Part("id_user") RequestBody id_user
    );

    @GET("Penyewa/Kostum/allKostum")
    Call<GetKostumAll>getKostumAll(
    );

    //upload buktisewa
    @Multipart
    @POST("Penyewa/sewa/updateSewa")
    Call<GetPesan>putBuktiSewa(
            @Part MultipartBody.Part file,
            @Part("id_sewa") RequestBody id_sewa

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
    //detail tempat sewa per kostum
    @Multipart
    @POST("Penyewa/tempatsewa/detailTempat")
    Call<GetTempat>getDetailTempat(
            @Part("id_kostum") RequestBody id_kostum
    );
    //tampil review
    @Multipart
    @POST("Penyewa/komentar/tampilReview")
    Call<GetKomentar>getReview(
            @Part("id_kostum") RequestBody id_kostum
    );
//notif pesan
    @Multipart
    @POST("Penyewa/pesan/sewaPesan")
    Call<GetPesan>hitungPesan(
            @Part("id_user") RequestBody id_user
    );
    //notif valid
    @Multipart
    @POST("Penyewa/pesan/sewaValid")
    Call<GetPesan>hitungValid(
            @Part("id_user") RequestBody id_user
    );
    //notif pesan
    @Multipart
    @POST("Penyewa/pesan/sewaSewa")
    Call<GetPesan>hitungSewa(
            @Part("id_user") RequestBody id_user
    );
    //cek identitas untuk memilih kostum

    @Multipart
    @POST("Penyewa/identitasku/cekIdentitas")
    Call<GetIdentitas>cekIdentitas(
            @Part("id_user") RequestBody id_user
    );

    //cek status tempat sewa tutup
    @Multipart
    @POST("Penyewa/kostum/kostumBelanja")
    Call<GetTempat>cekTempatTutup(
            @Part("id_tempat") RequestBody id_tempat
    );


    //cek alamat
    @Multipart
    @POST("Penyewa/Alamat/cekAlamat")
    Call<GetAlamat>cekAlamat(
            @Part("id_user") RequestBody id_user
    );
    //login with email
    @FormUrlEncoded
    @POST("Penyewa/login/loginEmail")
    Call<GetLogin>postEmail(
            @Field("email") String email
    );
}

