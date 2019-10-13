package com.example.gsevie.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static android.view.View.VISIBLE;

public class DetailSewaAdapter extends RecyclerView.Adapter<DetailSewaAdapter.DetailSewaViewHolder> {
    APIInterface mAPIInterface;
    List<Pemesanan> daftarDetail;
    String url_photo;

    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    public  DetailSewaAdapter(List<Pemesanan>daftarDetail){

        this.daftarDetail=daftarDetail;
    }

    @NonNull
    @Override
    public DetailSewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_sewa,parent,false);
        DetailSewaViewHolder mHolder = new DetailSewaAdapter.DetailSewaViewHolder(view);
        return  mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final  DetailSewaAdapter.DetailSewaViewHolder holder, int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga_kostum = Integer.parseInt(daftarDetail.get(position).getHarga_kostum());
        holder.namaKostum.setText(daftarDetail.get(position).getNama_kostum());
        holder.jumlahSewa.setText(daftarDetail.get(position).getJumlah());
        holder.hargaKostum.setText(formatRupiah.format(harga_kostum));
        if(daftarDetail.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarDetail.get(position).getFoto_kostum()).into(holder.fotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.fotoKostum);
        }


        url_photo = APIClient.BASE_URL+"uploads/"+daftarDetail.get(position).getFoto_kostum();
        Integer jumlah = Integer.parseInt(daftarDetail.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarDetail.get(position).getHarga_kostum());
        Integer total = jumlah*harga;
        holder.total.setText(formatRupiah.format(total));
    }

    @Override
    public int getItemCount() {
        return daftarDetail.size();
    }

    public class DetailSewaViewHolder extends RecyclerView.ViewHolder {
        TextView namaKostum, jumlahSewa, hargaKostum, total;
        ImageView fotoKostum;
        public DetailSewaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKostum = (TextView) itemView.findViewById(R.id.namaKostum);
            jumlahSewa = (TextView) itemView.findViewById(R.id.jumlah_kostun);
            hargaKostum =(TextView) itemView.findViewById(R.id.harga_kostum);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
            fotoKostum =(ImageView) itemView.findViewById(R.id.gbrKostum);

        }
    }
}
