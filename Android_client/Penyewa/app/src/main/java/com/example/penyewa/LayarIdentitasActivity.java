package com.example.penyewa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.penyewa.MODEL.GetIdentitas;
import com.example.penyewa.REST.APIClient;
import com.example.penyewa.REST.APIInterface;
import com.example.penyewa.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class LayarIdentitasActivity extends AppCompatActivity {
    ImageView gbrId;
    TextView statusId,tvIdentitas;
    Button btEditId;
    Button btInsId;
    String id_user;
    APIInterface mAPIInterface;
    String url_photo, photoName;
    String id_identitas,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_identitas);

        tvIdentitas =(TextView) findViewById(R.id.tvIdentitas);

        btInsId =(Button) findViewById(R.id.btInsId);
        gbrId = (ImageView) findViewById(R.id.gbrId);
        statusId = (TextView) findViewById(R.id.statusId);
        btEditId = (Button) findViewById(R.id.btUpId);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

        getIdentitas();
        btInsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(),IdentitasActivity.class);
                startActivity(mIntent);
            }
        });

        btEditId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), UpdateIdentitasActivity.class);
                mIntent.putExtra("id_identitas", tvIdentitas.getText().toString()) ;
                mIntent.putExtra("status", statusId.getText().toString());
                mIntent.putExtra("foto_ktp", photoName);
                mIntent.putExtra("foto_ktp_url", url_photo);

                startActivity(mIntent);

            }
        });

    }


    private void getIdentitas(){

        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));

        Call<GetIdentitas> mIdentitasCall = mAPIInterface.getIdentitas(reqid_user);
        mIdentitasCall.enqueue(new retrofit2.Callback<GetIdentitas>() {
            @Override
            public void onResponse(Call<GetIdentitas> call, Response<GetIdentitas> response) {
                Log.d("My Identitas", response.body().getStatus());
                if(response.body(). getStatus().equals("success")){
                    btEditId.setEnabled(true);
                    btInsId.setEnabled(false);
                    btInsId.setBackgroundColor(Color.GRAY);
                    tvIdentitas.setText(response.body().getResult().get(0).getId_identitas());
                    statusId.setText(response.body().getResult().get(0).getStatus());

                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_ktp();
                    photoName = response.body().getResult().get(0).getFoto_ktp();
                    if (photoName != null){
                        Glide.with(getApplicationContext()).load(url_photo).into(gbrId);
                    } else {
                        Glide.with(getApplicationContext()).load(R.drawable.fotoktp).into(gbrId);
                    }


                } else if(response.body(). getStatus().equals("kosong")){
                    btInsId.setEnabled(true);
                    btEditId.setEnabled(false);
                    btEditId.setBackgroundColor(Color.GRAY);
                    tvIdentitas.setText("kosong");
                    statusId.setText("kosong");

                } else{
                    Toast.makeText(LayarIdentitasActivity.this, "Gagal mendapatkan foto ktp", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetIdentitas> call, Throwable t) {
                Log.d("MyIdentitas", t.getMessage());
            }
        });
    }
}
