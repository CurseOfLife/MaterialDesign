package com.example.materialdesign.activity.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.SongAdapter;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.helper.SwipeItemTouchHelper;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SwipeListGestureActivity extends AppCompatActivity {

    private List<SongDTO> songs;
    private RecyclerView song_recyclerView;
    private SongAdapter songAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list_gesture);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        song_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        song_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        song_recyclerView.setHasFixedSize(true);

        populate();

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, int pos) {

                Toast.makeText(SwipeListGestureActivity.this, obj.songtitle +   "Clicked", Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onItemLongClick(View view, SongDTO obj, int pos) {
            }
        });

        ItemTouchHelper.Callback callback = new SwipeItemTouchHelper(songAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(song_recyclerView);
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

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List Item Swipping");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                showInformationDialog("Swippable gesture implementation on a song item");
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
