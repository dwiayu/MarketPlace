package com.example.sikostum.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sikostum.LayarDetailPemesanan;
import com.example.sikostum.LayarEditKostum;
import com.example.sikostum.MODEL.Kostum;
import com.example.sikostum.MODEL.Pemesanan;
import com.example.sikostum.R;
import com.example.sikostum.REST.APIInterface;

import java.util.List;

public class PemesananAdapter  extends RecyclerView.Adapter<PemesananAdapter.PemesananViewHolder> {
    APIInterface mApiInterface;
    List<Pemesanan>daftarPemesanan;

    public  PemesananAdapter(List<Pemesanan>daftarPemesanan){
        this.daftarPemesanan= daftarPemesanan;
    }
    @NonNull
    @Override
    public PemesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan,parent,false);
       PemesananViewHolder mHolder = new PemesananViewHolder(view);
       return  mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PemesananAdapter.PemesananViewHolder holder, final int position) {
        holder.tvIdTempat.setText(daftarPemesanan.get(position).getId_tempat());
        holder.tvNamaUser.setText(daftarPemesanan.get(position).getNama_user());
        holder.tvAlamat.setText(daftarPemesanan.get(position).getAlamat());
        holder.tglTrans.setText(daftarPemesanan.get(position).getTgl_transaksi());
        holder.tvNamaKostum.setText(daftarPemesanan.get(position).getNama_kostum());
        holder.jumlah.setText(daftarPemesanan.get(position).getJumlah());
        holder.hargaKostum.setText(daftarPemesanan.get(position).getHarga_kostum());
        holder.status_log.setText(daftarPemesanan.get(position).getStatus_log());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent= new Intent(v.getContext(), LayarDetailPemesanan.class);
                    mIntent.putExtra("id_tempat",daftarPemesanan.get(position).getId_tempat());
                    mIntent.putExtra("nama_user",daftarPemesanan.get(position).getNama_user());
                    mIntent.putExtra("alamat",daftarPemesanan.get(position).getAlamat());
                    mIntent.putExtra("tgl_transaksi", daftarPemesanan.get(position).getTgl_transaksi());
                    mIntent.putExtra("nama_kostum", daftarPemesanan.get(position).getNama_kostum());
                    mIntent.putExtra("jumlah", daftarPemesanan.get(position).getJumlah());
                    mIntent.putExtra("harga_kostum", daftarPemesanan.get(position).getHarga_kostum());
                    mIntent.putExtra("status_log", daftarPemesanan.get(position).getStatus_log());
                    v.getContext().startActivity(mIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return daftarPemesanan.size();
    }

    public class PemesananViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTempat,tvNamaUser, tvAlamat, tglTrans,tvNamaKostum,jumlah,hargaKostum,status_log;
        public PemesananViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdTempat=(TextView) itemView.findViewById(R.id.tvIdTempat);
            tvNamaUser = (TextView) itemView.findViewById(R.id.tNamaUser);
            tvAlamat =(TextView) itemView.findViewById(R.id.tvAlamatUser);
            tglTrans =(TextView) itemView.findViewById(R.id.tvTglTrans);
            tvNamaKostum=(TextView) itemView.findViewById(R.id.tvNamaKostum);
            jumlah =(TextView) itemView.findViewById(R.id.tvJmlh);
            hargaKostum=(TextView) itemView.findViewById(R.id.tvHg);
            status_log =(TextView)itemView.findViewById(R.id.tvStatus_log);
        }
    }
}
