package com.example.materialdesign.activity.bottomsheet;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;

import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

// SBS displays content that complements the screen’s primary content. They remain visible while users interact with the primary content.
public class StandardBottomSheet extends AppCompatActivity {

    private TextView textViewSearch;
    private LinearLayout bottomSheet;
    private ImageButton btn_expand_schedule;
    private View layout_expanded_schedule;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_bottom_sheet);

        initComponents();
    }

    private void initComponents() {
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        textViewSearch = findViewById(R.id.txtView_search);
        bottomSheet = findViewById(R.id.standard_bottom_sheet);
        btn_expand_schedule = findViewById(R.id.btn_toggle_schedule);
        fab = findViewById(R.id.fab_standard_bottom_sheet);

        layout_expanded_schedule = findViewById(R.id.expanded_schedule);

        textViewSearch.setText("Material Maps");

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // callback
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        textViewSearch.setText("Fully expanded. Close the sheet.");

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        textViewSearch.setText("Collapsed sheet. Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        textViewSearch.setText("Dragging Sheet");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        textViewSearch.setText("Settled Sheet");
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        textViewSearch.setText("Half Expanded Sheet");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                // By calling the toast, the toast will run a long time since the scroll goes up from 0 to 1 or -1 to 1; for each pixel the toast is called
                // Toast.makeText(getApplicationContext(), "onStateChanged() is called. in onSlide()", Toast.LENGTH_SHORT).show();
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

        btn_expand_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandView(view, layout_expanded_schedule);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    // button is injected by the view
    // expandView(view, layout_expanded_schedule);
    private void expandView(View button, View layout) {
        boolean showHiddenView = toggleArrow(button);

        if (showHiddenView) {
            VariousTools.expand(layout, new VariousTools.iOnAnimationFinished() {
                @Override
                public void onFinish() {
                    Toast.makeText(StandardBottomSheet.this, "Finished expanding", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            VariousTools.collapse(layout);
        }
    }

    //animating the arrow up and down
    public boolean toggleArrow(View view) {

        if (view.getRotation() == 0) {
            view.animate().setDuration(100).rotation(180);
            return true;
        } else {
            view.animate().setDuration(100).rotation(0);
            return false;
        }
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
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("Activity contents are static placeholder items. \n\n The activity serves as a showcase of a standard bottom sheet which displays content that complement the screen’s primary content. \n The sheet remains visible while users interact with the primary content. ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
