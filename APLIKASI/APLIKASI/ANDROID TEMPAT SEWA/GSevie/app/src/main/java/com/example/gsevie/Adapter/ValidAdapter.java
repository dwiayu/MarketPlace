package com.example.gsevie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.DetailValidActivity;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ValidAdapter extends RecyclerView.Adapter<ValidAdapter.ValidViewHolder> {
    String url_photo;

    private List<Pemesanan> daftarPesan = new ArrayList<>();


    public  ValidAdapter(List<Pemesanan> listPesan){
        daftarPesan.clear();
        daftarPesan.addAll(listPesan);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ValidAdapter.ValidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_valid,parent,false);
        ValidViewHolder mHolder =new ValidViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ValidAdapter.ValidViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_sewa());
        holder.kode_sewa.setText(daftarPesan.get(position).getKode_sewa());
        holder.tglTrannsaksi.setText(daftarPesan.get(position).getTgl_transaksi());
        holder.tglSewa.setText(daftarPesan.get(position).getTgl_sewa());
        holder.tglKembali.setText(daftarPesan.get(position).getTgl_kembali());
        holder.nama.setText(daftarPesan.get(position).getNama_user());

        Integer hari = Integer.parseInt(daftarPesan.get(position).getJumlahTerlambat());
        if (hari<-1){
            holder.textTerlambat.setVisibility(View.VISIBLE);
            holder.layout_late.setVisibility(View.VISIBLE);
        }else if (hari == 0  || hari <= -1){
            holder.textTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.VISIBLE);
            holder.layout_rent.setVisibility(View.VISIBLE);
        }else{
            holder.textOnGoing.setText("Waktu Mengambil Kostum "+String.valueOf(hari)+" Hari lagi");
            holder.textTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.GONE);
            holder.layout_rent.setVisibility(View.GONE);
            holder.textOnGoing.setVisibility(View.VISIBLE);
            holder.layout_onGoing.setVisibility(View.VISIBLE);
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
                Intent intent2 = new Intent(v.getContext(), DetailValidActivity.class);
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

    public class ValidViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status, tglSewa, tglKembali, tglTrannsaksi,kode_sewa, nama, textTerlambat, textSewa, textOnGoing;
        ImageView gambarBukti;
        LinearLayout layout_late, layout_rent, layout_onGoing;
        public ValidViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tglTrannsaksi = (TextView) itemView.findViewById(R.id.tvTglTransaksi);
            tglSewa =(TextView) itemView.findViewById(R.id.tglsewa);
            tglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            gambarBukti = (ImageView) itemView.findViewById(R.id.gbrBukti);
            kode_sewa = (TextView) itemView.findViewById(R.id.kodeSewa);
            nama = (TextView) itemView.findViewById(R.id.namaPenyewa);
            textTerlambat = (TextView) itemView.findViewById(R.id.textTerlambat);
            textSewa =(TextView) itemView.findViewById(R.id.textSewa);
            textOnGoing = (TextView) itemView.findViewById(R.id.textOnGoing);
            layout_late = (LinearLayout) itemView.findViewById(R.id.layout_late);
            layout_rent = (LinearLayout) itemView.findViewById(R.id.layout_rent);
            layout_onGoing = (LinearLayout) itemView.findViewById(R.id.layout_ongoing);
        }
    }

    public void setFilter(ArrayList<Pemesanan> filter){
        daftarPesan = new ArrayList<>();
        daftarPesan.addAll(filter);
        notifyDataSetChanged();
    }
}
