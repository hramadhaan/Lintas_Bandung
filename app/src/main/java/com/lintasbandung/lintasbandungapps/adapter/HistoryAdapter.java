package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;
import com.lintasbandung.lintasbandungapps.models.history.HistorySaatIni;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetHistoryTicket> historySaatIniArrayList;

    public HistoryAdapter(Context context, ArrayList<GetHistoryTicket> historySaatIniArrayList) {
        this.context = context;
        this.historySaatIniArrayList = historySaatIniArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false);
        ViewHolder listHistory = new ViewHolder(view);
        return listHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rute.setText(historySaatIniArrayList.get(position).getKeberangkatan() + " - " + historySaatIniArrayList.get(position).getTujuan());
        holder.type.setText(historySaatIniArrayList.get(position).getType());
        holder.tanggal.setText(historySaatIniArrayList.get(position).getTanggal_pemesanan());
        holder.tiket.setText(historySaatIniArrayList.get(position).getJumlahTiket() + " Tiket");
    }

    @Override
    public int getItemCount() {
        return historySaatIniArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView listHistory;
        private TextView type, rute, tanggal, tiket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listHistory = itemView.findViewById(R.id.listHistory);
            type = itemView.findViewById(R.id.listHistory_type);
            rute = itemView.findViewById(R.id.listHistory_rute);
            tanggal = itemView.findViewById(R.id.listHistory_tanggal);
            tiket = itemView.findViewById(R.id.listHistory_tiket);
        }
    }
}
