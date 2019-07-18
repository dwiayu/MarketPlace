package com.example.sikostumpenyewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BerandaFragment extends Fragment {

    private Button kostum, riwayat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_beranda, null);

        kostum = rootView.findViewById(R.id.kostum_menu);
        riwayat = rootView.findViewById(R.id.riwayat_menu);

        kostum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),KostumActivity.class);
                startActivity(intent);
            }
        });
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),RiwayatActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}