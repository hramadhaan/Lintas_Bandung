package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.lintasbandung.lintasbandungapps.R;

public class OnboardingAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public OnboardingAdapter(Context context) {
        this.context = context;
    }

    //    ARRAYS
    public int[] slide_images = {
            R.drawable.ic_undraw_order_ride_xjs4,
            R.drawable.ic_undraw_verified_tw20,
            R.drawable.ic_undraw_happy_feeling_slmw,
    };

    public String[] slide_headings = {
            "Akhirnya...", "Aman dan Akurat", "Langsung aja pakai",
    };

    public String[] slide_subheadings = {
            "Pesan kapan saja dimana saja sesibuk apapun Anda",
            "Metode pembayarannya banyak, jadi ga perlu khawatir lagi deh...",
            "Hanya lewat Lintas Bandung pemesanan akan lebih mudah"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_layout, container, false);
        ImageView imageView = view.findViewById(R.id.layout_gambar);
        TextView header = view.findViewById(R.id.layout_header);
        TextView subheader = view.findViewById(R.id.layout_subheader);

        imageView.setImageResource(slide_images[position]);
        header.setText(slide_headings[position]);
        subheader.setText(slide_subheadings[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
