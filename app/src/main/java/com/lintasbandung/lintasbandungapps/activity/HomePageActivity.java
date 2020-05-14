package com.lintasbandung.lintasbandungapps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.activity.angkot.PesanAngkotActivity;
import com.lintasbandung.lintasbandungapps.activity.damri.ListDamriActivity;
import com.lintasbandung.lintasbandungapps.adapter.HistoryAdapter;
import com.lintasbandung.lintasbandungapps.awalan.LoginActivity;
import com.lintasbandung.lintasbandungapps.dashboard.HistoryActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;
import com.lintasbandung.lintasbandungapps.models.history.HistorySaatIni;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

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

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

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
        if (!appState.isLoggedIn()) {
            finish();
        }

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
        Call<HistorySaatIni> historySaatIniCall = apiService.getHistorySaatIni(id, date);
        historySaatIniCall.enqueue(new Callback<HistorySaatIni>() {
            @Override
            public void onResponse(Call<HistorySaatIni> call, Response<HistorySaatIni> response) {
                if (response.isSuccessful()) {
                    refreshLayout.setRefreshing(false);
                    ArrayList<GetHistoryTicket> arrayList = response.body().getData();
                    mAdapter = new HistoryAdapter(HomePageActivity.this, arrayList);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        ifNull.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        ifNull.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    showInfoToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<HistorySaatIni> call, Throwable t) {
                showErrorToast(t.getMessage());
            }
        });
    }

    private void showErrorToast(String message) {
        SweetToast.error(HomePageActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(HomePageActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(HomePageActivity.this, message, 2200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_profile:
                startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
                break;
            case R.id.toolbar_feedback:
                startActivity(new Intent(HomePageActivity.this, FeedbackActivity.class));
                break;
            case R.id.toolbar_logout:
                appState.logout();
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        appState = AppState.getInstance();
        if (!appState.isLoggedIn()) {
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }
    }
}
