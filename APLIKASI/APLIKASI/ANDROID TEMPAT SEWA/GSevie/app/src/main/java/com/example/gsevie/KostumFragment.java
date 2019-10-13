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


public class KostumFragment extends Fragment {
    private RelativeLayout kategori, kostum;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_kostum, container, false);

        kostum = rootView.findViewById(R.id.kostum_menu);
        kategori = rootView.findViewById(R.id.kategori_menu);

        kostum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KostumActivity.class);
                startActivity(intent);
            }
        });

        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KategoriActivity.class);
                startActivity(intent);
            }
        });

        return rootView;



    }
}
