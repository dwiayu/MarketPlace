package com.example.gsevie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gsevie.MODEL.GetKategori;
import com.example.gsevie.MODEL.GetKostum;
import com.example.gsevie.MODEL.Kategori;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertKategoriActivity extends AppCompatActivity {
    EditText kategori;
    Button simpan;
    Context mContext;
    APIInterface mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_kategori);

        mContext= getApplicationContext();
        kategori = (EditText) findViewById(R.id.eNamaKategori);
        simpan = (Button) findViewById(R.id.buttonSimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqnama_kategori= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (kategori.getText().toString().isEmpty()) ? "" : kategori.getText().toString());
                Call<GetKategori> mkategori= mAPIInterface.postKategori(reqnama_kategori);
                if(kategori.getText().toString().length()==0){
                    Toast.makeText(InsertKategoriActivity.this, "data kategori harus di isi!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(InsertKategoriActivity.this, "Sukses insert kategori!", Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(InsertKategoriActivity.this, KategoriActivity.class);
                    startActivity(mIntent);
                }
                mkategori.enqueue(new Callback<GetKategori>() {
                    @Override
                    public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {

                    }

                    @Override
                    public void onFailure(Call<GetKategori> call, Throwable t) {

                    }
                });

            }
        });



    }

    @Override
    public void onBackPressed() {
        // do what you want to do when the "back" button is pressed.
        startActivity(new Intent(InsertKategoriActivity.this, KategoriActivity.class));
        finish();
    }

}
