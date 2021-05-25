package com.example.materialdesign.activity.bottomsheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.SongAdapter;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModalDialogBottomSheet extends AppCompatActivity {

    private TextView textViewSearch;

    private List<SongDTO> songs;
    private RecyclerView song_recyclerView;
    private SongAdapter songAdapter;

    private FrameLayout bottom_sheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_dialog_bottom_sheet);

        initComponents();

        // showing the first dialog so the user of the app knows what has been implemented
        // the getter returns the object SongDTO at the position
        // convenient way of getting the object outside of the on click listener
        // this method is only called to showcase the implementation
        // In a real case scenario it wouldn't be needed
        showBottomSheet(songAdapter.getSongById(0));
    }

    private void initComponents() {
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        textViewSearch = findViewById(R.id.txtView_search);
        song_recyclerView = findViewById(R.id.song_recyclerView);
        bottom_sheet = findViewById(R.id.bottom_sheet);

        textViewSearch.setText("Modal DialogBottomSheet");
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);

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

        song_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        song_recyclerView.setHasFixedSize(true);

        populate();

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, int pos) {

                showBottomSheet(obj);
            }

            @Override
            public void onItemLongClick(View view, SongDTO obj, int pos) {
                //empty
            }
        });

        // On more button menu popup
        songAdapter.setOnMoreButtonClickListener(new SongAdapter.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, MenuItem menuItem) {

                Toast.makeText(ModalDialogBottomSheet.this, "\"" + obj.songtitle +"\" "+ menuItem.getTitle()+" "+ "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populate() {

        songs = new ArrayList<>();
        songs.add(new SongDTO("Song Title 1","Artist 1", R.drawable.image_1));
        songs.add(new SongDTO("Song Title 2","Artist 2", R.drawable.image_2));
        songs.add(new SongDTO("Song Title 3","Artist 3", R.drawable.image_3));
        songs.add(new SongDTO("Song Title 4","Artist 4", R.drawable.image_4));
        songs.add(new SongDTO("Song Title 5","Artist 5", R.drawable.image_5));
        songs.add(new SongDTO("Song Title 6","Artist 6", R.drawable.image_6));
        songs.add(new SongDTO("Song Title 7","Artist 7", R.drawable.image_7));
        songs.add(new SongDTO("Song Title 8","Artist 8", R.drawable.image_8));
        songs.add(new SongDTO("Song Title 9","Artist 9", R.drawable.image_9));
        songs.add(new SongDTO("Song Title 10","Artist 10", R.drawable.image_10));
        songs.add(new SongDTO("Song Title 1","Artist 1", R.drawable.image_1));
        songs.add(new SongDTO("Song Title 2","Artist 2", R.drawable.image_2));
        songs.add(new SongDTO("Song Title 3","Artist 3", R.drawable.image_3));
        songs.add(new SongDTO("Song Title 4","Artist 4", R.drawable.image_4));

        songAdapter = new SongAdapter(songs, this);
        song_recyclerView.setAdapter(songAdapter);
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
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("The activity showcases a modal bottom sheet which is created with the help of a Dialog fragment. \n Unlike other examples this one contains a FrameLayout. \n It serves as a container for our fragments.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showBottomSheet(final SongDTO obj) {

        //checking just to make sure if the sheet is already expanded
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.rate_music_dialog,null) ;
        ((TextView) view.findViewById(R.id.song_title)).setText(obj.songtitle);
        ((TextView) view.findViewById(R.id.artist)).setText(obj.artist);

        // creating the dialog
        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        // setting the background view transparent DIDN'T WORK
        // solved with styles

        // otherwise white background will surround the card view if its not transparent
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(view);

        // unlike the normal dialog which we usually dismiss
        // we just hide the sheet in this case (it's not dismissed)
        // to showcase the following

        // WARNING !!!  Using hide() could cause a Leaked Window Error.
        // If you choose to use hide() and you exit your application using finish(),
        // this action will cause an error message  about a window being leaked.
        // So, either dismiss() your dialogs properly before calling finish() or just use dismiss() instead of hide().

        // hide() is used when it takes a longer time to create the dialog

        // in this example I will be showing how to take precautions and solve the issue
        (view.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
            }
        });

        (view.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Submit Rating", Toast.LENGTH_SHORT).show();

                // Gather  android:id="@+id/rating"
                // Submit or store depending on the requirements
            }
        });

        bottomSheetDialog.show();

        // we can do it like this manually or on destroy
        // both shown
        // if my research was correct
        // in the first we garbage collect ourselves
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
    }

    /** after testing the thing crashes the app
    // Further testing needed to see if my research was correct
    @Override
    protected void onDestroy() {
        super.onDestroy();

        bottomSheetDialog.dismiss();

    }
    **/
}
