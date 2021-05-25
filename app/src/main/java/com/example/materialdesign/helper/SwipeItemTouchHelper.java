package com.example.materialdesign.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

//https://github.com/iPaulPro/Android-ItemTouchHelper-Demo/blob/master/app/src/main/java/co/paulburke/android/itemtouchhelperdemo/helper/SimpleItemTouchHelperCallback.java

public class SwipeItemTouchHelper extends ItemTouchHelper.Callback {

    public static final float ALPHA_FULL = 1.0f;
    private int backgroundColorCode = Color.LTGRAY;

    private final SwipeHelperAdapter adapter;

    public SwipeItemTouchHelper(SwipeHelperAdapter adapter) {
       this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        // Setting movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Notifying  the adapter of the dismissal
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;
            ColorDrawable background = new ColorDrawable();
            background.setColor(getBackgroundColorCode());

            if (dX > 0) { // swipe right
                background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
            } else { // swipe left
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            }
            background.draw(c);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof TouchViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                TouchViewHolder itemViewHolder = (TouchViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);

        if (viewHolder instanceof TouchViewHolder) {
            // Tell the view holder it's time to restore the idle state
            TouchViewHolder itemViewHolder = (TouchViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }

    public interface SwipeHelperAdapter {
        void onItemDismiss(int position);
    }

    public interface TouchViewHolder {
        void onItemSelected();
        void onItemClear();
    }

    public int getBackgroundColorCode() {
        return backgroundColorCode;
    }
}
