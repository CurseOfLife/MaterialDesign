package com.example.materialdesign.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.materialdesign.R;
import com.example.materialdesign.model.TableOfContentsType;

import java.util.List;

public class MainActivityAdapter extends ExpandableRecyclerViewAdapter <MainActivityAdapter.ListItem>  {

    // https://stackoverflow.com/questions/35605313/focusing-on-a-recyclerview-item/41232962#41232962
    private Context context;
    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Constructor
    public MainActivityAdapter(Context context, List<ListItem> items, OnItemClickListener onItemClickListener) {
        super(context);
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        setItems(items);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == NORMAL) {
            return new SimpleViewHolder(inflate(R.layout.simple_main_menu_item, parent));
        } else if (viewType == TITLE) {
            return new TitleViewHolder(inflate(R.layout.filled_main_menu_item, parent));
        } else if (viewType == SUB_TITLE) {
            return new SubTitleViewHolder(inflate(R.layout.sub_main_menu_item, parent));
        }  else {
            return new SimpleViewHolder(inflate(R.layout.filled_main_menu_item, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == NORMAL) {
            ((SimpleViewHolder) holder).bind(position);
        } else if (viewType == TITLE) {
            ((ExpandableRecyclerViewAdapter.TitleViewHolder) holder).bind(position);
        } else if (viewType == SUB_TITLE) {
            ((SubTitleViewHolder) holder).bind(position);
        } else {
            ((SubTitleViewHolder) holder).bind(position);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////// HOLDER
    /**
     * Holder for menu item with icon and text
     */
    public class SimpleViewHolder extends ExpandableRecyclerViewAdapter.ViewHolder {
        View view;
        TextView name;
        ImageView icon;

        public SimpleViewHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.menu_item_name);
            icon = (ImageView) view.findViewById(R.id.menu_item_image);
        }

        public void bind(final int position) {
            name.setText(visibleItems.get(position).Text);
            icon.setImageResource(visibleItems.get(position).Icon);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, visibleItems.get(position).Id);
                }
            });
        }
    }

    /**
     * Holder for filled menu item with icon, text, arrow and new item circle
     */
    public class TitleViewHolder extends ExpandableRecyclerViewAdapter.TitleViewHolder {
        TextView name;
        ImageView icon;
        ImageView newItem;

        public TitleViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.filled_item_arrow));
            name = (TextView) view.findViewById(R.id.filled_menu_item_name);
            icon = (ImageView) view.findViewById(R.id.filled_menu_item_image);
            newItem = (ImageView) view.findViewById(R.id.filled_menu_new_item);
        }

        public void bind(final int position) {
            super.bind(position);
            name.setText(visibleItems.get(position).Text);
            icon.setImageResource(visibleItems.get(position).Icon);
            newItem.setVisibility(visibleItems.get(position).New ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * Holder for menu item with text, sub header and new item circle
     */
    public class SubTitleViewHolder extends ExpandableRecyclerViewAdapter.ViewHolder {
        View view;
        TextView name;
        ImageView newItem;

        public SubTitleViewHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.sub_menu_item_name);
            newItem = (ImageView) view.findViewById(R.id.sub_menu_sub_new_item);
        }

        public void bind(final int position) {
            name.setText(visibleItems.get(position).Text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, visibleItems.get(position).Id);
                }
            });
            newItem.setVisibility(visibleItems.get(position).New ? View.VISIBLE : View.INVISIBLE);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////// OnItemClickListener
    // we cant use the AdapterView on click as it doesn't know the position without our id provided
    public interface OnItemClickListener {
        void onItemClick(View view, int itemId);
    }
    ////////////////////////////////////////////////////////////////////////////////////// OnItemClickListener

    /**
     * RecyclerView Items..
     */
    ////////////////////////////////////////////////////////////////////////////////////// ITEM
    public static class ListItem extends ExpandableRecyclerViewAdapter.ListItem {
        public int Id = -1;
        public int Icon = -1;
        public String Text;
        public boolean New = false;

        public ListItem(int id, String title, int icon, TableOfContentsType type) {
            super(type.getValue());
            Id = id;
            Text = title;
            Icon = icon;
        }

        // Constructor when patching new items
        // if isNew is set then an icon appears to indicate that the component is newly added to the list
        public ListItem(int id, String title, int icon, boolean isNew, TableOfContentsType type) {
            super(type.getValue());
            Id = id;
            Text = title;
            Icon = icon;
            New = isNew;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////// ITEM
}
