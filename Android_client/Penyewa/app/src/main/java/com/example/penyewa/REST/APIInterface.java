package com.example.penyewa.REST;

import com.example.penyewa.MODEL.GetAlamat;
import com.example.penyewa.MODEL.GetDelete;
import com.example.penyewa.MODEL.GetEditProfil;
import com.example.penyewa.MODEL.GetIdentitas;
import com.example.penyewa.MODEL.GetKostumAll;
import com.example.penyewa.MODEL.GetLogin;
import com.example.penyewa.MODEL.GetPendaftaran;
import com.example.penyewa.MODEL.GetPesan;
import com.example.penyewa.MODEL.GetProfilId;

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
            @Part("id_user")RequestBody id_user,
            @Part ("action") RequestBody action
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
            @Part("kota") RequestBody kota,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("Penyewa/Alamat/editalamat")
    Call<GetAlamat>putAlamat(
            @Part("id_alamat") RequestBody id_alamat,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("kota") RequestBody kota
    );
    @Multipart
    @POST("Penyewa/Alamat/deletemy")
    Call<GetDelete>deleteAlamat(
            @Part("id_alamat") RequestBody id_alamat
    );
@Multipart
    @POST("Penyewa/Pesan/getPesan")
    Call<GetPesan>getPesan(
            @Part("id_user") RequestBody id_user
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

}

