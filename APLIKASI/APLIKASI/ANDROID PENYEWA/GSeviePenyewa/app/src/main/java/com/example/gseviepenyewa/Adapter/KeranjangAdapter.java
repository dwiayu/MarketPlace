package com.example.gseviepenyewa.Adapter;

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

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.DataHelper.DBAdapter;
import com.example.gseviepenyewa.KeranjangActivity;
import com.example.gseviepenyewa.MODEL.GetKostumAll;
import com.example.gseviepenyewa.MODEL.TampilKeranjang;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {

    private static final String TAG = "LOL";
    private Context c;
    DBAdapter dbAdapter;
    APIInterface mApiInterface;

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
    public void onBindViewHolder(@NonNull final KeranjangViewHolder holder, final int position) {
        final TampilKeranjang tampilKeranjang= dataKeranjangArrayList.get(position);
        Locale localeID= new Locale("in","ID");
        NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        final NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        holder.tIdKeranjang.setText(tampilKeranjang.getId_kerajang());
        holder.tvIdKostum.setText(tampilKeranjang.getId_kostum());
        holder.tvNamaKostum.setText(tampilKeranjang.getNama_kostum());
        holder.tvJumlah.setText(tampilKeranjang.getJumlah_kostum());
        Integer harga = Integer.parseInt(tampilKeranjang.getHarga_kostum());
        holder.tvHarga.setText(formatRupiah.format(harga));
        holder.tJml.setText(tampilKeranjang.getJumlah_sewa());
        Integer sub_harga = Integer.parseInt(tampilKeranjang.getSub_harga());
        holder.tSub_harga.setText(formatRupiah.format(sub_harga));
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
        TextView tvIdKostum, stokAkhir, tJml,tvNamaKostum,tvHarga,tvJumlah,tvIdUser,tIdKeranjang,tSub_harga;
        Button batal;
        ImageView fotoKostum;
        public KeranjangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser =(TextView) itemView.findViewById(R.id.id_user);
            tvIdKostum=(TextView) itemView.findViewById(R.id.idKostum);
            tvNamaKostum= (TextView) itemView.findViewById(R.id.namaKostum);
            tvHarga = (TextView) itemView.findViewById(R.id.hargaKostum);
            tvJumlah = (TextView) itemView.findViewById(R.id.jumlahKostum);
            batal = (Button) itemView.findViewById(R.id.btlSewa);
            tJml=(TextView) itemView.findViewById(R.id.jml);
            tIdKeranjang=(TextView)itemView.findViewById(R.id.kjId);
            stokAkhir = (TextView) itemView.findViewById(R.id.stok_akhir);
            tSub_harga=(TextView) itemView.findViewById(R.id.totalBayar);
            fotoKostum = (ImageView) itemView.findViewById(R.id.gambarKostum);



        }
    }


}
