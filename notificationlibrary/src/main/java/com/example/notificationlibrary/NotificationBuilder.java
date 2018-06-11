package com.example.notificationlibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.ByteArrayOutputStream;

public class NotificationBuilder {
    private static final String TAG = NotificationBuilder.class.getName();
    protected Notification notification;
    protected String tag;
    protected NotificationCompat.Builder builder;
    protected int notificationId;

    public NotificationBuilder(NotificationCompat.Builder builder, int identifier, String tag){
        this.builder = builder;
        this.notificationId = identifier;
        this.tag = tag;
    }

    public void build(){
        notification = builder.build();
    }

    public void setBigContentView(RemoteViews views){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            notification.bigContentView = views;
            return;
        }
        Log.d(TAG, "Big content view is unsupported in this version");
    }

    protected Notification notification(){
        if(tag != null){
            return notify(tag, notificationId);
        }
        return notify(notificationId);
    }

    protected Notification notify(int identifier){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(SceneDocNotification.sdNotification.context);
        notificationManagerCompat.notify(identifier, notification);
        return notification;
    }

    protected Notification notify(String tag, int identifier){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(SceneDocNotification.sdNotification.context);
        notificationManagerCompat.notify(tag, identifier, notification);
        return notification;
    }
}
