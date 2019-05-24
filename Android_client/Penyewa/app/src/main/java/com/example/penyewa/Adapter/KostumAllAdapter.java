package com.example.penyewa.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.penyewa.MODEL.KostumAll;
import com.example.penyewa.R;
import com.example.penyewa.REST.APIClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KostumAllAdapter  extends RecyclerView.Adapter<KostumAllAdapter.KostumAllViewHolder> {
    private List<KostumAll> daftarKostum = new ArrayList<>();



    public KostumAllAdapter(List<KostumAll>listKostum){
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
    public void onBindViewHolder(@NonNull KostumAllAdapter.KostumAllViewHolder holder, int position) {
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdTempat.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdAlamat.setText(daftarKostum.get(position).getId_alamat());
        holder.tvNamaTempat.setText(daftarKostum.get(position).getNama_tempat());
        holder.tvAlamat.setText(daftarKostum.get(position).getAlamat());
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        holder.tvJumlahKostum.setText(daftarKostum.get(position).getJumlah_kostum());
        holder.tvHargaKostum.setText(daftarKostum.get(position).getHarga_kostum());
        if(daftarKostum.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.fotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.bungaa).into(holder.fotoKostum);
        }

    }

    @Override
    public int getItemCount() {
        return daftarKostum.size();
    }

    public class KostumAllViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvIdTempat,tvIdAlamat,tvNamaTempat, tvAlamat,tvNamaKostum,tvJumlahKostum,tvHargaKostum;
        Button keranjang;
        ImageView fotoKostum;
        public KostumAllViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdKostum = (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvIdAlamat = (TextView) itemView.findViewById(R.id.tvIdAlamat);
            tvIdTempat =(TextView) itemView.findViewById(R.id.tvIdTempat);
            tvNamaTempat = (TextView) itemView.findViewById(R.id.namaTempatSewa);
            tvAlamat = (TextView) itemView.findViewById(R.id.alamatTempatSewa);
            tvNamaKostum =(TextView) itemView.findViewById(R.id.tvNamaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.jumlahKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.hargaKostum);
            keranjang =(Button)itemView.findViewById(R.id.btKeranjang);
            fotoKostum =(ImageView) itemView.findViewById(R.id.gambarKostum);
        }
    }

    public void setFilter(ArrayList<KostumAll> filter){
        daftarKostum = new ArrayList<>();
        daftarKostum.addAll(filter);
        notifyDataSetChanged();
    }
}
