package com.lintasbandung.lintasbandungapps;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.lintasbandung.lintasbandungapps.activity.HomePageActivity;
import com.lintasbandung.lintasbandungapps.adapter.OnboardingAdapter;
import com.lintasbandung.lintasbandungapps.awalan.LoginActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;

public class OnBoardingScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private OnboardingAdapter onboardingAdapter;
    private TextView[] dots;
    private Button next, back;
    private int currentPage;
    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bluePrimary));
        }

        appState = AppState.getInstance();
        if (appState.isLoggedIn()) {
            startActivity(new Intent(OnBoardingScreen.this, HomePageActivity.class));
            finish();
        }

        viewPager = findViewById(R.id.onboarding_viewpager);
        linearLayout = findViewById(R.id.onboarding_linear);
        back = findViewById(R.id.onboarding_back);
        next = findViewById(R.id.onboarding_next);
        back.setVisibility(View.INVISIBLE);

        onboardingAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(onboardingAdapter);

        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage + 1);
                if (next.getText().toString().equals("Finish")) {
                    startActivity(new Intent(OnBoardingScreen.this, LoginActivity.class));
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage - 0);
            }
        });
    }

    public void addDotsIndicator(int position) {
        dots = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dark_gray));
            linearLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.bluePrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;
            if (position == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                next.setText("Next");
                back.setText("");
            } else if (position == dots.length - 1) {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setText("Finish");
                back.setText("Back");
            } else {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setText("Next");
                back.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
