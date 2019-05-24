package com.example.penyewa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.penyewa.MODEL.GetAlamat;
import com.example.penyewa.MODEL.GetDelete;
import com.example.penyewa.REST.APIClient;
import com.example.penyewa.REST.APIInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAlamatActivity extends AppCompatActivity {
    Context mContext;
    EditText edLabel, edAlamat, edKota;
    TextView tvMsg;
    Button btUpdateAl, btDeleteAl;
    APIInterface mAPIInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alamat);
        mContext = getApplicationContext();

        edLabel = (EditText) findViewById(R.id.edLabel);
        edAlamat = (EditText) findViewById(R.id.edAlamat);
        edKota = (EditText) findViewById(R.id.edKota);
        btUpdateAl = (Button) findViewById(R.id.bt_updateAl);
        btDeleteAl = (Button) findViewById(R.id.bt_hapusAl);
        tvMsg = (TextView) findViewById(R.id.tvMssgAl);

        final Intent mIntent = getIntent();
        edLabel.setText(mIntent.getStringExtra("label_alamat"));
        edAlamat.setText(mIntent.getStringExtra("alamat"));
        edKota.setText(mIntent.getStringExtra("kota"));


        final APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);

        btUpdateAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_alamat")));
                RequestBody reqlabel_alamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edLabel.getText().toString().isEmpty()) ?
                                        "" : edLabel.getText().toString());
                RequestBody reqalamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edAlamat.getText().toString().isEmpty()) ?
                                        "" : edAlamat.getText().toString());
                RequestBody reqkota =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edKota.getText().toString().isEmpty()) ?
                                        "" : edKota.getText().toString());

                Call<GetAlamat> callUpdate = mAPIInterface.putAlamat(reqid_alamat, reqlabel_alamat, reqalamat, reqkota
                );
                callUpdate.enqueue(new Callback<GetAlamat>() {
                    @Override
                    public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
//
                        Log.d("EditTempat", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditAlamatActivity.this, "Gagal Edit", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditAlamatActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAlamat> call, Throwable t) {
                        tvMsg.setText("Retrofit Update \n Status = " + t.getMessage());
                    }
                });
            }
        });
        btDeleteAl.setOnClickListener(new View.OnClickListener() {
            final APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);

            @Override
            public void onClick(View view) {
                RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_alamat")));
                Call<GetDelete> mDeleteAlamatCall = mAPIInterface.deleteAlamat(reqid_alamat);
                mDeleteAlamatCall.enqueue(new Callback<GetDelete>() {
                    @Override
                    public void onResponse(Call<GetDelete> call, Response<GetDelete> response) {
                        if (response.body().getStatus().equals("failed")) {
                            Toast.makeText(EditAlamatActivity.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditAlamatActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDelete> call, Throwable t) {
                        Toast.makeText(EditAlamatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), AlamatActivity.class);
                startActivity(intent);
            }
        });


    }
}
