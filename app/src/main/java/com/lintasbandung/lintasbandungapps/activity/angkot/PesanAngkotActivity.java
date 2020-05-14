package com.lintasbandung.lintasbandungapps.activity.angkot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListAngkotAdapter;
import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

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
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_angkot);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

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
//                showToast("Toast");
                IntentIntegrator intentIntegrator = new IntentIntegrator(PesanAngkotActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setPrompt("Letakkan secara horizontal dan pastikan barcode sejajar dengan garis merah");
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                showInfoToast("Scan dibatalkan");
            } else {
                id = intentResult.getContents();
                sendToForm(id);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendToForm(String id) {
        Intent intent = new Intent(PesanAngkotActivity.this, ScanAngkotActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
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
                        showInfoToast("Data Angkot Tidak Ada");
                    }
                } else {
                    showInfoToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllAngkot>> call, Throwable t) {
                showErrorToast(t.getMessage());
            }
        });
    }

    private void showErrorToast(String message) {
        SweetToast.error(PesanAngkotActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(PesanAngkotActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(PesanAngkotActivity.this, message, 2200);
    }
}
