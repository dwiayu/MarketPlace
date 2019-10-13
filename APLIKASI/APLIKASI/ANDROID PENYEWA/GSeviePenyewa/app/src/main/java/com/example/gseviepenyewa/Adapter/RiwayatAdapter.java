package com.example.gseviepenyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.PemesananDetailActivity;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.RiwayatDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    String url_photo;

    private List<Pesan> daftarPesan = new ArrayList<>();


    public  RiwayatAdapter(List<Pesan> listPesan){
        daftarPesan.clear();
        daftarPesan.addAll(listPesan);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RiwayatAdapter.RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat,parent,false);
        RiwayatAdapter.RiwayatViewHolder mHolder =new RiwayatAdapter.RiwayatViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.RiwayatViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_sewa());
        holder.kode_sewa.setText(daftarPesan.get(position).getKode_Sewa());
        holder.status.setText(daftarPesan.get(position).getStatus_detail());
        holder.tglTrannsaksi.setText(daftarPesan.get(position).getTgl_transaksi());
        holder.tglSewa.setText(daftarPesan.get(position).getTgl_sewa());
        holder.tglKembali.setText(daftarPesan.get(position).getTgl_kembali());


        if(daftarPesan.get(position).getBukti_sewa()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa()).into(holder.gambarBukti);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarBukti);

        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), RiwayatDetailActivity.class);
                intent2.putExtra("id_sewa", daftarPesan.get(position).getId_sewa());
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

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status, tglSewa, tglKembali, tglTrannsaksi,kode_sewa;
        ImageView gambarBukti;
        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tglTrannsaksi = (TextView) itemView.findViewById(R.id.tvTglTransaksi);
            tglSewa =(TextView) itemView.findViewById(R.id.tglsewa);
            tglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            gambarBukti = (ImageView) itemView.findViewById(R.id.gbrBukti);
            kode_sewa = (TextView) itemView.findViewById(R.id.kodeSewa);
        }
    }
    public void setFilter(ArrayList<Pesan> filter){
        daftarPesan = new ArrayList<>();
        daftarPesan.addAll(filter);
        notifyDataSetChanged();
    }
}
