package com.lintasbandung.lintasbandungapps.network;

import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.models.AllDamri;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;
import com.lintasbandung.lintasbandungapps.models.SpecificRuteDamri;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.models.Token;
import com.lintasbandung.lintasbandungapps.models.ticket.CetakTicketDB;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("damriAllRoute")
    Call<ArrayList<AllDamri>> getAllDamri();

    @GET("allVenichle")
    Call<ArrayList<AllAngkot>> getAllAngkot();

    @GET("damriSpecificRoute/{id}")
    Call<SpecificRuteDamri> getSpecificRoute(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("createOrder")
    Call<Status> createOrder(
            @Field("rute") String rute,
            @Field("jumlah_tiket") int jumlahTiket,
            @Field("harga") int totalHarga,
            @Field("order_id") String orderId,
            @Field("customer") int customer,
            @Field("keberangkatan") String keberangkatan,
            @Field("tujuan") String tujuan,
            @Field("status") String status,
            @Field("bill_key") String bill_key,
            @Field("biner_code") String biller_code,
            @Field("payment_type") String payment_type,
            @Field("tanggal_pemesanan") String tanggal_pemesanan
    );

    @GET("showUserOrder/{id}")
    Call<List<GetHistoryTicket>> getHistoryUser(
            @Path("id") String id
    );

    @GET("showSpecificOrder/{id}")
    Call<CetakTicketDB> getCetakDB(
            @Path("id") String id
    );
}
