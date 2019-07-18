package com.example.sikostumpenyewa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumpenyewa.MODEL.Data;
import com.example.sikostumpenyewa.MODEL.GetPesan;
import com.example.sikostumpenyewa.MODEL.Pesan;
import com.example.sikostumpenyewa.MODEL.Region;
import com.example.sikostumpenyewa.MODEL.UniqueCode;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIInterface;
import com.example.sikostumpenyewa.REST.APIWilayah;
import com.example.sikostumpenyewa.REST.WilayahInterface;
import com.example.sikostumpenyewa.VerifikasiActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiAdapter extends RecyclerView.Adapter<VerifikasiAdapter.VerViewHolder> {
    String url_photo;
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    List<Pesan> daftarVer;

    public VerifikasiAdapter(List<Pesan> daftarVer){
        this.daftarVer =daftarVer;
    }
    @NonNull
    @Override
    public VerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_verifikasi,parent,false);
        VerViewHolder mHolder= new VerViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VerifikasiAdapter.VerViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarVer.get(position).getId_detail());
        holder.status.setText(daftarVer.get(position).getStatus_log());
        holder.tvIdLog.setText(daftarVer.get(position).getId_log());
        holder.tvKostum.setText(daftarVer.get(position).getNama_kostum());
        holder.tvNamaTempat.setText(daftarVer.get(position).getNama_tempat());
        holder.tvAlamat.setText(daftarVer.get(position).getAlamat());
        final WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();
        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {

                code = "MeP7c5ne" + response.body().getUniqueCode();
                Call<Data> call2 = apiService.getProvinceList(code);

                call2.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarVer.get(position).getProvinsi()) ){
                                holder.provinsi.setText(capitalize(daftarProvinsi.get(i).name));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarVer.get(position).getProvinsi()));

                call3.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarVer.get(position).getKota())){
                                holder.kota.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarVer.get(position).getKota()));

                call4.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarVer.get(position).getKecamatan()) ){
                                holder.kecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarVer.get(position).getKecamatan()));

                call5.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarVer.get(position).getDesa())){
                                holder.desa.setText(capitalize(daftarProvinsi.get(i).getName()));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });





            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
        holder.tvJumlahSewa.setText(daftarVer.get(position).getJumlah());
        holder.tvTglSewa.setText(daftarVer.get(position).getTgl_sewa());
        holder.tvTglKembali.setText(daftarVer.get(position).getTgl_kembali());
        holder.tvHarga.setText(daftarVer.get(position).getHarga_kostum());
        Integer harga = Integer.parseInt(daftarVer.get(position).getHarga_kostum());
        Integer jml = Integer.parseInt(daftarVer.get(position).getJumlah());
        Integer totalKu= harga*jml;

        holder.total.setText(totalKu.toString());

        if(daftarVer.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarVer.get(position).getFoto_kostum()).into(holder.foto_kostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.foto_kostum);
        }

        holder.ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_log =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (holder.tvIdLog.getText().toString().isEmpty()) ?
                                        "" : holder.tvIdLog.getText().toString());
                Call<GetPesan> mLog= mApiInterface.putAmbil(reqid_log);
                mLog.enqueue(new Callback<GetPesan>() {
                    @Override
                    public void onResponse(Call<GetPesan> call, Response<GetPesan> response) {
                        if (response.body().getStatus().equals("success")){
                            Toast.makeText(holder.itemView.getContext(), "Kostum sudah diambil", Toast.LENGTH_SHORT).show();
                            openKostum(holder.itemView.getContext());

                        }else{
                            Toast.makeText(holder.itemView.getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetPesan> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarVer.size();
    }
    private void openKostum(Context context){
        Intent intent = new Intent(context, VerifikasiActivity.class);
        context.startActivity(intent);
    }

    public class VerViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, total, status, tvIdLog, tvKostum, tvAlamat,provinsi, kota, kecamatan, desa, tvJumlahSewa, tvTglSewa, tvTglKembali,tvHarga, tvNamaTempat;
        Button ambil;
        ImageView foto_kostum;
        public VerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tvIdLog = (TextView) itemView.findViewById(R.id.idLog);
            tvKostum =(TextView) itemView.findViewById(R.id.namaKos);
            tvNamaTempat =(TextView)itemView.findViewById(R.id.tempat_sewa);
            tvAlamat = (TextView) itemView.findViewById(R.id.alamatTempat);
            provinsi = (TextView) itemView.findViewById(R.id.tvProv);
            kota = (TextView) itemView.findViewById(R.id.tvKot);
            kecamatan = (TextView) itemView.findViewById(R.id.tvKec);
            desa = (TextView) itemView.findViewById(R.id.tvDes);
            tvJumlahSewa=(TextView) itemView.findViewById(R.id.jumlaSe);
            tvTglSewa= (TextView) itemView.findViewById(R.id.tglSewa);
            tvTglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            tvHarga =(TextView) itemView.findViewById(R.id.hargaKos);
            total =(TextView) itemView.findViewById(R.id.tvTotal);
            foto_kostum = (ImageView) itemView.findViewById(R.id.fotoKostum);
            ambil = (Button) itemView.findViewById(R.id.btAmbil);
        }
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
