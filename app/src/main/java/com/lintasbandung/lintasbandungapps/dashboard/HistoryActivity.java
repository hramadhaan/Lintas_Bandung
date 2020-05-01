package com.lintasbandung.lintasbandungapps.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.HistoryUserAdapter;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiService apiService;
    private String idUser;
    private List<GetHistoryTicket> getHistoryTickets;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ifNull;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idUser = AppState.getInstance().getUser().getId();

        recyclerView = findViewById(R.id.history_recyclerView);
        apiService = ApiUtils.getApiSerives();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = findViewById(R.id.history_refresh);
        getData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        ifNull = findViewById(R.id.history_ifNull);
    }


    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        Call<List<GetHistoryTicket>> listCall = apiService.getHistoryUser(idUser);
        listCall.enqueue(new Callback<List<GetHistoryTicket>>() {
            @Override
            public void onResponse(Call<List<GetHistoryTicket>> call, Response<List<GetHistoryTicket>> response) {
                if (response.isSuccessful()) {
                    getHistoryTickets = response.body();
                    ifNull.setVisibility(View.GONE);
                    mAdapter = new HistoryUserAdapter(getApplicationContext(), getHistoryTickets);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (mAdapter.getItemCount() == 0) {
                        swipeRefreshLayout.setRefreshing(false);
                        ifNull.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Tidak ada daftar angkot", Toast.LENGTH_LONG).show();
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(HistoryActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetHistoryTicket>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
