package com.example.materialdesign.activity.card;


import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class SimpleCard extends AppCompatActivity {

    private LinearLayout parent;
    private TextView textViewSearch;
    private MaterialCardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_card);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        textViewSearch = findViewById(R.id.txtView_search);
        textViewSearch.setText("Simple Cards");

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

        initComponents();
    }

    private void initComponents() {
        card1 = findViewById(R.id.card);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);

        card1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                card1.setChecked(!card1.isChecked());
                return true;
            }
        });
        card2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                card2.setChecked(!card2.isChecked());
                return true;
            }
        });
        card3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                card3.setChecked(!card3.isChecked());
                return true;
            }
        });

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
        ((TextView)dialog.findViewById(R.id.txtInfo)).setText("Activity showcases some outlined and none outlined cards.\n They can be selected as well as dragged.\n\n Dragging will be done in lists chapter, as all the transitions/animations fit that chapter well. ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
