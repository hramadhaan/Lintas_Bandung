package com.lintasbandung.lintasbandungapps.activity.damri;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListDamriAdapter;
import com.lintasbandung.lintasbandungapps.models.AllDamri;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDamriActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiService apiService;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private TextView judul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_damri);

        toolbar = findViewById(R.id.damri_toolbar);
        judul = toolbar.findViewById(R.id.damri_judul);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiService = ApiUtils.getApiSerives();
        recyclerView = findViewById(R.id.damri_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        refreshLayout = findViewById(R.id.damri_refresh);
        refreshLayout.setRefreshing(true);
        getRouteDamri();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRouteDamri();
            }
        });

    }

    private void getRouteDamri() {
        Call<ArrayList<AllDamri>> listCall = apiService.getAllDamri();
        listCall.enqueue(new Callback<ArrayList<AllDamri>>() {
            @Override
            public void onResponse(Call<ArrayList<AllDamri>> call, Response<ArrayList<AllDamri>> response) {
                if (response.isSuccessful()) {
                    refreshLayout.setRefreshing(false);
                    ArrayList<AllDamri> allDamriArrayList = response.body();
                    mAdapter = new ListDamriAdapter(getApplicationContext(), allDamriArrayList);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        Toast.makeText(getApplicationContext(), "Tidak ada rute damri", Toast.LENGTH_LONG).show();
                        refreshLayout.setRefreshing(false);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllDamri>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
