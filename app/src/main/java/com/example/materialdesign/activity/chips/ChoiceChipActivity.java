package com.example.materialdesign.activity.chips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Objects;

//

public class ChoiceChipActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private Chip chip1, chip2, chip3, chip4;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_chip);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        iniComponent();
    }

    private void iniComponent() {

        chipGroup = findViewById(R.id.chip_group_choice);
        chip1 = findViewById(R.id.chip1);
        chip2 = findViewById(R.id.chip2);
        chip3 = findViewById(R.id.chip3);
        chip4 = findViewById(R.id.chip4);
        button = findViewById(R.id.button_choice);

        chip1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chip1.isChecked()) {
                    Toast.makeText(ChoiceChipActivity.this, "Choice One Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChoiceChipActivity.this, "Choice One Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chip2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chip2.isChecked()) {
                    Toast.makeText(ChoiceChipActivity.this, "Choice Two Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChoiceChipActivity.this, "Choice Two Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chip3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chip3.isChecked()) {
                    Toast.makeText(ChoiceChipActivity.this, "Choice Three Selected", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(ChoiceChipActivity.this, "Choice Three Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chip4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chip4.isChecked()) {
                    Toast.makeText(ChoiceChipActivity.this, "Choice Four Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChoiceChipActivity.this, "Choice Four Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* does the same
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);
                if (chip != null)
                    Toast.makeText(getApplicationContext(), "Chip is " + chip.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "Checked chip is: ";
                int chipsCount = chipGroup.getChildCount();
                if (chipsCount == 0) {
                    msg += " None!!";

                } else {

                    int i = 0;
                    while (i < chipsCount) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);
                        if (chip.isChecked()) {
                            msg += chip.getText().toString() + " ";

                        }

                        i++;
                    }
                }
                // show message
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choice Chip");
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
                showInformationDialog("Drag the content up.");
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
