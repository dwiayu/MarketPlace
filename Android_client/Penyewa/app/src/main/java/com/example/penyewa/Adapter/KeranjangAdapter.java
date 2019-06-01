package com.example.penyewa.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import com.example.penyewa.BerandaActivity;
import com.example.penyewa.DataHelper.Constant;
import com.example.penyewa.DataHelper.DBAdapter;
import com.example.penyewa.DataHelper.MyDataHelper;
import com.example.penyewa.KeranjangActivity;
import com.example.penyewa.MODEL.Keranjang;
import com.example.penyewa.MODEL.KostumAll;
import com.example.penyewa.MODEL.TampilKeranjang;
import com.example.penyewa.R;
import com.example.penyewa.REST.APIClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class KeranjangAdapter  extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {
    private static final String TAG = "LOL";
    private Context c;
    MyDataHelper dataHelper;
    DBAdapter dbAdapter;
    //List<KostumAll> listKeranjang;
    List<TampilKeranjang> dataKeranjangArrayList;

public KeranjangAdapter(List<TampilKeranjang>dataKeranjangArrayList){

    this.dataKeranjangArrayList= dataKeranjangArrayList;

}
    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keranjang,parent,false);

        return new KeranjangAdapter.KeranjangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangViewHolder holder, final int position) {

        final TampilKeranjang tampilKeranjang= dataKeranjangArrayList.get(position);


      //  holder.tIdKeranjang.setText(tampilKeranjang.getId_keranjang());
        holder.tvNamaKostum.setText(tampilKeranjang.getNama_kostum());
        holder.tvNamaToko.setText(tampilKeranjang.getNama_tempat());
        holder.tvAlamat.setText(tampilKeranjang.getAlamat());
        holder.tvJumlah.setText(tampilKeranjang.getJumlah_kostum());
        holder.tvHarga.setText(tampilKeranjang.getHarga_kostum());
        holder.tJml.setText(tampilKeranjang.getJumlah_sewa());
        holder.tSub_harga.setText(tampilKeranjang.getSub_harga());

        holder.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter = new DBAdapter(c);
                dbAdapter.Delete(tampilKeranjang.getId_keranjang());
                Toast.makeText(c, "Remove "+tampilKeranjang.getNama_kostum()+" Dari DB", Toast.LENGTH_SHORT).show();
                dbAdapter.close();
                ((KeranjangActivity)c).finish();
                Intent a = new Intent(c, KeranjangActivity.class);
//                ((KeranjangActivity)c).finish();
                ((KeranjangActivity)c).startActivity(a);
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataKeranjangArrayList.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum, tvIdTempat,tvIdAlamat, tJml,tvNamaKostum,
                tvNamaToko,tvAlamat,tvHarga,tvJumlah,tvIdUser,tIdKeranjang,tSub_harga;
        Button batal;
        ImageView fotoKostum;
        public KeranjangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser =(TextView) itemView.findViewById(R.id.id_user);
            tvIdKostum=(TextView) itemView.findViewById(R.id.idKostum);
            tvIdTempat = (TextView) itemView.findViewById(R.id.id_tempat);
            tvIdAlamat =(TextView) itemView.findViewById(R.id.id_alamat);
            tvNamaKostum= (TextView) itemView.findViewById(R.id.namaKostum);
            tvAlamat =(TextView) itemView.findViewById(R.id.alamat);
            tvNamaToko = (TextView) itemView.findViewById(R.id.namaTempat);
            tvHarga = (TextView) itemView.findViewById(R.id.hargaKostum);
            tvJumlah = (TextView) itemView.findViewById(R.id.jumlahKostum);
            batal =(Button) itemView.findViewById(R.id.btlSewa);
            tJml=(TextView) itemView.findViewById(R.id.jml);
            tIdKeranjang=(TextView)itemView.findViewById(R.id.kjId);
            tSub_harga=(TextView) itemView.findViewById(R.id.totalBayar);

        }
    }
}
