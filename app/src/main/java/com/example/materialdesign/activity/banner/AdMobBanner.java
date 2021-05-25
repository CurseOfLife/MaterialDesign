package com.example.materialdesign.activity.banner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.URLSpanNoUnderline;
import com.example.materialdesign.utility.VariousTools;

public class AdMobBanner extends AppCompatActivity {

    private TextView link1, link2, link3, link4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_mob_banner);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initToolbar();
        initComponents();
    }

    private void initComponents() {

        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);
        link3 = findViewById(R.id.link3);
        link4 = findViewById(R.id.link4);

        link1.setText(
                Html.fromHtml(
                        "<a href=\"https://admob.google.com/home/\">AdMob Home Page</a> "));
        link1.setMovementMethod(LinkMovementMethod.getInstance());

        link2.setText(
                Html.fromHtml(
                        "<a href=\"https://developers.google.com/admob/android/banner\">AdMob Google Developers</a> "));
        link2.setMovementMethod(LinkMovementMethod.getInstance());

        link3.setText(
                Html.fromHtml(
                        "<a href=\"https://www.youtube.com/c/AdMob/videos\">AdMob Official Youtube</a> "));
        link3.setMovementMethod(LinkMovementMethod.getInstance());

        link4.setText(
                Html.fromHtml(
                        "<a href=\"https://firebase.google.com/docs/admob\">&quot;AdMob and Firebase&quot; Docs</a> "));
        link4.setMovementMethod(LinkMovementMethod.getInstance());

        VariousTools.stripUnderlines(link1);
        VariousTools.stripUnderlines(link2);
        VariousTools.stripUnderlines(link3);
        VariousTools.stripUnderlines(link4);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AdMob Banner");
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