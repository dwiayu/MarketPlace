package com.example.sikostum.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikostum.LayarDetailSewa;
import com.example.sikostum.LayarInsertDenda;
import com.example.sikostum.MODEL.Pemesanan;
import com.example.sikostum.R;
import com.example.sikostum.REST.APIInterface;

import java.util.List;

public class RiwayatAdapter  extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {
    APIInterface mApiInterface;
    List<Pemesanan> daftarRiwayat;

    public RiwayatAdapter(List<Pemesanan> daftarRiwayat) {
        this.daftarRiwayat= daftarRiwayat;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat,parent,false);
        RiwayatViewHolder mHolder = new RiwayatViewHolder(view);
        return  mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.RiwayatViewHolder holder, final int position) {
        holder.tvIdLog.setText(daftarRiwayat.get(position).getId_log());
        holder.tvIdDetail.setText(daftarRiwayat.get(position).getId_detail());
        holder.tvIdTempat.setText(daftarRiwayat.get(position).getId_tempat());
        holder.tvNamaUser.setText(daftarRiwayat.get(position).getNama_user());
        holder.tvAlamat.setText(daftarRiwayat.get(position).getAlamat());
        holder.tglTrans.setText(daftarRiwayat.get(position).getTgl_transaksi());
        holder.tvNamaKostum.setText(daftarRiwayat.get(position).getNama_kostum());
        holder.jumlah.setText(daftarRiwayat.get(position).getJumlah());
        holder.jumlahDenda.setText(daftarRiwayat.get(position).getJumlah_denda());
        holder.hargaKostum.setText(daftarRiwayat.get(position).getHarga_kostum());
        holder.status_log.setText(daftarRiwayat.get(position).getStatus_log());
        holder.insertDenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), LayarInsertDenda.class);
                mIntent.putExtra("id_detail",daftarRiwayat.get(position).getId_detail());
                view.getContext().startActivity(mIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent= new Intent(v.getContext(), LayarDetailSewa.class);
                mIntent.putExtra("id_detail",daftarRiwayat.get(position).getId_detail());
                mIntent.putExtra("id_tempat",daftarRiwayat.get(position).getId_tempat());
                mIntent.putExtra("nama_user",daftarRiwayat.get(position).getNama_user());
                mIntent.putExtra("jumlah_denda",daftarRiwayat.get(position).getJumlah_denda());
                mIntent.putExtra("alamat",daftarRiwayat.get(position).getAlamat());
                mIntent.putExtra("tgl_transaksi", daftarRiwayat.get(position).getTgl_transaksi());
                mIntent.putExtra("nama_kostum", daftarRiwayat.get(position).getNama_kostum());
                mIntent.putExtra("jumlah", daftarRiwayat.get(position).getJumlah());
                mIntent.putExtra("harga_kostum", daftarRiwayat.get(position).getHarga_kostum());
                mIntent.putExtra("status_log", daftarRiwayat.get(position).getStatus_log());
                v.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarRiwayat.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTempat,tvIdDetail,tvNamaUser, tvAlamat,jumlahDenda, tglTrans,tvIdLog,tvNamaKostum,jumlah,hargaKostum,status_log;
        Button insertDenda;
        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdLog =(TextView) itemView.findViewById(R.id.idLog);
            tvIdDetail=(TextView) itemView.findViewById(R.id.tvIdDetail);
            tvIdTempat=(TextView) itemView.findViewById(R.id.tvIdTempat);
            tvNamaUser = (TextView) itemView.findViewById(R.id.tNamaUser);
            tvAlamat =(TextView) itemView.findViewById(R.id.tvAlamatUser);
            tglTrans =(TextView) itemView.findViewById(R.id.tvTglTrans);
            tvNamaKostum=(TextView) itemView.findViewById(R.id.tvNamaKostum);
            jumlah =(TextView) itemView.findViewById(R.id.tvJmlh);
            jumlahDenda =(TextView) itemView.findViewById(R.id.jmlhDenda);
            hargaKostum=(TextView) itemView.findViewById(R.id.tvHg);
            status_log =(TextView)itemView.findViewById(R.id.tvStatus_log);
            insertDenda = (Button) itemView.findViewById(R.id.btInputDenda);
        }
    }
}
