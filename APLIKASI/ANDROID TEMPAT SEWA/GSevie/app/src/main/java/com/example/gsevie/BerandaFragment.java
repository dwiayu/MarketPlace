package com.example.gsevie;

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
    private RelativeLayout galeri_sevie, penyewa,komentar;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        galeri_sevie = rootView.findViewById(R.id.tempat_sewa_menu);
        penyewa = rootView.findViewById(R.id.penyewa_menu);
        komentar = rootView.findViewById(R.id.komentar_menu);

        galeri_sevie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GaleriSevieActivity.class);
                startActivity(intent);
            }
        });

        penyewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),PenyewaActivity.class);
                startActivity(intent);
            }
        });
        komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), KomentarActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


}
