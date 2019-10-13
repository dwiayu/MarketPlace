package com.example.gsevie.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gsevie.DetailSewaActivity;
import com.example.gsevie.MODEL.GetPesanOff;
import com.example.gsevie.MODEL.PesanOff;
import com.example.gsevie.PesanOfflineActivity;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.example.gsevie.REST.APIInterface;
import com.example.gsevie.SewaOfflineActivity;
import com.squareup.picasso.Picasso;

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

public class PesanOffAdapter extends RecyclerView.Adapter<PesanOffAdapter.PesanOffViewHolder>  {
    APIInterface mApiInterface;
    String url_photo;
    private List<PesanOff> daftarOff = new ArrayList<>();
    public  PesanOffAdapter(List<PesanOff> listPesan){
        daftarOff.clear();
        daftarOff.addAll(listPesan);
        notifyDataSetChanged();
    }


    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    @NonNull
    @Override
    public PesanOffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesan_off,parent,false);
        PesanOffViewHolder mHolder=new PesanOffAdapter.PesanOffViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PesanOffAdapter.PesanOffViewHolder holder, final int position) {
        holder.id_pemesanan.setText(daftarOff.get(position).getId_pemesanan());
        holder.namakostum.setText(daftarOff.get(position).getNama_kostum());
        holder.namapenyewa.setText(daftarOff.get(position).getNama());
        holder.jumlahsewa.setText("Jumlah "+daftarOff.get(position).getJumlah());
        holder.harga.setText(daftarOff.get(position).getHarga_kostum());
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer totalku = Integer.parseInt((daftarOff.get(position).getJumlah()))*Integer.parseInt(daftarOff.get(position).getHarga_kostum());
        holder.total.setText("Total "+formatRupiah.format(totalku));
        holder.alamat.setText(daftarOff.get(position).getAlamat());
        holder.tanggalsewa.setText(daftarOff.get(position).getTgl_sewa());
        holder.tanggalkembali.setText(daftarOff.get(position).getTgl_kembali());
        holder.NoHp.setText(daftarOff.get(position).getNo_hp());

        Integer terlambat = Integer.parseInt(daftarOff.get(position).getJumlahTerlambat());
        if (terlambat<0){
            Integer hari = terlambat*(-1);
            holder.hitungTerlambat.setText(String.valueOf(hari)+" Hari");
        }else if(terlambat == 0) {
            holder.hitungTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.GONE);
            holder.layout_green.setVisibility(View.GONE);
            holder.textKembali.setVisibility(View.VISIBLE);
            holder.layout_kembali.setVisibility(View.VISIBLE);
        }else{
            holder.hitungTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
            holder.textSewa.setVisibility(View.VISIBLE);
            holder.layout_green.setVisibility(View.VISIBLE);
            holder.textKembali.setVisibility(View.GONE);
            holder.layout_kembali.setVisibility(View.GONE);
        }

        if(daftarOff.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarOff.get(position).getFoto_kostum()).into(holder.fotokostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.fotokostum);
        }

        url_photo = APIClient.BASE_URL+"uploads/"+daftarOff.get(position).getFoto_kostum();
        holder.kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog= new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.layout_dendaoff);
                dialog.setTitle("Input Denda");
                final EditText text = (EditText) dialog.findViewById(R.id.tv_denda);
                final EditText keterangan = (EditText) dialog.findViewById(R.id.tv_ket);
                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressBar simpleProgressBar= (ProgressBar) dialog.findViewById(R.id.simpleProgressBarr);
                        final LinearLayout progressLayout = (LinearLayout) dialog.findViewById(R.id.progressLayout);
                        simpleProgressBar.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.VISIBLE);
                        if(text.getText().toString().length()==0 || keterangan.getText().toString().length()==0){
                            simpleProgressBar.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            Toast.makeText(holder.itemView.getContext(), "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                        }else{
                            mApiInterface= APIClient.getClient().create(APIInterface.class);
                            RequestBody reqid_pesan =MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    holder.id_pemesanan.getText().toString());
                            RequestBody reqdenda = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    text.getText().toString());
                            RequestBody reqketerangan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                    keterangan.getText().toString());
                            Call<GetPesanOff>putOff = mApiInterface.putOff(reqid_pesan, reqdenda, reqketerangan);
                            putOff.enqueue(new Callback<GetPesanOff>() {
                                @Override
                                public void onResponse(Call<GetPesanOff> call, Response<GetPesanOff> response) {
                                    if(response.body().getStatus().equals("success")){
                                        simpleProgressBar.setVisibility(View.GONE);
                                        progressLayout.setVisibility(View.GONE);
                                        Toast.makeText(holder.itemView.getContext(),"Kostum telah dikembalikan", Toast.LENGTH_LONG).show();
                                        Intent mIntent = new Intent(view.getContext(), SewaOfflineActivity.class);
                                        view.getContext().startActivity(mIntent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<GetPesanOff> call, Throwable t) {

                                }
                            });
                        }


                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarOff.size();
    }

    public class PesanOffViewHolder extends RecyclerView.ViewHolder {
        TextView id_pemesanan, namakostum, namapenyewa, jumlahsewa, alamat, tanggalsewa,tanggalkembali,total,harga,NoHp,hitungTerlambat, textSewa, textKembali;
        ImageView fotokostum;
        LinearLayout layout_late, layout_green, layout_kembali;
        Button kembali;
        public PesanOffViewHolder(@NonNull View itemView) {
            super(itemView);
            id_pemesanan = (TextView) itemView.findViewById(R.id.idPemesanan);
            namakostum =(TextView) itemView.findViewById(R.id.namaKostum);
            namapenyewa =(TextView) itemView.findViewById(R.id.namaPenyewa);
            jumlahsewa =(TextView) itemView.findViewById(R.id.jmlSewa);
            alamat =(TextView) itemView.findViewById(R.id.alamat);
            tanggalsewa =(TextView) itemView.findViewById(R.id.tglSewa);
            tanggalkembali =(TextView) itemView.findViewById(R.id.tglKembali);
            fotokostum=(ImageView) itemView.findViewById(R.id.gambarKostum);
            kembali= (Button)itemView.findViewById(R.id.btKembali);
            total= (TextView) itemView.findViewById(R.id.total);
            harga = (TextView) itemView.findViewById(R.id.harga);
            NoHp = (TextView) itemView.findViewById(R.id.NoHp);
            hitungTerlambat = (TextView) itemView.findViewById(R.id.hariTerlambat);
            layout_green = (LinearLayout) itemView.findViewById(R.id.layout_rent);
            layout_late = (LinearLayout) itemView.findViewById(R.id.layout_late);
            textSewa = (TextView) itemView.findViewById(R.id.textSewa);
            layout_kembali= (LinearLayout) itemView.findViewById(R.id.layout_kembali);
            textKembali= (TextView) itemView.findViewById(R.id.textKembali);
        }
    }

    public void setFilter(ArrayList<PesanOff> filter){
        daftarOff = new ArrayList<>();
        daftarOff.addAll(filter);
        notifyDataSetChanged();
    }

}
