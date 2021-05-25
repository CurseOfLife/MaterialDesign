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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


//http://prntscr.com/rs0391
public class SpeedDialTwoActivity extends AppCompatActivity {

    private View backdrop;
    private View ll_child_one, ll_child_two, ll_child_three;

    private boolean is_expanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial_two);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        backdrop = findViewById(R.id.backdrop);
        ll_child_one = findViewById(R.id.ll_fab_child_one);
        ll_child_two = findViewById(R.id.ll_fab_child_two);
        ll_child_three = findViewById(R.id.ll_fab_child_three);

        final FloatingActionButton fab_child_one = (FloatingActionButton) findViewById(R.id.fab_child_one);
        final FloatingActionButton fab_child_two = (FloatingActionButton) findViewById(R.id.fab_child_two);
        final FloatingActionButton fab_child_three = (FloatingActionButton) findViewById(R.id.fab_child_three);
        final FloatingActionButton parent_fab = (FloatingActionButton) findViewById(R.id.parent_fab);

        backdrop.setVisibility(View.GONE);
        VariousTools.setVisibilityGoneAnimationAtStart(ll_child_one);
        VariousTools.setVisibilityGoneAnimationAtStart(ll_child_two);
        VariousTools.setVisibilityGoneAnimationAtStart(ll_child_three);


        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(parent_fab);
            }
        });

        parent_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(v);
            }
        });

        fab_child_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Child One Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        fab_child_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Child Two Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        fab_child_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Child Three Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggle(View view) {

        is_expanded = VariousTools.setFabRotation(view, !is_expanded);
        if (is_expanded) {
            backdrop.setVisibility(View.VISIBLE);
            VariousTools.setVisibilityVisibleAnimation(ll_child_one);
            VariousTools.setVisibilityVisibleAnimation(ll_child_two);
            VariousTools.setVisibilityVisibleAnimation(ll_child_three);
        } else {
            backdrop.setVisibility(View.GONE);
            VariousTools.setVisibilityGoneAnimation(ll_child_one);
            VariousTools.setVisibilityGoneAnimation(ll_child_two);
            VariousTools.setVisibilityGoneAnimation(ll_child_three);
        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Speed Dial 2");
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
                showInformationDialog("Speed Dial Two has an extra text next to the child fabs.\n We can mimic a dialog as well, when the user clicks the parent fab it expands to a text fab with a layout above it. For example we can pick a person to send an email to. By clicking the text fab it shrinks the window and sends the mail. ");
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
