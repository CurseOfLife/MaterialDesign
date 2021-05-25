package com.example.materialdesign.activity.button;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;


public class SimpleButtonTypes extends AppCompatActivity {

    private TextView textViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_button_types);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        textViewSearch = findViewById(R.id.txtView_search);
        textViewSearch.setText("Buttons");

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });
        findViewById(R.id.b9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToast("Clicked");
            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_more_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInformationDialog();
            }
        });
    }

    private void writeToast (String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void showInformationDialog() {
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
        ((TextView)dialog.findViewById(R.id.txtInfo)).setText("Buttons. Buttons. Buttons.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
