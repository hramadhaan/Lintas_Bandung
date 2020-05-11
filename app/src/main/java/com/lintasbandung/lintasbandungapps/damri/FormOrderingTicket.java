package com.lintasbandung.lintasbandungapps.damri;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.data.MinMaxFilter;
import com.lintasbandung.lintasbandungapps.models.SpecificRuteDamri;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.ticketing.PembayaranActivity;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import org.angmarch.views.NiceSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class FormOrderingTicket extends AppCompatActivity {

    private NiceSpinner keberangkatan, tujuan;
    private Button checkOut;
    private static final int duration = 2500;
    private ApiService apiService;
    private String a, b, harga;
    private List<String> getTrayek;
    private EditText namaPemesan, jumlahPemesan;
    private TextView currentDate;
    private LinearLayout chooseDate;
    private int year, month, day;
    private String waktu, rute;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_ordering_ticket);

        toolbar = findViewById(R.id.fromOrder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar.getInstance();
        year = Calendar.YEAR;
        month = Calendar.MONTH;
        day = Calendar.DAY_OF_MONTH;

        currentDate = findViewById(R.id.fromOrder_currentDate);
        chooseDate = findViewById(R.id.formOrder_date);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        apiService = ApiUtils.getApiSerives();
        namaPemesan = findViewById(R.id.formOrder_namaPemesan);
        namaPemesan.setText(AppState.getInstance().getUser().getFirstName() + " " + AppState.getInstance().getUser().getLastName());
        jumlahPemesan = findViewById(R.id.formOrder_jumlahPesanan);

        jumlahPemesan.setFilters(new InputFilter[]{new MinMaxFilter("1", "4")});

        Intent getIntent = getIntent();
        a = getIntent.getStringExtra("id");
        b = getIntent.getStringExtra("rute");

        keberangkatan = findViewById(R.id.fromOrder_keberangkatan);
        tujuan = findViewById(R.id.fromOrder_tujuan);
        checkOut = findViewById(R.id.fromOrder_checkout);

        getData();

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sKeberangkatan, sTujuan, sNamaPemesan, sJumlahPemesan;
                sKeberangkatan = keberangkatan.getSelectedItem().toString();
                sTujuan = tujuan.getSelectedItem().toString();
                sNamaPemesan = namaPemesan.getText().toString();
                sJumlahPemesan = jumlahPemesan.getText().toString();

                if (sKeberangkatan.equals(sTujuan)) {
                    showInfoToast("Keberangkatan dan Tujuan tidak boleh sama");
                } else if (sNamaPemesan.isEmpty() || sJumlahPemesan.isEmpty() || currentDate.getText().equals("")) {
                    showInfoToast("Data Anda harus diisi");
                } else {
                    Intent intent = new Intent(FormOrderingTicket.this, PembayaranActivity.class);
                    intent.putExtra("namaTrayek", b);
                    intent.putExtra("keberangkatan", sKeberangkatan);
                    intent.putExtra("tujuan", sTujuan);
                    intent.putExtra("namapemesan", sNamaPemesan);
                    intent.putExtra("jumlah", jumlahPemesan.getText().toString());
                    intent.putExtra("harga", harga);
                    intent.putExtra("waktu", waktu);
                    intent.putExtra("rute", rute);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showErrorToast(String message) {
        SweetToast.error(FormOrderingTicket.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(FormOrderingTicket.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(FormOrderingTicket.this, message, 2200);
    }

    private void getData() {
        Call<SpecificRuteDamri> specificRuteDamriCall = apiService.getSpecificRoute(a);
        specificRuteDamriCall.enqueue(new Callback<SpecificRuteDamri>() {
            @Override
            public void onResponse(Call<SpecificRuteDamri> call, Response<SpecificRuteDamri> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        showInfoToast("Data Kosong");
                    } else {
                        getTrayek = response.body().getTrayek();
                        keberangkatan.attachDataSource(getTrayek);
                        tujuan.attachDataSource(getTrayek);
                        harga = response.body().getHarga();
                        rute = response.body().getId();
                    }
                } else {
                    showInfoToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<SpecificRuteDamri> call, Throwable t) {
                showErrorToast(t.getMessage());
            }
        });
    }

    public void showDatePicker(View view) {
        DatePickerDialog dpd = new DatePickerDialog(FormOrderingTicket.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, i);
                c.set(Calendar.MONTH, i1);
                c.set(Calendar.DAY_OF_MONTH, i2);
                String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                currentDate.setText(currentDateString);
                waktu = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            }
        }, year, month, day);
        //disaple past date
        dpd.getDatePicker().setMinDate(new Date().getTime());
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3));
        dpd.show();
    }
}
