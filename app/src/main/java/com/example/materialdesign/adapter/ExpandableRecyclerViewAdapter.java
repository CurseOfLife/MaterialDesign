package com.example.materialdesign.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.model.TableOfContentsType;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpandableRecyclerViewAdapter<T extends ExpandableRecyclerViewAdapter.ListItem>
        extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

    protected Context context;
    protected List<T> allItems = new ArrayList<>();
    protected List<T> visibleItems = new ArrayList<>();
    private List<Integer> indexList = new ArrayList<>();
    private SparseIntArray expandMap = new SparseIntArray();
    private int mode;

    public static final int NORMAL = TableOfContentsType.NORMAL.getValue();
    public static final int TITLE = TableOfContentsType.TITLE.getValue();
    public static final int SUB_TITLE = TableOfContentsType.SUB_TITLE.getValue();

    private static final int ARROW_ROTATION_DURATION = 500; //you can fiddle around with this number .. to test it out.. i was satisfied with this speed

    public static final int EXPANDED = 1; //normal is 0

    // Constructor
    public ExpandableRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * Resolving generics
     */
    //region class ListItem
    public static class ListItem {
        public int mItemType;

        public ListItem(int itemType) {
            mItemType = itemType;
        }
    }
    //endregion

    /**
     * @param i id
     * @return recycler view position
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * @return null if there are no items else returns the size
     */
    @Override
    public int getItemCount() {
        return visibleItems == null ? 0 : visibleItems.size();
    }

    /**
     * @param id        resource view id
     * @param viewGroup viewGroup
     * @return inflating  item in the RecyclerView
     */
    protected View inflate(int id, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(id, viewGroup, false);
    }

    //region class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
    //endregion class ViewHolder

    //region class TitleViewHolder
    public class TitleViewHolder extends ViewHolder {
        ImageView arrow;

        public TitleViewHolder(View view, final ImageView arrow) {
            super(view);
            this.arrow = arrow;

            // when arrow is clicked the View is expanded and the arrow itself turns
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleClick();
                }
            });
        }

        protected void handleClick() {
            if (toggleExpandedItems(getLayoutPosition(), false)) {
                openArrow(arrow);
            } else {
                closeArrow(arrow);
            }
            // refresh item Id
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 1500);
        }

        public void bind(int position) {
            arrow.setRotation(isExpanded(position) ? 180 : 0);
        }
    }
    //endregion class TitleViewHolder

    //region TOGGLE ITEMS

    /**
     * expanding and collapsing items
     */
    public boolean toggleExpandedItems(int position, boolean notify) {
        if (isExpanded(position)) {
            collapseItems(position, notify);
            return false;
        } else {
            expandItems(position, notify);

            if (mode == EXPANDED) {
                collapseAllExcept(position);
            }
            return true;
        }
    }

    public void expandItems(int position, boolean notify) {
        int count = 0;
        int index = indexList.get(position);
        int insert = position;

        for (int i = index + 1; i < allItems.size(); i++) {
            int type = allItems.get(i).mItemType;

            if (type != TITLE && type != NORMAL) {
                insert++;
                count++;
                visibleItems.add(insert, allItems.get(i));
                indexList.add(insert, i);
            } else {
                break;
            }
        }

        notifyItemRangeInserted(position + 1, count);

        int allItemsPosition = indexList.get(position);
        expandMap.put(allItemsPosition, 1);

        if (notify) {
            notifyItemChanged(position);
        }
    }

    public void collapseItems(int position, boolean notify) {
        int count = 0;
        int index = indexList.get(position);

        for (int i = index + 1; i < allItems.size(); i++) {
            int type = allItems.get(i).mItemType;

            if (type != TITLE && type != NORMAL) {
                count++;
                visibleItems.remove(position + 1);
                indexList.remove(position + 1);
            } else {
                break;
            }
        }

        notifyItemRangeRemoved(position + 1, count);

        int allItemsPosition = indexList.get(position);
        expandMap.delete(allItemsPosition);

        if (notify) {
            notifyItemChanged(position);
        }
    }

    /**
     * Collapsing all items under a child besides the param
     *
     * @param position of the item that stays visible, all other get collapsed
     */
    public void collapseAllExcept(int position) {
        for (int i = visibleItems.size() - 1; i >= 0; i--) {
            int type = getItemViewType(i);
            if (i != position && (type == TITLE || type == NORMAL)) {
                if (isExpanded(i)) {
                    collapseItems(i, true);
                }
            }
        }
    }

    //endregion TOGGLE ITEMS

    /**
     * @param position desired title we want to check if its expanded or not
     * @return true or false if the Title is expanded
     */
    protected boolean isExpanded(int position) {
        int allItemsPosition = indexList.get(position);
        return expandMap.get(allItemsPosition, -1) >= 0;
    }

    @Override
    public int getItemViewType(int position) {
        return visibleItems.get(position).mItemType;
    }

    public void setItems(List<T> items) {
        allItems = items;
        List<T> visibleItems = new ArrayList<>();
        expandMap.clear();
        indexList.clear();

        for (int i = 0; i < items.size(); i++) {
            int type = allItems.get(i).mItemType;

            if (type == TITLE || type == NORMAL) {
                indexList.add(i);
                visibleItems.add(items.get(i));
            }
        }
        this.visibleItems = visibleItems;
        notifyDataSetChanged();
    }



    /**
     * Rotate the arrow 180*  when item is expanded
     *
     * @param view arrow view
     */
    public static void openArrow(View view) {
        view.animate().setDuration(ARROW_ROTATION_DURATION).rotation(180);
    }

    /**
     * Rotate the arrow to 0* when item is closed
     *
     * @param view arrow view
     */
    public static void closeArrow(View view) {
        view.animate().setDuration(ARROW_ROTATION_DURATION).rotation(0);
    }

    public int getMode() {
        return mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
}
