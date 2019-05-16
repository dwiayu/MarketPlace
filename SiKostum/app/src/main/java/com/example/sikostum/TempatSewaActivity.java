package com.example.sikostum;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class TempatSewaActivity extends AppCompatActivity {
    private Button editTempat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_sewa);

        //inisial tombol
        editTempat= (Button)findViewById(R.id.buttonEditTempat);


        // function tombol
        editTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yap = new Intent(getApplicationContext(),EditTempatActivity.class);
                startActivity(yap);
            }
        });

        ScrollView sView = (ScrollView) findViewById(R.id.kelola_tempat);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
    }
}
