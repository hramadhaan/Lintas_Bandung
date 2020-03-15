package com.lintasbandung.lintasbandungapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
            R.drawable.onboarding_satu,
            R.drawable.onboarding_dua,
            R.drawable.onboarding_tiga,
    };

    public String[] slide_headings = {
            "Pesan Kapanpun", "Pembayaran lewat apa saja", "Tak perlu menunggu lagi",
    };

    public String[] slide_subheadings = {
            "Pesan kapan saja dimana saja sesibuk apapun Anda",
            "Metode pembayarannya lebih banyak",
            "Tak perlu lagi menunggu lama di terminal"
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
        container.removeView((RelativeLayout) object);
    }
}
