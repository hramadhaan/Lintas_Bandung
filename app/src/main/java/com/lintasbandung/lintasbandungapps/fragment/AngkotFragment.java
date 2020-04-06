package com.lintasbandung.lintasbandungapps.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListAngkotAdapter;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AngkotFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiService apiService;
    private SearchView search;
    private ArrayList<AllAngkot> allAngkots;
    private ProgressBar loading;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewAngkot = inflater.inflate(R.layout.fragment_angkot, container, false);

        refreshLayout = viewAngkot.findViewById(R.id.fragmentAngkot_refresh);
        apiService = ApiUtils.getApiSerives();
        search = viewAngkot.findViewById(R.id.fragmentAngkot_search);
        loading = viewAngkot.findViewById(R.id.fragmentAngkot_loading);
        loading.setVisibility(View.VISIBLE);
        search.setVisibility(View.INVISIBLE);
        recyclerView = viewAngkot.findViewById(R.id.fragmentAngkot_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        getAngkot();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                getAngkot();
                loading.setVisibility(View.VISIBLE);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return viewAngkot;
    }

    private void filter(String text) {
        ArrayList<AllAngkot> filteredList = new ArrayList<>();
        for (AllAngkot item : allAngkots) {
            if (item.getTo().getPlaceName().toLowerCase().contains(text.toLowerCase()) || item.getFrom().getPlaceName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter = new ListAngkotAdapter(getContext(), filteredList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void getAngkot() {

        Call<ArrayList<AllAngkot>> getAllAngkot = apiService.getAllAngkot();
        getAllAngkot.enqueue(new Callback<ArrayList<AllAngkot>>() {
            @Override
            public void onResponse(Call<ArrayList<AllAngkot>> call, Response<ArrayList<AllAngkot>> response) {
                if (response.isSuccessful()) {
                    loading.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    allAngkots = response.body();
                    mAdapter = new ListAngkotAdapter(getContext(), allAngkots);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        Toast.makeText(getContext(), "Tidak ada daftar angkot", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllAngkot>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
