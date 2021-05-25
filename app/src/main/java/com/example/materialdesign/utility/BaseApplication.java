package com.example.materialdesign.utility;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;

// Notification Manager https://developer.android.com/reference/android/app/NotificationManager
// <application android:name=".utility.BaseApplication" />

public class BaseApplication extends Application {

    //psfs
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    public static final String CHANNEL_3_ID = "channel3";
    public static final String CHANNEL_4_ID = "channel4";
    public static final String CHANNEL_5_ID = "channel5";
    public static final String CHANNEL_6_ID = "channel6";
    public static final String CHANNEL_7_ID = "channel7";
    public static final String CHANNEL_8_ID = "channel8";
    public static final String CHANNEL_9_ID = "channel9";

    public static final String GROUP_1_ID = "GROUP_ONE";
    public static final String GROUP_2_ID = "GROUP_TWO";
    public static final String GROUP_3_ID = "GROUP_THREE";

    //oncr
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    //available only on Oreo api lvl 26
    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannelGroup group_one = new NotificationChannelGroup(GROUP_1_ID, "Group name one");
            NotificationChannelGroup group_two = new NotificationChannelGroup(GROUP_2_ID, "Group name two");
            NotificationChannelGroup group_three = new NotificationChannelGroup(GROUP_3_ID, "Group name three");

            // "Channel 1" will be visible to the user
            // IMPORTANCE_HIH Higher notification importance: shows everywhere, makes noise and peeks.
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Big Text", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");
            //has no group, check options

            // Low notification importance: Shows in the shade, and potentially in the status bar (see shouldHideSilentStatusBarIcons()),
            // but is not audibly intrusive.
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Inbox Style", NotificationManager.IMPORTANCE_LOW);

            // default settings, the user can disable
            // they can't be changed afterwards
            // unless we change the code and reinstall the app
            channel2.setDescription("This is Channel 2");
            // this is how we group them in options , cant be changed afterwards
            channel2.setGroup(GROUP_1_ID);

            // channel 3
            NotificationChannel channel3 = new NotificationChannel(CHANNEL_3_ID, "Big Picture", NotificationManager.IMPORTANCE_LOW);
            channel3.setDescription("This is Channel 3");
            channel3.setGroup(GROUP_2_ID);

            // channel 4
            NotificationChannel channel4 = new NotificationChannel(CHANNEL_4_ID, "Media Notification", NotificationManager.IMPORTANCE_LOW);
            channel4.setDescription("This is Channel 4");
            channel4.setGroup(GROUP_2_ID);

            // channel 5
            NotificationChannel channel5 = new NotificationChannel(CHANNEL_5_ID, "Messaging Notification", NotificationManager.IMPORTANCE_HIGH);
            channel5.setDescription("This is Channel 5");
            channel5.setGroup(GROUP_1_ID);

            // channel 6
            NotificationChannel channel6 = new NotificationChannel(CHANNEL_6_ID, "Progress Bar Notification", NotificationManager.IMPORTANCE_LOW);
            channel6.setDescription("This is Channel 6");
            channel6.setGroup(GROUP_1_ID);
            // channel 7
            NotificationChannel channel7 = new NotificationChannel(CHANNEL_7_ID, "Group Notification", NotificationManager.IMPORTANCE_LOW);
            channel7.setDescription("This is Channel 7");
            channel6.setGroup(GROUP_3_ID);

            // channel 8
            NotificationChannel channel8 = new NotificationChannel(CHANNEL_8_ID, "Different kind grouping", NotificationManager.IMPORTANCE_LOW);
            channel8.setDescription("This is Channel 8");
            channel6.setGroup(GROUP_3_ID);

            // custom notification channel 7
            NotificationChannel channel9 = new NotificationChannel(CHANNEL_9_ID, "Custom Channel", NotificationManager.IMPORTANCE_HIGH);

            // Notification manager
            NotificationManager manager = getSystemService(NotificationManager.class);

            // creating notification groups
            manager.createNotificationChannelGroup(group_one);
            manager.createNotificationChannelGroup(group_two);
            manager.createNotificationChannelGroup(group_three);

            // creating notification channels that don't belong to a group
            // channels under comments have already been created above while creating the channel groups
            manager.createNotificationChannel(channel1);
            // manager.createNotificationChannel(channel2);
            // manager.createNotificationChannel(channel3);
            //  manager.createNotificationChannel(channel4);
            //  manager.createNotificationChannel(channel5);
            //  manager.createNotificationChannel(channel6);
            //  manager.createNotificationChannel(channel7);
            // manager.createNotificationChannel(channel8);
            manager.createNotificationChannel(channel9);
        }
    }
}
