package com.example.materialdesign.activity.toastsnack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        ((Button) findViewById(R.id.simple_toast)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "This is a basic simple toast", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button) findViewById(R.id.custom_toast)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View custom_toast_layout = getLayoutInflater().inflate(R.layout.toast_custom_one, (ViewGroup) findViewById(R.id.custom_toast_one_parent));
                TextView text = (TextView) custom_toast_layout.findViewById(R.id.custom_toast_one_text);
                text.setText("Custom Toast One");

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(custom_toast_layout);
                toast.show();
            }
        });

        ((Button) findViewById(R.id.custom_toast_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View custom_toast_layout = getLayoutInflater().inflate(R.layout.toast_custom_one, (ViewGroup) findViewById(R.id.custom_toast_one_parent));

                TextView text = (TextView) custom_toast_layout.findViewById(R.id.custom_toast_one_text);
                text.setTextColor(Color.WHITE);
                text.setText("Custom Toast Two");

                CardView card = (CardView) custom_toast_layout.findViewById(R.id.custom_toast_one_card);
                card.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(custom_toast_layout);
                toast.show();
            }
        });

        ((Button) findViewById(R.id.toast_custom_position)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(), "Toast with custom position", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 250);
                toast.show();
            }
        });

        ((Button) findViewById(R.id.toast_short)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "This is a short toast", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button) findViewById(R.id.toast_long)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "This is a long toast", Toast.LENGTH_LONG).show();

            }

        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Toast");
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
                showInformationDialog("A toast provides simple feedback about an operation in a small popup. It only fills the amount of space required for the message and the current activity remains visible and interactive. Toasts automatically disappear after a timeout.\n\n If toast is blocking a view we could move the toast, but most of the times we would move the view as users are used to the toast popping out at the same place.");
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
