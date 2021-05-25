package com.example.materialdesign.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.materialdesign.R;
import com.example.materialdesign.model.EventDTO;
import com.example.materialdesign.utility.VariousTools;

import java.util.Calendar;
import java.util.Objects;


public class DialogFullscreenFragment extends DialogFragment {

    //if the background color isnt set the background is transparent

    public CallbackResult callbackResult;
    private int request_code = 0;

    public void setCallbackResult(CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }
    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public DialogFullscreenFragment() {
        // Required empty public constructor
    }

    private View view;
    private TextView txtv_email;
    private EditText etxt_name, etxt_location;
    private Button from_date, to_date, from_time, to_time;
    private CheckBox cb_allday;
    private Spinner spn_timezone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dialog_fullscreen2, container, false);

        txtv_email = (TextView) view.findViewById(R.id.email);
        etxt_name = (EditText) view.findViewById(R.id.event_name);
        etxt_location = (EditText) view.findViewById(R.id.location);
        from_date = (Button) view.findViewById(R.id.from_date);
        to_date = (Button) view.findViewById(R.id.to_date);
        from_time = (Button) view.findViewById(R.id.from_time);
        to_time = (Button) view.findViewById(R.id.to_time);
        cb_allday = (CheckBox) view.findViewById(R.id.allday);
        spn_timezone = (Spinner) view.findViewById(R.id.timezone);

        ((ImageButton) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((Button) view.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataResult();
                dismiss();
            }
        });

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePicker((Button) v);
            }
        });
        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePicker((Button) v);
            }
        });

        from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTimePicker((Button) v);
            }
        });
        to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTimePicker((Button) v);
            }
        });

        String[] timezones = new String[] {"Greenwich Mean Time","Universal Coordinated Time","European Central Time","Eastern European Time","(Arabic) Egypt Standard Time"};
        ArrayAdapter spinner_array_adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_list_item_1, timezones);
        spinner_array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_timezone.setAdapter(spinner_array_adapter);
        spn_timezone.setSelection(0);


        return view;
    }

    private void dialogDatePicker (final Button button){

        Calendar calender = Calendar.getInstance();

        //https://stackoverflow.com/questions/28738089/how-to-change-datepicker-dialog-color-for-android-5-0
        //dialog theme styles
       DatePickerDialog datePickerDialog = new  DatePickerDialog(
               getActivity(),
               new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       Calendar calendar = Calendar.getInstance();
                       calendar.set(Calendar.YEAR, year);
                       calendar.set(Calendar.MONTH, month);
                       calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                       long date_ship_millis = calendar.getTimeInMillis();
                       button.setText(VariousTools.getDateFormat(date_ship_millis));
                   }
               },
               calender.get(Calendar.YEAR),
               calender.get(Calendar.MONTH),
               calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private void dialogTimePicker(final Button button) {

        Calendar calender = Calendar.getInstance();
        TimePickerDialog time = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
                long time = calendar.getTimeInMillis();
                button.setText(VariousTools.getTimeFormat(time));
            }
        } ,calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE),true );

        time.show();
    }

    private void sendDataResult() {
        EventDTO event = new EventDTO();
        event.email = txtv_email.getText().toString();
        event.name = etxt_name.getText().toString();
        event.location = etxt_location.getText().toString();
        event.from = from_date.getText().toString() + " (" + from_time.getText().toString() + ")";
        event.to = to_date.getText().toString() + " (" + to_time.getText().toString() + ")";
        event.is_allday = cb_allday.isChecked();
        event.timezone = spn_timezone.getSelectedItem().toString();

        if (callbackResult != null) {
            callbackResult.sendResult(request_code, event);
        }
    }

    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }
}
