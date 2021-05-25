package com.example.materialdesign.activity.selectioncontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.SharedPf;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SwitchesActivity extends AppCompatActivity {


    private EditText edit_txt;
    private TextView text_view_text_view;

    private Button btn_save, btn_apply;
    private SwitchMaterial switch1, switch2, switch3, switch4;


    private static final String SHARED_PREFS = "shared_prefs";

    private static final String TEXT = "text";
    private static final String SWITCH1 = "switch1";
    private static final String SWITCH2 = "switch2";
    private static final String SWITCH3 = "switch3";
    private static final String SWITCH4 = "switch4";

    private String text;
    private boolean switch1ONOFF, switch2ONOFF, switch3ONOFF, switch4ONOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switches);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();

        loadData();
        updateViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String message = "Switches can be On or Off.\n Switches have enabled, hover, focused, and pressed states.\n They are handy tools for use inside a settings tab.\n\n We use SharedPreferences to save data (remember it) even when our app is down.  ";

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.error:
                showInformationDialog(message);
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

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Text Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initComponents() {

        edit_txt = (EditText) findViewById(R.id.edit_text_one);
        text_view_text_view = (TextView) findViewById(R.id.text_view_text_view);

        btn_save = findViewById(R.id.btn_save);
        btn_apply = findViewById(R.id.btn_apply);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        switch4 = findViewById(R.id.switch4);


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_txt.getText().toString() != "") {
                    text_view_text_view.setText(edit_txt.getText().toString());
                } else {
                    Toast.makeText(SwitchesActivity.this, "Line is empty", Toast.LENGTH_SHORT).show();
                }

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
            }
        });

        //On -> change text color
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //on
                    text_view_text_view.setTextColor(Color.parseColor("#7B1FA2"));
                } else {
                    //off
                    text_view_text_view.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        //On -> change text view size
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //on
                    text_view_text_view.setTextSize(16);

                } else {
                    //off
                    text_view_text_view.setTextSize(26);
                }
            }
        });

        //On -> change background of text view
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //on
                    text_view_text_view.setBackgroundColor(Color.parseColor("#FFCA28"));
                } else {
                    //off
                    text_view_text_view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }

            }
        });

        //On -> change icon
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //on
                    text_view_text_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sentiment_very_satisfied_green_32dp, 0, 0, 0);
                } else {
                    //off
                    text_view_text_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sentiment_very_dissatisfied_green_32dp, 0, 0, 0);
                }

            }
        });
    }

    // for some reason I couldn't get it to work with my SharedPref class
    // in other words I wanted to use my custom class to save data to shared pref in it
    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, text_view_text_view.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());
        editor.putBoolean(SWITCH2, switch2.isChecked());
        editor.putBoolean(SWITCH3, switch3.isChecked());
        editor.putBoolean(SWITCH4, switch4.isChecked());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        text = sharedPreferences.getString(TEXT, "");
        switch1ONOFF = sharedPreferences.getBoolean(SWITCH1, false);
        switch2ONOFF = sharedPreferences.getBoolean(SWITCH2, false);
        switch3ONOFF = sharedPreferences.getBoolean(SWITCH3, false);
        switch4ONOFF = sharedPreferences.getBoolean(SWITCH4, false);
    }

    public void updateViews() {
        text_view_text_view.setText(text);
        switch1.setChecked(switch1ONOFF);
        switch2.setChecked(switch2ONOFF);
        switch3.setChecked(switch3ONOFF);
        switch4.setChecked(switch4ONOFF);
    }
}



