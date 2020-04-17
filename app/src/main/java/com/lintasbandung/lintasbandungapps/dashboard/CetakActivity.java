package com.lintasbandung.lintasbandungapps.dashboard;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.DataToast;
import com.lintasbandung.lintasbandungapps.models.midtrans.Gojek;
import com.lintasbandung.lintasbandungapps.models.midtrans.Indomart;
import com.lintasbandung.lintasbandungapps.models.midtrans.Mandiri;
import com.lintasbandung.lintasbandungapps.models.ticket.CetakTicketDB;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.network.ApiServiceMidtrans;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView keberangkatan, jumlahPesanan, tglKeberangkatan, status, kodePembayaran, tipePembayaran;
    private ApiService apiService;
    private ApiServiceMidtrans apiServiceMidtrans;
    private String id_order, id, tipe, keb, tuj;
    private String statusMidtrans;
    private String statusApi;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayout;
    private static int overview = 0;
    private ImageView barcode;

    @Override
    protected void onStart() {
        super.onStart();
        if (tipe.equals("BANK MANDIRI")) {
            getDataBankMandiri(id_order);
        }
        if (tipe.equals("INDOMART")) {
            getDataIndomart(id_order);
        }
        if (tipe.equals("GOPAY")) {
            getDataGopay(id_order);
            kodePembayaran.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_cetak);
        mapFragment.getMapAsync(this);

        apiService = ApiUtils.getApiSerives();
        apiServiceMidtrans = ApiUtils.getDatabase();

        linearLayout = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_EXPANDED) {

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        keberangkatan = findViewById(R.id.cetak_keberangkatan);
        jumlahPesanan = findViewById(R.id.cetak_jumlahPesanan);
        tglKeberangkatan = findViewById(R.id.cetak_tglKeberangkatan);
        status = findViewById(R.id.cetak_status);
        kodePembayaran = findViewById(R.id.cetak_kodePembayaran);
        tipePembayaran = findViewById(R.id.cetak_tipePembayaran);
        barcode = findViewById(R.id.cetak_barcode);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("id");
        id_order = getIntent.getStringExtra("id_order");
        tipe = getIntent.getStringExtra("tipe");
        keb = getIntent.getStringExtra("keb");
        tuj = getIntent.getStringExtra("tuj");

        Log.d("JALAN", keb + "=>" + tuj);

        getDataDatabase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (statusApi.equals("READY") && statusMidtrans.equals("settlement")) {
                    createBarcode(id);
                } else if (statusApi.equals("PRINTED") && statusMidtrans.equals("settlement")) {
                    Toast.makeText(CetakActivity.this, "Anda telah melakukan perjalanan", Toast.LENGTH_LONG).show();
                } else if (statusApi.equals("READY") && statusMidtrans.equals("PENDING")) {
                    Toast.makeText(CetakActivity.this, "Lakukan pembayaran terlebih dahulu", Toast.LENGTH_LONG).show();
                }
            }
        }, 1000);
    }


    public void createBarcode(String id) {
        try {
            barcode.setVisibility(View.VISIBLE);
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(id, BarcodeFormat.CODE_128, 400, 200, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            barcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("Error_Barcode", e.getMessage());
        }
    }

    public void getDataGopay(String id_order) {
        Call<Gojek> getGopay = apiServiceMidtrans.getGojek(id_order);
        getGopay.enqueue(new Callback<Gojek>() {
            @Override
            public void onResponse(Call<Gojek> call, Response<Gojek> response) {
                if (response.isSuccessful()) {
                    DataToast dataToast = new DataToast();
                    dataToast.setStringApi(response.body().getStatusMessage());
                    status.setText(response.body().getStatusMessage());
                    statusMidtrans = response.body().getTransactionStatus();
                } else {
                    Toast.makeText(CetakActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Gojek> call, Throwable t) {
                Toast.makeText(CetakActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void getDataIndomart(String id_order) {
        Call<Indomart> getIndomart = apiServiceMidtrans.getIndomart(id_order);
        getIndomart.enqueue(new Callback<Indomart>() {
            @Override
            public void onResponse(Call<Indomart> call, Response<Indomart> response) {
                if (response.isSuccessful()) {
//                    final String statusMidtrans = response.body().getTransactionStatus();
                    statusMidtrans = response.body().getTransactionStatus();
                    status.setText(response.body().getTransactionStatus());
                    kodePembayaran.setText(response.body().getPaymentCode());
                } else {
                    Toast.makeText(CetakActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Indomart> call, Throwable t) {

            }
        });
    }

    private void getDataBankMandiri(String id_order) {
        Call<Mandiri> getMandiri = apiServiceMidtrans.getMandiri(id_order);
        getMandiri.enqueue(new Callback<Mandiri>() {
            @Override
            public void onResponse(Call<Mandiri> call, Response<Mandiri> response) {
                if (response.isSuccessful()) {
                    statusMidtrans = response.body().getTransactionStatus();
                    status.setText(response.body().getTransactionStatus());
                    kodePembayaran.setText(response.body().getBillerCode() + " + " + response.body().getBillKey());
                }
            }

            @Override
            public void onFailure(Call<Mandiri> call, Throwable t) {

            }
        });
    }

    private void getDataDatabase() {
        Call<CetakTicketDB> cetakTicketDBCall = apiService.getCetakDB(id);
        cetakTicketDBCall.enqueue(new Callback<CetakTicketDB>() {
            @Override
            public void onResponse(Call<CetakTicketDB> call, Response<CetakTicketDB> response) {
                if (response.isSuccessful()) {
                    keberangkatan.setText(response.body().getKeberangkatan() + " => " + response.body().getTujuan());
                    jumlahPesanan.setText(response.body().getJumlahTiket());
                    tglKeberangkatan.setText(response.body().getTanggalPemesanan());
                    tipePembayaran.setText(response.body().getPaymentType());
                    DataToast dataToast = new DataToast();
                    dataToast.setStringApi(response.body().getStatus());
                    statusApi = response.body().getStatus();
//                    Toast.makeText(getApplicationContext(), dataToast.getStringApi(), Toast.LENGTH_LONG).show();
//                    check(statusApi, null);
                }
            }

            @Override
            public void onFailure(Call<CetakTicketDB> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            boolean isSuccess = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            );
            if (!isSuccess) {
                Toast.makeText(CetakActivity.this, "Map Style Tidak Berfungsi", Toast.LENGTH_LONG).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        setupGoogleMapScreenSettings(googleMap);

        DirectionsResult results = getDirectionsDetails(keb + " ,Bandung", tuj + " ,Bandung", TravelMode.TRANSIT);

        if (results != null) {
            addPolyline(results, googleMap);
            positionCamera(results.routes[overview], googleMap);
            addMarkersToMap(results, googleMap);
        }

//        Log.d("MAPS", origins + "\n" + destinations);

//        Log.d("onMapReady", results.routes[overview].fare.value.toString());
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
//        List<PatternItem> patternItems = Arrays.<PatternItem>asList(
//                new Dot(),new Gap(20), new Dash(30),new Gap(20)
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath).width(10).color(Color.WHITE));
    }

    private String getEndLocationTitle(DirectionsResult results) {
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
}
