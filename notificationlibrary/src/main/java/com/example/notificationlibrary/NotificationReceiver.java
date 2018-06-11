package com.example.notificationlibrary;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TESTING123", "RECEIVED BROADCAST");
        String action = intent.getAction();
        if(Actions.SCENEDOC_CLICK.equals(action)){
            Toast.makeText(context, "CLICKED VIEW", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("TESTING123", String.valueOf(intent.getIntExtra("identifier", 0)));
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(intent.getStringExtra("tag")==null|| intent.getStringExtra("tag").equals("")){
                Log.d("TESTING123", "the tag was null");
                nm.cancel(intent.getIntExtra("identifier", 0));
            }
            else{
                nm.cancel(intent.getStringExtra("tag"), intent.getIntExtra("identifier", 0));
            }
        }
    }
}
