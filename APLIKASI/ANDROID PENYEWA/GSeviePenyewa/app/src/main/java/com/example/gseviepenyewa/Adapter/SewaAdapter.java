package com.example.gseviepenyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.PemesananDetailActivity;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.SewaDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SewaAdapter extends RecyclerView.Adapter<SewaAdapter.SewaViewHolder> {
    String url_photo;

    List<Pesan> daftarPesan;

    public  SewaAdapter(List<Pesan> daftarPesan){
        this.daftarPesan=daftarPesan;

    }

    @NonNull
    @Override
    public SewaAdapter.SewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sewa,parent,false);
        SewaAdapter.SewaViewHolder mHolder =new SewaAdapter.SewaViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SewaAdapter.SewaViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_sewa());
        holder.kode_sewa.setText(daftarPesan.get(position).getKode_Sewa());
        holder.status.setText(daftarPesan.get(position).getStatus_detail());
        holder.tglTrannsaksi.setText(daftarPesan.get(position).getTgl_transaksi());
        holder.tglSewa.setText(daftarPesan.get(position).getTgl_sewa());
        holder.tglKembali.setText(daftarPesan.get(position).getTgl_kembali());

        Integer terlambat = Integer.parseInt(daftarPesan.get(position).getJumlahTerlambat());
        if (terlambat<0){
            Integer hari = terlambat*(-1);
            holder.hitungTerlambat.setText("Terlambat Mengembalikan : "+String.valueOf(hari)+" Hari");
        }else if(terlambat == 0) {
            holder.hitungTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.GONE);
            holder.layout_green.setVisibility(View.GONE);
            holder.textKembali.setVisibility(View.VISIBLE);
            holder.layout_kembali.setVisibility(View.VISIBLE);
        }else{
            holder.hitungTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.VISIBLE);
            holder.layout_green.setVisibility(View.VISIBLE);
            holder.textKembali.setVisibility(View.GONE);
            holder.layout_kembali.setVisibility(View.GONE);
        }

        if(daftarPesan.get(position).getBukti_sewa()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa()).into(holder.gambarBukti);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarBukti);

        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), SewaDetailActivity.class);
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

    public class SewaViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status, tglSewa, tglKembali, tglTrannsaksi,kode_sewa,hitungTerlambat, textSewa, textKembali;
        ImageView gambarBukti;
        LinearLayout layout_late, layout_green, layout_kembali;
        public SewaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tglTrannsaksi = (TextView) itemView.findViewById(R.id.tvTglTransaksi);
            tglSewa =(TextView) itemView.findViewById(R.id.tglsewa);
            tglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            gambarBukti = (ImageView) itemView.findViewById(R.id.gbrBukti);
            kode_sewa = (TextView) itemView.findViewById(R.id.kodeSewa);
            hitungTerlambat = (TextView) itemView.findViewById(R.id.hariTerlambat);
            layout_green = (LinearLayout) itemView.findViewById(R.id.layout_rent);
            layout_late = (LinearLayout) itemView.findViewById(R.id.layout_late);
            textSewa = (TextView) itemView.findViewById(R.id.textSewa);
            layout_kembali= (LinearLayout) itemView.findViewById(R.id.layout_kembali);
            textKembali= (TextView) itemView.findViewById(R.id.textKembali);
        }
    }
}
