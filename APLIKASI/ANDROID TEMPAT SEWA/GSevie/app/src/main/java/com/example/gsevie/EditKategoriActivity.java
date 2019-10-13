package com.example.gsevie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gsevie.MODEL.GetKategori;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKategoriActivity extends AppCompatActivity {
    Context mContext;
    TextView idKategori;
    EditText eNamaKategori;
    Button edit,hapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kategori);

        mContext= getApplicationContext();

        idKategori = (TextView) findViewById(R.id.idKategori);
        eNamaKategori = (EditText) findViewById(R.id.eNamaKategori);
        edit = (Button) findViewById(R.id.buttonSimpanKategori);
        hapus = (Button) findViewById(R.id.buttonHapusKategori);
        final Intent mIntent = getIntent();
        idKategori.setText(mIntent.getStringExtra("id_kategori"));
        eNamaKategori.setText(mIntent.getStringExtra("kategori"));
        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody reqid_kategori= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_kategori")));
                RequestBody reqnama_kategori= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (eNamaKategori.getText().toString().isEmpty()) ? "" : eNamaKategori.getText().toString());
                Call<GetKategori> editKategori = mApiInterface.putKategori(reqid_kategori, reqnama_kategori);
                editKategori.enqueue(new Callback<GetKategori>() {
                    @Override
                    public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditKategoriActivity.this, "Gagal Edit", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditKategoriActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent(getApplicationContext(), KategoriActivity.class);
                            startActivity(mIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKategori> call, Throwable t) {

                    }
                });

            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_kategori= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_kategori")));
                Call<GetKategori> hapusKategori = mApiInterface.deleteKategori(reqid_kategori);
                hapusKategori.enqueue(new Callback<GetKategori>() {
                    @Override
                    public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditKategoriActivity.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditKategoriActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent(getApplicationContext(), KategoriActivity.class);
                            startActivity(mIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKategori> call, Throwable t) {

                    }
                });
            }
        });
    }
}
