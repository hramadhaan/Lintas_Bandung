package com.lintasbandung.lintasbandungapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.activity.angkot.PesanAngkotActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.network.ApiService;

public class HomePageActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextView judul;
    private TextView nama, lihatSemua;
    private RelativeLayout pesanAngkot, pesanDamri;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AppState appState;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.home_toolbar);
        judul = toolbar.findViewById(R.id.home_judul);
        setSupportActionBar(toolbar);

        nama = findViewById(R.id.home_nama);
        lihatSemua = findViewById(R.id.home_lihatSemuaPerjalanan);
        pesanAngkot = findViewById(R.id.home_pesanAngkot);
        pesanDamri = findViewById(R.id.home_pesanTiketDamri);
        refreshLayout = findViewById(R.id.home_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        recyclerView = findViewById(R.id.home_recyclerView);

        appState = AppState.getInstance();

        nama.setText(appState.getUser().getFirstName() + " " + appState.getUser().getLastName());
        pesanAngkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesanAngkot = new Intent(HomePageActivity.this, PesanAngkotActivity.class);
                startActivity(pesanAngkot);
            }
        });
        lihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Lihat Semua");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(HomePageActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
