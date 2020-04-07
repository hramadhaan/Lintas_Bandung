package com.lintasbandung.lintasbandungapps.utils;

import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.network.ApiServiceMidtrans;
import com.lintasbandung.lintasbandungapps.services.RetrofitClient;
import com.lintasbandung.lintasbandungapps.services.RetrofitClientMidtrans;

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String API_URL = "https://lintasbandung.nyoobie.com/API/";
    public static final String API_DB = "https://api.sandbox.midtrans.com/v2/";

    public static ApiService getApiSerives() {
        return RetrofitClient.getClient(API_URL).create(ApiService.class);
    }

    public static ApiServiceMidtrans getDatabase() {
        return RetrofitClientMidtrans.getDatabase(API_DB).create(ApiServiceMidtrans.class);
    }

}
