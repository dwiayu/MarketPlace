package com.example.penyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.penyewa.DetailPesan;
import com.example.penyewa.DetailVerifikasi;
import com.example.penyewa.MODEL.Pesan;
import com.example.penyewa.R;

import java.util.List;

public class VerAdapter extends RecyclerView.Adapter<VerAdapter.VerViewHolder>  {
    List<Pesan> daftarVer;

    public VerAdapter(List<Pesan> daftarVer){
        this.daftarVer =daftarVer;
    }
    @NonNull
    @Override
    public VerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ver,parent,false);
        VerViewHolder mHolder= new VerViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerAdapter.VerViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarVer.get(position).getId_detail());
        holder.status.setText(daftarVer.get(position).getStatus_log());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DetailVerifikasi.class);
                intent2.putExtra("id_sewa", daftarVer.get(position).getId_sewa());
                intent2.putExtra("nama_tempat",daftarVer.get(position).getNama_tempat());
                intent2.putExtra("alamat", daftarVer.get(position).getAlamat());
                intent2.putExtra("nama_kostum",daftarVer.get(position).getNama_kostum());
                intent2.putExtra("tgl_transaksi",daftarVer.get(position).getTgl_transaksi());
                intent2.putExtra("status_log",daftarVer.get(position).getStatus_log());
                intent2.putExtra("harga_kostum", daftarVer.get(position).getHarga_kostum());
                v.getContext().startActivity(intent2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarVer.size();
    }

    public class VerViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status;
        public VerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
        }
    }
}
