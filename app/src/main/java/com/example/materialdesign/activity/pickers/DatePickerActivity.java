package com.example.materialdesign.activity.pickers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.example.materialdesign.validator.DateValidatorWeekdaysOnly;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

//https://www.youtube.com/watch?v=m3yj7JaTTPI

public class DatePickerActivity extends AppCompatActivity {

    private final String DATE_PICKER = "DATE_PICKER";

    private TextView txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponent();
    }

    private void initComponent() {

        txt_date=findViewById(R.id.txt_date);

        ((Button) findViewById(R.id.btn_pick_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rangeDialogDatePicker((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker3((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker4((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker5((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker6((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker6((Button) view);
            }
        });

        ((Button) findViewById(R.id.btn_pick_date_7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePicker7((Button) view);
            }
        });

    }

    private void dialogDatePicker7(Button view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        calendar.clear();

        long current_day = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(current_day);

        //calendar constraints
        // weekdays only
        CalendarConstraints.Builder constraints_builder = new CalendarConstraints.Builder();
        constraints_builder.setValidator(new DateValidatorWeekdaysOnly());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
        // builder.setSelection(current_day); //sets selection as current date
        builder.setCalendarConstraints(constraints_builder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    private void dialogDatePicker6(Button view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        calendar.clear();

        long current_day = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(current_day);

        calendar.set(Calendar.MONTH, Calendar.APRIL);
        long april = calendar.getTimeInMillis();


        //calendar constraints
        //date behind current is disabled

        CalendarConstraints.Builder constraints_builder = new CalendarConstraints.Builder();
        constraints_builder.setValidator(DateValidatorPointForward.from(april));

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
        // builder.setSelection(current_day); //sets selection as current date
        builder.setCalendarConstraints(constraints_builder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    private void dialogDatePicker5(Button view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        calendar.clear();

        long current_day = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(current_day);

       // calendar.set(Calendar.MONTH, Calendar.APRIL);
       // long april = calendar.getTimeInMillis();

        //calendar constraints
        //date behind current is disabled

        CalendarConstraints.Builder constraints_builder = new CalendarConstraints.Builder();
        constraints_builder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
        // builder.setSelection(current_day); //sets selection as current date
        builder.setCalendarConstraints(constraints_builder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    private void dialogDatePicker4(Button view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        calendar.clear();

        long current_day = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(current_day);

        calendar.set(Calendar.MONTH, Calendar.APRIL);
        long april = calendar.getTimeInMillis();

        //calendar constraints
        CalendarConstraints.Builder constraints_builder = new CalendarConstraints.Builder();
        constraints_builder.setOpenAt(april);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
       // builder.setSelection(current_day); //sets selection as current date
        builder.setCalendarConstraints(constraints_builder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    private void dialogDatePicker3(Button view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        calendar.clear();

        long current_day = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(current_day);


        calendar.set(Calendar.MONTH, Calendar.APRIL);
        long april = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        long august = calendar.getTimeInMillis();

        //calendar constraints
        CalendarConstraints.Builder constraints_builder = new CalendarConstraints.Builder();
        constraints_builder.setStart(april);
        constraints_builder.setEnd(august);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
        builder.setSelection(current_day); //sets selection as current date
        builder.setCalendarConstraints(constraints_builder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);

    }

    private void rangeDialogDatePicker(Button view) {

        //pair -> start and end range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        builder.setTitleText("Pick a date");
        final MaterialDatePicker materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    //rangedatepicker

    private void dialogDatePicker(Button view) {

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pick a date");
        final MaterialDatePicker materialDatePicker = builder.build();


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                //we get the selection as an object in milli
                //we need plain text from it
                txt_date.setText("Selected date: " + materialDatePicker.getHeaderText());

            }
        });

        materialDatePicker.show(getSupportFragmentManager(),DATE_PICKER);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Date Picker");
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
                showInformationDialog(" Just simple light date pickers.");
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
