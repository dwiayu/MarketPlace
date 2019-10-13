package com.example.gseviepenyewa.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PemesananDetailAdapter extends RecyclerView.Adapter<PemesananDetailAdapter.PemesananDetailViewHolder> {

    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);
    List<Pesan> daftarPesan;
    public  PemesananDetailAdapter(List<Pesan> daftarPesan){
        this.daftarPesan=daftarPesan;

    }

    @NonNull
    @Override
    public PemesananDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan_detail,parent,false);
        PemesananDetailViewHolder mHolder = new PemesananDetailViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PemesananDetailAdapter.PemesananDetailViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga_kostum = Integer.parseInt(daftarPesan.get(position).getHarga_kostum());
        holder.id_sewa.setText(daftarPesan.get(position).getId_sewa());
        holder.nama_kostum.setText(daftarPesan.get(position).getNama_kostum());
        holder.harga_kostum.setText(formatRupiah.format(harga_kostum));
        holder.jumlah_sewa.setText(daftarPesan.get(position).getJumlah());
        Integer jml = Integer.parseInt(daftarPesan.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarPesan.get(position).getHarga_kostum());
        if(daftarPesan.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getFoto_kostum()).into(holder.gambarKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarKostum);

        }

        Integer totalKu= harga*jml;

        holder.total.setText(formatRupiah.format(totalKu));


    }


    @Override
    public int getItemCount() {
        return daftarPesan.size();
    }

    public class PemesananDetailViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kostum, jumlah_sewa, harga_kostum, id_sewa, total;
        ImageView gambarKostum;

        public PemesananDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kostum = (TextView) itemView.findViewById(R.id.namaKostum);
            harga_kostum =(TextView) itemView.findViewById(R.id.harga_kostum);
            jumlah_sewa = (TextView) itemView.findViewById(R.id.jumlah_kostun);
            id_sewa = (TextView) itemView.findViewById(R.id.tvIdSewa);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
            gambarKostum = (ImageView) itemView.findViewById(R.id.gbrKostum);

        }
    }
}
