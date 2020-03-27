package com.lintasbandung.lintasbandungapps.utils;

import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.network.ApiServiceDatabase;
import com.lintasbandung.lintasbandungapps.services.RetrofitClient;
import com.lintasbandung.lintasbandungapps.services.RetrofitClientDatabase;

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String API_URL = "https://lintasbandung.nyoobie.com/API/";
    public static final String API_DB = "https://lintasbandung.nyoobie.com/API/";

    public static ApiService getApiSerives() {
        return RetrofitClient.getClient(API_URL).create(ApiService.class);
    }

    public static ApiServiceDatabase getDatabase() {
        return RetrofitClientDatabase.getDatabase(API_DB).create(ApiServiceDatabase.class);
    }

}
