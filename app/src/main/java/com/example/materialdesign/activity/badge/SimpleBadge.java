package com.example.materialdesign.activity.badge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.BooksPagerAdapter;
import com.example.materialdesign.fragment.BookStoreFragment;
import com.example.materialdesign.fragment.BooksFragment;
import com.example.materialdesign.fragment.FavouritesFragment;
import com.example.materialdesign.interfaces.IBadge;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;


// TabLayoutMediator -> https://developer.android.com/reference/com/google/android/material/tabs/TabLayoutMediator

// I split notification badges into two kinds
// in app https://material.io/develop/android/components/badging/
// the device app notification badges https://developer.android.com/training/notify-user/badges

public class SimpleBadge extends AppCompatActivity implements IBadge {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_badge);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simple Badge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponents() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new BooksPagerAdapter(this));  // setting the Book Pager Adapter

        // there are different ways we can build a tab layout
        // more information in the documentation above
        // or check the tab layout section in this project
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                // setting 3 tabs in our tab layout
                // setting a badge object (drawable) to it
                switch (position) {
                    case 0: {
                        tab.setText("Books");
                        tab.setIcon(R.drawable.ic_book);
                        //if we need to change the icon color we do it like this
                        // with built in colors
                        tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();  // creating a badge to be displayed on the tab
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);

                        break;
                    }
                    case 1: {
                        tab.setText("Favorites");
                        tab.setIcon(R.drawable.ic_favorite);
                        //if we need to change the icon color from a resource we do it like this
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(10);
                        badgeDrawable.setMaxCharacterCount(3); // 999+
                        break;
                    }
                    case 2: {
                        tab.setText("Store");
                        tab.setIcon(R.drawable.ic_shopping_cart_white_24dp);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(1400); // setting the number displayed on the badge as 1400

                        badgeDrawable.setMaxCharacterCount(4);  //we are able to set the max character count
                        break;
                    }
                }
            }
        });

        tabLayoutMediator.attach();


        //what happens when we select the tabs
        //unselected and selected icon color changes can be done through code like this
        //but its better done through selector drawables check tab layout section
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //  tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_200), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        //on scrolling through the view pager set badge to 0 and set its visibility to false
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setNumber(0);
                badgeDrawable.setVisible(false);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        else {
            showInformationDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;

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

        ((TextView) dialog.findViewById(R.id.version)).setText("Simple Badge " );
        ((TextView) dialog.findViewById(R.id.version)).setText("Version 1.0" );
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("Activity showcases a limited use of notification badges. \nThis component is still under development. Some things might change so at this time I will not dive deep into the component. \n\nThe \"old way\" is to use a drawable and anchor \nit/position it in the corner of the view.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    //interface override
    @Override
    public void setBadgeNumber(int position, int amount) {

        //increasing the badge number
        //we first need to get the current number as it can be 0
        //then we add the number of new notifications
        //and set the visibility of the badge to true

        BadgeDrawable badge = tabLayout.getTabAt(position).getBadge();

        int current = badge.getNumber();
        int newAmount = current + amount;
        badge.setNumber(newAmount);
        badge.setVisible(true);
    }
}
