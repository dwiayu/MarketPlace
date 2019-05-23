package com.example.sikostum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostum.MODEL.GetDenda;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class LayarInsertDenda extends AppCompatActivity {
EditText jumlahDenda, edKeterangan;
Button simpanDenda;
TextView tvIdDetail;
APIInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_denda);

        jumlahDenda =(EditText) findViewById(R.id.edJumlahDenda);
        edKeterangan = (EditText) findViewById(R.id.edKeterangan);
        simpanDenda= (Button) findViewById(R.id.btSimpanDenda);
        tvIdDetail =(TextView) findViewById(R.id.tvIdDetail);
        final Intent mIntent = getIntent();
        tvIdDetail.setText(mIntent.getStringExtra("id_detail"));


        simpanDenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_detail")));
                RequestBody reqjumlah_denda = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (jumlahDenda.getText().toString().isEmpty()) ? "" : jumlahDenda.getText().toString());
                RequestBody reqketerangan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edKeterangan.getText().toString().isEmpty()) ? "" : edKeterangan.getText().toString());
                Call<GetDenda>mDenda = mApiInterface.postDenda(reqid_detail,reqjumlah_denda,reqketerangan);
                mDenda.enqueue(new Callback<GetDenda>() {
                    @Override
                    public void onResponse(Call<GetDenda> call, Response<GetDenda> response) {
                        if (response.body().getStatus().equals("success")){
                            Toast.makeText(LayarInsertDenda.this, "Sukses insert tempat!", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent(getApplicationContext(), beranda.class);
                            startActivity(mIntent);

                        }else{
                            Toast.makeText(LayarInsertDenda.this, "Gagal insert!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDenda> call, Throwable t) {
                        Log.d("Insert Denda", t.getMessage());
                    }
                });

            }
        });
    }

}
