package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.angkot.AngkotMapsActivity;
import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAngkotAdapter extends RecyclerView.Adapter<ListAngkotAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllAngkot> allAngkotArrayList;

    public ListAngkotAdapter(Context context, ArrayList<AllAngkot> allAngkotArrayList) {
        this.context = context;
        this.allAngkotArrayList = allAngkotArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_angkot, parent, false);
        ViewHolder listAngkot = new ViewHolder(view);
        return listAngkot;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.code.setText(allAngkotArrayList.get(position).getKode());
        holder.from.setText(allAngkotArrayList.get(position).getFrom().getPlaceName());
        holder.to.setText(allAngkotArrayList.get(position).getTo().getPlaceName());

        Picasso.get().load(allAngkotArrayList.get(position).getImg()).into(holder.image);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AngkotMapsActivity.class);
                intent.putExtra("origin", allAngkotArrayList.get(position).getFrom().getPlaceName());
                intent.putExtra("originLat", allAngkotArrayList.get(position).getFrom().getLatitude());
                intent.putExtra("originLong", allAngkotArrayList.get(position).getFrom().getLongitude());
                intent.putExtra("destination", allAngkotArrayList.get(position).getTo().getPlaceName());
                intent.putExtra("destinationLat", allAngkotArrayList.get(position).getTo().getLatitude());
                intent.putExtra("destinationLong", allAngkotArrayList.get(position).getTo().getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allAngkotArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView code, from, to;
        private ImageView image;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.listAngkotCode);
            from = itemView.findViewById(R.id.listAngkotFrom);
            to = itemView.findViewById(R.id.listAngkotTo);
            image = itemView.findViewById(R.id.listAngkotImage);
            linearLayout = itemView.findViewById(R.id.listAngkot);
        }
    }
}
