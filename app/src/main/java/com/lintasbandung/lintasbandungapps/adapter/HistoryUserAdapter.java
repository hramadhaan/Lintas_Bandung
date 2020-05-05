package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.dashboard.CetakActivity;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;

import java.util.List;

public class HistoryUserAdapter extends RecyclerView.Adapter<HistoryUserAdapter.ViewHolder> {

    private Context context;
    private List<GetHistoryTicket> getHistoryTicketList;

    public HistoryUserAdapter(Context context, List<GetHistoryTicket> getHistoryTicketList) {
        this.context = context;
        this.getHistoryTicketList = getHistoryTicketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false);
        ViewHolder listHistory = new ViewHolder(view);
        return listHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.rute.setText(getHistoryTicketList.get(position).getKeberangkatan() + " - " + getHistoryTicketList.get(position).getTujuan());
        holder.type.setText(getHistoryTicketList.get(position).getType());
        holder.tanggal.setText(getHistoryTicketList.get(position).getTanggal_pemesanan());
        holder.tiket.setText(getHistoryTicketList.get(position).getJumlahTiket() + " Tiket");

        holder.listHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CetakActivity.class);
                intent.putExtra("id_order", getHistoryTicketList.get(position).getOrderId());
                intent.putExtra("id", getHistoryTicketList.get(position).getId());
                intent.putExtra("tipe", getHistoryTicketList.get(position).getPayment_type());
                intent.putExtra("keb", getHistoryTicketList.get(position).getKeberangkatan());
                intent.putExtra("tuj", getHistoryTicketList.get(position).getTujuan());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return getHistoryTicketList.size();
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
