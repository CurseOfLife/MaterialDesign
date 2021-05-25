package com.example.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.R;
import com.example.materialdesign.model.CarEntity;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemPopupMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CarEntity> cars = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;

    public SimpleItemPopupMenuAdapter(Context context, List<CarEntity> cars) {
        this.cars = cars;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_with_popup_menu, parent, false);
        viewHolder = new CarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof CarViewHolder) {
            CarViewHolder viewHolder = (CarViewHolder) holder;

            final CarEntity carEntity = cars.get(position);

            viewHolder.carname.setText(carEntity.name);


            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(view, carEntity, position);
                }
            });

            viewHolder.more_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMoreButtonClickListener == null) return;
                    onMoreButtonClick(view, carEntity);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    private void onMoreButtonClick(final View view, final CarEntity car) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onMoreButtonClickListener.onItemClick(view, car, menuItem);
                return true;
            }
        });
        popupMenu.inflate(R.menu.car_popup_menu);
        popupMenu.show();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {

        public View parent;
        public TextView carname;
        public ImageButton more_menu;


        public CarViewHolder(View v) {
            super(v);

            parent = (View) v.findViewById(R.id.parent);
            carname = (TextView) v.findViewById(R.id.carname);
            more_menu = (ImageButton) v.findViewById(R.id.more_menu);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, CarEntity obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, CarEntity obj, MenuItem item);
    }
}
