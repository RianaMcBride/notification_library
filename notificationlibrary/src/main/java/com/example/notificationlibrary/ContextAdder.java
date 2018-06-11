package com.example.notificationlibrary;

import android.content.Context;

public class ContextAdder {
    private final Context context;

    public ContextAdder(Context context){
        if(context == null){
            throw new IllegalArgumentException("Cannot set context to null");
        }
        this.context = context.getApplicationContext();
    }

    public SceneDocNotification build(){
        return new SceneDocNotification(this.context);
    }
}
