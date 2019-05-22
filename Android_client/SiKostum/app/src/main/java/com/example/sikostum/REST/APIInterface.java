package com.example.sikostum.REST;

import com.example.sikostum.MODEL.GetAlamat;
import com.example.sikostum.MODEL.GetDelete;
import com.example.sikostum.MODEL.GetEditProfil;
import com.example.sikostum.MODEL.GetIdentitas;
import com.example.sikostum.MODEL.GetKategori;
import com.example.sikostum.MODEL.GetKostum;
import com.example.sikostum.MODEL.GetLogin;
import com.example.sikostum.MODEL.GetPemesanan;
import com.example.sikostum.MODEL.GetPendaftaran;
import com.example.sikostum.MODEL.GetProfilId;
import com.example.sikostum.MODEL.GetTempat;
import com.example.sikostum.MODEL.PostIdentitas;
import com.example.sikostum.MODEL.ProfilId;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {
    @FormUrlEncoded
    @POST("Login/login")
    Call<GetLogin> loginToko(
            @Field("username") String username,
            @Field("password") String password);
//@GET ("com/example/dell/si_kostum/REST/user")
//Call<AuthLogin> login(@Field("username") String username,
//                      @Field("password") String password;

    //tampil kostum
//    @GET("Kostum/user")
//    Call<GetKostum> getKostum();
//
//    //input kostum
//    @FormUrlEncoded
//    @POST("Kostum/user")
//    Call<PostPutDelKostum> postKostum
//    (
//            @Field("id_kategori") String idKategori, @Field("id_toko") String id_toko,
//            @Field("nama_kostum") String namaKostum,
//            @Field("jumlah_kostum") String jumlahKostum,
//            @Field("harga_kostum") String hargaKostum,
//            @Field("deskripsi_kostum") String deskripsiKostum,
//            @Field("foto_kostum") String fotoKostum);
//
//    //Update Kostum
//    @FormUrlEncoded
//    @PUT("Kostum/user")
//    Call<PostPutDelKostum>putKostum(
//            @Field("id_kategori") String idKategori, @Field("id_toko") String id_toko,
//            @Field("nama_kostum") String namaKostum,
//            @Field("jumlah_kostum") String jumlahKostum,
//            @Field("harga_kostum") String hargaKostum,
//            @Field("deskripsi_kostum") String deskripsiKostum,
//            @Field("foto_kostum") String fotoKostum
//    );
//
//    //Delete Kostum
//    @FormUrlEncoded
//    @HTTP(method = "DELETE", path = "Kostum/user", hasBody = true)
//    Call<PostPutDelKostum> deleteKostum(@Field("id_kostum") String idKostum);

    //insert kategori
    @Multipart
    @POST("pendaftaran/all")
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
//    //GET PROFIL CUSTOMER
//    @GET("index.php/api/customer/uname/username/{username}")
//    Call<CustomerByUsernameRespond> profileCustByUsername(@Path("username") String username);
//
//    //GET PROFIL CUSTOMER
//    @GET("index.php/api/customer/customer/id/{custId}")
//    Call<ProfilRespon> getProfil(@Path("custId") String custId);

    //TAMPIL Profil
    @Multipart
    @POST("profil/myProfil")
    Call<GetProfilId> getMyProfile(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("profil/myedit")
    Call<GetEditProfil>postEditProfil(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("nama") RequestBody nama,
            @Part("no_hp") RequestBody no_hp,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    //Identitas
    @Multipart
    @POST("identitas/myidentitas")
    Call<GetIdentitas> getIdentitas(
        @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("identitas/all")
    Call<GetIdentitas>postIdentitas(
            @Part MultipartBody.Part file,
      @Part("id_user")RequestBody id_user,
      @Part ("action") RequestBody action
    );
    @Multipart
    @POST("identitas/editidentitas")
    Call<GetIdentitas>putIdentitas(
            @Part MultipartBody.Part file,
            @Part("id_identitas") RequestBody id_identitas
    );

    //Alamat
    @Multipart
    @POST("alamat/myAlamat")
    Call<GetAlamat> getAlamat(
            @Part("id_user") RequestBody id_user

    );

@Multipart
    @POST("alamat/all")
    Call<GetAlamat>postAlamat(

     @Part("id_user") RequestBody id_user,
     @Part("label_alamat") RequestBody label_alamat,
     @Part("alamat") RequestBody alamat,
     @Part("kota") RequestBody kota,
     @Part("action") RequestBody action
    );

@Multipart
    @POST("alamat/editalamat")
    Call<GetAlamat>putAlamat(
            @Part("id_alamat") RequestBody id_alamat,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("kota") RequestBody kota
 );
@Multipart
    @POST("alamat/deletemy")
    Call<GetDelete>deleteAlamat(
        @Part("id_alamat") RequestBody id_alamat
);
@Multipart
    @POST("TempatSewa/insertTempat")
    Call<GetTempat>posTempat(
        @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("id_alamat") RequestBody id_alamat,
            @Part("nama_tempat") RequestBody nama_tempat,
            @Part("no_rekening") RequestBody no_rekening,
            @Part("slogan_tempat") RequestBody slogan_tempat,
            @Part("deskripsi_tempat")RequestBody deskripsi_tempat,
            @Part("status_tempat") RequestBody status_tempat
);
@Multipart
    @POST("TempatSewa/alamat")
    Call<GetAlamat>getAlm(
            @Part("id_user") RequestBody id_user
//            @Part("id_alamat") RequestBody id_alamat

);
@Multipart
    @POST("TempatSewa/tampilTempat")
    Call<GetTempat>getTempat(
            @Part("id_user") RequestBody id_user
);
@Multipart
    @POST("TempatSewa/updateTempat")
    Call<GetTempat>putTempat(
        @Part MultipartBody.Part file,
        @Part("id_tempat") RequestBody id_tempat,
        @Part("id_alamat") RequestBody id_alamat,
        @Part("nama_tempat") RequestBody nama_tempat,
        @Part("no_rekening") RequestBody no_rekening,
        @Part("slogan_tempat") RequestBody slogan_tempat,
        @Part("deskripsi_tempat") RequestBody deskrisi_tempat,
        @Part("status") RequestBody status
        );
//KOSTUM

    @GET("Kostum/getKategori")
    Call<GetKategori>getKategori();
    @Multipart
    @POST("Kostum/insertKostum")
    Call<GetKostum>postKostum(
            @Part MultipartBody.Part file,

            @Part("id_kategori")RequestBody id_kategori,
            @Part("id_tempat") RequestBody id_tempat,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );
    @Multipart
    @POST("Kostum/tampilKostum")
    Call<GetKostum>tampilKostum(
            @Part("id_user") RequestBody id_user

    );
    @Multipart
    @POST("TempatSewa/statusIdentitas")
    Call<GetIdentitas>tampilIdentitas(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("Kostum/updateKostum")
    Call<GetKostum>putKostum(
            @Part MultipartBody.Part file,
            @Part ("id_kostum") RequestBody id_kostum,
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );
    @Multipart
    @POST("Kostum/hapusKostum")
    Call<GetKostum> deleteKostum(
            @Part ("id_kostum") RequestBody id_kostum
    );
    //Pemesanan
    @Multipart
    @POST("Pemesanan/tampilPemesanan")
    Call<GetPemesanan>getPemesanan(
            @Part("id_user") RequestBody id_user
            );
    @Multipart
    @POST("Pemesanan/getSewa")
    Call<GetPemesanan>getSewa(
            @Part("id_user") RequestBody id_user
    );

}
