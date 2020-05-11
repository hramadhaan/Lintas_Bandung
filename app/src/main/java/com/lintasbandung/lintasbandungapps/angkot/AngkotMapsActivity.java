package com.lintasbandung.lintasbandungapps.angkot;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.adapter.AngkotMapsAdapter;
import com.lintasbandung.lintasbandungapps.models.angkot.AngkotScan;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import org.joda.time.DateTime;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class AngkotMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String getFromLat, getFromLong;
    private String getToLat, getToLong, getHarga, getJarakKm;
    private long getJarak;
    private String id;
    private static final int overview = 0;
    private BottomSheetBehavior bottomSheetBehavior;
    private ApiService apiService;
    private RelativeLayout bottom_sheet;
    private TextView harga, judul, jarak;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angkot_maps);

        apiService = ApiUtils.getApiSerives();
        recyclerView = findViewById(R.id.angkotMaps_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        bottom_sheet = findViewById(R.id.angkotMaps);
        harga = findViewById(R.id.angkotMaps_harga);
        judul = findViewById(R.id.angkotMaps_judul);
        jarak = findViewById(R.id.angkotMaps_jarak);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("id");
        getFromLat = getIntent.getStringExtra("originLat");
        getFromLong = getIntent.getStringExtra("originLong");
        getToLat = getIntent.getStringExtra("destinationLat");
        getToLong = getIntent.getStringExtra("destinationLong");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getDatabase(id);

        new Handler().postDelayed(() -> {
            int hargaInt = Integer.parseInt(getHarga);
            int jarakInt = (int) getJarak;
            int total = hargaInt * (jarakInt / 1000);
            String totalString = String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(total));
            harga.setText("Rp. " + totalString);
            jarak.setText(getJarakKm);
        }, 1100);
    }

    private void getDatabase(String id) {
        Call<AngkotScan> angkotScanCall = apiService.getDataAngkot(id);
        angkotScanCall.enqueue(new Callback<AngkotScan>() {
            @Override
            public void onResponse(Call<AngkotScan> call, Response<AngkotScan> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> getRute = response.body().getTrayek();
                    Log.d("Angkot", getRute.get(0));
                    getHarga = response.body().getTarif();
                    judul.setText(response.body().getName());
                    mAdapter = new AngkotMapsAdapter(getApplicationContext(), getRute);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    showInfoToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<AngkotScan> call, Throwable t) {
                showErrorToast(t.getMessage());
            }
        });
    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext()).mode(mode).origin(origin).destination(destination).departureTime(now).await();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            boolean isSuccess = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            );
            if (!isSuccess) {
                showInfoToast("Map style tidak berfungsi");
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            showErrorToast(e.getMessage());
        }

        setupGoogleMapScreenSettings(googleMap);
        DirectionsResult results = getDirectionsDetails(getFromLat + "," + getFromLong, getToLat + "," + getToLong, TravelMode.TRANSIT);
        if (results != null) {
            addPolyline(results, googleMap);
            positionCamera(results.routes[overview], googleMap);
            addMarkersToMap(results, googleMap);
        }
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setTrafficEnabled(false);

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat, results.routes[overview].legs[overview].startLocation.lng)).title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath).width(10).color(Color.WHITE));
    }

    private String getEndLocationTitle(DirectionsResult results) {
        getJarak = results.routes[overview].legs[overview].distance.inMeters;
        getJarakKm = results.routes[overview].legs[overview].distance.humanReadable;
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private void showErrorToast(String message) {
        SweetToast.error(AngkotMapsActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(AngkotMapsActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(AngkotMapsActivity.this, message, 2200);
    }
}
