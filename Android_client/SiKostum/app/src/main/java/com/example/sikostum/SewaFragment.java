package com.example.sikostum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class SewaFragment extends Fragment {
    Button pemesanan, sewa;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sewa, container, false);
        pemesanan = rootView.findViewById(R.id.pemesanan_menu);
        sewa = rootView.findViewById(R.id.sewa_menu);

        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent mIntent= new Intent(getContext(),DaftarPemesanan.class);
               startActivity(mIntent);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isi intent;
            }
        });
        return rootView;


    }


}
