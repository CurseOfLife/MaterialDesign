package com.example.materialdesign.activity.bottomnavigation;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.fragment.BottomSheetFragment;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
//ModalBottomSheets are an alternative to inline menus or simple dialogs on mobile and provide room for additional items, longer descriptions, and iconography.
// They must be dismissed in order to interact with the underlying content.
public class BottomAppBarVersionOne extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private View home_bar;
    private TextView textViewSearch;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fab;

    private boolean isHomeBarHidden = false;
    private boolean isFabClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_app_bar_version_one);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initComponents();
    }

    private void initComponents() {

        bottomAppBar = findViewById(R.id.bottomAppBar_versionOne);
        home_bar = findViewById(R.id.home_bar);
        textViewSearch = findViewById(R.id.txtView_search);
        nestedScrollView = findViewById(R.id.nested_scroll);
        fab = findViewById(R.id.fab_versionOne);

        //changing the color over the overflow menu icon by overlaying the same drawable over it in white color
        bottomAppBar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        //This method sets the toolbar as the app bar for the activity.
        setSupportActionBar(bottomAppBar);

        //animating and setting the visibility of home bar and fab when the user pulls the nested scroll view up or down
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY < oldScrollY) { // up
                    animateHomeBar(false);
                    fab.setVisibility(View.VISIBLE);
                }
                if (scrollY > oldScrollY) { // down
                    animateHomeBar(true);
                    fab.setVisibility(View.INVISIBLE);
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


        displayImg();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFabClicked = !isFabClicked;

                if (isFabClicked) {

                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_left));

                } else {
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add_white_24dp));
                }
            }
        });
    }

    private void displayImg() {

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
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("Activity contents are static placeholder items. \n Only the \"Search bar \" text is changed. \n\n Additionally parallax effect \n has been implemented. ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                //other option is bottom sheet dialog
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getClass().getSimpleName());

                break;

            case R.id.navigation_books:
                //Toast.makeText(this, "Books menu item selected", Toast.LENGTH_SHORT).show();
                showToast("Books");
                textViewSearch.setText(item.getTitle());
                break;

            case R.id.navigation_favorites:
                //Toast.makeText(this, "Favorites menu item selected", Toast.LENGTH_SHORT).show();
                showToast("Favorites");
                textViewSearch.setText(item.getTitle());
                break;

            case R.id.fingerprint:
                //Toast.makeText(this, "Fingerprint menu item selected", Toast.LENGTH_SHORT).show();
                showToast("Fingerprint");
                textViewSearch.setText(item.getTitle());
                break;

            case R.id.error:
                //Toast.makeText(this, "Error menu item selected", Toast.LENGTH_SHORT).show();
                showToast("Error");
                textViewSearch.setText(item.getTitle());

                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showToast(String string) {

        String s1 = String.format("%s item selected", string);
        Toast toast = Toast.makeText(this, s1, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void animateHomeBar(final boolean hide) {
        if (isHomeBarHidden && hide || !isHomeBarHidden && !hide) return;
        isHomeBarHidden = hide;
        int moveY = hide ? -(2 * home_bar.getHeight()) : 0;
        home_bar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }
}
