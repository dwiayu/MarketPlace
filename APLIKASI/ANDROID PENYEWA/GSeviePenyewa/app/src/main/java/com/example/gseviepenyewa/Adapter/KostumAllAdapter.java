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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gseviepenyewa.DataHelper.DBAdapter;
import com.example.gseviepenyewa.DataHelper.MyDataHelper;
import com.example.gseviepenyewa.MODEL.GetTempat;
import com.example.gseviepenyewa.MODEL.KostumAll;
import com.example.gseviepenyewa.OnGoingSewaActivity;
import com.example.gseviepenyewa.R;
import com.example.gseviepenyewa.REST.APIClient;
import com.example.gseviepenyewa.REST.APIInterface;
import com.example.gseviepenyewa.ReviewKostumActivity;
import com.example.gseviepenyewa.Utils.SaveSharedPreferences;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.GRAY;
import static android.view.View.GONE;

public class KostumAllAdapter extends RecyclerView.Adapter<KostumAllAdapter.KostumAllViewHolder>{
    private Context mContext;
    MyDataHelper dbHelper;
    DBAdapter dbAdapter;
    String url_photo, id_user;
    APIInterface mApiInterface;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    public static KostumAllAdapter layarUtama;
    private List<KostumAll> daftarKostum = new ArrayList<>();

    public KostumAllAdapter(Context c){
        mContext= c;
    }


    public KostumAllAdapter(List<KostumAll>listKostum, Context c){
        mContext = c;
        dbAdapter = new DBAdapter(mContext);
        daftarKostum.clear();
        daftarKostum.addAll(listKostum);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public KostumAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kostum_all,parent,false);
        KostumAllViewHolder mHolder = new KostumAllViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KostumAllAdapter.KostumAllViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(daftarKostum.get(position).getHarga_kostum());
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        holder.tvJumlahKostum.setText("Stok "+daftarKostum.get(position).getJumlah_kostum());
        holder.tvHargaKostum.setText(formatRupiah.format(harga));
        holder.deskripsiKostum.setText(daftarKostum.get(position).getDeskripsi_kostum());
        holder.tvNamaKategori.setText(daftarKostum.get(position).getNama_kategori());
        if(daftarKostum.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.fotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.splash_user).into(holder.fotoKostum);
        }
        id_user = SaveSharedPreferences.getId(mContext);
        if (dbAdapter.selectKeranjang(id_user,daftarKostum.get(position).getId_kostum())){

            id_user = SaveSharedPreferences.getId(mContext);

            holder.keranjang.setEnabled(false);
            holder.keranjang.setBackgroundColor(GRAY);


        }
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetTempat> cekTempatTutup = mApiInterface.getTutup();
        cekTempatTutup.enqueue(new Callback<GetTempat>() {
            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                if (response.body().getStatus().equals("success")){
                    holder.keranjang.setVisibility(GONE);
                    Toast.makeText(mContext, "Tempat Sewa Sedang Tutup", Toast.LENGTH_LONG).show();
                }else{
                    holder.keranjang.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {

            }
        });

        url_photo = APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum();
        id_user = SaveSharedPreferences.getId(mContext);

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), ReviewKostumActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("nama_user", daftarKostum.get(position).getNama());
                mIntent.putExtra("komentar", daftarKostum.get(position).getKomentar());
                v.getContext().startActivity(mIntent);


            }
        });




        holder.keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), OnGoingSewaActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("nama_kostum", daftarKostum.get(position).getNama_kostum());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("harga_kostum", daftarKostum.get(position).getHarga_kostum());
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

    public class KostumAllViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvNamaKostum,deskripsiKostum,tvJumlahKostum,tvHargaKostum, tvStatusTempat, tvNamaKategori;
        Button keranjang,review,lokasiKu;
        ImageView fotoKostum;
        ProgressBar progressBarD;
        public KostumAllViewHolder(@NonNull View itemView) {
            super(itemView);
            lokasiKu = (Button) itemView.findViewById(R.id.btn_navigasi);
            tvIdKostum = (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvNamaKostum =(TextView) itemView.findViewById(R.id.tvNamaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.jumlahKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.hargaKostum);
            keranjang =(Button)itemView.findViewById(R.id.btKeranjang);
            deskripsiKostum =(TextView) itemView.findViewById(R.id.deskripsiKostum);
            fotoKostum =(ImageView) itemView.findViewById(R.id.gambarKostum);
            keranjang =(Button) itemView.findViewById(R.id.btKeranjang);
            review=(Button) itemView.findViewById(R.id.btReview);
            tvNamaKategori = (TextView) itemView.findViewById(R.id.tvNamaKategori);
            progressBarD =(ProgressBar) itemView.findViewById(R.id.simpleProgressBarr);
            tvStatusTempat = (TextView) itemView.findViewById(R.id.statusTempat);
        }
    }

    public void setFilter(ArrayList<KostumAll> filter){
        daftarKostum = new ArrayList<>();
        daftarKostum.addAll(filter);
        notifyDataSetChanged();
    }

    }

