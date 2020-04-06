package com.lintasbandung.lintasbandungapps.damri;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lintasbandung.lintasbandungapps.BuildConfig;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

public class CheckOutActivity extends AppCompatActivity implements TransactionFinishedCallback {

    private String sKeberangkatan, sTujuan, sNamaPemesan, sJumlahPemesan, sHarga, sWaktu;
    private TextView keberangkatan, tujuan, namaPemesan, jumlahPemesan, harga, waktu, jumlahHarga;
    private int totalHarga;
    private Button indomaret, mandiri, gojek;

    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

//        MIDTRANS
        initMidTrans();

//        BUTTON
        indomaret = findViewById(R.id.checkOut_indomaret);
        mandiri = findViewById(R.id.checkOut_mandiri);
        gojek = findViewById(R.id.checkOut_gojek);

        keberangkatan = findViewById(R.id.checkOut_keberangkatan);
        tujuan = findViewById(R.id.checkOut_tujuan);
        namaPemesan = findViewById(R.id.checkOut_namaPemesan);
        jumlahPemesan = findViewById(R.id.checkOut_jumlahPesanan);
        harga = findViewById(R.id.checkOut_harga);
        waktu = findViewById(R.id.checkOut_waktu);
        jumlahHarga = findViewById(R.id.checkOut_jumlahHarga);

        Intent getIntent = getIntent();
        sKeberangkatan = getIntent.getStringExtra("keberangkatan");
        sTujuan = getIntent.getStringExtra("tujuan");
        sNamaPemesan = getIntent.getStringExtra("namapemesan");
        sJumlahPemesan = getIntent.getStringExtra("jumlah");
        sHarga = getIntent.getStringExtra("harga");
        sWaktu = getIntent.getStringExtra("waktu");

        int a, b;
        a = Integer.parseInt(sJumlahPemesan);
        b = Integer.parseInt(sHarga);


        totalHarga = a * b;

        Log.d("Coba", String.valueOf(totalHarga));

        keberangkatan.setText(sKeberangkatan);
        tujuan.setText(sTujuan);
        namaPemesan.setText(sNamaPemesan);
        jumlahPemesan.setText(sJumlahPemesan);
        harga.setText(sHarga);
        waktu.setText(sWaktu);
        jumlahHarga.setText(String.valueOf(totalHarga));

//        PEMBAYARAN
        indomaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indomaret();
            }
        });
        mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandiri();
            }
        });

        gojek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gojek();
            }
        });

    }

    private void gojek() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("1", b, a, "Ticket "));
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.GO_PAY);
    }

    private void mandiri() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", b, a, "Ticket "));
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER_MANDIRI);
    }

    private void indomaret() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("3", b, a, "Ticket "));
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.INDOMARET);
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

    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {
        if (transactionResult.getResponse() != null) {
            switch (transactionResult.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(CheckOutActivity.this, "Transaction Finished ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("SUCCESS: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(CheckOutActivity.this, "Transaction Pending ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("PENDING: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(CheckOutActivity.this, "Transaction Failed ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("FAILED: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
            }
            transactionResult.getResponse().getValidationMessages();
        } else if (transactionResult.isTransactionCanceled()) {
            Toast.makeText(CheckOutActivity.this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(CheckOutActivity.this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CheckOutActivity.this, "Transaction Finished with Failure", Toast.LENGTH_LONG).show();
            }
        }
    }
}
