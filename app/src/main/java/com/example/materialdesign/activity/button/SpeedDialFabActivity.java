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
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SpeedDialFabActivity extends AppCompatActivity {

    private View coordinator;
    private FloatingActionButton fab_parent;
    private boolean is_expanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial_fab);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        coordinator = findViewById(R.id.coordinator);
        fab_parent = findViewById(R.id.fab_parent);
        final FloatingActionButton fab_child_one = (FloatingActionButton) findViewById(R.id.fab_child_1);
        final FloatingActionButton fab_child_two = (FloatingActionButton) findViewById(R.id.fab_child_2);

        VariousTools.setVisibilityGoneAnimationAtStart(fab_child_one);
        VariousTools.setVisibilityGoneAnimationAtStart(fab_child_two);

        fab_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                is_expanded = VariousTools.setFabRotation(v, !is_expanded);
                //returns true the first time
                if (is_expanded) {
                    VariousTools.setVisibilityVisibleAnimation(fab_child_one);
                    VariousTools.setVisibilityVisibleAnimation(fab_child_two);
                } else {
                    VariousTools.setVisibilityGoneAnimation(fab_child_one);
                    VariousTools.setVisibilityGoneAnimation(fab_child_two);
                }
            }
        });

        fab_child_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab Child One Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        fab_child_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab Child Two Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Speed Dial Fab");
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
                showInformationDialog("There seems to be a bug going around where the Fab icon tint won't change. I've solved this issue with PorterDuff in a few activities, but as this activity is shill in alpha stage I will leave it like this and revisit the issue later.");
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
