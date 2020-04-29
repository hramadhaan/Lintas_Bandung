package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.damri.FormOrderingTicket;
import com.lintasbandung.lintasbandungapps.models.AllDamri;

import java.util.ArrayList;

public class ListDamriAdapter extends RecyclerView.Adapter<ListDamriAdapter.DamriHolder> {

    private Context context;
    private ArrayList<AllDamri> allDamriArrayList;

    public ListDamriAdapter(Context context, ArrayList<AllDamri> allDamriArrayList) {
        this.context = context;
        this.allDamriArrayList = allDamriArrayList;
    }

    @NonNull
    @Override
    public DamriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_damri, parent, false);
        DamriHolder listDamri = new DamriHolder(view);
        return listDamri;
    }

    @Override
    public void onBindViewHolder(@NonNull DamriHolder holder, final int position) {
        holder.listDamriTrayek.setText(allDamriArrayList.get(position).getNamaTrayek());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormOrderingTicket.class);
                intent.putExtra("id", allDamriArrayList.get(position).getId());
                intent.putExtra("rute", allDamriArrayList.get(position).getNamaTrayek());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allDamriArrayList.size();
    }

    public class DamriHolder extends RecyclerView.ViewHolder {
        private TextView listDamriTrayek;
        private LinearLayout linearLayout;

        public DamriHolder(@NonNull View itemView) {
            super(itemView);
            listDamriTrayek = itemView.findViewById(R.id.listDamriTrayek);
            linearLayout = itemView.findViewById(R.id.listDamri);
        }
    }
}
