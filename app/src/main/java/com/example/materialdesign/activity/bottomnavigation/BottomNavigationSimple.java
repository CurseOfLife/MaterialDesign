package com.example.materialdesign.activity.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class BottomNavigationSimple extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private View home_bar;
    private TextView textViewSearch;
    private boolean isHomeBarHidden = false;
    private boolean isNavigationHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_simple);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initComponents();
    }

    private void initComponents() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getOrCreateBadge(R.id.navigation_books).setNumber(2);  // creating a badge badge

        home_bar = findViewById(R.id.home_bar);
        textViewSearch = findViewById(R.id.txtView_search);
        NestedScrollView nestedScrollView = findViewById(R.id.nested_scroll);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_home) {

                    textViewSearch.setText(item.getTitle());
                    return true;
                } else if (item.getItemId() == R.id.navigation_books) {

                    textViewSearch.setText(item.getTitle());
                    return true;
                } else if (item.getItemId() == R.id.navigation_favorites) {

                    textViewSearch.setText(item.getTitle());
                    return true;
                }

                return false;
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY < oldScrollY) { // up
                    animateHomeBar(false);
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateHomeBar(true);
                    animateNavigation(true);
                }
            }
        });


        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_more_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInformationDialog();
            }
        });

        // Otherwise the images aren't displayed
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_1);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_2);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_3);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_4);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_5), R.drawable.image_5);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_6), R.drawable.image_6);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_7), R.drawable.image_7);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_8), R.drawable.image_8);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_9), R.drawable.image_9);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_10), R.drawable.image_10);
    }

    private void showInformationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_information_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Version 1.0");
        ((TextView)dialog.findViewById(R.id.txtInfo)).setText("Activity contents are static placeholder items. \n Only the \"Search\" text is changed. \n\n Additionally parallax effect \n and \"Navigation Menu Item in Focus\" \n have been implemented. ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //when the user moves the interface the home bar is removed off screen
    private void animateHomeBar(final boolean hide) {
        if (isHomeBarHidden && hide || !isHomeBarHidden && !hide) return;
        isHomeBarHidden = hide;
        int moveY = hide ? -(2 * home_bar.getHeight()) : 0;
        home_bar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    //when the user moves the interface bottom navigation bar is removed off screen
    private void animateNavigation(boolean b) {

        if (isNavigationHidden && b || !isNavigationHidden && !b) return;
        isNavigationHidden = b;

        int moveY = b ? (2 * bottomNavigationView.getHeight()) : 0;
        bottomNavigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }
}
