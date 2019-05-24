package com.example.penyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.penyewa.DetailSewa;
import com.example.penyewa.DetailVerifikasi;
import com.example.penyewa.MODEL.Pesan;
import com.example.penyewa.R;

import java.util.List;

public class SewAdapter extends RecyclerView.Adapter<SewAdapter.SewViewHolder> {
    List<Pesan> daftarSewa;
    public  SewAdapter(List<Pesan>daftarSewa){
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
    public void onBindViewHolder(@NonNull SewAdapter.SewViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarSewa.get(position).getId_detail());
        holder.status.setText(daftarSewa.get(position).getStatus_log());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DetailSewa.class);
                intent2.putExtra("id_sewa", daftarSewa.get(position).getId_sewa());
                intent2.putExtra("nama_tempat",daftarSewa.get(position).getNama_tempat());
                intent2.putExtra("alamat", daftarSewa.get(position).getAlamat());
                intent2.putExtra("nama_kostum",daftarSewa.get(position).getNama_kostum());
                intent2.putExtra("tgl_transaksi",daftarSewa.get(position).getTgl_transaksi());
                intent2.putExtra("status_log",daftarSewa.get(position).getStatus_log());
                intent2.putExtra("harga_kostum", daftarSewa.get(position).getHarga_kostum());
                v.getContext().startActivity(intent2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarSewa.size();
    }

    public class SewViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status;
        public SewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
        }
    }
}
