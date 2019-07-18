package com.example.sikostumpenyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sikostumpenyewa.MODEL.Data;
import com.example.sikostumpenyewa.MODEL.Pesan;
import com.example.sikostumpenyewa.MODEL.Region;
import com.example.sikostumpenyewa.MODEL.UniqueCode;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIWilayah;
import com.example.sikostumpenyewa.REST.WilayahInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewaAdapter extends RecyclerView.Adapter<SewaAdapter.SewViewHolder> {
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    List<Pesan> daftarSewa;
    public  SewaAdapter(List<Pesan>daftarSewa){
        this.daftarSewa= daftarSewa;
    }
    @NonNull
    @Override
    public SewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sewa,parent,false);
        SewViewHolder mHolder= new SewViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SewaAdapter.SewViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarSewa.get(position).getId_detail());
        holder.status.setText(daftarSewa.get(position).getStatus_log());
        holder.tvIdLog.setText(daftarSewa.get(position).getId_log());
        holder.tvKostum.setText(daftarSewa.get(position).getNama_kostum());
        holder.tvNamaTempat.setText(daftarSewa.get(position).getNama_tempat());
        holder.tvAlamat.setText(daftarSewa.get(position).getAlamat());
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
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getProvinsi()) ){
                                holder.provinsi.setText(capitalize(daftarProvinsi.get(i).name));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarSewa.get(position).getProvinsi()));

                call3.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getKota())){
                                holder.kota.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarSewa.get(position).getKota()));

                call4.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getKecamatan()) ){
                                holder.kecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarSewa.get(position).getKecamatan()));

                call5.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getDesa())){
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
        holder.tvJumlahSewa.setText(daftarSewa.get(position).getJumlah());
        holder.tvTglSewa.setText(daftarSewa.get(position).getTgl_sewa());
        holder.tvTglKembali.setText(daftarSewa.get(position).getTgl_kembali());
        holder.tvHarga.setText(daftarSewa.get(position).getHarga_kostum());
        Integer jml = Integer.parseInt(daftarSewa.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarSewa.get(position).getHarga_kostum());
        Integer totalKu= harga*jml;

        holder.total.setText(totalKu.toString());
        if(daftarSewa.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarSewa.get(position).getFoto_kostum()).into(holder.foto_kostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.foto_kostum);
        }
    }

    @Override
    public int getItemCount() {
        return daftarSewa.size();
    }

    public class SewViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status, tvIdLog, tvKostum, tvAlamat,tvJumlahSewa, provinsi, kota, kecamatan, desa, total, tvTglSewa, tvTglKembali,tvHarga, tvNamaTempat;
        ImageView foto_kostum;

        public SewViewHolder(@NonNull View itemView) {
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
