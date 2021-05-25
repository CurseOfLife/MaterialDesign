package com.example.materialdesign.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.RemoteInput;

import com.example.materialdesign.activity.badge.DeviceNotifications;
import com.example.materialdesign.model.MessageEntity;

public class MessageBroadcastReciever extends BroadcastReceiver {

    private final String REMOTE_INPUT = "MESSAGING_REMOTE_INPUT";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            CharSequence replyText = remoteInput.getCharSequence(REMOTE_INPUT);
            MessageEntity answer = new MessageEntity(replyText, null);

            DeviceNotifications.MESSAGES.add(answer);

            // if we just leave it with the above code we wont be updating the messages
            // a progress circle will be spinning forever

            DeviceNotifications.sendOnChannel5Notif(context);
        }
    }
}
