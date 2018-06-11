package com.example.notificationlibrary;

import android.support.v4.app.NotificationCompat;

public class SimpleNotification extends NotificationBuilder {

    public SimpleNotification(NotificationCompat.Builder builder, int identifier, String tag){
        super(builder, identifier, tag);
    }

    @Override
    public void build() {
        super.build();
        super.notification();
    }
}
