package com.example.materialdesign.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

// https://medium.com/better-programming/bottom-sheet-android-340703e114d2
public class CustomBottomSheetFullDialogFragment extends BottomSheetDialogFragment {

    private View view1;
    private View empty_view;
    private AppBarLayout toolbar;

    private TextView textViewSearch;
    private ImageButton btn_exit;
    private Button textBtnOne;
    private Button textBtnTwo;
    private BottomSheetBehavior bottomSheetBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        final View view = View.inflate(getContext(), R.layout.fragment_custom_bottom_sheet_full_dialog, null);

        //setting layout with bottom sheet
        bottomSheetDialog.setContentView(view);

        //setting behavior of the view
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        //setting peek hight
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        view1 = view.findViewById(R.id.view);
        empty_view = view.findViewById(R.id.empty_view);
        toolbar = view.findViewById(R.id.app_bar);
        textViewSearch =view.findViewById(R.id.txtView_search);
        btn_exit = view.findViewById(R.id.btn_close);
        textBtnOne = view.findViewById(R.id.btn_one);
        textBtnTwo = view.findViewById(R.id.btn_two);

        view1.setVisibility(View.INVISIBLE);
        textViewSearch.setText("Test 1");


        empty_view.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels)/2);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        dismiss();
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED: {
                        showView(toolbar, getActionBarSize());
                        hideView(textBtnOne);
                        //hideView(textBtnTwo);
                    }
                    break;

                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        hideView(toolbar);
                        showView(textBtnOne, getActionBarSize());
                      //  showView(textBtnTwo, getActionBarSize());

                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) { }
        });

        hideView(toolbar);

        btn_exit.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        btn_exit .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btn_more_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog();
            }
        });

        return bottomSheetDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void showInformationDialog() {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_information_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Version " + BuildConfig.VERSION_NAME);
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("All elements are static.\n Dismiss and info button are only functional elements, besides the transitions.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private void hideView(View toolbar) {
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        params.height = 0;
        toolbar.setLayoutParams(params);
    }

    //important to set the toolbar the size of an action bar
    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) styledAttributes.getDimension(0, 0);
        return size;
    }
}
