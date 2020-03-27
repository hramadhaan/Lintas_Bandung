package com.lintasbandung.lintasbandungapps.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.BuildConfig;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.models.CobaBeli;
import com.lintasbandung.lintasbandungapps.ticketing.PembayaranActivity;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.xw.repo.BubbleSeekBar;

public class TicketFragment extends Fragment {

    private BubbleSeekBar seekBar;
    private TextView hasilJumlah;
    //    private int progressSeekbar = 0;
    private Spinner spinner;
    private String[] rute = {
            "Jakarta - Bandung",
            "Jakarta - Semarang",
            "Jakarta - Jogjakarta",
    };
    private String hasilRute;
    private Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewTicket = inflater.inflate(R.layout.fragment_ticket, container, false);

        next = viewTicket.findViewById(R.id.ticket_button);
        hasilJumlah = viewTicket.findViewById(R.id.ticket_hasilJumlah);
        spinner = viewTicket.findViewById(R.id.ticket_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rute);
        spinner.setAdapter(adapter);

        seekBar = viewTicket.findViewById(R.id.ticket_jumlahTicket);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PembayaranActivity.class);
                intent.putExtra("ticket", String.valueOf(seekBar.getProgress()));
                intent.putExtra("rute", spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        return viewTicket;
    }
}
