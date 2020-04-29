package com.lintasbandung.lintasbandungapps.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListDamriAdapter;
import com.lintasbandung.lintasbandungapps.models.AllDamri;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiService apiService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewTicket = inflater.inflate(R.layout.fragment_ticket, container, false);

        apiService = ApiUtils.getApiSerives();
        recyclerView = viewTicket.findViewById(R.id.fragmentTicket_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        getRouteDamri();

        return viewTicket;
    }

    private void getRouteDamri() {
        Call<ArrayList<AllDamri>> listCall = apiService.getAllDamri();
        listCall.enqueue(new Callback<ArrayList<AllDamri>>() {
            @Override
            public void onResponse(Call<ArrayList<AllDamri>> call, Response<ArrayList<AllDamri>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AllDamri> allDamriArrayList = response.body();
                    mAdapter = new ListDamriAdapter(getContext(), allDamriArrayList);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        Toast.makeText(getContext(), "Tidak ada rute damri", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllDamri>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
