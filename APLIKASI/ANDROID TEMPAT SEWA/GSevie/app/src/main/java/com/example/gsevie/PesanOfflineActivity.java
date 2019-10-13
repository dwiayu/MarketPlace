package com.example.gsevie;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.GetKostum;
import com.example.gsevie.MODEL.GetPesanOff;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanOfflineActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    TextView id_kostum, jumlah_kostum, tanggal_sewa,harga,Total, namaKostum, noHp;
    ImageView kostum;
    TextView namaPenyewa, jumlahSewa, alamat;
    Button tanggalSewa,simpan;
    String url_photo, photoName;
    APIInterface mApiInterface;

    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_offline);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        id_kostum = (TextView) findViewById(R.id.idKostum);
        jumlah_kostum =(TextView) findViewById(R.id.jmlK);
        tanggal_sewa =(TextView) findViewById(R.id.tglSewa);
        harga = (TextView)  findViewById(R.id.harga);
        Total =(TextView) findViewById(R.id.bayar);
        namaPenyewa =(TextView) findViewById(R.id.edNamaP);
        jumlahSewa =(TextView) findViewById(R.id.jumlahSewa);
        alamat =(TextView) findViewById(R.id.alamat);
        simpan =(Button) findViewById(R.id.btSimpan);
        tanggalSewa = (Button) findViewById(R.id.tgl);
        namaKostum = (TextView) findViewById(R.id.namaKostum);
        kostum = (ImageView) findViewById(R.id.kostum);
        noHp = (TextView) findViewById(R.id.NoHp);

        final Intent mIntent = getIntent();
        id_kostum.setText(mIntent.getStringExtra("id_kostum"));
        jumlah_kostum.setText(mIntent.getStringExtra("jumlah_kostum"));
        harga.setText(mIntent.getStringExtra("harga_kostum"));
        namaKostum.setText(mIntent.getStringExtra("nama_kostum"));

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_kostum=MultipartBody.create(MediaType.parse("multipart/form-data"),
                mIntent.getStringExtra("id_kostum"));
        Call<GetKostum> mDetailCall = mApiInterface.getGambar(reqid_kostum);
        mDetailCall.enqueue(new Callback<GetKostum>() {
            @Override
            public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_kostum();
                photoName = response.body().getResult().get(0).getFoto_kostum();
                if (photoName.equals("")){
                    Glide.with(getApplicationContext()).load(R.drawable.kostum_icon).into(kostum);
                }else{
                    Glide.with(getApplicationContext()).load(url_photo).into(kostum);
                }
            }

            @Override
            public void onFailure(Call<GetKostum> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });

        tanggalSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namaPenyewa.getText().toString().length()==0 || noHp.getText().toString().length()==0 ||
                alamat.getText().toString().length()==0 || jumlahSewa.getText().toString().length()==0 || tanggal_sewa.getText().toString().length()==0 ){
                    Toast.makeText(PesanOfflineActivity.this,"Data Pemesanan Tidak Boleh Kosong!",Toast.LENGTH_LONG ).show();
                }else{
                    Integer total =Integer.parseInt(jumlahSewa.getText().toString())*Integer.parseInt(harga.getText().toString());

                    final Dialog dialog = new Dialog(PesanOfflineActivity.this);
                    dialog.setContentView(R.layout.layout_pesan_off);
                    final TextView judul =(TextView) dialog.findViewById(R.id.judul);
                    final TextView hmm=(TextView) dialog.findViewById(R.id.ttl);
                    judul.setText("Total Pembayaran ");
                    NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
                    hmm.setText(formatRupiah.format(total));
                    Button ok = (Button) dialog.findViewById(R.id.ok);
                    Button batal = (Button) dialog.findViewById(R.id.batal);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(Integer.parseInt(jumlah_kostum.getText().toString())>=Integer.parseInt(jumlahSewa.getText().toString())){
                                mApiInterface= APIClient.getClient().create(APIInterface.class);
                                RequestBody reqid_kostum = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (id_kostum.getText().toString()));
                                final RequestBody reqJumlah = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (jumlahSewa.getText().toString().isEmpty()) ? "" : jumlahSewa.getText().toString());
                                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (namaPenyewa.getText().toString().isEmpty()) ? "" : namaPenyewa.getText().toString());
                                RequestBody reqNoHp = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (noHp.getText().toString().isEmpty()) ? "" : noHp.getText().toString());
                                RequestBody reqAlamat =MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (alamat.getText().toString().isEmpty()) ? "" : alamat.getText().toString());
                                RequestBody reqTgl=MultipartBody.create(MediaType.parse("multipart/form-data"),
                                        (tanggal_sewa.getText().toString().isEmpty()) ? "" : tanggal_sewa.getText().toString());

                                Call<GetPesanOff> mPesan = mApiInterface.postOff(reqid_kostum,reqJumlah,reqNama,reqNoHp,reqAlamat,reqTgl);
                                mPesan.enqueue(new Callback<GetPesanOff>() {
                                    @Override
                                    public void onResponse(Call<GetPesanOff> call, Response<GetPesanOff> response) {
                                        if (response.body().getStatus().equals("success")){
                                            Toast.makeText(PesanOfflineActivity.this,"Pemesanan Berhasil",Toast.LENGTH_LONG ).show();
                                            Intent mIntent = new Intent(getApplicationContext(),PeminjamanActivity.class);
                                            startActivity(mIntent);
                                        }else{
                                            Toast.makeText(PesanOfflineActivity.this,"Lengkapi Data",Toast.LENGTH_LONG ).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetPesanOff> call, Throwable t) {

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(),"Stok tidak tersedia",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }



            }
        });
    }
    public  void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();



        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggal_sewa.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }
}
