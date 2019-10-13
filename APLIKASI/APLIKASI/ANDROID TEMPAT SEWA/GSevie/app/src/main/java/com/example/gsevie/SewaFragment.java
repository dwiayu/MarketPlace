package com.example.gsevie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gsevie.MODEL.GetPemesanan;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.nex3z.notificationbadge.NotificationBadge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SewaFragment extends Fragment {
    private RelativeLayout pemesanan, verifikasi, sewa, riwayat;
    NotificationBadge jumlahPesanMasuk, jumlahValidMasuk, jumlahSewaMasuk;
    APIInterface mApiInterface;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sewa, container, false);


        pemesanan = rootView.findViewById(R.id.pemesanan_menu);
        verifikasi = rootView.findViewById(R.id.verifikasi_menu);
        sewa = rootView.findViewById(R.id.sewa_menu);
        riwayat = rootView.findViewById(R.id.riwayat_menu);

        jumlahPesanMasuk = rootView.findViewById(R.id.jumlahPesanMasuk);
        jumlahValidMasuk = rootView.findViewById(R.id.jumlahVerifikasiMasuk);
        jumlahSewaMasuk = rootView.findViewById(R.id.jumlahSewaMasuk);

        mApiInterface  = APIClient.getClient().create(APIInterface.class);
        Call<GetPemesanan> mPesan = mApiInterface.getJumlahPesan();
        mPesan.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahPesanMasuk.setText(response.body().getResult().get(0).getJumlahPesan());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {

            }
        });

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetPemesanan> mVerifikasi = mApiInterface.getJumlahVerifikasi();
        mVerifikasi.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahValidMasuk.setText(response.body().getResult().get(0).getJumlahVerifikasi());
                }else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {

            }
        });

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetPemesanan> mSewa= mApiInterface.getJumlahSewa();
        mSewa.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahSewaMasuk.setText(response.body().getResult().get(0).getJumlahSewa());
                }else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {

            }
        });

        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PemesananActivity.class);
                startActivity(intent);
            }
        });
        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),VerifikasiActivity.class);
                startActivity(intent);
            }
        });
        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),SewaActivity.class);
                startActivity(intent);
            }
        });
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), RiwayatActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
