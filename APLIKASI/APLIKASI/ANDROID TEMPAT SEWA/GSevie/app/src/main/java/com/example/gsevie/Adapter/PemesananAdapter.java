package com.example.gsevie.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gsevie.DetailPemesananActivity;
import com.example.gsevie.MODEL.Pemesanan;
import com.example.gsevie.R;
import com.example.gsevie.REST.APIClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.PesViewHolder> {
    String url_photo;

    private List<Pemesanan> daftarPesan = new ArrayList<>();


    public  PemesananAdapter(List<Pemesanan> listPesan){
        daftarPesan.clear();
        daftarPesan.addAll(listPesan);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PemesananAdapter.PesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan,parent,false);
        PesViewHolder mHolder =new PesViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananAdapter.PesViewHolder holder, final int position) {
        holder.tvIdDetail.setText(daftarPesan.get(position).getId_sewa());
        holder.tglTrannsaksi.setText(daftarPesan.get(position).getTgl_transaksi());
        holder.tglSewa.setText(daftarPesan.get(position).getTgl_sewa());
        holder.tglKembali.setText(daftarPesan.get(position).getTgl_kembali());
        holder.nama.setText(daftarPesan.get(position).getNama_user());
        Integer hari = Integer.parseInt(daftarPesan.get(position).getJumlahTerlambat());
        if(daftarPesan.get(position).getBukti_sewa().equals("")){
            Glide.with(holder.itemView.getContext()).load(R.drawable.shopping).into(holder.gambarBukti);
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_access_time_red_24dp).into(holder.statusTime);

            if (hari==0){
                holder.textUploadProses.setVisibility(View.GONE);
                holder.layout_uploadProses.setVisibility(View.GONE);
                holder.textUpload.setVisibility(View.VISIBLE);
                holder.layout_upload.setVisibility(View.VISIBLE);
                holder.textUpload1.setVisibility(View.GONE);
                holder.layout_upload1.setVisibility(View.GONE);
                holder.textTerlambat.setVisibility(View.GONE);
                holder.layout_late.setVisibility(View.GONE);
            }else if (hari == 1){
                holder.textUploadProses.setVisibility(View.GONE);
                holder.layout_uploadProses.setVisibility(View.GONE);
                holder.textUpload1.setVisibility(View.VISIBLE);
                holder.layout_upload1.setVisibility(View.VISIBLE);
                holder.textUpload.setVisibility(View.GONE);
                holder.layout_upload.setVisibility(View.GONE);
                holder.textTerlambat.setVisibility(View.GONE);
                holder.layout_late.setVisibility(View.GONE);
            }else{
                holder.textUploadProses.setVisibility(View.GONE);
                holder.layout_uploadProses.setVisibility(View.GONE);
                holder.textUpload.setVisibility(View.GONE);
                holder.layout_upload.setVisibility(View.GONE);
                holder.textTerlambat.setVisibility(View.VISIBLE);
                holder.layout_late.setVisibility(View.VISIBLE);
            }

        }else{
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa()).into(holder.gambarBukti);
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_access_time_green_24dp).into(holder.statusTime);
            holder.textUploadProses.setVisibility(View.VISIBLE);
            holder.layout_uploadProses.setVisibility(View.VISIBLE);
            holder.textUpload.setVisibility(View.GONE);
            holder.layout_upload.setVisibility(View.GONE);
            holder.textUpload1.setVisibility(View.GONE);
            holder.layout_upload1.setVisibility(View.GONE);
            holder.textTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);
        }

        if(daftarPesan.get(position).getStatus_detail().equals("tidak valid")){
            holder.statusTime.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.status.setText(daftarPesan.get(position).getStatus_detail());

            holder.layout_uploadTidakValid.setVisibility(View.VISIBLE);
            holder.textTidakValid.setVisibility(View.VISIBLE);
            holder.textUploadProses.setVisibility(View.GONE);
            holder.layout_uploadProses.setVisibility(View.GONE);
            holder.textUpload.setVisibility(View.GONE);
            holder.layout_upload.setVisibility(View.GONE);
            holder.textUpload1.setVisibility(View.GONE);
            holder.layout_upload1.setVisibility(View.GONE);
            holder.textTerlambat.setVisibility(View.GONE);
            holder.layout_late.setVisibility(View.GONE);


        }else{
            holder.statusTime.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);

            holder.layout_uploadTidakValid.setVisibility(View.GONE);
            holder.textTidakValid.setVisibility(View.GONE);
        }



        url_photo = APIClient.BASE_URL+"uploads/"+daftarPesan.get(position).getBukti_sewa();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DetailPemesananActivity.class);
                intent2.putExtra("id_sewa", daftarPesan.get(position).getId_sewa());
                intent2.putExtra("alamat", daftarPesan.get(position).getAlamat());
                intent2.putExtra("nama_kostum",daftarPesan.get(position).getNama_kostum());
                intent2.putExtra("harga_kostum", daftarPesan.get(position).getHarga_kostum());
                intent2.putExtra("bukti_sewa",daftarPesan.get(position).getBukti_sewa());
                v.getContext().startActivity(intent2);
            }
        });


    }

    @Override
    public int getItemCount() {
        return daftarPesan.size();
    }

    public class PesViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdDetail, status, tglSewa, tglKembali, tglTrannsaksi,nama, textTerlambat, textUpload, textUpload1, textUploadProses, textTidakValid;
        ImageView gambarBukti, statusTime;
        LinearLayout layout_late, layout_upload, layout_upload1, layout_uploadProses, layout_uploadTidakValid;
        public PesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDetail =(TextView) itemView.findViewById(R.id.tvIdDetail);
            nama =(TextView) itemView.findViewById(R.id.namaUser);
            status =(TextView) itemView.findViewById(R.id.statusSewa);
            tglTrannsaksi = (TextView) itemView.findViewById(R.id.tvTglTransaksi);
            tglSewa =(TextView) itemView.findViewById(R.id.tglsewa);
            tglKembali= (TextView) itemView.findViewById(R.id.tglKembali);
            gambarBukti = (ImageView) itemView.findViewById(R.id.gbrBukti);
            statusTime = (ImageView) itemView.findViewById(R.id.statusTime);
            textTerlambat = (TextView) itemView.findViewById(R.id.textTerlambat);
            textUpload = (TextView) itemView.findViewById(R.id.textUpload);
            textUpload1 = (TextView) itemView.findViewById(R.id.textUpload1);
            layout_late = (LinearLayout) itemView.findViewById(R.id.layout_late);
            layout_upload = (LinearLayout) itemView.findViewById(R.id.layout_upload);
            layout_upload1 = (LinearLayout) itemView.findViewById(R.id.layout_upload1);
            textUploadProses = (TextView) itemView.findViewById(R.id.textUploadProses);
            layout_uploadProses = (LinearLayout) itemView.findViewById(R.id.layout_uploadProses);
            textTidakValid= (TextView) itemView.findViewById(R.id.textTidakValid);
            layout_uploadTidakValid = (LinearLayout) itemView.findViewById(R.id.layout_tidakValid);
        }
    }
    public void setFilter(ArrayList<Pemesanan> filter){
        daftarPesan = new ArrayList<>();
        daftarPesan.addAll(filter);
        notifyDataSetChanged();
    }
}
