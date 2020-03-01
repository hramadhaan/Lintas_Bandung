package com.lintasbandung.lintasbandungapps.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.fragment.AngkotFragment;
import com.lintasbandung.lintasbandungapps.fragment.ProfileFragment;
import com.lintasbandung.lintasbandungapps.fragment.TicketFragment;
import com.midtrans.sdk.corekit.core.MidtransSDK;

public class DashboardActivity extends AppCompatActivity {

    private Fragment selectedFragment = null;
    private BubbleNavigationLinearView bubbleNavigationLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        bubbleNavigationLinearView = findViewById(R.id.dashboard_bottom_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frame_layout, new AngkotFragment()).commit();

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                        selectedFragment = new AngkotFragment();
                        break;
                    case 1:
                        selectedFragment = new TicketFragment();
                        break;
                    case 2:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frame_layout, selectedFragment).commit();
            }
        });
    }
}
