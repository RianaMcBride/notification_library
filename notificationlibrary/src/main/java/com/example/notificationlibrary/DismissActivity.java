package com.example.notificationlibrary;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DismissActivity implements PendingIntentNotification{
    private final Bundle mBundle;
    private final int mIdentifier;
    private final Context context;

    public DismissActivity(Bundle bundle, int identifier, Context context) {
        this.mBundle = bundle;
        this.mIdentifier = identifier;
        this.context = context;
    }

    @Override
    public PendingIntent onSettingPendingIntent() {
        Intent dismissIntentBroadcast = new Intent(context, NotificationReceiver.class);
        dismissIntentBroadcast.setAction(Actions.SCENEDOC_DISMISS);
        dismissIntentBroadcast.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        dismissIntentBroadcast.setPackage(SceneDocNotification.sdNotification.context.getPackageName());

        if (mBundle != null) {
            dismissIntentBroadcast.putExtras(mBundle);
        }
        return PendingIntent.getBroadcast(SceneDocNotification.sdNotification.context, mIdentifier, dismissIntentBroadcast,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
