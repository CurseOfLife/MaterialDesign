package com.example.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.materialdesign.R;
import com.example.materialdesign.model.GalleryImgEntity;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;

//staggered grid layout
//https://stackoverflow.com/questions/34216890/android-difference-between-gridlayout-and-staggered-gridlayout

public class GridListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<GalleryImgEntity> images = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //default view is item
    private final int IS_SECTION = 0;
    private final int IS_ITEM = 1;

    public interface OnItemClickListener {
        void onItemClick(View view, GalleryImgEntity obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GridListAdapter(Context context, List<GalleryImgEntity> images) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if (viewType == IS_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_image_item, parent, false);
            viewHolder = new GridListHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_section, parent, false);
            viewHolder = new GridSectionHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final GalleryImgEntity img_entity = images.get(position);

        if (holder instanceof GridListHolder) {
            GridListHolder view = (GridListHolder) holder;

            VariousTools.displayImage(context, view.image, img_entity.image);

            view.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, img_entity, position);
                    }
                }
            });

        } else {
            GridSectionHolder view = (GridSectionHolder) holder;
            view.section_title.setText(img_entity.title);
        }

        /*
         Grid View : Is a ViewGroup that displays items in a two-dimensional, scrollable grid. In this each Grid is of same size (Height and width). Grid View shows symmetric items in view

         Staggered Grid View : Is an extension to Grid View but in this each Grid is of varying size (Height and width). Staggered Grid View shows asymmetric items in view
         It supports both vertical and horizontal layouts.

        *more*
        Grid Layout (API Level 14): A layout that places its children in a rectangular grid.
        The number of rows and columns within the grid can be declared using the android:rowCount and android:columnCount properties.
        Typically, however, if the number of columns is declared the GridLayout will infer the number of rows based on the number of occupied cells making the use of the rowCount property unnecessary.
        Similarly, the orientation of the GridLayout may optionally be defined via the android:orientation property.

       StaggeredGridLayoutManager : It is one of the layout managers used in a Recyclerview.
       A LayoutManager that lays out children in a staggered grid formation.
       It supports horizontal & vertical layout as well as an ability to layout children in reverse.

       Staggered GridView : The StaggeredGridView allows the user to create a GridView with uneven rows similar to how Pinterest looks.
       Includes own OnItemClickListener and OnItemLongClickListener, selector, and fixed position restore.
       */

        if (img_entity.section) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        } else {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.images.get(position).section ? IS_SECTION : IS_ITEM;
    }

    public class GridListHolder extends RecyclerView.ViewHolder {
        public View parent;
        public ImageView image;

        public GridListHolder(View view) {
            super(view);
            parent = (View) view.findViewById(R.id.parent);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public static class GridSectionHolder extends RecyclerView.ViewHolder {
        public TextView section_title;

        public GridSectionHolder(View view) {
            super(view);
            section_title = (TextView) view.findViewById(R.id.section_title);
        }
    }
}
