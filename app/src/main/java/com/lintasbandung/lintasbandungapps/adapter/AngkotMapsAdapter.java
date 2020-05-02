package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;

import java.util.ArrayList;

public class AngkotMapsAdapter extends RecyclerView.Adapter<AngkotMapsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> stringArrayList;

    public AngkotMapsAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_angkot_maps, parent, false);
        ViewHolder listAngkot = new ViewHolder(view);
        return listAngkot;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.namaTrayek.setText(stringArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaTrayek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTrayek = itemView.findViewById(R.id.listAngkotMaps_namaRute);
        }
    }
}
