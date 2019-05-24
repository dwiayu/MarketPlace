package com.example.penyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.penyewa.DetailPesan;
import com.example.penyewa.MODEL.Pesan;
import com.example.penyewa.R;
import com.example.penyewa.R.*;
import com.example.penyewa.REST.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PesAdapter extends RecyclerView.Adapter<PesAdapter.PesViewHolder> {
    List<Pesan>daftarPesan;
    public  PesAdapter(List<Pesan> daftarPesan){
        this.daftarPesan=daftarPesan;

    }
    @NonNull
    @Override
    public PesAdapter.PesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout.list_pesan,parent,false);
        PesViewHolder mHolder =new PesViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PesAdapter.PesViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_detail());
        holder.status.setText(daftarPesan.get(position).getStatus_log());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DetailPesan.class);
                intent2.putExtra("id_sewa", daftarPesan.get(position).getId_sewa());
                intent2.putExtra("nama_tempat",daftarPesan.get(position).getNama_tempat());
                intent2.putExtra("alamat", daftarPesan.get(position).getAlamat());
                intent2.putExtra("nama_kostum",daftarPesan.get(position).getNama_kostum());
                intent2.putExtra("tgl_transaksi",daftarPesan.get(position).getTgl_transaksi());
                intent2.putExtra("status_sewa",daftarPesan.get(position).getStatus_log());
                intent2.putExtra("harga_kostum", daftarPesan.get(position).getHarga_kostum());
                intent2.putExtra("bukti_sewa",daftarPesan.get(position).getBukti_sewa());
                v.getContext().startActivity(intent2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarPesan.size();
    }

    public class PesViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status;
        public PesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
        }
    }
}
