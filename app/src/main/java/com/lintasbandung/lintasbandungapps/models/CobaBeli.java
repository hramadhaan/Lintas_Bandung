package com.lintasbandung.lintasbandungapps.models;

import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.snap.CreditCard;

public class CobaBeli {
    public static TransactionRequest transactionRequest(String id, int price, int qty, String name){
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis()+" ",20000);

        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);
        creditCard.setBank(BankType.MANDIRI);
        request.setCreditCard(creditCard);
        return request;

    }
}
