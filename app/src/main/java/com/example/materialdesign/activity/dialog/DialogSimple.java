package com.example.materialdesign.activity.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.snackbar.Snackbar;

public class DialogSimple extends AppCompatActivity {

    private View viewGroup;
    private String single_choice_item_selected;

    private static final String[] SINGLE_CHOICES = new String[]{
            "None", "One", "Two", "Three"
    };

    private static final String[] MULTIPLE_CHOICES = new String[]{
             "One", "Two", "Three", "Four"
    };

    private boolean[] clicked_choices = new boolean[MULTIPLE_CHOICES.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_simple);

        viewGroup = findViewById(android.R.id.content);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initiateToolbar();
    }

    public void onDialogItemClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.alert_dialog:
                showAlertDialog();
                break;
            case R.id.confirmation_dialog:
                showConfirmationDialog();
                break;
            case R.id.single_choice_dialog:
                showSingleChoiceDialog();
                break;
            case R.id.multiple_choice_dialog:
                showMultipleChoiceDialog();
                break;
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Allow Google to hack your phone ?")
                .setMessage("By agreeing with the following you give all rights to Google :)")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Snackbar.make(viewGroup, "Google be hacking your phone now", Snackbar.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("DISAGREE", null)
                .show();
    }

    private void showMultipleChoiceDialog() {

        clicked_choices = new boolean[MULTIPLE_CHOICES.length];

        new AlertDialog.Builder(this)
                .setTitle("Choose One ?")
                .setMultiChoiceItems(MULTIPLE_CHOICES, clicked_choices, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        clicked_choices[which] = isChecked;
                    }
                })

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Snackbar.make(viewGroup, "Sent", Snackbar.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("CANCEL", null)

                .show();

    }

    private void showSingleChoiceDialog() {

        //Default 0
        single_choice_item_selected = SINGLE_CHOICES[0];

        new AlertDialog.Builder(this)
                .setTitle("Choose One ?")
                .setSingleChoiceItems(SINGLE_CHOICES, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        single_choice_item_selected = SINGLE_CHOICES[which];
                    }
                })

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Snackbar.make(viewGroup, "You have selected:" + single_choice_item_selected, Snackbar.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("CANCEL", null)

                .show();
    }

    private void showAlertDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Discard draft ?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Snackbar.make(viewGroup, "Discard clicked", Snackbar.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void initiateToolbar() {

       // final Drawable arrow = getResources().getDrawable(R.drawable.ic_arrow_left);

        Toolbar toolbar = (Toolbar) findViewById(R.id.simple_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Simple Dialogs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Different ways to set the back arrow instead of using  setDisplayHomeAsUpEnabled
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_left);


        //  arrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        // getSupportActionBar().setHomeAsUpIndicator(arrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
