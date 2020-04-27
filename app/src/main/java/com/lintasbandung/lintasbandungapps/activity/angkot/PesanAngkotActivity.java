package com.lintasbandung.lintasbandungapps.activity.angkot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListAngkotAdapter;
import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanAngkotActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AllAngkot> allAngkots;
    private SwipeRefreshLayout refreshLayout;
    private ApiService apiService;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_angkot);

        apiService = ApiUtils.getApiSerives();
        toolbar = findViewById(R.id.pesanAngkot_toolbar);
        searchView = toolbar.findViewById(R.id.pesanAngkot_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.pesanAngkot_recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        refreshLayout = findViewById(R.id.pesanAngkot_refresh);
        refreshLayout.setRefreshing(true);
        getAngkot();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAngkot();
            }
        });

        searchView = findViewById(R.id.pesanAngkot_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        floatingActionButton = findViewById(R.id.pesanAngkot_floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Toast");
            }
        });

    }

    private void filter(String newText) {
        ArrayList<AllAngkot> filteredList = new ArrayList<>();
        for (AllAngkot item : allAngkots) {
            if (item.getTo().getPlaceName().toLowerCase().contains(newText.toLowerCase()) || item.getFrom().getPlaceName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter = new ListAngkotAdapter(getApplicationContext(), filteredList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void getAngkot() {
        Call<ArrayList<AllAngkot>> getAllAngkot = apiService.getAllAngkot();
        getAllAngkot.enqueue(new Callback<ArrayList<AllAngkot>>() {
            @Override
            public void onResponse(Call<ArrayList<AllAngkot>> call, Response<ArrayList<AllAngkot>> response) {
                if (response.isSuccessful()) {
                    refreshLayout.setRefreshing(false);
                    allAngkots = response.body();
                    mAdapter = new ListAngkotAdapter(PesanAngkotActivity.this, allAngkots);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        showToast("Data Angkot Tidak Ada");
                    }
                } else {
                    showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllAngkot>> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(PesanAngkotActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
