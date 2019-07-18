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
import com.example.sikostumpenyewa.DetailPesanActivity;
import com.example.sikostumpenyewa.MODEL.Pesan;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.PesViewHolder> {


    String url_photo;

    List<Pesan> daftarPesan;

    public  PesanAdapter(List<Pesan> daftarPesan){
        this.daftarPesan=daftarPesan;

    }
    @NonNull
    @Override
    public PesanAdapter.PesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesan,parent,false);
        PesViewHolder mHolder =new PesViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PesanAdapter.PesViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_sewa());
        holder.status.setText(daftarPesan.get(position).getStatus_log());
        holder.tglTrannsaksi.setText(daftarPesan.get(position).getTgl_transaksi());
        holder.tglSewa.setText(daftarPesan.get(position).getTgl_sewa());
        holder.tglKembali.setText(daftarPesan.get(position).getTgl_kembali());
        if(daftarPesan.get(position).getBukti_sewa()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa()).into(holder.gambarBukti);
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_access_time_green_24dp).into(holder.statusTime);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarBukti);

        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DetailPesanActivity.class);
                intent2.putExtra("id_sewa", daftarPesan.get(position).getId_sewa());
                intent2.putExtra("nama_tempat",daftarPesan.get(position).getNama_tempat());
                intent2.putExtra("alamat", daftarPesan.get(position).getAlamat());
                intent2.putExtra("nama_kostum",daftarPesan.get(position).getNama_kostum());
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
        TextView tvIdDetail, status, tglSewa, tglKembali, tglTrannsaksi;
        ImageView gambarBukti, statusTime;
        public PesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tglTrannsaksi = (TextView) itemView.findViewById(R.id.tvTglTransaksi);
            tglSewa =(TextView) itemView.findViewById(R.id.tglsewa);
            tglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            gambarBukti = (ImageView) itemView.findViewById(R.id.gbrBukti);
            statusTime = (ImageView) itemView.findViewById(R.id.statusTime);
        }
    }
}
