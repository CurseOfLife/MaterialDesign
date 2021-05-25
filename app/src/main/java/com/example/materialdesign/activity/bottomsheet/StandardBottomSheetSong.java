package com.example.materialdesign.activity.bottomsheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.adapter.SongAdapter;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StandardBottomSheetSong extends AppCompatActivity {

    boolean shouldExecuteOnResume;

    private View home_bar;
    private TextView textViewSearch;
    private boolean isHomeBarHidden = false;

    private View parent;
    private LinearLayout bottom_sheet_song;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<SongDTO> songs;
    private RecyclerView song_recyclerView;
    private SongAdapter songAdapter;

    //Music
    private ImageButton btn_play;
    private ImageButton btn_expand;
    private ProgressBar song_progressbar;
    private MediaPlayer mediaPlayer;
    // Handler used to update the progress bar
    private Handler time_handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_bottom_sheet_song);

        shouldExecuteOnResume = false;
        initComponents();
    }

    private void initComponents() {

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        parent = findViewById(R.id.parent);
        home_bar = findViewById(R.id.home_bar);
        textViewSearch = findViewById(R.id.txtView_search);
        song_recyclerView = findViewById(R.id.rv_song);
        btn_play =  findViewById(R.id.btn_play);
        song_progressbar = findViewById(R.id.song_progressbar);
        bottom_sheet_song = findViewById(R.id.standard_bottom_sheet_song);
        btn_expand =findViewById(R.id.btn_expand_song);

        textViewSearch.setText("Standard Song Sheet");

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_song);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        song_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        song_recyclerView.setHasFixedSize(true);

        // callback for do something
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

        // On parent item clicked
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, int pos) {

                Toast.makeText(StandardBottomSheetSong.this, "Song"+ " \""+obj.songtitle +"\" "+"Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, SongDTO obj, int pos) {
            }
        });

        // On more button menu popup
        songAdapter.setOnMoreButtonClickListener(new SongAdapter.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, SongDTO obj, MenuItem menuItem) {

                Toast.makeText(StandardBottomSheetSong.this, "\"" + obj.songtitle +"\" "+ menuItem.getTitle()+" "+ "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            song_recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY < oldScrollY) { // up
                        animateHomeBar(false);

                    }
                    if (scrollY > oldScrollY) { // down
                        animateHomeBar(true);

                    }
                }
            });
        }

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

        btn_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean showHiddenView = toggleArrow(v);

                if (showHiddenView) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Toast.makeText(StandardBottomSheetSong.this, "Finished expanding", Toast.LENGTH_SHORT).show();
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        // Media Player
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                btn_play.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            }
        });

        try {
            //https:stackoverflow.com/questions/3289038/play-audio-file-from-the-assets-directory
            //https://stackoverflow.com/questions/16795600/can-you-play-a-mp3-file-from-the-assets-folder
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("Tale.mp3");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();

            mediaPlayer.prepare();
        } catch (Exception e) {
            Toast.makeText(this, "For what ever reason this song won't play", Toast.LENGTH_SHORT).show();
        }

        playMusic();
        //endregion
    }

    private void playMusic() {
        //https://www.youtube.com/watch?v=HB3DoZh1QWU

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // check for already playing
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    // Changing button drawable to play
                    btn_play.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                } else {
                    song_progressbar.setMax(mediaPlayer.getDuration());
                    // Resuming the song
                    mediaPlayer.start();
                    // Changing button drawable to paused
                    btn_play.setImageResource(R.drawable.ic_pause_white_24dp);

                    if (mediaPlayer.isPlaying()){
                        //Running the time_handler in a background thread
                        time_handler.post(updateTimer);
                    }
                }
            }
        });
    }

    //fixing the song continuing when app turned off
    @Override
    protected void onPause() {
        super.onPause();

        mediaPlayer.pause();
        btn_play.setImageResource(R.drawable.ic_play_arrow_white_24dp);
    }

    // Stopping the music when activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mHandler.removeCallbacks(mUpdateTimeTask);
        mediaPlayer.release();
        time_handler.removeCallbacks(updateTimer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(shouldExecuteOnResume){
            mediaPlayer.start();
            btn_play.setImageResource(R.drawable.ic_pause_white_24dp);
            Toast.makeText(this, "Music Playing", Toast.LENGTH_SHORT).show();

        } else{
            shouldExecuteOnResume = true;
        }
    }



    /**
     * Background Runnable thread
     */
    private Runnable updateTimer = new Runnable() {
        public void run() {
            updateTimer();

            // Running this thread after 10 milliseconds
            if (mediaPlayer.isPlaying()) {
                time_handler.postDelayed(this, 100);
            }
        }
    };

    private void updateTimer() {
       song_progressbar.setProgress(mediaPlayer.getCurrentPosition());
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
        ((TextView)dialog.findViewById(R.id.txtInfo)).setText("Activity contents are static placeholder items. \n Only the \"Search\" text is changed. \n\n Additionally parallax effect \n and \"Music player for one song\" \n has been implemented. \nNote SeekBar would be a better solution on some Activities - because of the visible \"thumb\" ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void animateHomeBar(final boolean hide) {
        if (isHomeBarHidden && hide || !isHomeBarHidden && !hide) return;
        isHomeBarHidden = hide;
        int moveY = hide ? -(2 * home_bar.getHeight()) : 0;
        home_bar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
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
}
