package com.lintasbandung.lintasbandungapps.ticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.BuildConfig;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.CobaBeli;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

public class PembayaranActivity extends AppCompatActivity implements TransactionFinishedCallback {

    private int hargaRute = 10000;
    private int jumlahTicket = 1;
    private int jumlahHarga = 0;
    private TextView rute, tiket, harga;
    private Button indomaret, mandiri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        initMidTrans();

        rute = findViewById(R.id.pembayaran_rute);
        tiket = findViewById(R.id.pembayaran_jumlahTicket);
        harga = findViewById(R.id.pembayaran_jumlahHarga);

        Intent intent = getIntent();
        tiket.setText(intent.getStringExtra("ticket"));
        rute.setText(intent.getStringExtra("rute"));

        jumlahTicket = Integer.parseInt(intent.getStringExtra("ticket"));

        jumlahHarga = hargaRute * jumlahTicket;

        harga.setText(String.valueOf(jumlahHarga));

        indomaret = findViewById(R.id.pembayaran_button);
        indomaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithIndomaret();
            }
        });

        mandiri = findViewById(R.id.pembayaran_mandiri);
        mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItWithMandiri();
            }
        });
    }

    private void buyItWithMandiri() {
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", hargaRute, jumlahTicket, "Ticket " + rute));
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER_MANDIRI);
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
        MidtransSDK.getInstance().setTransactionRequest(DataUser.transactionRequest("2", hargaRute, jumlahTicket, "Ticket " + rute));
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.INDOMARET);

    }


    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {
        if (transactionResult.getResponse() != null) {
            switch (transactionResult.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(PembayaranActivity.this, "Transaction Finished ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("SUCCESS: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(PembayaranActivity.this, "Transaction Pending ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("PENDING: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(PembayaranActivity.this, "Transaction Failed ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Log.d("FAILED: ", transactionResult.getResponse().getOrderId() + "\n" + transactionResult.getResponse().getTransactionId());
                    break;
            }
            transactionResult.getResponse().getValidationMessages();
        } else if (transactionResult.isTransactionCanceled()) {
            Toast.makeText(PembayaranActivity.this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(PembayaranActivity.this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PembayaranActivity.this, "Transaction Finished with Failure", Toast.LENGTH_LONG).show();
            }
        }
    }
}
