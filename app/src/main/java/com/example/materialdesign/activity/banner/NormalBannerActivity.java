package com.example.materialdesign.activity.banner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.BannerViewPagerAdapter;
import com.example.materialdesign.model.BannerItem;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NormalBannerActivity extends AppCompatActivity {

    //check the comments in ContinualBannerActivity
    //the only difference between them is in the bannerLooper() method which you can check here

    private ViewPager viewPager_banner;
    private BannerViewPagerAdapter bannerViewPagerAdapter;
    private List<BannerItem> bannerItems_list;
    private int shownBannerListPosition = 2;

    private Timer timer;
    final private long BANNER_UP_TIMER = 3000;
    final private long DELAY_TIMER_BEFORE_BANNER_CHANGE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_banner);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponents() {

        ////////// BANNER
        viewPager_banner = findViewById(R.id.view_pager_banner);

        // Instantiating the list of banners
        bannerItems_list = new ArrayList<>();

        bannerItems_list.add(new BannerItem(R.drawable.image_7));
        bannerItems_list.add(new BannerItem(R.drawable.image_8));
        bannerItems_list.add(new BannerItem(R.drawable.image_1));

        bannerItems_list.add(new BannerItem(R.drawable.image_2));
        bannerItems_list.add(new BannerItem(R.drawable.image_3));
        bannerItems_list.add(new BannerItem(R.drawable.image_4));
        bannerItems_list.add(new BannerItem(R.drawable.image_5));
        bannerItems_list.add(new BannerItem(R.drawable.image_6));
        bannerItems_list.add(new BannerItem(R.drawable.image_7));

        bannerItems_list.add(new BannerItem(R.drawable.image_8));
        bannerItems_list.add(new BannerItem(R.drawable.image_1));
        bannerItems_list.add(new BannerItem(R.drawable.image_2));

        // Instantiating the view pager adapter
        bannerViewPagerAdapter = new BannerViewPagerAdapter(bannerItems_list);
        viewPager_banner.setAdapter(bannerViewPagerAdapter);

        viewPager_banner.setClipToPadding(false);
        viewPager_banner.setPageMargin(20);

        viewPager_banner.setCurrentItem(shownBannerListPosition);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                shownBannerListPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                // if no animation is in progress
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    bannerLooper();
                }
            }
        };
        viewPager_banner.addOnPageChangeListener(onPageChangeListener);

        startBannerSliderShow();

        viewPager_banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                bannerLooper();
                stopBannerSlideShow();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSliderShow();

                }
                return false;
            }
        });
        ////////// BANNER
    }

    //when the current position is equal to the last position in the list the next transition will return us back to the first item in the list
    //instead of continually going to the right side for example this type of banner will only go  0 to X then return to 0 and start again
    private void bannerLooper() {

        /* Continual
        if (shownBannerListPosition == bannerItems_list.size() - 2) {
            shownBannerListPosition = 2;
            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }

        if (shownBannerListPosition == 1) {
            shownBannerListPosition = bannerItems_list.size() - 3;
            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }
*/
        for (int i = 0; i < bannerItems_list.size(); ++i) {

            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }
    }

    private void startBannerSliderShow() {

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (shownBannerListPosition >= bannerItems_list.size()) {
                    shownBannerListPosition = 1;

                }
                viewPager_banner.setCurrentItem(shownBannerListPosition++, true);
            }
        };

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(update);
            }
        }, DELAY_TIMER_BEFORE_BANNER_CHANGE, BANNER_UP_TIMER);

    }

    private void stopBannerSlideShow() {
        timer.cancel();

    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Normal Banner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.error:
                showInformationDialog("A normal banner goes through a list and when it reaches the end of the list it scrolls back to first again and starts again.\n Personally I'm not a fan of this banner. I dislike the transition from last to first where it \"flies over\" the content of the list.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInformationDialog(String message) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_information_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Normal Banner" );
        ((TextView) dialog.findViewById(R.id.version)).setText("Version 1.0 " );
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText(message);

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
