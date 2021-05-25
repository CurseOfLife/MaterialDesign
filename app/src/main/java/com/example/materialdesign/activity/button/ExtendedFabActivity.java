package com.example.materialdesign.activity.button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class ExtendedFabActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton fab1, fab2, fab3, fab4, fab5, fab6, fab7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_fab);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        fab1 = findViewById(R.id.extended_fab_1);
        fab2 = findViewById(R.id.extended_fab_2);
        fab3 = findViewById(R.id.extended_fab_3);
        fab4 = findViewById(R.id.extended_fab_4);
        fab5 = findViewById(R.id.extended_fab_5);
        fab6 = findViewById(R.id.extended_fab_6);
        fab7 = findViewById(R.id.extended_fab_7);

        fab1.setExtended(false);
        fab2.setExtended(false);
        fab3.setExtended(false);
        fab4.setExtended(false);
        fab5.setExtended(false);
        fab6.setExtended(false);
        fab7.setExtended(false);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) v;

                if (extendedFloatingActionButton.isExtended()) {
                    extendedFloatingActionButton.setExtended(false);
                }else{
                    extendedFloatingActionButton.setExtended(true);
                }
            }

        };

        fab1.setOnClickListener(onClickListener);
        fab2.setOnClickListener(onClickListener);
        fab3.setOnClickListener(onClickListener);
        fab4.setOnClickListener(onClickListener);
        fab5.setOnClickListener(onClickListener);
        fab6.setOnClickListener(onClickListener);
        fab7.setOnClickListener(onClickListener);

}

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Extended Fab");
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
                showInformationDialog("Different kinds of Extended Floating Action Buttons");
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
