package com.example.gseviepenyewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gseviepenyewa.MODEL.GetPesan;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;
import com.nex3z.notificationbadge.NotificationBadge;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewaFragment extends Fragment {
    RelativeLayout pemesanan, verifikasi, sewa, riwayat;
    NotificationBadge jumlahPesanMasuk, jumlahValidMasuk, jumlahSewaMasuk;
    APIInterface mApiInterface;
    String id_user;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sewa, container, false);

        id_user = SaveSharedPreferences.getId(getContext());

        pemesanan = rootView.findViewById(R.id.pemesanan_menu);
        verifikasi = rootView.findViewById(R.id.verifikasi_menu);
        sewa = rootView.findViewById(R.id.sewa_menu);
        riwayat = rootView.findViewById(R.id.riwayat_menu);

        jumlahPesanMasuk = rootView.findViewById(R.id.jumlahPesanMasuk);
        jumlahValidMasuk = rootView.findViewById(R.id.jumlahVerifikasiMasuk);
        jumlahSewaMasuk = rootView.findViewById(R.id.jumlahSewaMasuk);

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetPesan> mJumlahMasuk= mApiInterface.hitungPesan(reqid_user);
        mJumlahMasuk.enqueue(new Callback<GetPesan>() {
            @Override
            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahPesanMasuk.setText(response.body().getResult().get(0).getJumlahPesan());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPesan> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });


        mApiInterface =APIClient.getClient().create(APIInterface.class);
        RequestBody reqid= MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetPesan>mValidMasuk= mApiInterface.hitungVerifikasi(reqid);
        mValidMasuk.enqueue(new Callback<GetPesan>() {
            @Override
            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahValidMasuk.setText(response.body().getResult().get(0).getJumlahVerifikasi());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPesan> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });

        mApiInterface =APIClient.getClient().create(APIInterface.class);
        RequestBody requser= MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetPesan>mSewaMasuk= mApiInterface.hitungSewa(requser);
        mSewaMasuk.enqueue(new Callback<GetPesan>() {
            @Override
            public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahSewaMasuk.setText(response.body().getResult().get(0).getJumlahSewa());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPesan> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });



        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PemesananActivity.class);
                startActivity(intent);
            }
        });

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VerifikasiActivity.class);
                startActivity(intent);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SewaActivity.class);
                startActivity(intent);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RiwayatActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


}
