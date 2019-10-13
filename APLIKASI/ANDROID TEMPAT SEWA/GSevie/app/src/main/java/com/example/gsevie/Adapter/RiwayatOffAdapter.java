package com.example.gsevie.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.PesanOff;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RiwayatOffAdapter  extends RecyclerView.Adapter<RiwayatOffAdapter.RiwayatOffViewHolder> {
    APIInterface mApiInterface;
    String url_photo;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);
    private List<PesanOff> riwayatOff = new ArrayList<>();
    public  RiwayatOffAdapter(List<PesanOff> listRiwayat){
        riwayatOff.clear();
        riwayatOff.addAll(listRiwayat);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RiwayatOffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat_off,parent,false);
        RiwayatOffViewHolder mHolder=new RiwayatOffAdapter.RiwayatOffViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RiwayatOffAdapter.RiwayatOffViewHolder holder, int position) {
            holder.id_pemesanan.setText(riwayatOff.get(position).getId_pemesanan());
            holder.namakostum.setText(riwayatOff.get(position).getNama_kostum());
            holder.namapenyewa.setText(riwayatOff.get(position).getNama());
            holder.jumlahsewa.setText("Jumlah "+riwayatOff.get(position).getJumlah());
            NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
            Integer totalku = Integer.parseInt((riwayatOff.get(position).getJumlah()))*Integer.parseInt(riwayatOff.get(position).getHarga_kostum());
            holder.total.setText("Total "+formatRupiah.format(totalku));
            holder.alamat.setText(riwayatOff.get(position).getAlamat());
            holder.tanggalsewa.setText(riwayatOff.get(position).getTgl_sewa());
            holder.tanggalkembali.setText(riwayatOff.get(position).getTgl_kembali());
            holder.NoHp.setText(riwayatOff.get(position).getNo_hp());
            Integer dendaku = Integer.parseInt(riwayatOff.get(position).getDenda());
            holder.denda.setText("Denda : "+formatRupiah.format(dendaku));
            holder.keterangan.setText("Keterangan : "+riwayatOff.get(position).getKeterangan());
            if(riwayatOff.get(position).getFoto_kostum()!=""){
                Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+riwayatOff.get(position).getFoto_kostum()).into(holder.fotokostum);
            }else{
                Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.fotokostum);
            }

            url_photo = APIClient.BASE_URL+"uploads/"+riwayatOff.get(position).getFoto_kostum();

    }

    @Override
    public int getItemCount() {
        return riwayatOff.size();
    }

    public class RiwayatOffViewHolder extends RecyclerView.ViewHolder {
        TextView id_pemesanan, namakostum, namapenyewa, jumlahsewa, alamat, tanggalsewa,tanggalkembali,total,harga,NoHp,denda,keterangan;
        ImageView fotokostum;
        public RiwayatOffViewHolder(@NonNull View itemView) {
            super(itemView);
            id_pemesanan = (TextView) itemView.findViewById(R.id.idPemesanan);
            namakostum =(TextView) itemView.findViewById(R.id.namaKostum);
            namapenyewa =(TextView) itemView.findViewById(R.id.namaPenyewa);
            jumlahsewa =(TextView) itemView.findViewById(R.id.jmlSewa);
            alamat =(TextView) itemView.findViewById(R.id.alamat);
            tanggalsewa =(TextView) itemView.findViewById(R.id.tglSewa);
            tanggalkembali =(TextView) itemView.findViewById(R.id.tglKembali);
            fotokostum=(ImageView) itemView.findViewById(R.id.gambarKostum);
            total= (TextView) itemView.findViewById(R.id.total);
            harga = (TextView) itemView.findViewById(R.id.harga);
            NoHp = (TextView) itemView.findViewById(R.id.NoHp);
            denda = (TextView) itemView.findViewById(R.id.denda);
            keterangan = (TextView) itemView.findViewById(R.id.keterangan);
        }
    }

    public void setFilter(ArrayList<PesanOff> filter){
        riwayatOff = new ArrayList<>();
        riwayatOff.addAll(filter);
        notifyDataSetChanged();
    }
}
