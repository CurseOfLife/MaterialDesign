package com.example.materialdesign.validator;

import android.os.Parcel;

import androidx.annotation.Nullable;

import com.google.android.material.datepicker.CalendarConstraints;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

//singleton
public class DateValidatorWeekdaysOnly  implements CalendarConstraints.DateValidator {

    private Calendar cet = Calendar.getInstance(TimeZone.getTimeZone("CET"));

     public DateValidatorWeekdaysOnly() { }

    //required a creator
    public static final Creator<DateValidatorWeekdaysOnly> CREATOR =
            new Creator<DateValidatorWeekdaysOnly>() {
                @Override
                public DateValidatorWeekdaysOnly createFromParcel(Parcel source) {
                    return new DateValidatorWeekdaysOnly();
                }

                @Override
                public DateValidatorWeekdaysOnly[] newArray(int size) {
                    return new DateValidatorWeekdaysOnly[size];
                }
            };


    @Override
    public boolean isValid(long date) {
        cet.setTimeInMillis(date);
        int dayOfWeek = cet.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DateValidatorWeekdaysOnly)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        Object[] hashedFields = {};
        return Arrays.hashCode(hashedFields);
    }

}
