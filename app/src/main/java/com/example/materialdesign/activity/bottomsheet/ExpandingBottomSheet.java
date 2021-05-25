package com.example.materialdesign.activity.bottomsheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.view.ActionMode;


/*
android.view.ActionMode - what you need to import.
        android.support.v7.view.ActionMode - what you have imported.*/


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.SongAdapter;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// EBS provide a small, collapsed surface that can be expanded by the user to access a key feature or task.
// They offer the persistent access of a standard sheet with the space and focus of a modal sheet.
// FAB shouldn't be used for this
public class ExpandingBottomSheet extends AppCompatActivity {

    /* I was unable to find any examples of EBS in use, which doesn't mean that there are none
       This example is my attempt of solving the problem
       it showcases how two things:

        1. the first, related to the topic, is how to permit the user from moving/dragging the bottom sheet
           the following discussion thread helps in figuring that out

            https://stackoverflow.com/questions/35794264/disabling-user-dragging-on-bottomsheet

        2. the second, unrelated to the topic but shown in the documentation - selecting multiple rows from the recycler view/list and doing an action
           for the purpose of this class we will just count the selected rows


          note - if there is time i will do some other functionaries, I don't plan spending more than one day on this (debugging taken into the calculation)
          note - if i come back and update this class the starting tutorial menu will be updated accordingly

       Action Mode - https://stackoverflow.com/questions/49072285/implementation-of-actionmode-callback
                     https://medium.com/over-engineering/using-androids-actionmode-e903181f2ee3

                     in style have to use
                     <item name="windowActionModeOverlay">true</item>
     */

    private View parent;
    private LinearLayout bottomSheet;
    private LinearLayout support_parent;
    private TextView support_text;

    private List<SongDTO> songs;
    private RecyclerView song_recyclerView;
    private SongAdapter songAdapter;

    private ActionMode actionMode;
    private ActionModeCallBack actionModeCallback;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanding_bottom_sheet);

        parent = findViewById(R.id.parent_coordinator);

        initToolbar();
        initComponents();
        Toast.makeText(this, "Hold a row from the list for additional functionality", Toast.LENGTH_LONG).show();
    }

    private void initToolbar() {

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.simple_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expanding Bottom Sheet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        else {
            showInformationDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    private void initComponents() {

        support_parent = findViewById(R.id.support_parent);
        support_text = findViewById(R.id.txt_support);

        song_recyclerView = findViewById(R.id.song_recyclerView); //recycler view
        song_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        song_recyclerView.setHasFixedSize(true);

        populate();

        bottomSheet = findViewById(R.id.standard_bottom_sheet); //bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(-112);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); //setting the state of the sheet as collapsed

        //handling clicks on the bottom sheet
        support_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        // callback for do something
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        support_text.setText("CLOSE"); //changing the text of the visible small sheet hint when expanded
                        //  support_text.setTypeface(support_text.getTypeface(), Typeface.BOLD);
                        support_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close, 0, 0, 0); // changing the icon of the visible small sheet hint
                        getSupportActionBar().setTitle("Can you drag the sheet?");

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                        support_text.setText("EXAMPLE");
                        support_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_message_white_24dp, 0, 0, 0);
                        getSupportActionBar().setTitle("Expanding Bottom Sheet");
                    }

                    break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                // By calling the toast, the toast will run a long time since the scroll goes up from 0 to 1 or -1 to 1; for each pixel the toast is called
                // Toast.makeText(getApplicationContext(), "onStateChanged() is called. in onSlide()", Toast.LENGTH_SHORT).show();
            }
        });


        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, int pos) {

                if (songAdapter.getSelectedSongCount() > 0) {
                    enableActionMode(pos);
                } else {
                    // read the inbox which removes bold from the row
                    SongDTO songDTO = songAdapter.getSongItem(pos);

                    Toast.makeText(getApplicationContext(), "You've Clicked On" + songDTO.songtitle, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, SongDTO obj, int pos) {

                enableActionMode(pos); // on long click the items are selected
            }
        });

        actionModeCallback = new ActionModeCallBack();
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
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("Expanding bottom sheet dialogs provide a small, collapsed surface that can be expanded by the user to access a key feature or task.\n They offer the persistent access of a standard sheet with the space and focus of a modal sheet.\nFAB shouldn't be used for this as it's function is to be used as a button for one usually main function of the screen.\n\n Multi-selection list implemented on long button click.");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void populate() {

        songs = new ArrayList<>();
        songs.add(new SongDTO("Song Title 1", "Artist 1", R.drawable.image_1));
        songs.add(new SongDTO("Song Title 2", "Artist 2", R.drawable.image_2));
        songs.add(new SongDTO("Song Title 3", "Artist 3", R.drawable.image_3));
        songs.add(new SongDTO("Song Title 4", "Artist 4", R.drawable.image_4));
        songs.add(new SongDTO("Song Title 5", "Artist 5", R.drawable.image_5));
        songs.add(new SongDTO("Song Title 6", "Artist 6", R.drawable.image_6));
        songs.add(new SongDTO("Song Title 7", "Artist 7", R.drawable.image_7));
        songs.add(new SongDTO("Song Title 8", "Artist 8", R.drawable.image_8));
        songs.add(new SongDTO("Song Title 9", "Artist 9", R.drawable.image_9));
        songs.add(new SongDTO("Song Title 10", "Artist 10", R.drawable.image_10));
        songs.add(new SongDTO("Song Title 1", "Artist 1", R.drawable.image_1));
        songs.add(new SongDTO("Song Title 2", "Artist 2", R.drawable.image_2));
        songs.add(new SongDTO("Song Title 3", "Artist 3", R.drawable.image_3));
        songs.add(new SongDTO("Song Title 4", "Artist 4", R.drawable.image_4));

        songAdapter = new SongAdapter(songs, this);
        song_recyclerView.setAdapter(songAdapter);
    }

    //enabling action bode when item long click
    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    // making selections
    private void toggleSelection(int position) {
        songAdapter.toggleSelection(position);

        int count = songAdapter.getSelectedSongCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    //region Action Mode Class
    // styles changed for toolbar background
    private class ActionModeCallBack implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            VariousTools.setSystemBarColor(ExpandingBottomSheet.this, R.color.colorAccentDark);

            mode.getMenuInflater().inflate(R.menu.menu_delete_multiple_selection, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int itemId = item.getItemId();

            if (itemId == R.id.delete) {

                deleteSelectedSongs();

                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            songAdapter.clearSelections();
            actionMode = null;

            VariousTools.setSystemBarColor(ExpandingBottomSheet.this, R.color.colorPrimaryDark);
        }
    }

    private void deleteSelectedSongs() {

        List<Integer> selectedItemPositions = songAdapter.getSelectedSongs();

        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            songAdapter.removeData(selectedItemPositions.get(i));
        }

        runOnUiThread(new Runnable() {
            public void run() {
                songAdapter.notifyDataSetChanged();
            }
        });
    }
    //endregion
}
