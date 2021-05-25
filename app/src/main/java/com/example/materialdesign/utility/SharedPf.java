package com.example.materialdesign.utility;


import android.content.Context;
import android.content.SharedPreferences;

//ive had the bright idea, initially, to spam the user with dialogs of who made the application where to find more info or me
//that was before I've dug deeper into UX design
//the app still counts clicks in the background just ive commented out the action it takes when some number is reached
//in other words the dialogs arent shown anymore *commented out
//i believe that only upon first use of the application.. first launch.. a general information dialog is shown to introduce the user to the app
public class SharedPf {

    private Context context;
    private SharedPreferences sharedPreferences;

    //MainActivity
    private static final String FIRST_LAUNCH = "_.FIRST_LAUNCH";
    private static final String CLICK_OFFER = "_.MAX_CLICK_OFFER";
    private static final int MAX_CLICK_OFFER = 20;

    public SharedPf(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
    }

    public void setFirstLaunch(boolean flag) {
        sharedPreferences.edit().putBoolean(FIRST_LAUNCH, flag).apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
    }

    public boolean actionClickOffer() {
        int current = sharedPreferences.getInt(CLICK_OFFER, 1);
        boolean is_reset = false;

        if (current < MAX_CLICK_OFFER) {
            current++;
        } else {
            current = 1;
            is_reset = true;
        }

        sharedPreferences.edit().putInt(CLICK_OFFER, current).apply();
        return is_reset;
    }

}
