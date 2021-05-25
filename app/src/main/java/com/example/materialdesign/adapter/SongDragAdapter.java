package com.example.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.R;
import com.example.materialdesign.helper.DragItemTouchHelper;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongDragAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DragItemTouchHelper.DragHelperAdapter{

    private List<SongDTO> songs = new ArrayList<>();

    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnStartDragListener onStartDragListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, SongDTO obj, int position);
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDragListener(OnStartDragListener onStartDragListener) {
        this.onStartDragListener = onStartDragListener;
    }

    public SongDragAdapter(Context context, List<SongDTO> songs) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_album_draggable, parent, false);
        viewHolder = new SongDragViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof SongDragViewHolder) {

            final SongDragViewHolder viewHolder = (SongDragViewHolder) holder;
            final SongDTO song_item = songs.get(position);

            viewHolder.songtitle.setText(song_item.songtitle);
            viewHolder.artist.setText(song_item.artist);
            VariousTools.displayImage(context, viewHolder.image, song_item.image);


            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, songs.get(position), position);
                    }
                }
            });

            // Start a drag whenever the handle view it touched
            viewHolder.btn_drag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN && onStartDragListener != null) {
                        onStartDragListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(songs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class SongDragViewHolder extends RecyclerView.ViewHolder implements DragItemTouchHelper.DragTouchViewHolder {

        public View parent;

        public TextView songtitle;
        public TextView artist;
        public ImageView image;
        public ImageButton btn_drag;

        public SongDragViewHolder(View v) {
            super(v);

            parent = (View) itemView.findViewById(R.id.parent);

            songtitle = (TextView) itemView.findViewById(R.id.songtitle);
            artist = (TextView) itemView.findViewById(R.id.artist);
            image = (ImageView) itemView.findViewById(R.id.image);
            btn_drag = itemView.findViewById(R.id.btn_drag);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.grey_400));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
