package com.example.materialdesign.activity.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

public class ChipNavigationBar extends AppCompatActivity {

    private TextView link1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_navigation_bar);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        link1 = findViewById(R.id.link1);

        link1.setText(
                Html.fromHtml(
                        "<a href=\"https://github.com/ismaeldivita/chip-navigation-bar\">Chip Navigation Bar - Github</a> "));
        link1.setMovementMethod(LinkMovementMethod.getInstance());

        VariousTools.stripUnderlines(link1);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chip Navigation Bar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home) {

            finish();
            return true;
        } else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}