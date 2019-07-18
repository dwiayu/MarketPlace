package com.example.sikostumpenyewa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumpenyewa.DataHelper.DBAdapter;
import com.example.sikostumpenyewa.DataHelper.MyDataHelper;
import com.example.sikostumpenyewa.MODEL.Data;
import com.example.sikostumpenyewa.MODEL.GetIdentitas;
import com.example.sikostumpenyewa.MODEL.GetTempat;
import com.example.sikostumpenyewa.MODEL.KostumAll;
import com.example.sikostumpenyewa.MODEL.Region;
import com.example.sikostumpenyewa.MODEL.UniqueCode;
import com.example.sikostumpenyewa.OnGoingSewaActivity;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIInterface;
import com.example.sikostumpenyewa.REST.APIWilayah;
import com.example.sikostumpenyewa.REST.WilayahInterface;
import com.example.sikostumpenyewa.ReviewKostumActivity;
import com.example.sikostumpenyewa.Utils.SaveSharedPreferences;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.hardware.camera2.params.RggbChannelVector.BLUE;
import static com.example.sikostumpenyewa.Utils.PreferencesUtility.id;
import static okhttp3.MediaType.parse;

public class KostumAllAdapter extends RecyclerView.Adapter<KostumAllAdapter.KostumAllViewHolder> {
    private Context mContext;
    MyDataHelper dbHelper;
    DBAdapter dbAdapter;
    String url_photo, id_user;
    APIInterface mApiInterface;
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    public static KostumAllAdapter layarUtama;
    private List<KostumAll> daftarKostum = new ArrayList<>();

    public KostumAllAdapter(Context c){
        mContext= c;
    }


    public KostumAllAdapter(List<KostumAll>listKostum, Context c){
        mContext = c;
        dbAdapter = new DBAdapter(mContext);
        daftarKostum.clear();
        daftarKostum.addAll(listKostum);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public KostumAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kostum_all,parent,false);
        KostumAllViewHolder mHolder = new KostumAllViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KostumAllAdapter.KostumAllViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(daftarKostum.get(position).getHarga_kostum());
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdTempat.setText(daftarKostum.get(position).getId_tempat());
        holder.tvIdAlamat.setText(daftarKostum.get(position).getId_alamat());
        holder.tvNamaTempat.setText(daftarKostum.get(position).getNama_tempat());
        holder.tvAlamat.setText(daftarKostum.get(position).getAlamat());
        final String alamat = daftarKostum.get(position).getAlamat();
        final String googleMaps = "com.google.android.apps.maps";

        final String lokasi = ""+alamat+"";
        holder.lokasiKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri gmmIntentUri = Uri.parse("google.navigation:q=" +lokasi);
                final Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(googleMaps);
                v.getContext().startActivity(mapIntent);
            }
        });
//        final WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
//        Call<UniqueCode> call = apiService.getUniqueCode();
//        call.enqueue(new Callback<UniqueCode>() {
//            @Override
//            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
//
//                code = "MeP7c5ne" + response.body().getUniqueCode();
//                Call<Data> call2 = apiService.getProvinceList(code);
//
//                call2.enqueue(new Callback<Data>() {
//                    @Override
//                    public void onResponse(Call<Data> call, Response<Data> response) {
//
//                        final List<Region> daftarProvinsi = response.body().getData();
//                        for (int i = 0; i < daftarProvinsi.size(); i++) {
//                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarKostum.get(position).getProvinsi()) ){
//                                holder.provinsi.setText(capitalize(daftarProvinsi.get(i).name));
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Data> call, Throwable t) {
//
//                    }
//                });
//
//                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarKostum.get(position).getProvinsi()));
//
//                call3.enqueue(new Callback<Data>() {
//                    @Override
//                    public void onResponse(Call<Data> call, Response<Data> response) {
//                        final List<Region> daftarProvinsi = response.body().getData();
//                        for (int i = 0; i < daftarProvinsi.size(); i++) {
//                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarKostum.get(position).getKota())){
//                                holder.kota.setText(capitalize(daftarProvinsi.get(i).getName()));
//
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Data> call, Throwable t) {
//
//                    }
//                });
//
//                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarKostum.get(position).getKota()));
//
//                call4.enqueue(new Callback<Data>() {
//                    @Override
//                    public void onResponse(Call<Data> call, Response<Data> response) {
//
//                        final List<Region> daftarProvinsi = response.body().getData();
//                        for (int i = 0; i < daftarProvinsi.size(); i++) {
//                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarKostum.get(position).getKecamatan()) ){
//                                holder.kecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));
//
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Data> call, Throwable t) {
//
//                    }
//                });
//
//                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarKostum.get(position).getKecamatan()));
//
//                call5.enqueue(new Callback<Data>() {
//                    @Override
//                    public void onResponse(Call<Data> call, Response<Data> response) {
//
//                        final List<Region> daftarProvinsi = response.body().getData();
//                        for (int i = 0; i < daftarProvinsi.size(); i++) {
//                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarKostum.get(position).getDesa())){
//                                holder.desa.setText(capitalize(daftarProvinsi.get(i).getName()));
//
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Data> call, Throwable t) {
//
//                    }
//                });
//
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<UniqueCode> call, Throwable t) {
//
//            }
//        });
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        holder.tvJumlahKostum.setText("Stok "+daftarKostum.get(position).getJumlah_kostum());
        holder.tvHargaKostum.setText(formatRupiah.format(harga));
        holder.deskripsiKostum.setText(daftarKostum.get(position).getDeskripsi_kostum());
        holder.tvStatusTempat.setText(daftarKostum.get(position).getStatus_tempat());
        if (daftarKostum.get(position).getStatus_tempat().equals("buka")){
            holder.tvStatusTempat.setTextColor(Color.parseColor("#4CAF50"));
        }else{
            holder.tvStatusTempat.setTextColor(RED);
        }
        RequestBody reqid_tempat= MultipartBody.create(MediaType.parse("multipart/form-data"),
                holder.tvIdTempat.getText().toString());
        holder.progressBarD.setVisibility(View.VISIBLE);
        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), ReviewKostumActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("nama_user", daftarKostum.get(position).getNama());
                mIntent.putExtra("komentar", daftarKostum.get(position).getKomentar());
                v.getContext().startActivity(mIntent);


            }
        });
        if(daftarKostum.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.fotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.splash_user).into(holder.fotoKostum);
        }
        id_user = SaveSharedPreferences.getId(mContext);
        if (dbAdapter.selectKeranjang(id_user,daftarKostum.get(position).getId_kostum())){

            id_user = SaveSharedPreferences.getId(mContext);

            holder.keranjang.setEnabled(false);
            holder.keranjang.setBackgroundColor(GRAY);


        }

        url_photo = APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum();
        id_user = SaveSharedPreferences.getId(mContext);

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetTempat> cekTempatTutup = mApiInterface.cekTempatTutup(reqid_tempat);
        cekTempatTutup.enqueue(new Callback<GetTempat>() {
            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                if (response.body().getStatus().equals("success")){
                    holder.keranjang.setVisibility(View.GONE);
                }else{
                    holder.keranjang.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {

            }
        });

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user=MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetIdentitas> cekIdentitas =mApiInterface.cekIdentitas(reqid_user);
        cekIdentitas.enqueue(new Callback<GetIdentitas>() {
            @Override
            public void onResponse(Call<GetIdentitas> call, Response<GetIdentitas> response) {
                if(response.body().getStatus().equals("success")){
                    Toast.makeText(mContext,"Cek identitas",Toast.LENGTH_LONG).show();
                    holder.keranjang.setEnabled(false);
                    holder.keranjang.setBackgroundColor(GRAY);

                    holder.review.setEnabled(false);
                    holder.review.setBackgroundColor(GRAY);                }
                else{
                    holder.keranjang.setVisibility(View.VISIBLE);


                }
                holder.progressBarD.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetIdentitas> call, Throwable t) {
                //Toast.makeText(mContext,"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });

        holder.keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), OnGoingSewaActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("idTempat",daftarKostum.get(position).getId_tempat());
                mIntent.putExtra("id_alamat", daftarKostum.get(position).getId_alamat());
                mIntent.putExtra("nama_kostum", daftarKostum.get(position).getNama_kostum());
                mIntent.putExtra("namaTempat", daftarKostum.get(position).getNama_tempat());
                mIntent.putExtra("alamat", daftarKostum.get(position).getAlamat());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("harga_kostum", daftarKostum.get(position).getHarga_kostum());
                mIntent.putExtra("foto_kostum", daftarKostum.get(position).getFoto_kostum());
                mIntent.putExtra("foto_kostum_url", url_photo);
                v.getContext().startActivity(mIntent);

            }
        });

    }
    public void keOnGoing(){

    }

    @Override
    public int getItemCount() {
        return daftarKostum.size();
    }

    public class KostumAllViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvIdTempat,tvIdAlamat,tvNamaTempat, tvAlamat,tvNamaKostum,deskripsiKostum, provinsi, kota, kecamatan, desa,tvJumlahKostum,tvHargaKostum, tvStatusTempat;
        Button keranjang,review,lokasiKu;
        ProgressBar progressBarD;
        String id_user;
        ImageView fotoKostum;
        public KostumAllViewHolder(@NonNull View itemView) {

            super(itemView);
            lokasiKu = (Button) itemView.findViewById(R.id.btn_navigasi);
            tvIdKostum = (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvIdAlamat = (TextView) itemView.findViewById(R.id.tvIdAlamat);
            tvIdTempat =(TextView) itemView.findViewById(R.id.tvIdTempat);
            tvNamaTempat = (TextView) itemView.findViewById(R.id.namaTempatSewa);
            tvAlamat = (TextView) itemView.findViewById(R.id.alamatTempatSewa);
            tvNamaKostum =(TextView) itemView.findViewById(R.id.tvNamaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.jumlahKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.hargaKostum);
            keranjang =(Button)itemView.findViewById(R.id.btKeranjang);
            provinsi = (TextView) itemView.findViewById(R.id.tvProv);
            kota = (TextView) itemView.findViewById(R.id.tvKot);
            kecamatan = (TextView) itemView.findViewById(R.id.tvKec);
            desa = (TextView) itemView.findViewById(R.id.tvDes);
            deskripsiKostum =(TextView) itemView.findViewById(R.id.deskripsiKostum);
            fotoKostum =(ImageView) itemView.findViewById(R.id.gambarKostum);
            keranjang =(Button) itemView.findViewById(R.id.btKeranjang);
            review=(Button) itemView.findViewById(R.id.btReview);
            progressBarD =(ProgressBar) itemView.findViewById(R.id.simpleProgressBarr);
            tvStatusTempat = (TextView) itemView.findViewById(R.id.statusTempat);

        }
    }

    public void setFilter(ArrayList<KostumAll> filter){
        daftarKostum = new ArrayList<>();
        daftarKostum.addAll(filter);
        notifyDataSetChanged();
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
}
