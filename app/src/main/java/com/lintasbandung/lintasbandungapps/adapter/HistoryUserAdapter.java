package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;
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
        holder.rute.setText(getHistoryTicketList.get(position).getKeberangkatan() + " => " + getHistoryTicketList.get(position).getTujuan());
        holder.status.setText(getHistoryTicketList.get(position).getStatus());
        holder.listHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, getHistoryTicketList.get(position).getOrderId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getHistoryTicketList.size();
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
