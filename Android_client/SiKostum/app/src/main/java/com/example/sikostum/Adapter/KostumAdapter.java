package com.example.sikostum.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.sikostum.DaftarKostum;
import com.example.sikostum.LayarEditKostum;
import com.example.sikostum.MODEL.GetKostum;
import com.example.sikostum.MODEL.Kostum;
import com.example.sikostum.R;
import com.example.sikostum.REST.APIClient;
import com.example.sikostum.REST.APIInterface;
import com.example.sikostum.TempatSewaActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.Handshake;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostumAdapter  extends RecyclerView.Adapter<KostumAdapter.KostumViewHolder> {
    APIInterface mApiInterface;
    Handler mHandler;

    private static boolean dialogResult;
    List<Kostum> daftarKostum;
    public KostumAdapter(List<Kostum> daftarKostum){
        this.daftarKostum= daftarKostum;
    }
    @NonNull
    @Override
    public KostumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kostum,parent,false);
        KostumViewHolder mHolder=new KostumViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KostumAdapter.KostumViewHolder holder, final int position) {
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdTempat.setText(daftarKostum.get(position).getId_tempat());
        holder.tvIdKategori.setText(daftarKostum.get(position).getId_kategori());
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        holder.tvHargaKostum.setText(daftarKostum.get(position).getHarga_kostum());
        holder.tvJumlahKostum.setText(daftarKostum.get(position).getJumlah_kostum());
        holder.tvDeskripsiKostum.setText(daftarKostum.get(position).getDeskripsi_kostum());


        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
//                builder.setTitle("Anda Yakin Ingin Hapus Kostum ? ");
//                builder.setMessage("Yakin?");
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        getHapus();
//
//
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                       holder.itemView.getContext();
//                    }
//                });
//
                final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_kostum =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (holder.tvIdKostum.getText().toString().isEmpty()) ?
                                        "" : holder.tvIdKostum.getText().toString());
                Call<GetKostum>mKostum= mApiInterface.deleteKostum(reqid_kostum);
                mKostum.enqueue(new Callback<GetKostum>() {
                    @Override
                    public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                        Log.d("EditReview", response.body().getStatus());
                        if (response.body().getStatus().equals("success")){
                            Toast.makeText(holder.itemView.getContext(), "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                            openKostum(holder.itemView.getContext());

                        }else{
                            Toast.makeText(holder.itemView.getContext(), "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKostum> call, Throwable t) {

                    }
                });

            }
        });
       if(daftarKostum.get(position).getFoto_kostum()!=""){
           Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.imgFotoKostum);
       }else{
           Glide.with(holder.itemView.getContext()).load(R.drawable.ic_account_circle_black_24dp).into(holder.imgFotoKostum);
       }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent= new Intent(v.getContext(), LayarEditKostum.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("id_tempat",daftarKostum.get(position).getId_tempat());
                mIntent.putExtra("id_kategori", daftarKostum.get(position).getId_kategori());
                mIntent.putExtra("nama_kostum", daftarKostum.get(position).getNama_kostum());
                mIntent.putExtra("harga_kostum", daftarKostum.get(position).getHarga_kostum());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("deskripsi_kostum",daftarKostum.get(position).getDeskripsi_kostum());
                mIntent.putExtra("foto_kostum", daftarKostum.get(position).getFoto_kostum());
                v.getContext().startActivity(mIntent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return daftarKostum.size();
    }
 private void openKostum(Context context){
        Intent intent = new Intent(context,DaftarKostum.class);
        context.startActivity(intent);
 }
    public class KostumViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvIdKategori,tvNamaKostum,tvHargaKostum,tvJumlahKostum,tvDeskripsiKostum, tvIdTempat;
        ImageView imgFotoKostum;
        Button hapus;
        public KostumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdKostum= (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvIdTempat = (TextView)itemView.findViewById(R.id.tvIdTempat);
            tvIdKategori = (TextView) itemView.findViewById(R.id.tvIdKategori);
            tvNamaKostum = (TextView) itemView.findViewById(R.id.tRnamaKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.tRHargaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.tvRJumlah);
            tvDeskripsiKostum= (TextView) itemView.findViewById(R.id.tRDeskripsi);
            imgFotoKostum= (ImageView) itemView.findViewById(R.id.rFoto_kostum);
            hapus = (Button) itemView.findViewById(R.id.bRHapus);

        }
    }
}
