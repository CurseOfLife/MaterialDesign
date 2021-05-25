package com.example.materialdesign.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.materialdesign.R;
import com.example.materialdesign.adapter.ExpandableRecyclerViewAdapter;
import com.example.materialdesign.adapter.MainActivityAdapter;
import com.example.materialdesign.model.TableOfContentsType;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=pB7TNoPplvE

public class MDPatchNotesActivity extends AppCompatActivity {

    /**PATCH NOTES ACTIVITY FOR BOTH NOTE AND MD **/

    private RecyclerView recyclerView;
    private MainActivityAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_d_patch_notes);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // get the text from MainActivity
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String text = bundle.containsKey("MDC") ? bundle.getString("MDC") : "NOTES";


        assert text != null;

        if ( text.equals("MDC") ) {

            recyclerViewAdapter = new MainActivityAdapter(this, generateItems(), new MainActivityAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int itemId) {
                    //not needed here
                }
            });

            recyclerViewAdapter.setMode(ExpandableRecyclerViewAdapter.EXPANDED);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(recyclerViewAdapter);

        }else{

            recyclerViewAdapter = new MainActivityAdapter(this, generateItemsWaterMeter(), new MainActivityAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int itemId) {
                    //not needed here
                }
            });

            recyclerViewAdapter.setMode(ExpandableRecyclerViewAdapter.EXPANDED);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(recyclerViewAdapter);

        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Release Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private List<MainActivityAdapter.ListItem> generateItems() {

        List<MainActivityAdapter.ListItem> list = new ArrayList<>();

        list.add(new MainActivityAdapter.ListItem(1000, "Patch Zero (April 2nd, 2020)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(1001, "\u2022 Added 9 MD Component Sections", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1002, "\u2022 Across 27 UI Designs", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1003, "\u2022 Some designs are in their Alpha stage others are done", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(1010, "Patch 0.1 (April 16th, 2020)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(1011, "\u2022 Added 8 More MD Component Sections", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1012, "\u2022 Added 28 More UI Designs", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1013, "\u2022 Fixed Information Dialog Bug on devices with smaller screens", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1014, "\u2022 Fixed Information Dialog Bug on devices with bigger screens, where text info is small", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(1020, "Patch 0.2 (May 7th, 2020)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(1021, "\u2022 Added 3 More Custom Dialogs", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1022, "\u2022 Added Progress Bar Component", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1023, "\u2022 Added a bunch more comments in the source code", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1024, "\u2022 Updated older source code comments that didn't match the existing newer code", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(1030, "Patch 0.3 (to be determined)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(1031, "\u2022 soon", -1, TableOfContentsType.SUB_TITLE));

        return list;
    }

    private List<MainActivityAdapter.ListItem> generateItemsWaterMeter() {

        List<MainActivityAdapter.ListItem> list = new ArrayList<>();

        list.add(new MainActivityAdapter.ListItem(2000, "Patch Zero (April 19th, 2020)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(2001, "\u2022 Launched the first version of the Note app", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2002, "\u2022 Users are able to: add new, edit existing, delete individual existing and delete all existing notes", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(2010, "Patch 0.1 (May 23rd, 2020)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(2011, "\u2022 Users are now able to order notes by descending or ascending order (NOT WORKING)", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2012, "\u2022 Users are now able to filter through notes by priority (NOT WORKING)", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2013, "\u2022 Added a bunch more comments in the source code", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2014, "\u2022 Updated older source code comments that didn't match the existing newer code", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2015, "\u2022 UI improvements", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2016, "\u2022 Added a note content dialog, so users don't have to go to the edit/insert to read their note.", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(2020, "Patch 0.2 (to be determined)", R.drawable.ic_exposure_zero_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(2015, "\u2022 soon", -1, TableOfContentsType.SUB_TITLE));

        return list;
    }
}


