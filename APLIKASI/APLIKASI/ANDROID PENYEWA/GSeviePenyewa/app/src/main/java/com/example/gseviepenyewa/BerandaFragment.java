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

public class BerandaFragment extends Fragment {
    private RelativeLayout sewa_kostum, tempat_sewa,alamat, profil;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);
        sewa_kostum =rootView.findViewById(R.id.sewa_kostum_menu);
        tempat_sewa = rootView.findViewById(R.id.galeri_sevie_menu);
        alamat = rootView.findViewById(R.id.alamat_menu);
        profil = rootView.findViewById(R.id.profil_menu);

        sewa_kostum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KostumActivity.class);
                startActivity(intent);
            }
        });

        tempat_sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TempatSewaActivity.class);
                startActivity(intent);
            }
        });

        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlamatActivity.class);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfilActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
