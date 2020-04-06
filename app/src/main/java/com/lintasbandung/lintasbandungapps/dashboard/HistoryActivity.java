package com.lintasbandung.lintasbandungapps.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        idUser = AppState.getInstance().getUser().getId();

        recyclerView = findViewById(R.id.history_recyclerView);
        apiService = ApiUtils.getApiSerives();
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        getData();
    }

    private void getData() {
        Call<List<GetHistoryTicket>> listCall = apiService.getHistoryUser(idUser);
        listCall.enqueue(new Callback<List<GetHistoryTicket>>() {
            @Override
            public void onResponse(Call<List<GetHistoryTicket>> call, Response<List<GetHistoryTicket>> response) {
                if (response.isSuccessful()) {
                    getHistoryTickets = response.body();
                    mAdapter = new HistoryUserAdapter(getApplicationContext(), getHistoryTickets);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        Toast.makeText(getApplicationContext(), "Tidak ada daftar angkot", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HistoryActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetHistoryTicket>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
