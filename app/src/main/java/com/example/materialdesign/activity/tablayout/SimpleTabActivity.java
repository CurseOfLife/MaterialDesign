package com.example.materialdesign.activity.tablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
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
import com.example.materialdesign.adapter.SimpleTabPagerAdapter;
import com.example.materialdesign.fragment.TabLayoutFragmentItem;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SimpleTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        SimpleTabPagerAdapter adapter = new SimpleTabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TabLayoutFragmentItem.newInstance(1), "ITEM ONE");
        adapter.addFragment(TabLayoutFragmentItem.newInstance(2), "ITEM TWO");
        adapter.addFragment(TabLayoutFragmentItem.newInstance(3), "ITEM THREE");
        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Text Tab Layout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
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
                        "Tab menu items are displayed with text only, in this particular activity. While the transitions between the said menu items are showcased via fragment changes inside a view pager.");
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
