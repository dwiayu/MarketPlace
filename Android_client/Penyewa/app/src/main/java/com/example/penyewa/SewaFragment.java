package com.example.penyewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SewaFragment extends Fragment {

    Button pemesanan, verifikasi, sewa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_sewa, null);

        pemesanan = rootView.findViewById(R.id.pemesanan_menu);
        verifikasi = rootView.findViewById(R.id.verifikasi_menu);
        sewa = rootView.findViewById(R.id.sewa_menu);

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
                Intent intent = new Intent(getContext(), VerifikasiActivity.class);
                startActivity(intent);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SewaActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
