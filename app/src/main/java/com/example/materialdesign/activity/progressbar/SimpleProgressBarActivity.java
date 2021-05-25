package com.example.materialdesign.activity.progressbar;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleProgressBarActivity extends AppCompatActivity {

    private ProgressBar pb_linear_two;
    private TextView tv_done;

    private  int  counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_progress_bar);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        pb_linear_two = findViewById(R.id.pb_linear_2);
        tv_done = findViewById(R.id.tv_done);

        pb_linear_two.setSecondaryProgress(100);
        pb_linear_two.setMax(100);

        /**
         * Starting a new thread that lasts till the counter reaches 100
         * changes done to the ui have to be done on a UI thread
         * runOnUiThread solves the issue
         */
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb_linear_two.setProgress(counter);

                if (counter ==100) {
                timer.cancel();

                // if we dont do this we will get the following fatal error -> Only the original thread that created a view hierarchy can touch its views.
                    // this will crash the app

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_linear_two.setVisibility(View.GONE);
                        tv_done.setVisibility(View.VISIBLE);
                    }
                });
                }
            }
        };

        timer.schedule(timerTask,0,100);
    }



    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Various Progress Bars");
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
                showInformationDialog("Progress indicators express an unspecified wait time or display the length of a process.\n\nDeterminate indicators display how long a process will take. They should be used when the process completion rate can be detected.\n\nIndeterminate indicators express an unspecified amount of wait time. They should be used when progress isn’t detectable, or if it’s not necessary to indicate how long an activity will take.");
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
