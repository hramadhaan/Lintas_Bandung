package com.lintasbandung.lintasbandungapps.activity.angkot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.angkot.AngkotScan;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanAngkotActivity extends AppCompatActivity {

    private String id;
    private NiceSpinner keberangkatan, tujuan;
    private LinearLayout linearLayout;
    private Button checkOut;
    private TextView trayek;
    private ApiService apiService;
    private ArrayList<String> listTrayek;
    private SwipeRefreshLayout refreshLayout;
    private String hargaPerKm, kodeAngkot, setKeberangkatan, setTujuan;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_angkot);

        toolbar = findViewById(R.id.formAngkot_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        keberangkatan = findViewById(R.id.formAngkot_keberangkatan);
        tujuan = findViewById(R.id.formAngkot_tujuan);
        checkOut = findViewById(R.id.formAngkot_pembayaran);
        trayek = findViewById(R.id.formAngkot_trayek);
        apiService = ApiUtils.getApiSerives();
        linearLayout = findViewById(R.id.formAngkot_show);
        refreshLayout = findViewById(R.id.formAngkot_refresh);
        refreshLayout.setRefreshing(true);
        linearLayout.setVisibility(View.GONE);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("id");

        getData(id);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(id);
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keberangkatan.getSelectedItem().toString().equals(tujuan.getSelectedItem().toString())) {
                    showToast("Keberangkatan dan Tujuan tidak boleh sama");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("keberangkatan", keberangkatan.getSelectedItem().toString());
                    intent.putExtra("tujuan", tujuan.getSelectedItem().toString());
                    intent.putExtra("trayek", trayek.getText().toString());

                }
            }
        });

    }

    private void getData(String id) {
        Call<AngkotScan> angkotScanCall = apiService.getDataAngkot(id);
        angkotScanCall.enqueue(new Callback<AngkotScan>() {
            @Override
            public void onResponse(Call<AngkotScan> call, Response<AngkotScan> response) {
                if (response.isSuccessful()) {
                    linearLayout.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                    trayek.setText(response.body().getName());
                    listTrayek = response.body().getTrayek();
                    keberangkatan.attachDataSource(listTrayek);
                    tujuan.attachDataSource(listTrayek);
                    hargaPerKm = response.body().getTarif();
                } else {
                    showToast(response.message());
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<AngkotScan> call, Throwable t) {
                showToast(t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ScanAngkotActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
