package com.lintasbandung.lintasbandungapps.network;

import com.lintasbandung.lintasbandungapps.models.AllAngkot;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.models.midtrans.Gojek;
import com.lintasbandung.lintasbandungapps.models.midtrans.Indomart;
import com.lintasbandung.lintasbandungapps.models.midtrans.Mandiri;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceMidtrans {
    @GET("{id_order}/status")
    Call<Gojek> getGojek(
            @Path("id_order") String id_order
    );

    @GET("{id_order}/status")
    Call<Indomart> getIndomart(
            @Path("id_order") String id_order
    );

    @GET("{id_order}/status")
    Call<Mandiri> getMandiri(
            @Path("id_order") String id_order
    );
}
