package com.example.notificationlibrary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.text.Spanned;
import android.widget.RemoteViews;

public class CustomNotification extends NotificationBuilder implements OnImageLoaded {
    private static final String TAG = CustomNotification.class.getName();
    private RemoteViews remoteViews;
    private String title;
    private String message;
    private Spanned messageSpanned;
    private Bitmap bitmap;
    private int resourceId;
    private byte[] bytes;
    private int smallIcon;
    private int backgroundResourceId;
    private int placeHolderResourceId;
    private ImageLoader imageLoader;

    public CustomNotification(NotificationCompat.Builder builder, int identifier, String title, String message, Spanned messageSpanned, int smallIcon, String tag){
        super(builder, identifier, tag);
        this.remoteViews = new RemoteViews(SceneDocNotification.sdNotification.context.getPackageName(), R.layout.custom_scenedoc_notification);
        this.title = title;
        this.message = message;
        this.messageSpanned = messageSpanned;
        this.smallIcon = smallIcon;
        this.placeHolderResourceId = R.drawable.clear_button;
        this.setWidgets();
    }

    private void setWidgets(){
        remoteViews.setTextViewText(R.id.notification_text_title, title);
        if (messageSpanned != null) {
            remoteViews.setTextViewText(R.id.notification_text_message, messageSpanned);
        } else {
            remoteViews.setTextViewText(R.id.notification_text_message, message);
        }
        if (smallIcon <= 0) {
            remoteViews.setImageViewResource(R.id.notification_img_icon, R.drawable.clear_button);
        } else{
            remoteViews.setImageViewResource(R.id.notification_img_icon, smallIcon);
        }
    }

    @SuppressLint("ResourceType")
    public CustomNotification background(@DrawableRes int resource){
        if(resource <= 0){
            throw new IllegalArgumentException("Resource cannot be less than or equal to 0");
        }
        if(bitmap != null){
            throw new IllegalArgumentException("Background is already set.");
        }
        if(bytes != null){
            throw new IllegalArgumentException("Background is already set");
        }
        this.backgroundResourceId = resource;
        return this;
    }

    @SuppressLint("ResourceType")
    public CustomNotification setPlaceholder(@DrawableRes int resource){
        if(resource <= 0){
            throw new IllegalArgumentException("Resource cannot be less than or equal to 0.");
        }

        this.placeHolderResourceId = resource;
        return this;
    }

    public CustomNotification setImageLoader(ImageLoader imageLoader){
        this.imageLoader = imageLoader;
        return this;
    }

    public CustomNotification background(Bitmap bitmap){
        if(backgroundResourceId > 0){
            throw new IllegalStateException("Background is already set");
        }
        if(this.bitmap != null){
            throw new IllegalStateException("Background is already set");
        }
        if(bitmap == null){
            throw new IllegalArgumentException("Bitmap must not be null");
        }
        if(bytes != null){
            throw new IllegalArgumentException("Background is already set");
        }
        if(imageLoader == null){
            throw new IllegalStateException("Image loader must be set");
        }
        this.bitmap = bitmap;
        return this;
    }

    public CustomNotification background(byte[] bytes){
        if(backgroundResourceId > 0){
            throw new IllegalStateException("Background is already set");
        }
        if(this.bitmap != null){
            throw new IllegalStateException("Background is already set");
        }
        if(this.bytes != null){
            throw new IllegalArgumentException("Background is already set");
        }
        if(bytes == null){
            throw new IllegalArgumentException("Bytes cannot be null");
        }
        if(imageLoader == null){
            throw new IllegalStateException("Image loader must be set");
        }
        this.bytes = bytes;
        return this;
    }

    @Override
    public void build() {
        if(!(Looper.getMainLooper().getThread() == Thread.currentThread())){
            throw new IllegalStateException("Method call should happen from the main thread");
        }
        super.build();
        setBigContentView(remoteViews);
        loadBackground();
        super.notification();
    }

    private void loadBackground(){
        remoteViews.setImageViewResource(R.id.notification_img_background, placeHolderResourceId);
        if(bitmap != null){
            imageLoadingCompleted(bitmap);
        }
        else if(bytes != null){
            imageLoadingCompleted(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }
        else {
            imageLoader.load(backgroundResourceId, this);
        }
    }

    @Override
    public void imageLoadingCompleted(Bitmap bitmap) {
        if(bitmap == null){
            throw new IllegalArgumentException("Bitmap must not be null");
        }
        remoteViews.setImageViewBitmap(R.id.notification_img_background, bitmap);
        super.notification();
    }
}
