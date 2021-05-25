package com.example.materialdesign.activity.menu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.Objects;

/*
<item name="overlapAnchor">false</item>
<item name="android:dropDownVerticalOffset">5.0dp</item>
if we decide that we dont want an overflow menu to overlap the toolbar
 */
public class ToolbarOverflowMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_menu);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
    }

    private void initToolbar() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Toolbar Overflow Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // open overflow menu
                toolbar.showOverflowMenu();
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.error:
                showInformationDialog("We can set a dropdown menu on our toolbar.\n We call such menu an Overflow Menu.\nA menu is a temporary sheet of paper that always overlaps the app bar, rather than behaving as an extension of the app bar.");
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "Favorite clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_book:
                Toast.makeText(this, "Book clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_school:
                Toast.makeText(this, "School clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
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
