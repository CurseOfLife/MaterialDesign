package com.example.materialdesign.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.RemoteInput;

import com.example.materialdesign.activity.badge.DeviceNotifications;
import com.example.materialdesign.model.MessageEntity;

public class NotificationBroadcastReciever extends BroadcastReceiver {
    //we have to register it in manifest
    //works when we get out of the app

    private final String TOAST_MESSAGE = "TOAST_MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(TOAST_MESSAGE);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
