package com.example.gsevie.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.EditKategoriActivity;
import com.example.gsevie.EditKostumActivity;
import com.example.gsevie.MODEL.Kategori;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {

    private List<Kategori> daftarKategori = new ArrayList<>();

    public  KategoriAdapter(List<Kategori> listKategori){
        daftarKategori.clear();
        daftarKategori.addAll(listKategori);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KategoriAdapter.KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategori,parent,false);
        KategoriAdapter.KategoriViewHolder mHolder=new KategoriAdapter.KategoriViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KategoriAdapter.KategoriViewHolder holder, final int position) {
        holder.kategori.setText(daftarKategori.get(position).getKategori());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent= new Intent(v.getContext(), EditKategoriActivity.class);
                mIntent.putExtra("id_kategori",daftarKategori.get(position).getId_kategori());
                mIntent.putExtra("kategori",daftarKategori.get(position).getKategori());
                v.getContext().startActivity(mIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return daftarKategori.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        TextView kategori;
        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            kategori = (TextView) itemView.findViewById(R.id.kategori);

        }
    }

    public void setFilter(ArrayList<Kategori> filter){
        daftarKategori = new ArrayList<>();
        daftarKategori.addAll(filter);
        notifyDataSetChanged();
    }
}
