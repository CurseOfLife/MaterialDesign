package com.example.materialdesign.activity.badge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.activity.MainActivity;
import com.example.materialdesign.model.MessageEntity;
import com.example.materialdesign.broadcasts.CustomNotificationBroadcastReciever;
import com.example.materialdesign.broadcasts.MessageBroadcastReciever;
import com.example.materialdesign.broadcasts.NotificationBroadcastReciever;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.core.app.NotificationCompat.*;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_1_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_2_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_3_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_4_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_5_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_6_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_7_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_8_ID;
import static com.example.materialdesign.utility.BaseApplication.CHANNEL_9_ID;


/*
        NotificationManager:
        https://developer.android.com/reference/android/app/NotificationManager
        NotificationChannel:
        https://developer.android.com/reference/android/app/NotificationChannel
        NotificationCompat:
        https://developer.android.com/reference/android/support/v4/app/NotificationCompat
        NotificationCompat.Builder:
        https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder
        Understand Tasks and Back Stack:
        https://developer.android.com/guide/components/activities/tasks-and-back-stack
        Start an Activity from a Notification:
        https://developer.android.com/training/notify-user/navigation
        Remote Views:
        https://developer.android.com/reference/android/widget/RemoteViews

        Official android youtube android developers
            Using NotificationCompat for Beautiful Notifications (Android Development Patterns Ep 2)
            https://www.youtube.com/watch?v=-iog_fmm6mE
            Notification updates Oreo
            https://www.youtube.com/watch?v=zGIw4MIJn5o
*/

// NOTE - sendOnChannel5 has a bug where it wont post a msg at least on the devices i tested on
// since debugging can take time
// i will skip debugging for a simple reason of it being a showcase of the possibility for chat applications
// and not an actual chat application

// Oreo apps have a a badge next to the app icon in the app menu when we have a notification
// as well as if we long click the app icon we can see the notifications /delete them etc

// my device is Nougat 7.0 so so i didn't get sound etc
public class DeviceNotifications extends AppCompatActivity {

    private final String TOAST_MESSAGE = "TOAST_MESSAGE";

    //we don't have a database for storing and displaying messages from
    //set as private so it can be used in the receiver
    //just for demonstration purposes
    public static List<MessageEntity> MESSAGES = new ArrayList<>();

    //cant create channels unlike the NotificationManager class in BaseApplication
    private NotificationManagerCompat notificationManagerCompat;

    private EditText editTextTitle;
    private EditText editTextMessage;

    // note there are MediaSession and MediaSession2 which were added later and are supported by later versions
    private MediaSessionCompat mediaSessionCompat;

    private TextView textViewSearch;
    private LinearLayout layout_parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_notifications);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initComponents();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
        layout_parent = findViewById(R.id.layout_parent);

        mediaSessionCompat = new MediaSessionCompat(this, "tag");

        MESSAGES.add(new MessageEntity("Aa", "Sup bruw"));
        MESSAGES.add(new MessageEntity("null", "Nothing much, working you know"));
        MESSAGES.add(new MessageEntity("Ab", "Yooo have you seen the news?"));
    }

    private void initComponents() {
        textViewSearch = findViewById(R.id.txtView_search);
        textViewSearch.setText("Notifications");

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_more_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog();
            }
        });
    }

    //region Channels
    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Intent activityIntent = new Intent(this, MainActivity.class);

        // TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // stackBuilder.addNextIntentWithParentStack(activityIntent);
        // back stack text from documentation (link above)
        // problem is if we open the same activity as the one we left from last it opens it again
        // but it actually has two same activities opened
        // in other words as we close one the same is opened
        // we use a stack to solve the issue

        /*
            A task is a collection of activities that users interact with when performing a certain job.
            The activities are arranged in a stack—the back stack)—in the order in which each activity is opened.
            For example, an email app might have one activity to show a list of new messages.
            When the user selects a message, a new activity opens to view that message.
            This new activity is added to the back stack. If the user presses the Back button,
            that new activity is finished and popped off the stack.
            The following video provides a good overview of how the back stack works.
         */

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationBroadcastReciever.class);
        broadcastIntent.putExtra(TOAST_MESSAGE, message);

        // action buttons
        // we have to send a flag.. FLAG_UPDATE_CURRENT updates the extra from the intent
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // setteing an icon in the notification
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.image_1);


/*
         unlike the NotificationChannel settings we set that are only available on API 26 and up
         these here are used for older APIs too as well as newer

         for example setPriority() sets the priority in older devices
         we used the same thing in the other just with NotificationManager.PRIORITY_HIGH

         categories are used when our notification fits in some category
         .setCategory(NotificationCompat.CATEGORY_MESSAGE)
         check NotificationCompat.Builder

         CHANNEL_1_ID will be ignored on lower APIs so no need of checking like in BaseApplication
         notificationManagerCompat.notify(1, notification); we can override a notification by passing the same id with a diff notification

         .setContentIntent(contentIntent)  -> calls the specified intent when the notification is clicked

         .setAutoCancel(true) -> auto cancel on tap

         .addAction(R.drawable.ic_favorite, "Toast", actionIntent) -> icon is only displayed for lower api; max 3 action buttons

         .setOnlyAlertOnce(true) -> notification will make sound only the first time, the next time its updated it wont

          .setSmallIcon(R.drawable.ic_book) -> setting a small icon is mandatory, whereas setting the large icon isn't
          default big icon is a square but ofc it can be round, there are plenty of sources online on hot to make a round bitmap
          Example: https://stackoverflow.com/questions/18229358/bitmap-in-imageview-with-rounded-corners

           there are different built in styles (check documentation)
         */

        Notification notification1 = new Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setPriority(PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_favorite, "Toast", actionIntent)
                .setColor(Color.CYAN)
                .setStyle(new BigTextStyle()
                        .bigText(getString(R.string.long_notification_text))
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary text"))
                .build();

        notificationManagerCompat.notify(1, notification1);
    }

    //as its the shortest one I will showcase the way we ask the user to allow notifications
    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!notificationManagerCompat.areNotificationsEnabled()) {
            showAlertDialog();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isChannelBlocked(CHANNEL_2_ID)) {

            showAlertDialog2();
            return;
        }

        //depending on the api we can add up to 7, also depends on size of the text
        Notification notification2 = new Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(PRIORITY_LOW)
                .setStyle(new InboxStyle()
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary text")
                        .addLine("Line 1")
                        .addLine("Line 2")
                        .addLine("Line 3")
                        .addLine("Line 4")
                        .addLine("Line 5")
                        .addLine("Line 6")
                        .addLine("Line 7"))

                .build();

        notificationManagerCompat.notify(2, notification2);
    }

    public void sendOnChannel3(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();


        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);


        // setteing an icon in the notification
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image_2);


        //if we set an action button the image will be smaller
        Notification notification3 = new Builder(this, CHANNEL_3_ID)
                .setSmallIcon(R.drawable.ic_equalizer_white_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(image)
                .setPriority(PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setStyle(new BigPictureStyle()
                        .bigPicture(image)
                        .bigLargeIcon(null))
                .build();

        notificationManagerCompat.notify(3, notification3);
    }

    // implementation 'androidx.media:media:1.1.0'
    // its use is for use of a media player in the notification
    // the method below showcases how to build the notification NOT how to build the media player
    public void sendOnChannel4(View v) {
        String artist = editTextTitle.getText().toString();
        String songTitle = editTextMessage.getText().toString();


        Bitmap albumCover = BitmapFactory.decodeResource(getResources(), R.drawable.image_3);

        // depending on the api we can add up to 7, also depends on size of the text
        // up to 5 action buttons will be displayed in the expanded state; up to 3 of the 5 in the collapsed
        // the addAction intent: -> is a pending intent
        // gradient of the big picture is set by default

        // above Oreo the notification can take colors of the image and display it as the colors of the notification
        // to do this we need a Media Session
        // .setMediaSession(mediaSessionCompat.getSessionToken())) connects the notification with media session controls
        Notification notification4 = new Builder(this, CHANNEL_4_ID)
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle(artist)
                .setContentText(songTitle)
                .setLargeIcon(albumCover)
                .setPriority(PRIORITY_LOW)
                .addAction(R.drawable.ic_equalizer_white_24dp, "Action1", null)
                .addAction(R.drawable.ic_favorite, "Action2", null)
                .addAction(R.drawable.ic_book, "Action3", null)
                .addAction(R.drawable.ic_touch_app_white_24dp, "Action4", null)
                .addAction(R.drawable.ic_fingerprint_white_24dp, "Action5", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 2, 3)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setSubText("Subtext")
                .build();

        notificationManagerCompat.notify(4, notification4);
    }

    // features available from nougat
    // older versions don't have the remote input functionality
    // good practice is to instead send the user to the activity
    // on older versions a null will be triggered when we click on the button
    // we have to check for it in the NotificationBroadcastReciever

    // static so we can call it in the broadcast (used for updating, as a showcase)
    public static void sendOnChannel5Notif(Context context) {

        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder("MESSAGING_REMOTE_INPUT")
                .setLabel("Your answer ...")
                .build();

        // two  getBroadcasts with same requestCode or two getActivity will override each other
        // same request code used in two different gets works without overriding
        Intent replyIntent;
        PendingIntent pendingReplyIntent = null;

        //on lower api N -> Nougat api 24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, MessageBroadcastReciever.class);
            pendingReplyIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0);
        } else {
            // in a real situation we would start our chat  activity with PendingIntent.getActivity
            // cancel notification with notificationManagerCompat.cancel(notification_id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_touch_app_white_24dp,
                "Reply",
                pendingReplyIntent)
                .addRemoteInput(remoteInput)
                .build();


        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("You");
        messagingStyle.setConversationTitle("Conversation Group Title");
        //messagingStyle.setGroupConversation(true);

        for (MessageEntity chatMsg : MESSAGES
        ) {
            NotificationCompat.MessagingStyle.Message notificationMsg =
                    new NotificationCompat.MessagingStyle.Message(
                            chatMsg.getText(), chatMsg.getTimestamp(), chatMsg.getSender()
                    );

            messagingStyle.addMessage(notificationMsg);
        }

        Notification notification5 = new Builder(context, CHANNEL_5_ID)
                .setSmallIcon(R.drawable.ic_book)
                .setPriority(PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setCategory(CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setStyle(messagingStyle)
                .setColor(Color.CYAN)
                .addAction(replyAction)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(5, notification5);
    }

    public void sendOnChannel5(View v) {
        sendOnChannel5Notif(this);
    }

    // indeterminate false means we can actually see the progress
    // indeterminate true -> its sort of a gloving animation or one where we cant determine the progress
    public void sendOnChannel6(View v) {
        final int max_progress = 100;

        // depending on the api we can add up to 7, also depends on size of the text
        // .setOngoing(true); -> we cant swipe the notification away
        // IMPORTANT .setOnlyAlertOnce(true) FOR ANY PRIORITY THAT HAS SOUND
        // otherwise we will spam the user with sound each time we update the progress bar
        final NotificationCompat.Builder notification6 = new Builder(this, CHANNEL_6_ID)
                .setSmallIcon(R.drawable.ic_favorite)
                .setPriority(PRIORITY_LOW)
                .setContentTitle("Download")
                .setContentText("Hold on, downloading")
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setProgress(max_progress, 0, false);

        notificationManagerCompat.notify(6, notification6.build());

        //we need to update the progress
        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(1000);

                for (int progress = 0; progress <= max_progress; progress += 10) {

                    notification6.setProgress(max_progress, progress, false);
                    notificationManagerCompat.notify(6, notification6.build());

                    SystemClock.sleep(1000); //1s
                }
                //notification has to be final to be accessed
                //removing progress bar with 0 0 false
                notification6.setContentText("Download finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                //  .setContentIntent()  if we want to send the user to an activity, check above how to use intents
                ;

                notificationManagerCompat.notify(6, notification6.build());
            }
        }).start();
    }

    // same as above nougat an up grouping works
    // below we have to make it ourselves
    // 4+ notifications can be grouped
    public void sendOnChannel7(View v) {

        Notification notification7 = new NotificationCompat.Builder(this, CHANNEL_7_ID)
                .setSmallIcon(R.drawable.ic_favorite)
                .setPriority(PRIORITY_LOW)
                .setContentTitle("Title")
                .setContentText("Content Text")
                .build();

        for (int i = 0; i < 5; i++) {

            // for demonstration purposes we will freeze the UI thread by 1s so we can see the notifications coming in
            // note this will freeze the app and is not required in normal practice
            SystemClock.sleep(1000);

            notificationManagerCompat.notify(i, notification7);
        }
    }

    // we had override the on click method so we need to due it manually with setContentIntent .. pending intent  -> check above
    // grouping solution for api lower than nougat
    public void sendOnChannel8(View v) {

        String title = "Title One";
        String message = "Message One";
        String title2 = "Title Two";
        String message2 = "Message Two";

        Notification notification81 = new NotificationCompat.Builder(this, CHANNEL_8_ID)
                .setSmallIcon(R.drawable.ic_favorite)
                .setPriority(PRIORITY_LOW)
                .setContentTitle(title)
                .setContentText(message)
                .setGroup("group1")
                .build();

        Notification notification82 = new NotificationCompat.Builder(this, CHANNEL_8_ID)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(title2)
                .setContentText(message2)
                .setPriority(PRIORITY_LOW)
                .setGroup("group1")
                .build();

        // setGroupAlertBehavior -> changes the groups behavior if it makes sound each time or depending what we desire
        Notification sumNotification = new NotificationCompat.Builder(this, CHANNEL_8_ID)
                .setSmallIcon(R.drawable.ic_touch_app_white_24dp)
                .setPriority(PRIORITY_LOW)
                .setGroup("group1")
                .setGroupSummary(true)
                .setStyle(new InboxStyle()
                        .addLine(title2 + "" + message2)
                        .addLine(title + "" + message)
                        .setSummaryText("This here is the summary")
                        .setBigContentTitle("You've got two new messages")
                )
                .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
                .build();

        Notification notification83 = new NotificationCompat.Builder(this, CHANNEL_8_ID)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle("This here is a diff group notification")
                .setContentText("It won't go into the group")
                .setPriority(PRIORITY_LOW)
                .setGroup("group2")
                .build();


        SystemClock.sleep(1000);
        notificationManagerCompat.notify(8, notification81);
        SystemClock.sleep(1000);
        notificationManagerCompat.notify(9, notification82);
        SystemClock.sleep(1000);
        notificationManagerCompat.notify(10, sumNotification);
        SystemClock.sleep(1000);
        notificationManagerCompat.notify(11, notification83);
    }

    public void sendOnChannel9(View v) {
        // Remote views  -> A class that describes a view hierarchy that can be displayed in another process.
        //  The hierarchy is inflated from a layout resource file,
        //  and this class provides some basic operations for modifying the content of the inflated hierarchy.
        // example widgets etc
        // setting a default style helps with some features as for example the edit text bar (look above)

        // https://stackoverflow.com/questions/5752445/why-should-i-use-remoteviews-for-android-home-screen-widgets
        // also by clicking ctrl+left click / ctrl+b and scrolling up we can see the supported views

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_custom_collapsed);
        //max height is 256dp
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_custom_expanded);

        // RemoteViews headsUp = new RemoteViews(getPackageName(), R.layout.notification_custom_headsup);

        Intent onClickIntent = new Intent(this, CustomNotificationBroadcastReciever.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, onClickIntent, 0);

        // collapsedView.setTextViewText(R.id.collapsed_text_one, "This is how to change text");

        // this is how to change img
        // and add a click listener
        expandedView.setImageViewResource(R.id.expanded_image, R.drawable.image_5);
        expandedView.setOnClickPendingIntent(R.id.expanded_image, clickPendingIntent);

        expandedView.setImageViewResource(R.id.expanded_image_two, R.drawable.image_2);
        expandedView.setOnClickPendingIntent(R.id.expanded_image_two, clickPendingIntent);

        expandedView.setImageViewResource(R.id.expanded_image_three, R.drawable.image_4);
        expandedView.setOnClickPendingIntent(R.id.expanded_image_three, clickPendingIntent);

        /*this is how the methods are actually called
        public void setImageViewResource ( int viewId, int srcId){
            setInt(viewId, "setImageResource", srcId);
        }
         */

        Notification notification9 = new NotificationCompat.Builder(this, CHANNEL_9_ID)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_favorite)
                .setPriority(PRIORITY_HIGH)
                .setContentTitle("Title")
                .setContentText("Content Text")
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                //.setCustomHeadsUpContentView(headsUp) the default one is the custom content view
                .setStyle(new DecoratedCustomViewStyle())
                .build();

        notificationManagerCompat.notify(12, notification9);
    }

    // only visible in Oreo
    public void deleteChannel(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationManager manager = getSystemService(NotificationManager.class);

            //manager.deleteNotificationChannelGroup(GROUP_1_ID); -> deleting a group
            //manager.deleteNotificationChannel(CHANNEL_1_ID); -> deleting a channel

            showAlertDialogOnDeleteNotChannel();
        }
    }

    //endregion

    //region Dialogs
    private void showInformationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_information_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.title)).setText("Notifications");
        ((TextView) dialog.findViewById(R.id.version)).setText("Version 1.0");
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText("The Activity showcases Android notifications.\n Some work with entering text.\n It should work on newer and older devices, though there is a difference depending if the device is below Nougat or below Oreo.\n Some features were introduced in both of them.\n Newer decides can disable things in device settings (you can explore for yourself), older can't.\n\nCheck the code for more details and implementation. ");

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showAlertDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Hey we need some permissions for the app to work properly")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        openNotificationSettings();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("CANCEL", null)
                .show();
    }

    @RequiresApi(26)
    private void showAlertDialog2() {

        new AlertDialog.Builder(this)
                .setTitle("Hey we need some permissions for the app to work properly")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        openChannelSettings(CHANNEL_2_ID);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void showAlertDialogOnDeleteNotChannel() {

        new AlertDialog.Builder(this)
                .setTitle("Delete information?")
                .setMessage("By agreeing the chosen channel will be deleted (not actually, check how its done in code)")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Snackbar.make(layout_parent, "Channel has been deleted", Snackbar.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("DISAGREE", null)
                .show();
    }
    //endregion

    //region Methods
    private void openNotificationSettings() {

        // if user device runs on Oreo then
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //opening settings
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //opening setting for our app
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            //starting the intent
            startActivity(intent);
        } else {
            //on lower api the user disables all the notifications
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    // on Oreo and above the user can disable the specific notification channel
    // we can also ask him to turn it back on
    // RequiresApi is basically the if statement
    @RequiresApi(26)
    private boolean isChannelBlocked(String channelId) {
        //compat manager is used on versions below v26
        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        NotificationChannel channel = manager.getNotificationChannel(channelId);

        return  //channel might not exist
                channel != null &&
                        //if importance is disabled aka set to none
                        channel.getImportance() == NotificationManager.IMPORTANCE_NONE;
    }

    @RequiresApi(26)
    private void openChannelSettings(String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        startActivity(intent);
    }
    //endregion
}