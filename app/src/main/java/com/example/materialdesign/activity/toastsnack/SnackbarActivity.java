package com.example.materialdesign.activity.toastsnack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SnackbarActivity extends AppCompatActivity {

    private View parent;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        parent = findViewById(android.R.id.content);


        ((Button) findViewById(R.id.simple_snackbar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent, "This is a Simple Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });

        ((Button) findViewById(R.id.snackbar_short)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent, "This is a Short Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });

        ((Button) findViewById(R.id.snackbar_long)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent, "This is a Long Snackbar", Snackbar.LENGTH_LONG).show();
            }
        });

        ((Button) findViewById(R.id.snackbar_indefinite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent, "This is an Indefinite Snackbar", Snackbar.LENGTH_INDEFINITE).show();
            }
        });

        ((Button) findViewById(R.id.snackbar_action)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(parent, "Snackbar with an Action Button", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(parent, "UNDO CLICKED!", Snackbar.LENGTH_SHORT).show();
                            }
                        });

                snackbar.show();
            }
        });

        ((Button) findViewById(R.id.snackbar_custom_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar = Snackbar.make(parent, "Custom Snackbar One", Snackbar.LENGTH_INDEFINITE)
                        .setDuration(6000)
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white))
                        .setActionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green_900))
                        .setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(parent, "UNDO CLICKED!", Snackbar.LENGTH_SHORT).show();
                                snackbar.dismiss();

                            }
                        });
                snackbar.show();
            }
        });

        ((Button) findViewById(R.id.snackbar_custom_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Snackbar  snackbar1 = Snackbar.make(parent, "", Snackbar.LENGTH_LONG);

               View layout = getLayoutInflater().inflate(R.layout.snackbar_custom, null);

               snackbar1.getView().setBackgroundColor(Color.TRANSPARENT);
               Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout)snackbar1.getView();
               snackbarLayout.setPadding(0,0,0,0);

                (layout.findViewById(R.id.action)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "You have clicked the action button", Toast.LENGTH_SHORT).show();
                        snackbar1.dismiss();
                    }
                });

                snackbarLayout.addView(layout,0);
                snackbar1.show();
            }
        });

    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Snackbar");
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
                showInformationDialog("Snackbars provide lightweight feedback about an operation. They show a brief message at the bottom of the screen on mobile and lower left on larger devices. Snackbars appear above all other elements on screen and only one can be displayed at a time.\n\nThey automatically disappear after a timeout or after user interaction elsewhere on the screen, particularly after interactions that summon a new surface or activity. Snackbars can be swiped off screen.");
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
