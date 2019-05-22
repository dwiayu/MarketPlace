package com.example.sikostum;

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

import com.example.sikostum.MODEL.GetAlamat;
import com.example.sikostum.MODEL.GetDelete;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class LayarEditAlamat extends AppCompatActivity {
    Context mContext;
    EditText edLabel, edAlamat, edKota;
    TextView tvMsg;
    Button btUpdateAl, btDeleteAl, btCancelAl;
    APIInterface mAPIInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_edit_alamat);
        mContext = getApplicationContext();

        edLabel = (EditText) findViewById(R.id.edLabel);
        edAlamat = (EditText) findViewById(R.id.edAlamat);
        edKota = (EditText) findViewById(R.id.edKota);
        btUpdateAl = (Button) findViewById(R.id.bt_updateAl);
        btDeleteAl = (Button) findViewById(R.id.bt_hapusAl);
        btCancelAl = (Button) findViewById(R.id.bt_kembaliAl);
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
//                        if (response.body().getStatus().equals("failed")) {
//                            tvMsg.setText("Retrofit Update \n Status = " + response.body()
//                                    .getStatus() + "\n" +
//                                    "Message = " + response.body().getMessage() + "\n");
//                        } else {
//                            String detail = "\n" +
//
//                                    "label_alamat = " + response.body().getResult().get(0).getLabel_alamat() + "\n" +
//                                    "alamat = " + response.body().getResult().get(0).getAlamat() + "\n" +
//                                    "kota = " + response.body().getResult().get(0).getKota()
//                                    + "\n";
//                            tvMsg.setText("Retrofit Update \n Status = " + response.body().getStatus() + "\n" +
//                                    "Message = " + response.body().getMessage() + detail);
//                        }
                        Log.d("EditTempat", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(LayarEditAlamat.this, "Gagal Edit", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LayarEditAlamat.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(LayarEditAlamat.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LayarEditAlamat.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDelete> call, Throwable t) {
                        Toast.makeText(LayarEditAlamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), DaftarAlamat.class);
                startActivity(intent);
            }
        });
        btCancelAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), DaftarAlamat.class);
                startActivity(mIntent);
            }
        });

    }
}
