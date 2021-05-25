package com.example.materialdesign.activity.tablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

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
import com.example.materialdesign.adapter.IconTabPagerAdapter;
import com.example.materialdesign.adapter.SimpleTabPagerAdapter;
import com.example.materialdesign.fragment.TabLayoutFragmentItem;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TextIconTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SimpleTabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_icon_tab);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_book);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite);

        // setting up the colors as the app first opens
        //  the 1st tab is selected
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_400), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.grey_400), PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);   //selected item color

                //if we need to change the title of the activity aka the app bar to those of the selected tabs
                //we do it like this
                // getSupportActionBar().setTitle(adapter.getPageTitle(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_400), PorterDuff.Mode.SRC_IN);  //unselected icon color
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

    }

    private void setViewPager(ViewPager viewPager) {
        adapter = new SimpleTabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TabLayoutFragmentItem.newInstance(1), "ITEM ONE");
        adapter.addFragment(TabLayoutFragmentItem.newInstance(2), "ITEM TWO");
        adapter.addFragment(TabLayoutFragmentItem.newInstance(3), "ITEM THREE");
        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Text & Icon Tab");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setHomeButtonEnabled(true);
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
                showInformationDialog("TabLayout provides a horizontal layout to display tabs. The layout handles interactions for a group of tabs including:\nscrolling behavior,\n" +
                        "(swipe) gestures,\n" +
                        "tab selection,\n" +
                        "animations,\n" +
                        "and alignment.\n\n" +
                        "Tab menu items are displayed with text and icon. In most cases icon is an optional component, it depends on the designer if he or she wishes to implement it or not. Either way the tab has to be informative.\n\n Note that its not a nice design if we change between icons and text constantly or if our tab has both only icon and only text tab items.");
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

        ((TextView) dialog.findViewById(R.id.version)).setText("Version " + BuildConfig.VERSION_NAME);
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
