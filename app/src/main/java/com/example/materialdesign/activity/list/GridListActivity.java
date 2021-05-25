package com.example.materialdesign.activity.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.materialdesign.adapter.GridListAdapter;
import com.example.materialdesign.model.GalleryImgEntity;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GridListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_list);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        List<Integer> img_items = new ArrayList<>();
        //region items add
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);

        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);

        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
        img_items.add(R.drawable.image_7);
        img_items.add(R.drawable.image_8);
        img_items.add(R.drawable.image_9);
        img_items.add(R.drawable.image_10);
        img_items.add(R.drawable.image_11);
        img_items.add(R.drawable.image_12);
        img_items.add(R.drawable.image_1);
        img_items.add(R.drawable.image_2);
        img_items.add(R.drawable.image_3);
        img_items.add(R.drawable.image_4);
        img_items.add(R.drawable.image_5);
        img_items.add(R.drawable.image_6);
//endregion

        List<GalleryImgEntity> images = new ArrayList<>();
        //from what ive seen imges usually have this name IMG_101010201.jpg
        //the numbers are usually the date of creation
        //since we don't have that value here we will use the id
        for (Integer i : img_items) {
            images.add(new GalleryImgEntity("IMG_" + i + ".jpg", i, false));
        }

        int section_id = 0;

       //numbers of elements per section including the section header
        int section_count = 0;

        List<String> months = new ArrayList<>();
        //region months add
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        //endregion

        //String title, int image, boolean section
        // img_items = 50

        // img_items 120
        // i = 0 , i <12 , i++
        // we want to create the months from the array list but we want to position them in different intervals
        // not at the end of the images(items) list
        /*
            we are adding them at positions 0 10 20 30 40 50 60 70 80 90 100 110
            other items are moved accordingly between them

            only the month of december will have more pictures in it as each time we injected a section header we staggered the rest of the list

            so each section contains 10 items (secion count) 1 header + 9 images; last one has 1 header and (120 - 9*11)
            21
         */
        for (int i = 0; i < img_items.size()/ 10; i++) {
            images.add(section_count, new GalleryImgEntity(months.get(section_id), -1, true));
            section_count = section_count + 10;
            section_id++;
        }

        mAdapter = new GridListAdapter(this, images);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GridListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, GalleryImgEntity obj, int position) {

                Toast.makeText(GridListActivity.this, "You have clicked" + obj.title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setTitle("Grid List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
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
                showInformationDialog("A view that shows items in two-dimensional scrolling grid. The items in the grid come from the ListAdapter associated with this view.");
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
