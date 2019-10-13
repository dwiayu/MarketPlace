package com.example.gsevie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.EditKostumActivity;
import com.example.gsevie.KostumActivity;
import com.example.gsevie.MODEL.GetKostum;
import com.example.gsevie.MODEL.Kostum;
import com.example.gsevie.PesanOfflineActivity;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanAdapter extends RecyclerView.Adapter<PeminjamanAdapter.PeminjamanViewHolder> {

    String url_photo;
    private List<Kostum> daftarKostum = new ArrayList<>();

    public  PeminjamanAdapter(List<Kostum> listKostum){
        daftarKostum.clear();
        daftarKostum.addAll(listKostum);
        notifyDataSetChanged();
    }

    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);



    @NonNull
    @Override
    public PeminjamanAdapter.PeminjamanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peminjaman,parent,false);
        PeminjamanAdapter.PeminjamanViewHolder mHolder=new PeminjamanAdapter.PeminjamanViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PeminjamanAdapter.PeminjamanViewHolder holder, final int position) {
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdKategori.setText(daftarKostum.get(position).getId_kategori());
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(daftarKostum.get(position).getHarga_kostum());
        holder.tvHargaKostum.setText(formatRupiah.format(harga));
        holder.tvJumlahKostum.setText(daftarKostum.get(position).getJumlah_kostum());
        holder.tvDeskripsiKostum.setText(daftarKostum.get(position).getDeskripsi_kostum());
        holder.kategori.setText(daftarKostum.get(position).getKategori());

        if(daftarKostum.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.imgFotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.imgFotoKostum);
        }

        url_photo = APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum();
        holder.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent= new Intent(v.getContext(), PesanOfflineActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("harga_kostum",daftarKostum.get(position).getHarga_kostum());
                mIntent.putExtra("nama_kostum", daftarKostum.get(position).getNama_kostum());
                mIntent.putExtra("harga_kostum", daftarKostum.get(position).getHarga_kostum());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("deskripsi_kostum",daftarKostum.get(position).getDeskripsi_kostum());
                mIntent.putExtra("foto_kostum", daftarKostum.get(position).getFoto_kostum());
                mIntent.putExtra("foto_kostum_url", url_photo);
                v.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarKostum.size();
    }

    private void openKostum(Context context){
        Intent intent = new Intent(context, KostumActivity.class);
        context.startActivity(intent);
    }
    public class PeminjamanViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvIdKategori,tvNamaKostum,tvHargaKostum,tvJumlahKostum,tvDeskripsiKostum, tvIdTempat, kategori;
        ImageView imgFotoKostum;
        Button off;
        public PeminjamanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdKostum= (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvIdTempat = (TextView)itemView.findViewById(R.id.tvIdTempat);
            tvIdKategori = (TextView) itemView.findViewById(R.id.tvIdKategori);
            kategori = (TextView) itemView.findViewById(R.id.tvNamaKategori);
            tvNamaKostum = (TextView) itemView.findViewById(R.id.tRnamaKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.tRHargaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.tvRJumlah);
            tvDeskripsiKostum= (TextView) itemView.findViewById(R.id.tRDeskripsi);
            imgFotoKostum= (ImageView) itemView.findViewById(R.id.rFoto_kostum);
            off =(Button) itemView.findViewById(R.id.btOff);
        }
    }
    public void openOff(Context context){
        Intent intent = new Intent(context, PesanOfflineActivity.class);
        intent.putExtra("id_kostum",daftarKostum.get(0).getId_kostum());
        intent.putExtra("jumlah_kostum", daftarKostum.get(0).getJumlah_kostum());
        context.startActivity(intent);

    }

    public void setFilter(ArrayList<Kostum> filter){
        daftarKostum = new ArrayList<>();
        daftarKostum.addAll(filter);
        notifyDataSetChanged();
    }
}
