package com.example.penyewa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RiwayatActivity extends AppCompatActivity {
    Button keranjangLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        keranjangLihat= (Button)findViewById(R.id.getKeranjang);

        keranjangLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(),KeranjangActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
