package com.example.materialdesign.activity.bottomsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.fragment.CustomBottomSheetFullDialogFragment;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;

// MBS are an alternative to inline menus or simple dialogs on mobile and provide room for additional items, longer descriptions, and iconography.
// They must be dismissed in order to interact with the underlying content.
public class ModalFullscreenBottomSheet extends AppCompatActivity {

    private TextView textViewSearch;
    private TextView click_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_fullscreen_bottom_sheet);


        initComponents();
        Toast.makeText(this, "Swipe up bottom sheet", Toast.LENGTH_SHORT).show();
    }

    private void initComponents() {
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        textViewSearch = findViewById(R.id.txtView_search);
        click_me= findViewById(R.id.click_me);

        textViewSearch.setText("Full Modal Sheet");

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

        // tag is used to send a DAO
        // lets say we have a list/recycler view; by clicking on the holder the adapter triggers an onclick and sends an object to the dialog fragment
        // one solution is to do a setter on the bottomSheetFullDialogFragment
        // the fragment class has to have an object of the said DTO

        // UPDATE: ModalDialogBottomSheet.java has the data sender implemented
        // It's not exactly the same since that class uses an dialog and doesnt need to send data to a fragment
        // Dialog gets the DTO through the method call DP
        // While as mentioned before the fragment solution has an actual object of the DTO as a fragment class variable
        CustomBottomSheetFullDialogFragment bottomSheetFullDialogFragment = new CustomBottomSheetFullDialogFragment();
        bottomSheetFullDialogFragment.show(getSupportFragmentManager(), bottomSheetFullDialogFragment.getTag());

        click_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomBottomSheetFullDialogFragment bottomSheetFullDialogFragment = new CustomBottomSheetFullDialogFragment();
                bottomSheetFullDialogFragment.show(getSupportFragmentManager(), bottomSheetFullDialogFragment.getTag());
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
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("The activity serves as a showcase of a modal bottom sheet. \nMBS are an alternative to inline menus or simple dialogs on mobile and provide room for additional items, longer descriptions, and iconography.. \nThey must be dismissed in order to interact with the underlying content.\nThe fullscreen modal bottom sheet also showcases how to change the sheets layout depending on the state in which the sheet is currently.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
