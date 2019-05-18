package com.example.sikostum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikostum.MODEL.GetAlamat;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class InsertAlamat extends AppCompatActivity {
    EditText label_alamat, alamat, kota;
    Button simpan, kembali;
    Context mContext;
    TextView tvMessage;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_alamat);
        mContext = getApplicationContext();

        label_alamat= (EditText) findViewById(R.id.editTextLabel);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        kota = (EditText) findViewById(R.id.editTextKota);
        simpan = (Button) findViewById(R.id.buttonSimpan);
        kembali =(Button) findViewById(R.id.buttonKembali);
        tvMessage =(TextView) findViewById(R.id.tvMessage);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_user =
                        MultipartBody.create(parse("multipart/form-data"),
                                id_user);
                RequestBody reqlabel_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (label_alamat.getText().toString().isEmpty()) ? "" : label_alamat.getText().toString());
                RequestBody reqalamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (alamat.getText().toString().isEmpty()) ? "" : alamat.getText().toString());
                RequestBody reqkota = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (kota.getText().toString().isEmpty()) ? "" : kota.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetAlamat> malamatCall = mAPIInterface.postAlamat(reqid_user, reqlabel_alamat,reqalamat,reqkota,
                        reqAction);
                malamatCall.enqueue(new Callback<GetAlamat>() {
                    @Override
                    public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                        if (response.body().getStatus().equals("failed")) {
                            tvMessage.setText("Retrofit Insert \n Status = " + response.body()
                                    .getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + "\n");

                        } else {
                            String detail = "\n" +
                                    "id_user = " + response.body().getResult().get(0).getId_user() + "\n" +
                                    "label_alamat = " + response.body().getResult().get(0).getLabel_alamat() + "\n" +
                                    "alamat = "+response.body().getResult().get(0).getAlamat() + "\n"+
                                    "kota = " + response.body().getResult().get(0).getKota()
                                    + "\n";
                            tvMessage.setText("Retrofit Insert \n Status = " + response.body().getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + detail);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetAlamat> call, Throwable t) {
                        tvMessage.setText("Retrofit Insert Failure \n Status = " + t.getMessage
                                ());
                    }
                });
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DaftarAlamat.class);
                startActivity(intent);
            }
        });
    }
}
