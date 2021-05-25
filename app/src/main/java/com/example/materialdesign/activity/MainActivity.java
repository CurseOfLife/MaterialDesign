package com.example.materialdesign.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.badge.DeviceNotifications;
import com.example.materialdesign.activity.badge.SimpleBadge;
import com.example.materialdesign.activity.banner.AdMobBanner;
import com.example.materialdesign.activity.banner.ContinualBannerActivity;
import com.example.materialdesign.activity.banner.NormalBannerActivity;
import com.example.materialdesign.activity.bottomnavigation.BottomAppBarVersionOne;
import com.example.materialdesign.activity.bottomnavigation.BottomNavigationSimple;
import com.example.materialdesign.activity.bottomnavigation.ChipNavigationBar;
import com.example.materialdesign.activity.bottomsheet.ExpandingBottomSheet;
import com.example.materialdesign.activity.bottomsheet.ModalDialogBottomSheet;
import com.example.materialdesign.activity.bottomsheet.ModalFullscreenBottomSheet;
import com.example.materialdesign.activity.bottomsheet.StandardBottomSheet;
import com.example.materialdesign.activity.bottomsheet.StandardBottomSheetSong;
import com.example.materialdesign.activity.button.ExtendedFabActivity;
import com.example.materialdesign.activity.button.SimpleButtonTypes;
import com.example.materialdesign.activity.button.SpeedDialFabActivity;
import com.example.materialdesign.activity.button.SpeedDialTwoActivity;
import com.example.materialdesign.activity.card.OverlapCard;
import com.example.materialdesign.activity.card.RandomCards;
import com.example.materialdesign.activity.card.SetupWizard;
import com.example.materialdesign.activity.card.SimpleCard;
import com.example.materialdesign.activity.chips.ActionChipActivity;
import com.example.materialdesign.activity.chips.ChoiceChipActivity;
import com.example.materialdesign.activity.chips.EntryChipActivity;
import com.example.materialdesign.activity.chips.FilterChipActivity;
import com.example.materialdesign.activity.dialog.CustomRandomDialogs;
import com.example.materialdesign.activity.dialog.DialogFullscreen;
import com.example.materialdesign.activity.dialog.DialogSimple;
import com.example.materialdesign.activity.list.DraggableListGestureActivity;
import com.example.materialdesign.activity.list.GridListActivity;
import com.example.materialdesign.activity.list.MovieList;
import com.example.materialdesign.activity.list.SwipeListGestureActivity;
import com.example.materialdesign.activity.menu.CustomDrawerActivity;
import com.example.materialdesign.activity.menu.CustomDrawerActivityTwo;
import com.example.materialdesign.activity.menu.ListItemPopupMenuActivity;
import com.example.materialdesign.activity.menu.RightDrawerActivity;
import com.example.materialdesign.activity.menu.SimpleMenuDrawerActivity;
import com.example.materialdesign.activity.menu.SimpleTuckedInDrawerActivity;
import com.example.materialdesign.activity.menu.ToolbarOverflowMenuActivity;
import com.example.materialdesign.activity.other.other2.ViewFlipperActivity;
import com.example.materialdesign.activity.other.scratch.ScratchViewActivity;
import com.example.materialdesign.activity.pickers.ColorPickerActivity;
import com.example.materialdesign.activity.pickers.DatePickerActivity;
import com.example.materialdesign.activity.pickers.TimePickerActivity;
import com.example.materialdesign.activity.progressbar.ProgressLibrariesActivity;
import com.example.materialdesign.activity.progressbar.SimpleProgressBarActivity;
import com.example.materialdesign.activity.selectioncontrol.CheckboxActivity;
import com.example.materialdesign.activity.selectioncontrol.RadioButtonActivity;
import com.example.materialdesign.activity.selectioncontrol.SwitchesActivity;
import com.example.materialdesign.activity.slider.SeekBarActivity;
import com.example.materialdesign.activity.tablayout.IconTabActivity;
import com.example.materialdesign.activity.tablayout.ScrollableTabActivity;
import com.example.materialdesign.activity.tablayout.SimpleTabActivity;
import com.example.materialdesign.activity.tablayout.TextIconTabActivity;
import com.example.materialdesign.activity.text.VariousTextTypesActivity;
import com.example.materialdesign.activity.toastsnack.SnackbarActivity;
import com.example.materialdesign.activity.toastsnack.ToastActivity;
import com.example.materialdesign.activity.toolbar.CollapsePinToolbarActivity;
import com.example.materialdesign.activity.toolbar.CollapsingToolbarActivity;
import com.example.materialdesign.adapter.ExpandableRecyclerViewAdapter;
import com.example.materialdesign.adapter.MainActivityAdapter;
import com.example.materialdesign.utility.SharedPf;
import com.example.materialdesign.model.TableOfContentsType;
import com.example.materialdesign.utility.VariousTools;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * MD MAIN ACTIVITY
     **/

    private SharedPf sharedPreferences;

    private RecyclerView recyclerView;
    private MainActivityAdapter mainActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = new SharedPf(this);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.main_recyclerView);

        mainActivityAdapter = new MainActivityAdapter(this, generateItems(), new MainActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemId) {
                onMenuItemSelected(itemId);
            }
        });

        mainActivityAdapter.setMode(ExpandableRecyclerViewAdapter.EXPANDED);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mainActivityAdapter);

        if (sharedPreferences.isFirstLaunch()) {
            showAboutDialogWithButton(); // advised not to do it as its "not professional"
        }
    }

    // recycler view adapter on click listener
    // it serves as sort of a menu list so thats why the name
    private void onMenuItemSelected(int itemId) {

        if (sharedPreferences.actionClickOffer()) {
            showMadeByDialog(); // advised not to do it as its "not professional"
            return;
        }

        switch (itemId) {

            //region AlertDialog, Dialog, DialogFragment -------------------------------------------
            case 1001:
                startActivity(new Intent(this, DialogSimple.class));
                break;

            case 1002:
                startActivity(new Intent(this, DialogFullscreen.class));
                break;

            case 1003:
                startActivity(new Intent(this, CustomRandomDialogs.class));
                break;

            //endregion

            //region BottomNavigation - BottomAppBar, Badge, BottomNavigation ----------------------
            case 2001:
                startActivity(new Intent(this, BottomNavigationSimple.class));
                break;

            case 2002:
                startActivity(new Intent(this, BottomAppBarVersionOne.class));
                break;

            case 2003:
                startActivity(new Intent(this, ChipNavigationBar.class));
                break;
            //endregion

            //region Bottom Sheet ------------------------------------------------------------------
            case 3001:
                startActivity(new Intent(this, StandardBottomSheet.class));
                break;
            case 3002:
                startActivity(new Intent(this, StandardBottomSheetSong.class));
                break;

            case 3003:
                //modal bottom sheet a version of it without bottom sheet behavior
                //fragment
                startActivity(new Intent(this, BottomAppBarVersionOne.class));
                break;

            case 3004:
                //modal bottom sheet a version of it with bottom sheet behavior
                //dialog
                //it was done after the full screen one
                startActivity(new Intent(this, ModalDialogBottomSheet.class));
                break;

            case 3005:
                startActivity(new Intent(this, ModalFullscreenBottomSheet.class));
                break;

            case 3006:
                startActivity(new Intent(this, ExpandingBottomSheet.class));
                break;
            //endregion

            //region Badges
            case 4001:
                startActivity(new Intent(this, SimpleBadge.class));
                break;

            case 4002:
                startActivity(new Intent(this, DeviceNotifications.class));
                break;
            //endregion

            //region Buttons
            case 5001:
                startActivity(new Intent(this, SimpleButtonTypes.class));
                break;

            case 5002:
                startActivity(new Intent(this, ExtendedFabActivity.class));
                break;

            case 5003:
                startActivity(new Intent(this, SpeedDialFabActivity.class));
                break;

            case 5004:
                startActivity(new Intent(this, SpeedDialTwoActivity.class));
                break;
            //endregion

            //region Cards
            case 6001:
                startActivity(new Intent(this, SimpleCard.class));
                break;

            case 6002:
                startActivity(new Intent(this, OverlapCard.class));
                break;

            case 6003:
                startActivity(new Intent(this, SetupWizard.class));
                break;

            case 6004:
                startActivity(new Intent(this, RandomCards.class));
                break;
            //endregion

            //region Section Control
            case 7001:
                startActivity(new Intent(this, CheckboxActivity.class));
                break;

            case 7002:
                startActivity(new Intent(this, RadioButtonActivity.class));
                break;

            case 7003:
                startActivity(new Intent(this, SwitchesActivity.class));
                break;
            //endregion

            //region Chips
            case 8001:
                startActivity(new Intent(this, ActionChipActivity.class));
                break;

            case 8002:
                startActivity(new Intent(this, ChoiceChipActivity.class));
                break;

            case 8003:
                startActivity(new Intent(this, EntryChipActivity.class));
                break;

            case 8004:
                startActivity(new Intent(this, FilterChipActivity.class));
                break;
            //endregion

            //region Toolbar
            case 9001:
                startActivity(new Intent(this, CollapsingToolbarActivity.class));
                break;

            case 9002:
                startActivity(new Intent(this, OverlapCard.class));
                break;

            case 9003:
                startActivity(new Intent(this, CollapsePinToolbarActivity.class));
                break;
            //endregion

            //region List
            case 10001:
                startActivity(new Intent(this, ExpandingBottomSheet.class));
                break;

            case 10002:
                startActivity(new Intent(this, StandardBottomSheetSong.class));
                break;

            case 10003:
                startActivity(new Intent(this, SwipeListGestureActivity.class));
                break;

            case 10004:
                startActivity(new Intent(this, DraggableListGestureActivity.class));
                break;

            case 10005:
                startActivity(new Intent(this, GridListActivity.class));
                break;

            case 10006:
                startActivity(new Intent(this, MovieList.class));
                break;
            //endregion

            //region Banner -Not supported in Material design as of yet
            case 11001:
                startActivity(new Intent(this, NormalBannerActivity.class));
                break;

            case 11002:
                startActivity(new Intent(this, ContinualBannerActivity.class));
                break;

            case 11003:
                startActivity(new Intent(this, AdMobBanner.class));
                break;
            //endregion

            //region Text Fields
            case 12001:
                startActivity(new Intent(this, VariousTextTypesActivity.class));
                break;
            //endregion

            //region Menu
            case 13001:
                startActivity(new Intent(this, SimpleMenuDrawerActivity.class));
                break;

            case 13002:
                startActivity(new Intent(this, SimpleTuckedInDrawerActivity.class));
                break;

            case 13003:
                startActivity(new Intent(this, CustomDrawerActivity.class));
                break;

            case 13004:
                startActivity(new Intent(this, RightDrawerActivity.class));
                break;

            case 13005:
                startActivity(new Intent(this, ListItemPopupMenuActivity.class));
                break;

            case 13006:
                startActivity(new Intent(this, ToolbarOverflowMenuActivity.class));
                break;

            case 13007:
                startActivity(new Intent(this, CustomDrawerActivityTwo.class));
                break;
            //endregion

            //region Pickers
            case 14001:
                startActivity(new Intent(this, DatePickerActivity.class));
                break;

            case 14002:
                startActivity(new Intent(this, TimePickerActivity.class));
                break;

            case 14003:
                startActivity(new Intent(this, ColorPickerActivity.class));
                break;
            //endregion

            //region Sliders
            case 15001:
                startActivity(new Intent(this, SeekBarActivity.class));
                break;
            //endregion

            //region Toast & Snack
            case 16001:
                startActivity(new Intent(this, ToastActivity.class));
                break;

            case 16002:
                startActivity(new Intent(this, SnackbarActivity.class));
                break;
            //endregion

            //region Tab Layout
            case 17001:
                startActivity(new Intent(this, SimpleTabActivity.class));
                break;

            case 17002:
                startActivity(new Intent(this, IconTabActivity.class));
                break;

            case 17003:
                startActivity(new Intent(this, TextIconTabActivity.class));
                break;

            case 17004:
                startActivity(new Intent(this, ScrollableTabActivity.class));
                break;
            //endregion

            //region ProgressBar
            case 18001:
                startActivity(new Intent(this, SimpleProgressBarActivity.class));
                break;

            case 18002:
                startActivity(new Intent(this, ProgressLibrariesActivity.class));
                break;
            //endregion

            //region Other
            case 19001:
                startActivity(new Intent(this, ScratchViewActivity.class));
                break;

            case 19002:
                startActivity(new Intent(this, ViewFlipperActivity.class));
                break;
            //endregion

        }
    }

    private void showAboutDialogWithButton() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Version " + 2.0);

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((View) dialog.findViewById(R.id.gotIt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sharedPreferences.setFirstLaunch(false);
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    public void showMadeByDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_made_by);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((Button) dialog.findViewById(R.id.btn_dialog_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sharedPreferences.setFirstLaunch(false);
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    // generating the list of recycler view items
    private List<MainActivityAdapter.ListItem> generateItems() {

        List<MainActivityAdapter.ListItem> list = new ArrayList<>();

        // Mat Develop and Mat Components have different orders on material design io
        // i followed the development page
        list.add(new MainActivityAdapter.ListItem(1000, "Dialogs", R.drawable.ic_picture_in_picture, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(1001, "Simple", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1002, "Fullscreen", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(1003, "Custom Random Dialogs ", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(2000, "Bottom Navigation", R.drawable.ic_view_week, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(2001, "Simple", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2002, "Modern Fab", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(2003, "Chip Navigation Bar", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(3000, "Bottom Sheet", R.drawable.ic_receipt_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(3001, "Standard - Map", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(3002, "Standard - Song", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(3003, "Modal - SheetFragment - Menu", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(3004, "Modal - SheetDialog", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(3005, "Modal - Fullscreen", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(3006, "Expanding", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(4000, "Notification & Badge", R.drawable.ic_looks_5_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(4001, "Simple Badge Example", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(4002, "Device Notifications", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(5000, "Button", R.drawable.ic_touch_app_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(5001, "Various Button Types", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(5002, "Extended Fab", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(5003, "Speed Dial Fab", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(5004, "Speed Dial Fab 2", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(6000, "Cards", R.drawable.ic_credit_card_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(6001, "Simple Card", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(6002, "Overlap Card", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(6003, "Setup Wizard", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(6004, "Random Cards", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(7000, "Selection control", R.drawable.ic_check_box_green_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(7001, "Checkboxes", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(7002, "Radio Button", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(7003, "Switch Settings", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(8000, "Chips", R.drawable.ic_memory_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(8001, "Action Chip", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(8002, "Choice Chip", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(8003, "Entry Chip", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(8004, "Filter Chip", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(9000, "Toolbar", R.drawable.ic_web_asset_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(9001, "Collapsing", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(9002, "Parallax", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(9003, "Collapse & Pin", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(10000, "List - Transitions and Gestures", R.drawable.ic_format_align_justify_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(10001, "Multi-Selection", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(10002, "Item Popup Menu", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(10003, "Swippable item", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(10004, "Draggable Item", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(10005, "Grid List", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(10006, "Movie List", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(11000, "Banners - not supported by MD", R.drawable.ic_view_array_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(11001, "Normal", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(11002, "Continual", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(11003, "AdMob Banner", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(12000, "Text Fields", R.drawable.ic_text_fields_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(12001, "Various Text Types", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(13000, "Drawer & Overflow Menu", R.drawable.ic_menu_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(13001, "Simple Left Drawer", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13002, "Simple Left Tucked Drawer", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13003, "Custom Left Drawer", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13004, "Right Drawer", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13005, "List Popup Menu", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13006, "Toolbar Overflow Menu", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(13007, "Custom Drawer 2", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(14000, "Pickers", R.drawable.ic_access_time_white_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(14001, "Date Picker", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(14002, "Time Picker", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(14003, "Color Picker", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(15000, "Slider", R.drawable.ic_tune_black_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(15001, "Various slider designs", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(16000, "Toast & Snackbar", R.drawable.ic_wb_iridescent_black_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(16001, "Toast", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(16002, "Snackbar", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(17000, "Tab Layout", R.drawable.ic_tab_black_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(17001, "Simple Text (fixed)", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(17002, "Simple Icon (fixed)", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(17003, "Text & Icon (fixed)", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(17004, "Scrollable Tab", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(18000, "Progress Bar", R.drawable.ic_sync_black_24dp, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(18001, "Various types of Progress Bars", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(18002, "Various Progress Bar Libraries", -1, TableOfContentsType.SUB_TITLE));

        list.add(new MainActivityAdapter.ListItem(19000, "Other", R.drawable.ic_school, TableOfContentsType.TITLE));
        list.add(new MainActivityAdapter.ListItem(19001, "Scratch View", -1, TableOfContentsType.SUB_TITLE));
        list.add(new MainActivityAdapter.ListItem(19002, "View Flipper", -1, TableOfContentsType.SUB_TITLE));

        return list;
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Components");
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
                showAboutDialogWithButton();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
