package com.lintasbandung.lintasbandungapps.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.BuildConfig;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.CobaBeli;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

public class TicketFragment extends Fragment implements TransactionFinishedCallback {

    private Button buttonBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewTicket = inflater.inflate(R.layout.fragment_ticket, container, false);

        initMidtrans();

        buttonBuy = viewTicket.findViewById(R.id.ticket_button);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyIt();
//                Toast.makeText(getContext(),"Button Ticket",Toast.LENGTH_LONG).show();
            }
        });

        return viewTicket;
    }

    private void initMidtrans() {
        SdkUIFlowBuilder.init()
                .setContext(getContext())
                .setMerchantBaseUrl(BuildConfig.MERCHANT_BASE_URL)
                .setClientKey(BuildConfig.MERCHANT_CLIENT_KEY)
                .setTransactionFinishedCallback(this)
                .setColorTheme(new CustomColorTheme("#ffe51255", "#B61548", "#FFE51255"))
                .buildSDK();
    }

    private void buyIt() {
        MidtransSDK.getInstance().setTransactionRequest(CobaBeli.transactionRequest(
                "1",
                10000,
                2,
                "Pillow"
        ));
        MidtransSDK.getInstance().startPaymentUiFlow(getContext());
    }

    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {
        if (transactionResult.getResponse() != null) {
            switch (transactionResult.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(getContext(), "Transaction Finished ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(getContext(), "Transaction Pending ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(getContext(), "Transaction Failed ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
            }
            transactionResult.getResponse().getValidationMessages();
        } else if (transactionResult.isTransactionCanceled()) {
            Toast.makeText(getContext(), "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(getContext(), "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Transaction Finished with Failure", Toast.LENGTH_LONG).show();
            }
        }
    }
}
