package com.lintasbandung.lintasbandungapps.activity.damri;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
import xyz.hasnat.sweettoast.SweetToast;

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

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

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
                        showInfoToast("Data tidak ada");
                        refreshLayout.setRefreshing(false);
                    }
                } else {
                    showInfoToast(response.message());
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllDamri>> call, Throwable t) {
                showErrorToast(t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void showErrorToast(String message) {
        SweetToast.error(ListDamriActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(ListDamriActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(ListDamriActivity.this, message, 2200);
    }
}
