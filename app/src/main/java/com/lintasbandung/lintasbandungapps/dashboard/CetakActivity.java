package com.lintasbandung.lintasbandungapps.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.midtrans.Gojek;
import com.lintasbandung.lintasbandungapps.models.midtrans.Indomart;
import com.lintasbandung.lintasbandungapps.models.midtrans.Mandiri;
import com.lintasbandung.lintasbandungapps.models.ticket.CetakTicketDB;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.network.ApiServiceMidtrans;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;
import com.midtrans.sdk.corekit.models.snap.Gopay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakActivity extends AppCompatActivity {

    TextView keberangkatan, jumlahPesanan, tglKeberangkatan, status, kodePembayaran, tipePembayaran;
    private ApiService apiService;
    private ApiServiceMidtrans apiServiceMidtrans;
    private String id_order, id, tipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak);

        apiService = ApiUtils.getApiSerives();
        apiServiceMidtrans = ApiUtils.getDatabase();

        keberangkatan = findViewById(R.id.cetak_keberangkatan);
        jumlahPesanan = findViewById(R.id.cetak_jumlahPesanan);
        tglKeberangkatan = findViewById(R.id.cetak_tglKeberangkatan);
        status = findViewById(R.id.cetak_status);
        kodePembayaran = findViewById(R.id.cetak_kodePembayaran);
        tipePembayaran = findViewById(R.id.cetak_tipePembayaran);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("id");
        id_order = getIntent.getStringExtra("id_order");
        tipe = getIntent.getStringExtra("tipe");

        getDataDatabase();

        if (tipe.equals("BANK MANDIRI")) {
            getDataBankMandiri(id_order);
        }
        if (tipe.equals("INDOMART")) {
            getDataIndomart(id_order);
        }
        if (tipe.equals("GOPAY")) {
            getDataGopay(id_order);
            kodePembayaran.setVisibility(View.GONE);
        }

        Toast.makeText(CetakActivity.this, tipe, Toast.LENGTH_LONG).show();

    }

    private void getDataGopay(String id_order) {
        Call<Gojek> getGopay = apiServiceMidtrans.getGojek(id_order);
        getGopay.enqueue(new Callback<Gojek>() {
            @Override
            public void onResponse(Call<Gojek> call, Response<Gojek> response) {
                if (response.isSuccessful()) {
                    status.setText(response.body().getTransactionStatus());
                } else {
                    Toast.makeText(CetakActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Gojek> call, Throwable t) {
                Toast.makeText(CetakActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDataIndomart(String id_order) {
        Call<Indomart> getIndomart = apiServiceMidtrans.getIndomart(id_order);
        getIndomart.enqueue(new Callback<Indomart>() {
            @Override
            public void onResponse(Call<Indomart> call, Response<Indomart> response) {
                if (response.isSuccessful()) {
                    status.setText(response.body().getTransactionStatus());
                    kodePembayaran.setText(response.body().getPaymentCode());
                }
            }

            @Override
            public void onFailure(Call<Indomart> call, Throwable t) {

            }
        });
    }

    private void getDataBankMandiri(String id_order) {
        Call<Mandiri> getMandiri = apiServiceMidtrans.getMandiri(id_order);
        getMandiri.enqueue(new Callback<Mandiri>() {
            @Override
            public void onResponse(Call<Mandiri> call, Response<Mandiri> response) {
                if (response.isSuccessful()) {
                    status.setText(response.body().getTransactionStatus());
                    kodePembayaran.setText(response.body().getBillerCode() + " + " + response.body().getBillKey());
                }
            }

            @Override
            public void onFailure(Call<Mandiri> call, Throwable t) {

            }
        });
    }

    private void getDataDatabase() {
        Call<CetakTicketDB> cetakTicketDBCall = apiService.getCetakDB(id);
        cetakTicketDBCall.enqueue(new Callback<CetakTicketDB>() {
            @Override
            public void onResponse(Call<CetakTicketDB> call, Response<CetakTicketDB> response) {
                if (response.isSuccessful()) {
                    keberangkatan.setText(response.body().getKeberangkatan() + " => " + response.body().getTujuan());
                    jumlahPesanan.setText(response.body().getJumlahTiket());
                    tglKeberangkatan.setText(response.body().getTanggalPemesanan());
                    tipePembayaran.setText(response.body().getPaymentType());
                }
            }

            @Override
            public void onFailure(Call<CetakTicketDB> call, Throwable t) {

            }
        });
    }
}
