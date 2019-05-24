package com.example.penyewa.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.penyewa.EditAlamatActivity;
import com.example.penyewa.MODEL.Alamat;
import com.example.penyewa.R;

import java.util.List;

public class AlamatAdapter  extends RecyclerView.Adapter<AlamatAdapter.AlamatViewHolder> {
    List<Alamat> daftarAlamat;
    public AlamatAdapter(List<Alamat> daftarAlamat) {
        this.daftarAlamat = daftarAlamat;
    }
    @NonNull
    @Override
    public AlamatAdapter.AlamatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_alamat,parent,false);
        AlamatViewHolder mHolder = new AlamatViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlamatAdapter.AlamatViewHolder holder, final int position) {
        holder.tvId_Al.setText(daftarAlamat.get(position).getId_alamat());
        holder.tvLabel.setText(daftarAlamat.get(position).getLabel_alamat());
        holder.tvAlamat.setText(daftarAlamat.get(position).getAlamat());
        holder.tvKota.setText(daftarAlamat.get(position).getKota());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), EditAlamatActivity.class);
                intent2.putExtra("id_alamat",daftarAlamat.get(position).getId_alamat());
                intent2.putExtra("label_alamat",daftarAlamat.get(position).getLabel_alamat());
                intent2.putExtra("alamat", daftarAlamat.get(position).getAlamat());
                intent2.putExtra("kota",daftarAlamat.get(position).getKota());
                v.getContext().startActivity(intent2);

            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarAlamat.size();
    }

    public class AlamatViewHolder extends RecyclerView.ViewHolder {

        TextView tvId_Al,tvLabel,tvAlamat,tvKota;
        public AlamatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId_Al =(TextView) itemView.findViewById(R.id.tvIdAl);
            tvLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            tvAlamat = (TextView) itemView.findViewById(R.id.tvAlamat);
            tvKota = (TextView) itemView.findViewById(R.id.tvKota);
        }
    }
}
