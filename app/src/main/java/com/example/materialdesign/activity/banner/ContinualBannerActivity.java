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

// BANNERS ARE NOT YET SUPPORTED IN MD

public class ContinualBannerActivity extends AppCompatActivity {

    private ViewPager viewPager_banner;
    private BannerViewPagerAdapter bannerViewPagerAdapter;
    private List<BannerItem> bannerItems_list;
    private int shownBannerListPosition = 2;

    private Timer timer;
    //This is the time in milliseconds between successive task executions.
    final private long BANNER_UP_TIMER = 3000;
    //This is the delay in milliseconds before task is to be executed.
    final private long DELAY_TIMER_BEFORE_BANNER_CHANGE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continual_banner);
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
       //adding the adapter to the banner view pager
        viewPager_banner.setAdapter(bannerViewPagerAdapter);

        //setting some params
        viewPager_banner.setClipToPadding(false);
        viewPager_banner.setPageMargin(20);

        //setting which item is displayed initially when the view pager is displayed
        viewPager_banner.setCurrentItem(shownBannerListPosition);

        //creating a custom on page change listener
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                shownBannerListPosition = position; //displaying the prior or next item selected by the user or when changed by the looper
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                // if no animation is in progress
                // begin to loop through items
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    bannerLooper();
                }
            }
        };
        //adding the custom On Page Change Listener to the view pager
        viewPager_banner.addOnPageChangeListener(onPageChangeListener);

        startBannerSliderShow();


        //adding the on touch listener
        viewPager_banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                bannerLooper();
                stopBannerSlideShow();

                // A pressed gesture has finished, start the looper again
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSliderShow();

                }
                return false;
            }
        });
        ////////// BANNER
    }

    // handing what happens when our looper (startBannerSlideShow()) comes to the end of the list
    //code for a continual view pager
    private void bannerLooper() {

        if (shownBannerListPosition == bannerItems_list.size() - 2) {
            shownBannerListPosition = 2;
            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }

        if (shownBannerListPosition == 1) {
            shownBannerListPosition = bannerItems_list.size() - 3;
            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }

        /*
        List start to end without the function of going right from start or left from end

        for (int i = 0; i < bannerItems_list.size(); ++i) {

            viewPager_banner.setCurrentItem(shownBannerListPosition, false);
        }
       */
    }

    //starting the banner slide show with a little delay between each transition
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

    //stopping the slide show
    private void stopBannerSlideShow() {
        timer.cancel();
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Continual Banner");
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
                showInformationDialog("Banners aren't supported in Material Design as of yet. Though they are in use on most e-commerce apps.\n Continual Banner is one that goes from the 1st list object to the last and continually goes to the right in this case repeating the same items in the same order.\n A normal banner returns to the 1st position of the list when it reaches the last.");
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

        ((TextView) dialog.findViewById(R.id.version)).setText("Continual Banner" );
        ((TextView) dialog.findViewById(R.id.version)).setText("Version 1.0");
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
