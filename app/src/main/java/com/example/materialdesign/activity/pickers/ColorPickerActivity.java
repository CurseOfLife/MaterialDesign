package com.example.materialdesign.activity.pickers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

import org.w3c.dom.Text;

import java.util.Objects;

public class ColorPickerActivity extends AppCompatActivity {

    private TextView txt_color;
    private int current_red = 80;
    private int current_green = 80;
    private int current_blue = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();

    }

    private void initComponent() {
        txt_color = findViewById(R.id.txt_color);

        ((Button) findViewById(R.id.btn_pick_color)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorPickerDialog();
            }
        });
    }

    private void showColorPickerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_color_picker);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final View result_color = (View) dialog.findViewById(R.id.result_color);
        final TextView text_result = (TextView) dialog.findViewById(R.id.text_result);
        final SeekBar seek_red = (SeekBar) dialog.findViewById(R.id.seek_red);
        final SeekBar seek_green = (SeekBar) dialog.findViewById(R.id.seek_green);
        final SeekBar seek_blue = (SeekBar) dialog.findViewById(R.id.seek_blue);
        final TextView txt_red = (TextView) dialog.findViewById(R.id.txt_red);
        final TextView txt_green = (TextView) dialog.findViewById(R.id.txt_green);
        final TextView txt_blue = (TextView) dialog.findViewById(R.id.txt_blue);

        result_color.setBackgroundColor(Color.rgb(current_red, current_green, current_blue));
        text_result.setTextColor(Color.rgb(current_red, current_green, current_blue));
        seek_red.setProgress(current_red);
        seek_green.setProgress(current_green);
        seek_blue.setProgress(current_blue);
        txt_red.setText(current_red + "");
        txt_green.setText(current_green + "");
        txt_blue.setText(current_blue + "");

        seek_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_red.setText(progress + "");
                result_color.setBackgroundColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
                text_result.setTextColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seek_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_green.setText(progress + "");
                result_color.setBackgroundColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
                text_result.setTextColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seek_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_blue.setText(progress + "");
                result_color.setBackgroundColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
                text_result.setTextColor(Color.rgb(seek_red.getProgress(), seek_green.getProgress(), seek_blue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        ((Button) dialog.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                current_red = seek_red.getProgress();
                current_green = seek_green.getProgress();
                current_blue = seek_blue.getProgress();

                txt_color.setText("RGB(" + current_red + ", " + current_green + ", " + current_blue + ")");
                txt_color.setTextColor(Color.rgb(current_red, current_green, current_blue));
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Color Picker");
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
                showInformationDialog("When we say color picker we can think of two things.\nWe want to know the color of something - very useful tool in developing an app.\n Or we want the user to pick a color he wishes in order to apply the user specific color into our view, and or save it as an object.");
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
