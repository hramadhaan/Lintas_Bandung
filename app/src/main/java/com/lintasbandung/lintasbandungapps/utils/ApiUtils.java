package com.lintasbandung.lintasbandungapps.utils;

import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.services.RetrofitClient;

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String API_URL = "https://lintasbandung.nyoobie.com/API/";

    public static ApiService getApiSerives() {
        return RetrofitClient.getClient(API_URL).create(ApiService.class);
    }
}
