package com.example.materialdesign.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.materialdesign.R;
import com.example.materialdesign.model.BannerItem;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class BannerViewPagerAdapter extends PagerAdapter {

    private List<BannerItem> bannerItems;

    // DEFAULT CONSTRUCTOR
    public BannerViewPagerAdapter(List<BannerItem> bannerItems) {
        this.bannerItems = bannerItems;
    }

    @Override
    public int getCount() {
        return bannerItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_item, container, false);

        ImageView banner_image = view.findViewById(R.id.banner_image_item);
        banner_image.setImageResource(bannerItems.get(position).getBanner_image());

        banner_image.setScaleType(CENTER_CROP); // Otherwise the image goes outside the pager bounds, or is too small.. fitting the image to the whole pager

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }

}
