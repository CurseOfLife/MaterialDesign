package com.example.materialdesign.activity.menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class CustomDrawerActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawer);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initNavigationMenu();
    }

    private void initNavigationMenu() {

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {

                updateNavigation(navigationView);
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        drawer.openDrawer(GravityCompat.START);
        // since our drawer is opened on  activity start we need to update the navigation
        // otherwise we would just update it on the user hamburger icon click
        // if we don't do it here the formatting we did in our update method will not be applied
        updateNavigation(navigationView);
    }

    private void updateNavigation(NavigationView navigationView) {

        Menu menu = navigationView.getMenu();
        ((TextView) menu.findItem(R.id.nav_placeholder1).getActionView().findViewById(R.id.text_badge)).setText("15");

        //  nav_placeholder2
        TextView badge_placeholder2_text = (TextView) menu.findItem(R.id.nav_placeholder2).getActionView().findViewById(R.id.filled_badge);
        badge_placeholder2_text.setText("15 New");
        badge_placeholder2_text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Custom Drawer One");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.error) {
            showInformationDialog("We can do badges the old way where we add an actionLayout to our menu item which has a text view. We use the id of the view to update it's text.");
            return true;
        }
        return super.onOptionsItemSelected(item);
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
