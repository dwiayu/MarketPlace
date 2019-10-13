package com.example.gseviepenyewa.Adapter;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.MODEL.GetKomentar;
import com.example.gseviepenyewa.MODEL.Komentar;
import com.example.gseviepenyewa.MODEL.Pesan;
import com.example.gseviepenyewa.MenuBerandaActivity;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.RiwayatActivity;
import com.example.gseviepenyewa.RiwayatDetailActivity;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatDetailAdapter  extends RecyclerView.Adapter<RiwayatDetailAdapter.RiwayatDetailViewHolder> {
    String id_user;
    Context mContex;
    List<Pesan> daftarPesan;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    public RiwayatDetailAdapter(List<Pesan> daftarPesan, Context mContex) {
        this.daftarPesan= daftarPesan;
        this.mContex = mContex;
    }

    @NonNull
    @Override
    public RiwayatDetailAdapter.RiwayatDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat_detail,parent,false);
        RiwayatDetailAdapter.RiwayatDetailViewHolder mHolder = new RiwayatDetailAdapter.RiwayatDetailViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RiwayatDetailAdapter.RiwayatDetailViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga_kostum = Integer.parseInt(daftarPesan.get(position).getHarga_kostum());
        holder.id_sewa.setText(daftarPesan.get(position).getId_sewa());
        id_user = SaveSharedPreferences.getId(mContex);
        holder.id_Detail.setText(daftarPesan.get(position).getId_detail());
        holder.nama_kostum.setText(daftarPesan.get(position).getNama_kostum());
        holder.harga_kostum.setText(formatRupiah.format(harga_kostum));
        holder.jumlah_sewa.setText(daftarPesan.get(position).getJumlah());
        Integer jml = Integer.parseInt(daftarPesan.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarPesan.get(position).getHarga_kostum());

        if(daftarPesan.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getFoto_kostum()).into(holder.gambarKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarKostum);

        }

        Integer totalKu= harga*jml;

        holder.total.setText(formatRupiah.format(totalKu));

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

        holder.komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final EditText text;
                final Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.layout_komentar);
                dialog.setTitle("Tambah Komentar");
                text = (EditText) dialog.findViewById(R.id.tv_desc);
                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressBar simpleProgressBar= (ProgressBar) dialog.findViewById(R.id.simpleProgressBarr);
                        final LinearLayout progressLayout = (LinearLayout) dialog.findViewById(R.id.progressLayout);
                        simpleProgressBar.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.VISIBLE);
                        if (text.getText().toString().length()==0){
                            simpleProgressBar.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            Toast.makeText(holder.itemView.getContext(), "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                        }else{
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
                                        simpleProgressBar.setVisibility(View.GONE);
                                        progressLayout.setVisibility(View.GONE);
                                        Toast.makeText(holder.itemView.getContext(), "Komentar disimpan", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(view.getContext(), RiwayatActivity.class);
                                        view.getContext().startActivity(intent);

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

                    }
                });
                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return daftarPesan.size();
    }


    public class RiwayatDetailViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kostum,id_Detail,id_user, jumlah_sewa, harga_kostum, id_sewa, total;
        Button komentar;
        ImageView gambarKostum;
        ProgressBar simpleProgressBar;
        LinearLayout progressLayout;
        public RiwayatDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            id_sewa = (TextView) itemView.findViewById(R.id.tvIdSewa);
            id_user =(TextView) itemView.findViewById(R.id.idUser);
            id_Detail =(TextView) itemView.findViewById(R.id.idDetail);
            nama_kostum = (TextView) itemView.findViewById(R.id.namaKostum);
            harga_kostum =(TextView) itemView.findViewById(R.id.harga_kostum);
            jumlah_sewa = (TextView) itemView.findViewById(R.id.jumlah_kostun);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
            komentar =(Button) itemView.findViewById(R.id.btKomentar);
            gambarKostum = (ImageView) itemView.findViewById(R.id.gbrKostum);

        }
    }
}
