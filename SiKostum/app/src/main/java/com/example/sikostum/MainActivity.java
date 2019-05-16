package com.example.sikostum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private int waktu_loading=1500;

    //1500=1,5 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent login=new Intent(MainActivity.this, activity_login.class);
                startActivity(login);
                finish();

            }
        },waktu_loading);
    }
}
