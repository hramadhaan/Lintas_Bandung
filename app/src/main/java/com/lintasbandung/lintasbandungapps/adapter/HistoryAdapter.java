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
import com.lintasbandung.lintasbandungapps.models.history.HistorySaatIni;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HistorySaatIni> historySaatIniArrayList;

    public HistoryAdapter(Context context, ArrayList<HistorySaatIni> historySaatIniArrayList) {
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
        holder.rute.setText(historySaatIniArrayList.get(position).getRute().getNamaTrayek());
        holder.status.setText(historySaatIniArrayList.get(position).getTanggalPemesanan());
    }

    @Override
    public int getItemCount() {
        return historySaatIniArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView listHistory;
        private TextView rute, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listHistory = itemView.findViewById(R.id.listHistory);
            rute = itemView.findViewById(R.id.listHistory_rute);
            status = itemView.findViewById(R.id.listHistory_status);
        }
    }
}
