package com.example.gsevie.Adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.Komentar;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.KomentarViewHolder> {
    String url_photo, url_user;
    private List<Komentar> daftarKomentar = new ArrayList<>();
    public KomentarAdapter(List<Komentar> listKomentar)
    {
        daftarKomentar.clear();
        daftarKomentar.addAll(listKomentar);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KomentarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komentar,parent,false);
        KomentarViewHolder mHolder=new KomentarAdapter.KomentarViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KomentarAdapter.KomentarViewHolder holder, int position) {
        holder.namaKotum.setText(daftarKomentar.get(position).getNama_kostum());
        holder.tglTransaksi.setText(daftarKomentar.get(position).getTgl_transaksi());
        holder.namauser.setText(daftarKomentar.get(position).getNama());
        holder.komentar.setText(daftarKomentar.get(position).getKomentar());
        if(daftarKomentar.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKomentar.get(position).getFoto_kostum()).into(holder.gambarKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.gambarKostum);
        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarKomentar.get(position).getFoto_kostum();
        if(daftarKomentar.get(position).getFoto_user()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKomentar.get(position).getFoto_user()).into(holder.fotoUser);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.fotoUser);
        }
        url_user= APIClient.BASE_URL+"uploads/"+daftarKomentar.get(position).getFoto_user();

    }

    @Override
    public int getItemCount() {
        return daftarKomentar.size();
    }

    public class KomentarViewHolder extends RecyclerView.ViewHolder {
        TextView namaKotum, tglTransaksi, namauser, komentar;
        ImageView gambarKostum,fotoUser;
        public KomentarViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKotum = (TextView) itemView.findViewById(R.id.tvNamaKostum);
            tglTransaksi = (TextView) itemView.findViewById(R.id.tvTglTrans);
            namauser = (TextView) itemView.findViewById(R.id.namaUser);
            komentar =(TextView) itemView.findViewById(R.id.komentar);
            gambarKostum =(ImageView) itemView.findViewById(R.id.gbrKstm);
            fotoUser = (ImageView) itemView.findViewById(R.id.fotoUser);
        }
    }

    public void setFilter(ArrayList<Komentar> filter){
        daftarKomentar = new ArrayList<>();
        daftarKomentar.addAll(filter);
        notifyDataSetChanged();
    }
}
