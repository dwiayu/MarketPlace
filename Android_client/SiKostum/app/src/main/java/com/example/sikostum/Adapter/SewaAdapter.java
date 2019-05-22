package com.example.sikostum.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sikostum.LayarDetailPemesanan;
import com.example.sikostum.LayarDetailSewa;
import com.example.sikostum.MODEL.Pemesanan;
import com.example.sikostum.R;
import com.example.sikostum.REST.APIInterface;

import java.util.List;

public class SewaAdapter  extends RecyclerView.Adapter<SewaAdapter.SewaViewHolder> {
    APIInterface mApiInterface;
    List<Pemesanan> daftarSewa;
    public  SewaAdapter(List<Pemesanan>daftarSewa){
        this.daftarSewa= daftarSewa;
    }
    @NonNull
    @Override
    public SewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sewa,parent,false);
        SewaViewHolder mHolder = new SewaViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SewaAdapter.SewaViewHolder holder, final int position) {
        holder.tvIdTempat.setText(daftarSewa.get(position).getId_tempat());
        holder.tvNamaUser.setText(daftarSewa.get(position).getNama_user());
        holder.tvAlamat.setText(daftarSewa.get(position).getAlamat());
        holder.tglTrans.setText(daftarSewa.get(position).getTgl_transaksi());
        holder.tvNamaKostum.setText(daftarSewa.get(position).getNama_kostum());
        holder.jumlah.setText(daftarSewa.get(position).getJumlah());
        holder.hargaKostum.setText(daftarSewa.get(position).getHarga_kostum());
        holder.status_log.setText(daftarSewa.get(position).getStatus_log());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent= new Intent(v.getContext(), LayarDetailSewa.class);
                mIntent.putExtra("id_tempat",daftarSewa.get(position).getId_tempat());
                mIntent.putExtra("nama_user",daftarSewa.get(position).getNama_user());
                mIntent.putExtra("alamat",daftarSewa.get(position).getAlamat());
                mIntent.putExtra("tgl_transaksi", daftarSewa.get(position).getTgl_transaksi());
                mIntent.putExtra("nama_kostum", daftarSewa.get(position).getNama_kostum());
                mIntent.putExtra("jumlah", daftarSewa.get(position).getJumlah());
                mIntent.putExtra("harga_kostum", daftarSewa.get(position).getHarga_kostum());
                mIntent.putExtra("status_log", daftarSewa.get(position).getStatus_log());
                v.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarSewa.size();
    }

    public class SewaViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTempat,tvNamaUser, tvAlamat, tglTrans,tvNamaKostum,jumlah,hargaKostum,status_log;
        public SewaViewHolder(@NonNull View itemView) {
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
