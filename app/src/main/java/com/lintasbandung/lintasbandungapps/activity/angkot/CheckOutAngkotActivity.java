package com.lintasbandung.lintasbandungapps.activity.angkot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
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

import org.joda.time.DateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class CheckOutAngkotActivity extends AppCompatActivity implements TransactionFinishedCallback {

    private ApiService apiService;
    private AppState appState;
    private TextView namaPemesan, halteKeberangkatan, halteTujuan, jarak, harga, estimasiWaktu;
    private long getJarak;
    private String getJarakKm, getEstimasi, id_rute, getKeberangkatan, getTujuan;
    private Button button;
    private static final int overview = 0;
    private static final int duration = 2500;
    int a;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_angkot);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        initMidTrans();

        toolbar = findViewById(R.id.angkotCheckOut_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();
        namaPemesan = findViewById(R.id.angkotCheckOut_namaPemesan);
        halteKeberangkatan = findViewById(R.id.angkotCheckOut_keberangkatan);
        halteTujuan = findViewById(R.id.angkotCheckOut_tujuan);
        jarak = findViewById(R.id.angkotCheckOut_jarak);
        harga = findViewById(R.id.angkotCheckOut_totalHarga);
        estimasiWaktu = findViewById(R.id.angkotCheckOut_estimasi);
        button = findViewById(R.id.angkotCheckOut_button);

        Intent getIntent = getIntent();
        getKeberangkatan = getIntent.getStringExtra("keberangkatan");
        getTujuan = getIntent.getStringExtra("tujuan");
        id_rute = getIntent.getStringExtra("id_rute");
        double hargaInt = Double.parseDouble(getIntent.getStringExtra("harga"));
        Log.d("Harga", "Harga dari Dishub: " + String.valueOf(hargaInt));


        new Handler().postDelayed(() -> {
            a = (int) (hargaInt * (getJarak / 1000));
            Log.d("Harga", "Total Harga: " + String.valueOf(a));
            harga.setText(String.valueOf(a));
        }, 900);

        DirectionsResult results = getDirectionsDetails(getKeberangkatan + ", Bandung", getTujuan + ", Bandung", TravelMode.TRANSIT);
        if (results != null) {
            getEndLocationTitle(results);
        }

        namaPemesan.setText(appState.getUser().getFirstName() + " " + appState.getUser().getLastName());
        halteKeberangkatan.setText(getKeberangkatan);
        halteTujuan.setText(getTujuan);
        jarak.setText(getJarakKm);
        estimasiWaktu.setText(getEstimasi);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithGopay(a, 1);
            }
        });
    }

    private void buyItWithGopay(int harga, int qty) {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("3", harga, qty, "Ticket Angkot"));
        MidtransSDK.getInstance().startPaymentUiFlow(CheckOutAngkotActivity.this, PaymentMethod.GO_PAY);
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

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        DateTime now = new DateTime();

        try {
            return DirectionsApi.newRequest(getGeoContext()).mode(mode).origin(origin).destination(destination).departureTime(now).await();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getEndLocationTitle(DirectionsResult results) {
        getJarakKm = results.routes[overview].legs[overview].distance.humanReadable;
        getEstimasi = results.routes[overview].legs[overview].duration.humanReadable;
        getJarak = results.routes[overview].legs[overview].distance.inMeters;
        Log.d("Harga", "Jarak : " + getJarak);
        Log.d("Harga", getJarakKm);
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private void showErrorToast(String message) {
        SweetToast.error(CheckOutAngkotActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(CheckOutAngkotActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(CheckOutAngkotActivity.this, message, 2200);
    }

    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {
        if (transactionResult.getResponse() != null) {
            String hasil = transactionResult.getResponse().getPaymentType();

            String kodePembayaran;
            Date date = new Date();
            String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            int id = Integer.parseInt(appState.getUser().getId());

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
                    Call<Status> statusSuccess = apiService.createOrder(id_rute, 1, a, transactionResult.getResponse().getOrderId(), id,
                            getKeberangkatan, getTujuan, "READY", "", "",
                            kodePembayaran, modifiedDate, "angkot");
                    statusSuccess.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("success")) {
                                    showSuccessToast("Terimakasih telah melakukan pembayaran");
                                    finish();
                                } else {
                                    showInfoToast("Coba ulangi kembali");
                                }
                            } else {
                                showInfoToast(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            showErrorToast(t.getMessage());
                        }
                    });

                    break;
                case TransactionResult.STATUS_PENDING:
                    showErrorToast("Transaksi Dibatalkan");
                    break;
                case TransactionResult.STATUS_FAILED:
                    showErrorToast("Transaksi Dibatalkan");
                    break;
            }
            transactionResult.getResponse().getValidationMessages();
        } else if (transactionResult.isTransactionCanceled()) {
            showInfoToast("Transaksi dibatalkan");
        } else {
            if (transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                showInfoToast("Terjadi kesalahan pada pembayaran");
            } else {
                showInfoToast("Transaksi telah selesai, namun terdapat kendala");
            }
        }
    }
}
