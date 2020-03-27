package com.lintasbandung.lintasbandungapps.network;

import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.models.Token;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("register")
    Call<Status> createUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("loginapi")
    Call<Token> getToken(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("decode")
    Call<DataUser> getUser();
}
