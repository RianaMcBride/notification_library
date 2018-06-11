package com.example.notificationlibrary;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ClickActivity implements PendingIntentNotification {
    private final Bundle mBundle;
    private final int mIdentifier;
    private final Class<?> activity;

    public ClickActivity(Bundle bundle, int identifier, Class<?> activity) {
        this.activity = activity;
        this.mBundle = bundle;
        this.mIdentifier = identifier;
    }

    @Override
    public PendingIntent onSettingPendingIntent() {
        Intent clickIntentBroadcast = new Intent(SceneDocNotification.sdNotification.context, activity);
        clickIntentBroadcast.setAction(Actions.SCENEDOC_CLICK);
        clickIntentBroadcast.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        clickIntentBroadcast.setPackage(SceneDocNotification.sdNotification.context.getPackageName());
        if (mBundle != null) {
            clickIntentBroadcast.putExtras(mBundle);
            if(mBundle.getString("tag") == null){
            }
            else{
                String tag = mBundle.getString("tag");
            }
        }
        return PendingIntent.getActivity(SceneDocNotification.sdNotification.context, mIdentifier, clickIntentBroadcast,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }
}
