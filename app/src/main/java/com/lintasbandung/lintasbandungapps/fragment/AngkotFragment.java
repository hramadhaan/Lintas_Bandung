package com.lintasbandung.lintasbandungapps.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.ListAngkotAdapter;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.network.ApiServiceDatabase;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;
import com.vipulasri.ticketview.TicketView;

import java.util.ArrayList;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AngkotFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private ApiServiceDatabase apiServiceDatabase;
    private AppState appState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewAngkot = inflater.inflate(R.layout.fragment_angkot, container, false);

        apiServiceDatabase = ApiUtils.getDatabase();
        appState = AppState.getInstance();
        recyclerView = viewAngkot.findViewById(R.id.fragmentAngkot_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        getAngkot();

        return viewAngkot;
    }

    private void getAngkot() {
        Call<ArrayList<AllAngkot>> getAllAngkot = apiServiceDatabase.getAllAngkot();
        getAllAngkot.enqueue(new Callback<ArrayList<AllAngkot>>() {
            @Override
            public void onResponse(Call<ArrayList<AllAngkot>> call, Response<ArrayList<AllAngkot>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AllAngkot> allAngkots = response.body();
                    mAdapter = new ListAngkotAdapter(getContext(), allAngkots);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        Toast.makeText(getContext(), "Tidak ada daftar angkot", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllAngkot>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
