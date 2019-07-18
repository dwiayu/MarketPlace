package com.example.sikostumpenyewa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikostumpenyewa.DataHelper.DBAdapter;
import com.example.sikostumpenyewa.KeranjangActivity;
import com.example.sikostumpenyewa.MODEL.TampilKeranjang;
import com.example.sikostumpenyewa.R;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {

    private static final String TAG = "LOL";
    private Context c;
    DBAdapter dbAdapter;
    int stok_akhir;

    //List<KostumAll> listKeranjang;
    List<TampilKeranjang> dataKeranjangArrayList;

    public KeranjangAdapter(Context ctx, List<TampilKeranjang>dataKeranjangArrayList, DBAdapter adapterku){

        this.dataKeranjangArrayList= dataKeranjangArrayList;
        this.c= ctx;
        this.dbAdapter= adapterku;

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

        holder.tIdKeranjang.setText(tampilKeranjang.getId_kerajang());
        holder.tvIdKostum.setText(tampilKeranjang.getId_kostum());
        holder.tvNamaKostum.setText(tampilKeranjang.getNama_kostum());
        holder.tvNamaToko.setText(tampilKeranjang.getNama_tempat());
        holder.tvAlamat.setText(tampilKeranjang.getAlamat());
        holder.tvJumlah.setText(tampilKeranjang.getJumlah_kostum());
        holder.tvHarga.setText(tampilKeranjang.getHarga_kostum());
        holder.tJml.setText(tampilKeranjang.getJumlah_sewa());
        holder.tSub_harga.setText(tampilKeranjang.getSub_harga());
        Integer jumlah = Integer.parseInt(dataKeranjangArrayList.get(position).getJumlah_kostum());
        Integer jumlah_sewa = Integer.parseInt(dataKeranjangArrayList.get(position).getJumlah_sewa());

        Integer stok = jumlah - jumlah_sewa;
        holder.stokAkhir.setText(String.valueOf(stok));
        //String stoknya= holder.stokAkhir.getText().toString();

        holder.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete(position);

            }
        });





    }

    private void delete(int pos){
        dbAdapter.deleteItemCart(dataKeranjangArrayList.get(pos));
        dataKeranjangArrayList.remove(pos);
        Intent refresh = new Intent(c, KeranjangActivity.class);
        c.startActivity(refresh);
    }



    @Override
    public int getItemCount() {
        return dataKeranjangArrayList.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum, stokAkhir, tvIdTempat,tvIdAlamat, tJml,tvNamaKostum, tvNamaToko,tvAlamat,tvHarga,tvJumlah,tvIdUser,tIdKeranjang,tSub_harga;
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
            batal = (Button) itemView.findViewById(R.id.btlSewa);
            tJml=(TextView) itemView.findViewById(R.id.jml);
            tIdKeranjang=(TextView)itemView.findViewById(R.id.kjId);
            stokAkhir = (TextView) itemView.findViewById(R.id.stok_akhir);
            tSub_harga=(TextView) itemView.findViewById(R.id.totalBayar);



        }
    }

}
