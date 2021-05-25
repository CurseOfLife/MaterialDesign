package com.example.materialdesign.activity.selectioncontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class RadioButtonActivity extends AppCompatActivity {

    private RadioButton rb_left, rb_middle, rb_right;
    private RadioButton rb_normal, rb_colored, rb_bigger;
    private RadioButton rb_al_left, rb_al_right, rb_al_center, rb_al_justify;
    private RadioGroup rg1, rg2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String message = "app:useMaterialThemeColors=\"false\"\n\n will save you lots of nerves! \n\n If not set any custom theme you set will not work";

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
        getSupportActionBar().setTitle("Radio Buttons");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponents() {

        rb_left = findViewById(R.id.rb_left);
        rb_middle = findViewById(R.id.rb_middle);
        rb_right = findViewById(R.id.rb_right);

        rb_normal = findViewById(R.id.rb_normal);
        rb_colored = findViewById(R.id.rb_colored);
        rb_bigger = findViewById(R.id.rb_bigger);

        rb_al_left = findViewById(R.id.rb_al_left);
        rb_al_right = findViewById(R.id.rb_al_right);
        rb_al_center = findViewById(R.id.rb_al_center);
        rb_al_justify = findViewById(R.id.rb_al_justify);

        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);

        textView = findViewById(R.id.rb_textView);
    }

    public void onDirectionClicked(View view) {

        boolean isSelected = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rb_left:
                if (isSelected) {

                    // rb_left.setTextColor(Color.WHITE);
                    // rb_middle.setTextColor(Color.WHITE);
                    // rb_right.setTextColor(Color.WHITE);

                    rg2.setVisibility(View.GONE);
                    rg1.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "Pick a Color", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_middle:

                if (isSelected) {

                    rg1.setVisibility(View.VISIBLE);
                    rg2.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "Pick both Color/Alignment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_right:
                if (isSelected) {

                    rg1.setVisibility(View.GONE);
                    rg2.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "Pick an Alignment", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void onColorClicked(View view) {
        boolean isSelected = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rb_normal:
                if (isSelected) {
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(32);
                }
                Toast.makeText(this, "Text Color White", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_colored:

                if (isSelected) {

                    textView.setTextColor(Color.parseColor("#388E3C"));
                    textView.setTextSize(32);
                }

                Toast.makeText(this, "Text Color Green", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_bigger:
                if (isSelected) {
                    textView.setTextColor(Color.parseColor("#FF49F1"));
                    textView.setTextSize(40);
                }
                Toast.makeText(this, "Pink and Big", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onAlignmentSelected(View view) {

        boolean isSelected = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rb_al_left:
                if (isSelected) {

                    textView.setGravity(Gravity.START);
                }
                Toast.makeText(this, "Text aligned left", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_al_center:

                if (isSelected) {
                    textView.setGravity(Gravity.CENTER);
                }

                Toast.makeText(this, "Text aligned center", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_al_right:
                if (isSelected) {
                    textView.setGravity(Gravity.END);
                }
                Toast.makeText(this, "Text aligned right", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rb_al_justify:
                if (isSelected) {

                    // OREO
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                    }

                    Toast.makeText(this, "Oreo Only", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
}
