package com.example.notificationlibrary;

import android.graphics.Bitmap;

public interface ImageLoader {
    void load(String uri, OnImageLoaded onLoaded);
    void load(int imageResourceId, OnImageLoaded onLoaded);
}
