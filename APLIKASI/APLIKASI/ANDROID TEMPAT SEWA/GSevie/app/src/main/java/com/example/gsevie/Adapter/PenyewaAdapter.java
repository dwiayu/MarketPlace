package com.example.gsevie.Adapter;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.AlamatActivity;
import com.example.gsevie.DetailPemesananActivity;
import com.example.gsevie.MODEL.ProfilId;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PenyewaAdapter extends RecyclerView.Adapter<PenyewaAdapter.PenyewaViewHolder> {
    String url_photo;
    private List<ProfilId> daftarPeyewa = new ArrayList<>();
    public PenyewaAdapter(List<ProfilId> listPenyewa){
        daftarPeyewa.clear();
        daftarPeyewa.addAll(listPenyewa);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PenyewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penyewa,parent,false);
        PenyewaViewHolder mHolder=new PenyewaAdapter.PenyewaViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PenyewaAdapter.PenyewaViewHolder holder, final int position) {
        holder.idUser.setText(daftarPeyewa.get(position).getId_user());
        holder.namapenyewa.setText(daftarPeyewa.get(position).getNama());
        holder.email.setText(daftarPeyewa.get(position).getEmail());
        holder.nohp.setText(daftarPeyewa.get(position).getNo_hp());

        if (daftarPeyewa.get(position).getJenis_kelamin().equals("P")){
            Glide.with(holder.itemView.getContext()).load(R.drawable.female).into(holder.gender);
            holder.jeniskelamin.setText("Perempuan");
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.male).into(holder.gender);
            holder.jeniskelamin.setText("Laki - Laki");
        }

        if(daftarPeyewa.get(position).getFoto_user()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPeyewa.get(position).getFoto_user()).into(holder.fotopenyewa);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.user).into(holder.fotopenyewa);
        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarPeyewa.get(position).getFoto_user();

        holder.alamatKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), AlamatActivity.class);
                intent2.putExtra("id_user", daftarPeyewa.get(position).getId_user());
                v.getContext().startActivity(intent2);

            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarPeyewa.size();
    }

    public class PenyewaViewHolder extends RecyclerView.ViewHolder {
        TextView idUser,namapenyewa,jeniskelamin, nohp,email;
        ImageView fotopenyewa,gender, alamatKu;
        public PenyewaViewHolder(@NonNull View itemView) {
            super(itemView);
            idUser = (TextView) itemView.findViewById(R.id.idUser);
            namapenyewa = (TextView) itemView.findViewById(R.id.namaUser);
            jeniskelamin = (TextView) itemView.findViewById(R.id.jenisKelamin);
            nohp= (TextView) itemView.findViewById(R.id.noHp);
            email =(TextView) itemView.findViewById(R.id.email);
            fotopenyewa =(ImageView) itemView.findViewById(R.id.fotoUser);
            gender =(ImageView) itemView.findViewById(R.id.gender);
            alamatKu = (ImageView) itemView.findViewById(R.id.alamatKu);
        }
    }
    public void setFilter(ArrayList<ProfilId> filter){
        daftarPeyewa = new ArrayList<>();
        daftarPeyewa.addAll(filter);
        notifyDataSetChanged();
    }
}
