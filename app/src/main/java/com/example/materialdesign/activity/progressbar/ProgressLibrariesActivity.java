package com.example.materialdesign.activity.progressbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

public class ProgressLibrariesActivity extends AppCompatActivity {

    private TextView link1, link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_libraries);


        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initToolbar();
        initComponents();
    }

    private void initToolbar() {

        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);

        link1.setText(
                Html.fromHtml(
                        "<a href=\"https://github.com/wasabeef/awesome-android-ui/blob/master/pages/Progress.md\">Awesome Progress Libraries</a> "));
        link1.setMovementMethod(LinkMovementMethod.getInstance());


        link2.setText(
                Html.fromHtml(
                        "<a href=\"https://github.com/wasabeef/awesome-android-ui\">Base Awesome Android UI resources</a> "));
        link2.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Various Progress Bar Libraries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

}