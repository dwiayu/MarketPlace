package com.example.sikostumpenyewa.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sikostumpenyewa.MODEL.Komentar;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIInterface;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    List<Komentar> daftarReview;
    APIInterface mApiInterface;
    public  ReviewAdapter(List<Komentar> daftarReview){
        this.daftarReview=daftarReview;

    }
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review,parent,false);
        ReviewViewHolder mHolder= new ReviewViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.idkostum.setText(daftarReview.get(position).getId_kostum());
        holder.namauser.setText(daftarReview.get(position).getNama());
        holder.komentar.setText(daftarReview.get(position).getKomentar());
    }

    @Override
    public int getItemCount() {
        return daftarReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView idkostum, namauser, komentar;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            idkostum = (TextView) itemView.findViewById(R.id.idkostum);
            namauser = (TextView) itemView.findViewById(R.id.namauser);
            komentar =(TextView) itemView.findViewById(R.id.komentar);
        }
    }
}
