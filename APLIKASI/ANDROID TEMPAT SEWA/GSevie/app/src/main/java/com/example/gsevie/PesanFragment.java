package com.example.gsevie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gsevie.MODEL.GetPesanOff;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.nex3z.notificationbadge.NotificationBadge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PesanFragment extends Fragment {
    private RelativeLayout peminjaman, sewa, riwayat;
    NotificationBadge jumlahSewaMasuk;
    APIInterface mApiInterface;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_pesan, container, false);

        peminjaman = rootView.findViewById(R.id.peminjaman_menu);
        sewa = rootView.findViewById(R.id.pinjam_menu);
        riwayat = rootView.findViewById(R.id.kembali_menu);

        jumlahSewaMasuk = rootView.findViewById(R.id.jumlahSewaMasuk);
        peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getContext(), PeminjamanActivity.class);
                        startActivity(intent);

            }
        });

        mApiInterface  = APIClient.getClient().create(APIInterface.class);
        Call<GetPesanOff> mSewa= mApiInterface.getJumlahPinjam();
        mSewa.enqueue(new Callback<GetPesanOff>() {
            @Override
            public void onResponse(Call<GetPesanOff> call, Response<GetPesanOff> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahSewaMasuk.setText(response.body().getResult().get(0).getJumlahPinjam());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPesanOff> call, Throwable t) {

            }
        });


        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SewaOfflineActivity.class);
                startActivity(intent);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RiwayatOfflineActivity.class);
                startActivity(intent);
            }
        });
        return rootView;

    }
}
