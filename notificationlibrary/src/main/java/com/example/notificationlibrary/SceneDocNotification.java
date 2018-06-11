package com.example.notificationlibrary;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

public class SceneDocNotification {
    private static final String TAG = SceneDocNotification.class.getName();
    public static SceneDocNotification sdNotification = null;
    public final Context context;
    public boolean shutdown;

    public SceneDocNotification(Context context){
        this.context = context;
    }


    public static SceneDocNotification with(Context context){
        if(sdNotification == null){
            synchronized (SceneDocNotification.class){
                if(sdNotification==null){
                    sdNotification = new ContextAdder(context).build();
                }
            }
        }
        return sdNotification;
    }
    public Load load() {
        return new Load(context);
    }

    public void cancel(int identifier){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("TESTING123", "Deleting notification with Id " + String.valueOf(identifier));
        notificationManager.cancel(identifier);
    }

    public void cancel(String tag, int identifier){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(tag, identifier);
    }

    public void shutdown(){
        if(this == sdNotification)
            throw new UnsupportedOperationException("Default instance cannot be shutdown");
        if(shutdown)
            return;
        shutdown = true;
    }
}
