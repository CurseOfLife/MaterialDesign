package com.example.materialdesign.activity.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts;
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
import com.example.materialdesign.adapter.SimpleItemPopupMenuAdapter;
import com.example.materialdesign.adapter.SongAdapter;
import com.example.materialdesign.model.CarEntity;
import com.example.materialdesign.model.SongDTO;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListItemPopupMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SimpleItemPopupMenuAdapter adapter;
    private List<CarEntity> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_menu);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        populate();

        adapter.setOnItemClickListener(new SimpleItemPopupMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CarEntity carEntity, int pos) {
                Toast.makeText(getApplicationContext(), carEntity.name, Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnMoreButtonClickListener(new SimpleItemPopupMenuAdapter.OnMoreButtonClickListener() {

            @Override
            public void onItemClick(View view, CarEntity carEntity, MenuItem menuItem) {
                Toast.makeText(getApplicationContext(), carEntity.name + " (" + menuItem.getTitle() + ") clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populate() {

        cars = new ArrayList<>();
        cars.add(new CarEntity("BMW 1"));
        cars.add(new CarEntity("Golf 1"));
        cars.add(new CarEntity("Yugo 1"));
        cars.add(new CarEntity("Yugo 2"));
        cars.add(new CarEntity("Ford 1"));
        cars.add(new CarEntity("Ford 2"));
        cars.add(new CarEntity("Ford 3"));
        cars.add(new CarEntity("Trabant 1"));
        cars.add(new CarEntity("Fiat 1"));
        cars.add(new CarEntity("BMW 2"));
        cars.add(new CarEntity("Golf 2"));
        cars.add(new CarEntity("Yugo 3"));
        cars.add(new CarEntity("Yugo 4"));

        adapter = new SimpleItemPopupMenuAdapter(this, cars);
        recyclerView.setAdapter(adapter);
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popup Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                showInformationDialog("Although I've already showed it in a prior example how to use popup menus with recycler view, there the focus was on other components. Which is why in this example I will be making a new recycler view design - a simpler one; in order to just show the popup menu.\n\n Later it will be easier to copy paste it into a project.");
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
