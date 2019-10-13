package com.example.gseviepenyewa.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.Komentar;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewKostumAdapter extends RecyclerView.Adapter<ReviewKostumAdapter.ReviewKostumViewHolder> {

    List<Komentar> daftarReview;
    APIInterface mApiInterface;
    public  ReviewKostumAdapter(List<Komentar> daftarReview){
        this.daftarReview=daftarReview;

    }
    @NonNull
    @Override
    public ReviewKostumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_kostum,parent,false);
        ReviewKostumViewHolder mHolder= new ReviewKostumViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewKostumAdapter.ReviewKostumViewHolder holder, int position) {
        holder.idkostum.setText(daftarReview.get(position).getId_kostum());
        holder.namauser.setText(daftarReview.get(position).getNama());
        holder.komentar.setText(daftarReview.get(position).getKomentar());
        holder.tglTransaksi.setText(daftarReview.get(position).getTgl_transaksi());
        if(daftarReview.get(position).getFoto_user()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarReview.get(position).getFoto_user()).into(holder.gambarUser);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.user).into(holder.gambarUser);

        }
    }

    @Override
    public int getItemCount() {
        return daftarReview.size();
    }

    public class ReviewKostumViewHolder extends RecyclerView.ViewHolder {
        TextView idkostum, namauser, komentar, tglTransaksi;
        ImageView gambarUser;
        public ReviewKostumViewHolder(@NonNull View itemView) {
            super(itemView);
            idkostum = (TextView) itemView.findViewById(R.id.idkostum);
            namauser = (TextView) itemView.findViewById(R.id.namauser);
            komentar =(TextView) itemView.findViewById(R.id.komentar);
            tglTransaksi =(TextView) itemView.findViewById(R.id.tglTransaksi);
            gambarUser = (ImageView) itemView.findViewById(R.id.gbrUser);
        }
    }

}
