package com.example.sikostum.REST;

import com.example.sikostum.MODEL.GetEditProfil;
import com.example.sikostum.MODEL.GetIdentitas;
import com.example.sikostum.MODEL.GetLogin;
import com.example.sikostum.MODEL.GetPendaftaran;
import com.example.sikostum.MODEL.GetProfilId;
import com.example.sikostum.MODEL.PostIdentitas;
import com.example.sikostum.MODEL.ProfilId;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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
            @Part("id_user") RequestBody id_user,
            @Part("nama") RequestBody nama,
            @Part("no_hp") RequestBody no_hp,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );
    @GET("index.php/kategori/all")
    Call<GetIdentitas> getIdentitas();
    @Multipart
    @POST("identitas/all")
    Call<GetIdentitas>postIdentitas(
            @Part MultipartBody.Part file,
      @Part("id_user")RequestBody id_user,
      @Part ("action") RequestBody action
    );





}
