package com.example.materialdesign.activity.selectioncontrol;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;

// https://www.geeksforgeeks.org/stringbuffer-class-in-java/ -> good for Toasts
public class CheckboxActivity extends AppCompatActivity {

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private Button btn_submit;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkboxes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String message = "1. Normal checkbox \n\n2. We are able to change the button tint\n\n3. We are able to change the text color\n\n 4. Also, a bit more complicated,\nbut still easy enough,\n we can change the size of our button by\ntelling it to use a different sized drawable\n\nFor some unknown reason I was not able to set the theme (or view it on my device).\n Themes help us implement styles via the\nstyle folder.\n\n NOTE:\n If we set the button to be our own drawable\nwe lose the built in ripple effect,\n which normal checkboxes have. \n\nThe background was changed\nin order to best display the picked colors.\nIt should not be changed in a real app.\nBest practice is to stick to the picked background, which most of the times is white.";

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

    private void initComponents() {

        checkBox1 = findViewById(R.id.check1);
        checkBox2 = findViewById(R.id.check2);
        checkBox3 = findViewById(R.id.check3);
        checkBox4 = findViewById(R.id.check4);
        btn_submit = findViewById(R.id.btn_submit);
        editText = findViewById(R.id.editTxt);

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked()) {
                    checkBox1.setTextColor(getResources().getColor(R.color.green_700));
                    Toast.makeText(CheckboxActivity.this, checkBox1.getText()+""+ "Selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkBox1.setTextColor(getResources().getColor(R.color.white));
                    Toast.makeText(CheckboxActivity.this, checkBox1.getText()+""+ "Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox2.isChecked()) {
                    Toast.makeText(CheckboxActivity.this, checkBox2.getText()+" "+ "Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckboxActivity.this, checkBox2.getText()+" "+ "Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox2.isChecked()) {
                    Toast.makeText(CheckboxActivity.this, checkBox3.getText()+" "+ "Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckboxActivity.this, checkBox3.getText()+" "+ "Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox2.isChecked()) {
                    Toast.makeText(CheckboxActivity.this, checkBox4.getText()+" "+ "Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckboxActivity.this, checkBox4.getText()+" "+ "Unselected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "";

                if (checkBox1.isChecked()) {
                    string += " One ";
                }
                if (checkBox2.isChecked()) {
                    string += " Two ";
                }
                if (checkBox3.isChecked()) {
                    string += " Three ";
                }
                if (checkBox4.isChecked()) {
                    string += " Four ";
                }

                editText.setText(string);
            }
        });
    }
}
