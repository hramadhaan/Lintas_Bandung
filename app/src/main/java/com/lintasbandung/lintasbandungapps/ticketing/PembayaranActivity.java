package com.lintasbandung.lintasbandungapps.ticketing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lintasbandung.lintasbandungapps.BuildConfig;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class PembayaranActivity extends AppCompatActivity implements TransactionFinishedCallback {

    private String sKeberangkatan, sTujuan, sNamaPemesan, sJumlahPemesan, sHarga, sWaktu, sIdRute, sTrayek;
    private TextView keberangkatan, tujuan, namaPemesan, jumlahPemesan, waktu, jumlahHarga, namaTrayek;
    private int totalHarga;
    private Button indomaret, mandiri, gojek;
    private int a, b;
    private static final int duration = 2500;
    private ApiService apiService;
    private int idUser;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        apiService = ApiUtils.getApiSerives();

        toolbar = findViewById(R.id.checkOut_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initMidTrans();
//        BUTTON
        indomaret = findViewById(R.id.checkOut_indomaret);
        mandiri = findViewById(R.id.checkOut_mandiri);
        gojek = findViewById(R.id.checkOut_gojek);

        keberangkatan = findViewById(R.id.checkOut_keberangkatan);
        tujuan = findViewById(R.id.checkOut_tujuan);
        namaPemesan = findViewById(R.id.checkOut_namaPemesan);
        jumlahPemesan = findViewById(R.id.checkOut_jumlahPesanan);
        waktu = findViewById(R.id.checkOut_waktu);
        jumlahHarga = findViewById(R.id.checkOut_jumlahHarga);
        namaTrayek = findViewById(R.id.checkOut_namaTrayek);

        idUser = Integer.parseInt(AppState.getInstance().getUser().getId());

        Log.d("USER", String.valueOf(idUser));

        Intent getIntent = getIntent();
        sKeberangkatan = getIntent.getStringExtra("keberangkatan");
        sTujuan = getIntent.getStringExtra("tujuan");
        sNamaPemesan = getIntent.getStringExtra("namapemesan");
        sJumlahPemesan = getIntent.getStringExtra("jumlah");
        sHarga = getIntent.getStringExtra("harga");
        sWaktu = getIntent.getStringExtra("waktu");
        sIdRute = getIntent.getStringExtra("rute");
        sTrayek = getIntent.getStringExtra("namaTrayek");

        a = Integer.parseInt(sJumlahPemesan);
        b = Integer.parseInt(sHarga);

        totalHarga = a * b;

        keberangkatan.setText(sKeberangkatan);
        tujuan.setText(sTujuan);
        namaPemesan.setText(sNamaPemesan);
        jumlahPemesan.setText(sJumlahPemesan + " Tiket");
        waktu.setText(sWaktu);
        jumlahHarga.setText("Rp. " + NumberFormat.getNumberInstance(Locale.US).format(totalHarga));
        namaTrayek.setText(sTrayek);

//        PEMBAYARAN
        indomaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithIndomaret();
            }
        });
        mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithMandiri();
            }
        });

        gojek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithGopay();
            }
        });
    }

    private void buyItWithMandiri() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", b, a, "Ticket"));
        MidtransSDK.getInstance().startPaymentUiFlow(PembayaranActivity.this, PaymentMethod.BANK_TRANSFER_MANDIRI);
    }

    private void buyItWithGopay() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", b, a, "Ticket"));
        MidtransSDK.getInstance().startPaymentUiFlow(PembayaranActivity.this, PaymentMethod.GO_PAY);
    }

    private void initMidTrans() {
        SdkUIFlowBuilder.init()
                .setContext(this)
                .setMerchantBaseUrl(BuildConfig.MERCHANT_BASE_URL)
                .setClientKey(BuildConfig.MERCHANT_CLIENT_KEY)
                .setTransactionFinishedCallback(this)
                .setColorTheme(new CustomColorTheme("#ffe51255", "#B61548", "#FFE51255"))
                .buildSDK();
    }

    private void buyItWithIndomaret() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", b, a, "Ticket"));
        MidtransSDK.getInstance().startPaymentUiFlow(PembayaranActivity.this, PaymentMethod.INDOMARET);
    }


    @Override
    public void onTransactionFinished(final TransactionResult transactionResult) {
        if (transactionResult.getResponse() != null) {
            String hasil = transactionResult.getResponse().getPaymentType();

            String kodePembayaran;

            if (hasil.equals("gopay")) {
                kodePembayaran = "GOPAY";
            } else if (hasil.equals("cstore")) {
                kodePembayaran = "INDOMART";
            } else {
                kodePembayaran = "BANK MANDIRI";
            }
            switch (transactionResult.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
//                    idUser = Integer.parseInt(transactionResult.getResponse().getOrderId());
                    Call<Status> statusSuccess = apiService.createOrder(sIdRute, a, totalHarga, transactionResult.getResponse().getOrderId(), idUser,
                            sKeberangkatan, sTujuan, "READY", "", "",
                            kodePembayaran, sWaktu);
                    statusSuccess.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("success")) {
                                    SweetToast.success(PembayaranActivity.this, "Transaction Finished ID : " + transactionResult.getResponse().getOrderId(), duration);
                                    finish();
                                } else {
                                    SweetToast.error(PembayaranActivity.this, "Update tidak sukses, coba ulangi", duration);
                                }
                            } else {
                                SweetToast.error(PembayaranActivity.this, response.message(), duration);
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            SweetToast.error(PembayaranActivity.this, t.getMessage(), duration);
                        }
                    });

                    break;
                case TransactionResult.STATUS_PENDING:
//                    idUser = Integer.parseInt(transactionResult.getResponse().getOrderId());
                    Call<Status> statusPending = apiService.createOrder(sIdRute, a, totalHarga, transactionResult.getResponse().getOrderId(), idUser,
                            sKeberangkatan, sTujuan, "READY", "", "",
                            kodePembayaran, sWaktu);
                    statusPending.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("success")) {
                                    SweetToast.info(PembayaranActivity.this, "Transaction PENDING ID : " + transactionResult.getResponse().getOrderId(), duration);
                                    finish();
                                } else {
                                    SweetToast.error(PembayaranActivity.this, "Update tidak sukses, coba ulangi", duration);
                                }
                            } else {
                                SweetToast.error(PembayaranActivity.this, response.message(), duration);
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            SweetToast.error(PembayaranActivity.this, t.getMessage(), duration);
                        }
                    });

                    break;
                case TransactionResult.STATUS_FAILED:
                    Call<Status> statusFailed = apiService.createOrder(sIdRute, a, totalHarga, transactionResult.getResponse().getOrderId(), idUser,
                            sKeberangkatan, sTujuan, "READY", "", "",
                            kodePembayaran, sWaktu);
                    statusFailed.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("success")) {
                                    SweetToast.success(PembayaranActivity.this, "Transaction FAILED ID : " + transactionResult.getResponse().getOrderId(), duration);
                                    finish();
                                } else {
                                    SweetToast.error(PembayaranActivity.this, "Update tidak sukses, coba ulangi", duration);
                                }
                            } else {
                                SweetToast.error(PembayaranActivity.this, response.message(), duration);
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            SweetToast.error(PembayaranActivity.this, t.getMessage(), duration);
                        }
                    });

                    break;
            }
            transactionResult.getResponse().getValidationMessages();
        } else if (transactionResult.isTransactionCanceled()) {
            SweetToast.warning(PembayaranActivity.this, "Transaction Canceled", duration);
        } else {
            if (transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                SweetToast.warning(PembayaranActivity.this, "Transaction Invalid", duration);
            } else {
                SweetToast.warning(PembayaranActivity.this, "Transaction Finished with Failure", duration);
            }
        }
    }
}
