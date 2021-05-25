package com.example.materialdesign.ToDo;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// https://stackoverflow.com/questions/49726385/listadapter-not-updating-item-in-recyclerview

public class NoteAdapter extends ListAdapter<NoteEntity, NoteAdapter.NoteHolder> {

    List<NoteEntity> original_list;

    private OnNoteItemClickListener listener;

    //DiffUtil for comparing the two arrays
    //when changes happen we don't want to collapse and retrieve the whole list
    //doing so could take longer time if the list is long
    //DiffUtil allows us to compare the prior and the new list entry
    //and to just add the changes
    private static final DiffUtil.ItemCallback<NoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            //id can be the same but
            //content of the two notes can be different
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            //if title, description, priority is the same return true
            //otherwise if any of the 3 variables had been changed
            //the adapter has to update the RecycleView
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    // constructor
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, final int position) {

        final NoteEntity fedNote = getItem(position);

        holder.txtv_title.setText(fedNote.getTitle());
        holder.txtv_priority.setText(String.valueOf(fedNote.getPriority())); //returns string from given int
        holder.txtv_description.setText(fedNote.getDescription());


        holder.note_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(holder.note_content.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.about_dialog);
                dialog.setCancelable(true);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ((ImageView)dialog.findViewById(R.id.img_logo)).setImageDrawable(ContextCompat.getDrawable(dialog.getContext(), R.drawable.notepad));


                ((TextView) dialog.findViewById(R.id.title)).setText(fedNote.getTitle());
                ((TextView) dialog.findViewById(R.id.version)).setVisibility(View.INVISIBLE);
                ((TextView) dialog.findViewById(R.id.content)).setText(fedNote.getDescription());
                ((TextView) dialog.findViewById(R.id.gotIt_text)).setText("CLOSE");


                ((View) dialog.findViewById(R.id.gotIt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    // in order to delete a note from the recycler view we need its position
    public NoteEntity getNotePosition(int required_note_position) {
        return getItem(required_note_position);
    }

    @Override
    public void submitList(@Nullable List<NoteEntity> list) {
        super.submitList(list);

      original_list = list;

    }
    //NOT WORKING TRY SORT AGAIN
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort_ASC (List<NoteEntity> notes){
        original_list.sort(new Comparator<NoteEntity>() {
            @Override
            public int compare(NoteEntity o1, NoteEntity o2) {
                return 0;
            }
        });
    }
    //region HOLDER

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView txtv_title;
        private TextView txtv_description;
        private TextView txtv_priority;
        private ImageButton note_content;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            txtv_title = itemView.findViewById(R.id.text_view_title);
            txtv_description = itemView.findViewById(R.id.text_view_description);
            txtv_priority = itemView.findViewById(R.id.text_view_priority);
            note_content = itemView.findViewById(R.id.note_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onNoteItemClick(getItem(position));
                    }
                }
            });
        }
    }
    //endregion HOLDER

    public void setOnNoteItemClickListener(OnNoteItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnNoteItemClickListener {

        void onNoteItemClick(NoteEntity noteEntity);
    }
}
