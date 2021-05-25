package com.example.materialdesign.activity.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class CustomRandomDialogs extends AppCompatActivity {

    private View viewGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_random_dialogs);

        viewGroup = findViewById(android.R.id.content);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initiateToolbar();

    }

    private void initiateToolbar() {

        // final Drawable arrow = getResources().getDrawable(R.drawable.ic_arrow_left);

        Toolbar toolbar = (Toolbar) findViewById(R.id.simple_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Commonly Used Random Dialogs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Different ways to set the back arrow instead of using  setDisplayHomeAsUpEnabled
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        // arrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        // getSupportActionBar().setHomeAsUpIndicator(arrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void onDialogItemClicked(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.dialog1:
                showAboutDialog();
                break;

            case R.id.dialog2:
                showTermsOfServiceDialog();
                break;

            case R.id.dialog3:
                //many apps these days have some kind of a "leave a review" system
                //similarly we can use it to leave a comment but without the rating bars
                showReviewDialog();
                break;

            case R.id.dialog4:
                showVerificationCodeDialog();
                break;

            case R.id.dialog5:
                showCustomDialog5();
                break;

            case  R.id.dialog6:
                showCustomDialog6();
                break;
        }
    }

    // didnt implement next action or on change functionality
    // the user has to click each text field
    // it would be nice if he wrote a letter in one we moved him to the next field ourselves
    private void showVerificationCodeDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_verification_code);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((Button) dialog.findViewById(R.id.btn_verify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void showReviewDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_leave_review);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


        final RatingBar user_rating_input = (RatingBar) dialog.findViewById(R.id.user_review_rating_input);
        final EditText user_text_input = (EditText) dialog.findViewById(R.id.user_review_text_input);

        ((Button) dialog.findViewById(R.id.btn_cancel_review)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Review input canceled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_submit_review)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_review_text = user_text_input.getText().toString().trim();

                if (user_review_text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty review body", Toast.LENGTH_SHORT).show();
                    //we could also enable and disable buttons in these
                } else {
                    Toast.makeText(getApplicationContext(), "Review: "+ user_review_text +"\nRating: "+ user_rating_input.getRating(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void showTermsOfServiceDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_terms_of_service);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.btn_download)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Terms Downloaded", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Terms declined", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Terms Accepted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void showAboutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Version " + BuildConfig.VERSION_NAME);

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((View) dialog.findViewById(R.id.gotIt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showCustomDialog5() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_five);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((View) dialog.findViewById(R.id.btn_let_me_see)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showCustomDialog6() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_six);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((View) dialog.findViewById(R.id.btn_pass)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((View) dialog.findViewById(R.id.btn_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
