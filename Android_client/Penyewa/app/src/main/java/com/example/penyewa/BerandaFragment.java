package com.example.penyewa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.penyewa.MODEL.KostumAll;


public class BerandaFragment extends Fragment {

    Button tempat_sewa, riwayat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_beranda, null);

        tempat_sewa = rootView.findViewById(R.id.tempat_sewa_menu);
        riwayat = rootView.findViewById(R.id.riwayat_menu);

        tempat_sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), KostumActivity.class);
                startActivity(intent);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RiwayatActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
