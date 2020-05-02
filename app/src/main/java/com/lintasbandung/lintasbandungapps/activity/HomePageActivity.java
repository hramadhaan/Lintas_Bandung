package com.lintasbandung.lintasbandungapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.activity.angkot.PesanAngkotActivity;
import com.lintasbandung.lintasbandungapps.activity.damri.ListDamriActivity;
import com.lintasbandung.lintasbandungapps.adapter.HistoryAdapter;
import com.lintasbandung.lintasbandungapps.adapter.ListAngkotAdapter;
import com.lintasbandung.lintasbandungapps.dashboard.HistoryActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.history.HistorySaatIni;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextView judul;
    private TextView nama, lihatSemua;
    private RelativeLayout pesanAngkot, pesanDamri;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppState appState;
    private ApiService apiService;
    private LinearLayout ifNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.home_toolbar);
        judul = toolbar.findViewById(R.id.home_judul);
        setSupportActionBar(toolbar);

        apiService = ApiUtils.getApiSerives();

        nama = findViewById(R.id.home_nama);
        ifNull = findViewById(R.id.home_ifNull);
        lihatSemua = findViewById(R.id.home_lihatSemuaPerjalanan);
        pesanAngkot = findViewById(R.id.home_pesanAngkot);
        pesanDamri = findViewById(R.id.home_pesanTiketDamri);
        refreshLayout = findViewById(R.id.home_refresh);
        refreshLayout.setRefreshing(true);

        recyclerView = findViewById(R.id.home_recyclerView);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        appState = AppState.getInstance();

        nama.setText(appState.getUser().getFirstName() + " " + appState.getUser().getLastName());
        pesanAngkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesanAngkot = new Intent(HomePageActivity.this, PesanAngkotActivity.class);
                startActivity(pesanAngkot);
            }
        });
        pesanDamri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesanDamri = new Intent(HomePageActivity.this, ListDamriActivity.class);
                startActivity(pesanDamri);
            }
        });
        lihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, HistoryActivity.class));
            }
        });

        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String id = appState.getUser().getId();

        getPerjalananHariIni(id, modifiedDate);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPerjalananHariIni(id, modifiedDate);
            }
        });

    }

    private void getPerjalananHariIni(String id, String date) {
        Call<ArrayList<HistorySaatIni>> historySaatIniCall = apiService.getHistorySaatIni(id, date);
        historySaatIniCall.enqueue(new Callback<ArrayList<HistorySaatIni>>() {
            @Override
            public void onResponse(Call<ArrayList<HistorySaatIni>> call, Response<ArrayList<HistorySaatIni>> response) {
                if (response.isSuccessful()) {
                    refreshLayout.setRefreshing(false);
                    ifNull.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    ArrayList<HistorySaatIni> historySaatInis = response.body();
                    mAdapter = new HistoryAdapter(HomePageActivity.this, historySaatInis);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HistorySaatIni>> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(HomePageActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
