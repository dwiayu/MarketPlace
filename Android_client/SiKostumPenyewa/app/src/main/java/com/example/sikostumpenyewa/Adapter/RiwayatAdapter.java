package com.example.sikostumpenyewa.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumpenyewa.MODEL.GetKomentar;
import com.example.sikostumpenyewa.MODEL.Komentar;
import com.example.sikostumpenyewa.R;
import com.example.sikostumpenyewa.REST.APIClient;
import com.example.sikostumpenyewa.REST.APIInterface;
import com.example.sikostumpenyewa.RiwayatActivity;
import com.example.sikostumpenyewa.Utils.SaveSharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {
    String id_user;
    Context mContex;
    List<Komentar> daftarRiwayat;
    public RiwayatAdapter(List<Komentar> daftarRiwayat, Context mContex) {
        this.daftarRiwayat= daftarRiwayat;
        this.mContex = mContex;
    }

    @NonNull
    @Override
    public RiwayatAdapter.RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat,parent,false);
        RiwayatViewHolder mHolder= new RiwayatViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RiwayatAdapter.RiwayatViewHolder holder, int position) {
        holder.id_user.setText(daftarRiwayat.get(position).getId_user());
        holder.id_Detail.setText(daftarRiwayat.get(position).getId_detail());
        holder.nama_kostum.setText(daftarRiwayat.get(position).getNama_kostum());
        holder.nama_tempat.setText(daftarRiwayat.get(position).getNama_tempat());
        holder.denda.setText(daftarRiwayat.get(position).getDenda());
        holder.keterangan.setText(daftarRiwayat.get(position).getKeterangan());
        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                holder.id_Detail.getText().toString());
        Call<GetKomentar> mKomentar=mApiInterface.getKomentar(reqid_detail);
        mKomentar.enqueue(new Callback<GetKomentar>() {
            @Override
            public void onResponse(Call<GetKomentar> call, Response<GetKomentar> response) {
                if(response.body().getStatus().equals("success")){
                    holder.komentar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<GetKomentar> call, Throwable t) {

            }
        });
        if(daftarRiwayat.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarRiwayat.get(position).getFoto_kostum()).into(holder.foto_kostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.foto_kostum);
        }
        holder.tKomentar.setText(daftarRiwayat.get(position).getKomentar());
        id_user = SaveSharedPreferences.getId(mContex);
        holder.komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.layout_customdialog);
                dialog.setTitle("Tambah Komentar");
                final EditText text = (EditText) dialog.findViewById(R.id.tv_desc);
                text.setText("");
                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.dismiss();
                        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                id_user);
                        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                holder.id_Detail.getText().toString());
                        RequestBody reqkomentar = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                text.getText().toString());
                        Call<GetKomentar>mKomentar=mApiInterface.postKomentar(reqid_user,reqid_detail,reqkomentar);
                        mKomentar.enqueue(new Callback<GetKomentar>() {
                            @Override
                            public void onResponse(Call<GetKomentar> call, Response<GetKomentar> response) {
                                if (response.body().getStatus().equals("success")){

                                    Toast.makeText(holder.itemView.getContext(), "Komentar disimpan", Toast.LENGTH_SHORT).show();
                                    openBeranda(holder.itemView.getContext());;

                                }
                                else if(response.body().getStatus().equals("kosong")){
                                    holder.komentar.setVisibility(View.GONE);

                                }
                                else{

                                    Toast.makeText(holder.itemView.getContext(), "Gagal Hapus", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<GetKomentar> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return daftarRiwayat.size();
    }
    private void openBeranda(Context context){
        Intent intent = new Intent(context, RiwayatActivity.class);
        context.startActivity(intent);
    }
    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView id_user, id_Detail, nama_kostum, nama_tempat, denda, tKomentar, keterangan;
        ImageView foto_kostum;
        Button komentar;
        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            id_user =(TextView) itemView.findViewById(R.id.idUser);
            id_Detail =(TextView) itemView.findViewById(R.id.idDetail);
            nama_kostum =(TextView) itemView.findViewById(R.id.namaKostum);
            nama_tempat=(TextView) itemView.findViewById(R.id.namaTempat);
            denda =(TextView) itemView.findViewById(R.id.denda);
            tKomentar=(TextView) itemView.findViewById(R.id.tKomentar);
            komentar =(Button) itemView.findViewById(R.id.btKomentar);
            keterangan = (TextView) itemView.findViewById(R.id.keterangan);
            foto_kostum = (ImageView) itemView.findViewById(R.id.fotoKostum);
        }
    }
}
