package com.example.sikostumpenyewa.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sikostumpenyewa.MODEL.Pesan;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIInterface;

import java.util.List;

public class DetailPesanAdapter extends RecyclerView.Adapter<DetailPesanAdapter.DetailPesanViewHolder> {
    List<Pesan> daftarPesan;
    APIInterface mApiInterface;
    String fileNamePhoto;
    String imagePath = "";
    public  DetailPesanAdapter(List<Pesan> daftarPesan){
        this.daftarPesan=daftarPesan;

    }
    @NonNull
    @Override
    public DetailPesanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_pesan,parent,false);
        DetailPesanViewHolder mHolder = new DetailPesanViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailPesanAdapter.DetailPesanViewHolder holder, final int position) {
        holder.id_sewa.setText(daftarPesan.get(position).getId_sewa());
        holder.nama_kostum.setText(daftarPesan.get(position).getNama_kostum());
        holder.nama_tempat.setText(daftarPesan.get(position).getNama_tempat());
        holder.harga_kostum.setText(daftarPesan.get(position).getHarga_kostum());
        holder.jumlah_sewa.setText(daftarPesan.get(position).getJumlah());
        holder.alamat_tempat.setText(daftarPesan.get(position).getAlamat());
        Integer jml = Integer.parseInt(daftarPesan.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarPesan.get(position).getHarga_kostum());
        Integer totalKu= harga*jml;

        holder.total.setText(totalKu.toString());


    }


    @Override
    public int getItemCount() {
        return daftarPesan.size();
    }

    public class DetailPesanViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kostum, jumlah_sewa, harga_kostum,nama_tempat, alamat_tempat, id_sewa, total;

        public DetailPesanViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kostum = (TextView) itemView.findViewById(R.id.namaKostum);
            nama_tempat = (TextView) itemView.findViewById(R.id.namaTempat);
            harga_kostum =(TextView) itemView.findViewById(R.id.harga_kostum);
            jumlah_sewa = (TextView) itemView.findViewById(R.id.jumlah_kostun);
            alamat_tempat = (TextView) itemView.findViewById(R.id.alamatTempat);
            id_sewa = (TextView) itemView.findViewById(R.id.tvIdSewa);
            total = (TextView) itemView.findViewById(R.id.tvTotal);

        }
    }
}
