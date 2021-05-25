package com.example.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.R;
import com.example.materialdesign.helper.DragItemTouchHelper;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.helper.SwipeItemTouchHelper;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeItemTouchHelper.SwipeHelperAdapter {

    /*
        Multiple item selection https://www.youtube.com/watch?v=oS69I96gjn0
                                https://github.com/edwinab1/recycler-multiselect/blob/master/app/src/main/java/mobi/com/multiselect/Adapters/AdapterListInbox.java
                                https://medium.com/@droidbyme/android-recyclerview-with-single-and-multiple-selection-5d50c0c4c739
                                https://stackoverflow.com/questions/36369913/how-to-implement-multi-select-in-recyclerview
                                https://developer.android.com/reference/android/util/SparseBooleanArray mapping
                                https://stackoverflow.com/questions/18822708/what-is-the-clear-purpose-of-sparsebooleanarray-i-referred-official-android
     */

    private List<SongDTO> songs;
    private List<SongDTO> items_swiped = new ArrayList<>();

    private Context context_item;
    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;


    /*  Currently as I'm writing this I've pondered upon the task at hand for up to 10min.
        Namely, I've spent that time trying to decide which path to take when solving the multiple items checked issue.
        In short - do I just change the image each time the item is selected by just changing the image from the same view,
        or do I use two views on top of each other - show/gone the one I need.
        After asking a senior developer gave me yet another solution "how about you use one parent vertically oriented and two children within,
        one of them being set to gone".

        Another idea is to  remove the expanding menu view in each child,
        reason being - if items are selected I don't think we really need to give that option,
        and it would be nice if the items still kept their individual img.
        If i forget to do it - shame on me.
     */

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int current_checked_position = -1;
    private SparseBooleanArray selected_songs;

    //region Constructor
    public SongAdapter(List<SongDTO> songs, Context context) {
        this.songs = songs;
        // menu popup wont work if this.context = context
        context_item = context;
        // obj for multiple selection
        selected_songs = new SparseBooleanArray();
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }
    //endregion

    //region Override
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SongViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_album, parent, false);
        viewHolder = new SongViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof SongViewHolder) {
           final SongViewHolder viewHolder = (SongViewHolder) holder;

            final SongDTO song_item = songs.get(position);

            viewHolder.songtitle.setText(song_item.songtitle);
            viewHolder.artist.setText(song_item.artist);

            viewHolder.image_letter.setText(song_item.songtitle.substring(0, 1));

            // moved down into toggle image because of the multiple selection which was done on a later date
            // VariousTools.displayImage(context_item, viewHolder.image, song_item.image);

            viewHolder.parent.setActivated(selected_songs.get(position, false));

            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, song_item, position);
                    }
                }
            });

            viewHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMoreButtonClickListener == null) return;
                    onMoreImgButtonClick(view, song_item);
                }
            });

            viewHolder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (onItemClickListener == null) return false;

                    onItemClickListener.onItemLongClick(view, song_item, position);
                    return true;
                }
            });

            viewHolder.btn_undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songs.get(position).swiped = false;
                    items_swiped.remove(songs.get(position));
                    notifyItemChanged(position);
                }
            });

            if (song_item.swiped) {
                viewHolder.parent.setVisibility(View.GONE);
            } else {
                viewHolder.parent.setVisibility(View.VISIBLE);
            }

            displayImage(viewHolder, song_item);
            toggleImg(viewHolder, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (SongDTO song : items_swiped) {
                    int song_at_index_removed = songs.indexOf(song);
                    if (song_at_index_removed != -1) {
                        songs.remove(song_at_index_removed);
                        notifyItemRemoved(song_at_index_removed);
                    }
                }
                items_swiped.clear();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    //endregion

    //region Functions
    private void onMoreImgButtonClick(final View view, final SongDTO song_item) {
        PopupMenu popupSongMenu = new PopupMenu(context_item, view);

        popupSongMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onMoreButtonClickListener.onItemClick(view, song_item, menuItem);
                return true;
            }
        });
        popupSongMenu.inflate(R.menu.menu_song_popup);
        popupSongMenu.show();
    }

    @Override
    public void onItemDismiss(int position) {

        // handle when double swipe
        if (songs.get(position).swiped) {
            items_swiped.remove(songs.get(position));
            songs.remove(position);
            notifyItemRemoved(position);
            return;
        }

        songs.get(position).swiped = true;
        items_swiped.add(songs.get(position));
        notifyItemChanged(position);
    }

    public SongDTO getSongById(int position) {
        return songs.get(position);
    }

    // toggling the image
    private void toggleImg(SongViewHolder holder, int position) {

        if (selected_songs.get(position, false)) {
            holder.img_parent.setVisibility(View.GONE);
            holder.checked.setVisibility(View.VISIBLE);
            if (current_checked_position == position) resetCurrentCheckedPosition();
        } else {
            holder.checked.setVisibility(View.GONE);
            holder.img_parent.setVisibility(View.VISIBLE);
            if (current_checked_position == position) resetCurrentCheckedPosition();
        }
    }

    private void displayImage(SongViewHolder holder, SongDTO song_item) {
        // song item has to be Integer
        // it was int so caused error
        if (song_item.image != null) {
            VariousTools.displayImage(context_item, holder.image, song_item.image);
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.circle);
            holder.image.setColorFilter(song_item.color);
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }

    private void resetCurrentCheckedPosition() {
        current_checked_position = -1;
    }

    // number of selected songs
    public int getSelectedSongCount() {
        return selected_songs.size();
    }

    // DEBUGGED
    // adding songs.clear() instead of selected_songs will cause the whole list to be deleted after the view has returned to normal mode aka not selection mode
    public void clearSelections() {
        selected_songs.clear();
        notifyDataSetChanged();
    }

    public SongDTO getSongItem(int position) {
        return songs.get(position);
    }

    public void toggleSelection(int position) {
        current_checked_position = position;

        if (selected_songs.get(position, false)) {
            selected_songs.delete(position);
        } else {
            selected_songs.put(position, true);
        }
        notifyItemChanged(position);
    }

    public List<Integer> getSelectedSongs() {
        List<Integer> songs = new ArrayList<>(selected_songs.size());

        for (int i = 0; i < selected_songs.size(); i++) {
            songs.add(selected_songs.keyAt(i));
        }
        return songs;
    }

    public void removeData(int position) {
        songs.remove(position);
        resetCurrentCheckedPosition();
    }
    //endregion

    //region Song View Holder
    public class SongViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {

        public View parent;
        public RelativeLayout checked;
        public RelativeLayout img_parent;
        public TextView image_letter;
        public TextView songtitle;
        public TextView artist;
        public ImageView image;
        public ImageButton more;
        public ImageButton btn_swipe;
        public MaterialButton btn_undo;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = (View) itemView.findViewById(R.id.parent);

            songtitle = (TextView) itemView.findViewById(R.id.songtitle);
            artist = (TextView) itemView.findViewById(R.id.artist);
            image = (ImageView) itemView.findViewById(R.id.image);
            more = (ImageButton) itemView.findViewById(R.id.more_menu);

            checked = (RelativeLayout) itemView.findViewById(R.id.checked_item_img);
            img_parent = (RelativeLayout) itemView.findViewById(R.id.image_parent);
            image_letter = (TextView) itemView.findViewById(R.id.image_letter);

            btn_swipe = itemView.findViewById(R.id.btn_swipe);
            btn_undo = itemView.findViewById(R.id.btn_undo);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context_item.getResources().getColor(R.color.grey_400));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
    //endregion

    //region Interfaces
    public interface OnItemClickListener {

        void onItemClick(View view, SongDTO obj, int pos);
        void onItemLongClick(View view, SongDTO obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, SongDTO obj, MenuItem menuItem);
    }
    //endregion
}
