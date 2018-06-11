package com.example.notificationlibrary;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Load {
    private static final String TAG = Load.class.getName();
    private NotificationCompat.Builder builder;
    private String title;
    private String message;
    private Spanned messageSpanned;
    private String tag;
    private int notificationId;
    private int smallIcon;
    private String[] simpleNotificationMessages;
    private int[] simpleNotificationsIds;
    private int simpleCount;
    private int messageCount;
    private Context context;
    private String[] simpleNotificationTags;
    private SceneDocNotification sdn;
    private boolean ongoing;
    public Load(Context context){
        if(context==null){
            throw new IllegalArgumentException("Context cannot be null");
        }
        builder = new NotificationCompat.Builder(SceneDocNotification.sdNotification.context);
        builder.setContentIntent(PendingIntent.getBroadcast(SceneDocNotification.sdNotification.context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        simpleCount = 0;
        messageCount = 0;
        simpleNotificationMessages = new String[100];
        simpleNotificationsIds = new int[100];
        simpleNotificationTags = new String[100];
        this.context = context;
        sdn = new SceneDocNotification(context);
    }
    //get methods
    public String getTitle(){
        return title;
    }
    public String getMessage(){
        return message;
    }
    public String getTag(){
        return tag;
    }
    public int getNotificationId(){
        return notificationId;
    }
    public int getSmallIcon(){
        return smallIcon;
    }
    public int getSimpleCount(){
        return simpleCount;
    }
    public int getMessageCount(){
        return messageCount;
    }
    public Context getContext(){
        return context;
    }
    public boolean isOngoing(){
        return ongoing;
    }
    public String[] getSimpleNotificationMessages(){
        return simpleNotificationMessages;
    }
    public int[] getSimpleNotificationsIds(){
        return simpleNotificationsIds;
    }
    public String[] getSimpleNotificationTags(){
        return simpleNotificationTags;
    }
    public Load identifier(int identifier){
        if(identifier <= 0){
            throw new IllegalStateException("Identifier should not be less than or equal to 0.");
        }

        this.notificationId = identifier;
        return this;
    }

    public void clearSettings(){
        title = null;
        message = null;
        tag = null;
        ongoing = false;
        builder.setOngoing(false);
        builder.mActions.clear();
    }
    public Load tag(@NonNull String tag){
        this.tag = tag;
        return this;
    }

    @SuppressLint("ResourceType")
    public Load title(@StringRes int title){
        if(title <= 0){
            throw new IllegalArgumentException("Title should not be less than or equal to 0.");
        }
        this.title = SceneDocNotification.sdNotification.context.getResources().getString(title);
        this.builder.setContentTitle(this.title);
        return this;
    }

    public Load title(String title){
        if(title == null){
            throw new IllegalStateException("Title cannot be null");
        }
        if(title.trim().length()==0){
            throw new IllegalArgumentException("Title must not be empty");
        }
        this.title = title;
        this.builder.setContentTitle(this.title);
        return this;
    }

    @SuppressLint("ResourceType")
    public Load message(@StringRes int message){
        if(message <= 0){
            throw new IllegalArgumentException("Message should not be less than or equal to zero");
        }
        this.message = SceneDocNotification.sdNotification.context.getResources().getString(message);
        this.builder.setContentText(this.message);
        return this;
    }

    public Load message(@NonNull String message){
        if(message.trim().length()==0){
            throw new IllegalArgumentException("Message must not be empty");
        }
        this.message = message;
        this.builder.setContentText(message);
        return this;
    }

    @SuppressLint("ResourceType")
    public Load color(@ColorRes int color) {
        if (color <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }

        Context context = SceneDocNotification.sdNotification.context;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.builder.setColor(context.getColor(color));
        } else {
            this.builder.setColor(context.getResources().getColor(color));
        }
        return this;
    }

    @SuppressLint("ResourceType")
    public Load ticker(@StringRes int ticker) {
        if (ticker <= 0) {
            throw new IllegalArgumentException("Ticker should not be less than or equal to 0.");
        }

        this.builder.setTicker(SceneDocNotification.sdNotification.context.getResources().getString(ticker));
        return this;
    }

    public Load ticker(String ticker) {
        if (ticker == null) {
            throw new IllegalStateException("Ticker must not be null.");
        }

        if (ticker.trim().length() == 0) {
            throw new IllegalArgumentException("Ticker must not be empty.");
        }

        this.builder.setTicker(ticker);
        return this;
    }

    public Load when(long when) {
        if (when <= 0) {
            throw new IllegalArgumentException("When should not be less than or equal to 0.");
        }

        this.builder.setWhen(when);
        return this;
    }

    @SuppressLint("ResourceType")
    public Load bigTextStyle(@StringRes int bigTextStyle) {
        if (bigTextStyle <= 0) {
            throw new IllegalArgumentException("Style should not be less than or equal to 0.");
        }

        return bigTextStyle(SceneDocNotification.sdNotification.context.getResources().getString(
                bigTextStyle), null);
    }

    @SuppressLint("ResourceType")
    public Load bigTextStyle(@StringRes int bigTextStyle, @StringRes int summaryText) {
        if (bigTextStyle <= 0) {
            throw new IllegalArgumentException("Style should not be less than or equal to 0.");
        }

        return bigTextStyle(SceneDocNotification.sdNotification.context.getResources().getString(
                bigTextStyle), SceneDocNotification.sdNotification.context.getResources().getString(
                summaryText));
    }


    public Load bigTextStyle(@NonNull String bigTextStyle) {
        if (bigTextStyle.trim().length() == 0) {
            bigTextStyle = message;
        }

        return bigTextStyle(bigTextStyle, "New Notification");
    }

    public Load bigTextStyle(@NonNull String bigTextStyle, String summaryText) {
        if (bigTextStyle.trim().length() == 0) {
            bigTextStyle = "SceneDoc";
        }

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(bigTextStyle);
        if (summaryText != null) {
            bigStyle.setSummaryText(summaryText);
        }
        this.builder.setStyle(bigStyle);
        return this;
    }

    public Load inboxStyle(@NonNull String[] inboxLines, @NonNull String title, String summary) {
        if (inboxLines.length <= 0) {
            throw new IllegalArgumentException("Inbox lines must have a length of greater than 0");
        }

        if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Title must not be empty.");
        }

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (String inboxLine : inboxLines) {
            inboxStyle.addLine(inboxLine);
        }
        inboxStyle.setBigContentTitle(title);
        if (summary != null) {
            inboxStyle.setSummaryText(summary);
        }
        this.builder.setStyle(inboxStyle);
        return this;
    }

    public Load autoCancel(boolean autoCancel) {
        this.builder.setAutoCancel(autoCancel);
        return this;
    }

    public Load ongoing(boolean ongoing) {
        this.builder.setOngoing(ongoing);
        this.ongoing = ongoing;
        return this;
    }

    public Load smallIcon(@DrawableRes int smallIcon) {
        this.smallIcon = smallIcon;
        this.builder.setSmallIcon(smallIcon);
        return this;
    }

    public Load largeIcon(@NonNull Bitmap bitmap) {
        this.builder.setLargeIcon(bitmap);
        return this;
    }

    @SuppressLint("ResourceType")
    public Load largeIcon(@DrawableRes int largeIcon) {
        if (largeIcon <= 0) {
            throw new IllegalArgumentException("Icon should not be less than or equal to 0.");
        }

        Bitmap bitmap = BitmapFactory.decodeResource(SceneDocNotification.sdNotification.context.getResources(), largeIcon);
        this.builder.setLargeIcon(bitmap);
        return this;
    }

    public Load group(@NonNull String groupKey) {
        if (groupKey.trim().length() == 0) {
            throw new IllegalArgumentException("Group key must not be empty.");
        }

        this.builder.setGroup(groupKey);
        return this;
    }

    public Load groupSummary(boolean groupSummary) {
        this.builder.setGroupSummary(groupSummary);
        return this;
    }

    public Load number(int number) {
        this.builder.setNumber(number);
        return this;
    }

    public Load vibrate(@NonNull long[] vibrate) {
        for (long aVibrate : vibrate) {
            if (aVibrate <= 0) {
                throw new IllegalArgumentException("Vibrate Time " + aVibrate + " Invalid!");
            }
        }

        this.builder.setVibrate(vibrate);
        return this;
    }

    public Load lights(int color, int ledOnMs, int ledOfMs) {
        if (ledOnMs < 0) {
            throw new IllegalStateException("Led On Milliseconds Invalid!");
        }

        if (ledOfMs < 0) {
            throw new IllegalStateException("Led Off Milliseconds Invalid!");
        }

        this.builder.setLights(color, ledOnMs, ledOfMs);
        return this;
    }

    public Load sound(@NonNull Uri sound) {
        this.builder.setSound(sound);
        return this;
    }

    public Load onlyAlertOnce(boolean onlyAlertOnce) {
        this.builder.setOnlyAlertOnce(onlyAlertOnce);
        return this;
    }

    public Load addPerson(@NonNull String uri) {
        if (uri.length() == 0) {
            throw new IllegalArgumentException("URI Must Not Be Empty!");
        }
        this.builder.addPerson(uri);
        return this;
    }

    public Load button(@DrawableRes int icon, @NonNull String title, @NonNull PendingIntent pendingIntent) {
        this.builder.addAction(icon, title, pendingIntent);
        return this;
    }

    public Load button(@DrawableRes int icon, @NonNull String title, @NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.addAction(icon, title, pendingIntentNotification.onSettingPendingIntent());
        return this;
    }

    public Load button(@NonNull NotificationCompat.Action action) {
        this.builder.addAction(action);
        return this;
    }

    public Load click(@NonNull Class<?> activity, Bundle bundle) {
        this.builder.setContentIntent(new ClickActivity(bundle, notificationId, activity).onSettingPendingIntent());
        return this;
    }

    public Load click(@NonNull Class<?> activity) {
        click(activity, null);
        return this;
    }

    public Load click(@NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.setContentIntent(pendingIntentNotification.onSettingPendingIntent());
        return this;
    }
    public Load priority(int priority) {
        if (priority <= 0) {
            throw new IllegalArgumentException("Priority should not be less than or equal to 0");
        }
        this.builder.setPriority(priority);
        return this;
    }

    public Load flags(int defaults) {
        this.builder.setDefaults(defaults);
        return this;
    }

    public Load click(@NonNull PendingIntent pendingIntent) {
        this.builder.setContentIntent(pendingIntent);
        return this;
    }

    public Load dismiss(@NonNull Class<?> activity, Bundle bundle) {
        Log.d("TEST", "Called dismiss");
        this.builder.setDeleteIntent(new DismissActivity(bundle, notificationId, context).onSettingPendingIntent());
        return this;
    }

    public Load dismiss(@NonNull Class<?> activity) {
        Log.d("TEST", "Called dismiss with null bundle");
        dismiss(activity, null);
        return this;
    }

    public Load dismiss(@NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.setDeleteIntent(pendingIntentNotification.onSettingPendingIntent());
        return this;
    }

    public Load dismiss(@NonNull PendingIntent pendingIntent) {
        this.builder.setDeleteIntent(pendingIntent);
        return this;
    }
    public CustomNotification custom() {
        notificationShallContainAtLeastThoseSmallIconValid();
        return new CustomNotification(builder, notificationId, title, message, messageSpanned, smallIcon, tag);
    }

    public SimpleNotification simple() {
        SimpleNotification sn;
        if(ongoing){
            this.bigTextStyle(message);
            Log.d("TESTING123", "ADDING NOTIFICATION "+title+" with message "+message+" and id : "+notificationId+" as an ongoing message");
            sn = ongoingSimpleNotification();
        }
        else{
            simpleNotificationMessages[messageCount] = message;
            simpleNotificationsIds[simpleCount] = notificationId;
            simpleNotificationTags[simpleCount] = tag;
            messageCount++;
            simpleCount++;
            Log.d("TESTING123","Adding to the simple arrays and count. Count : "+String.valueOf(messageCount)+" notificationId: "+String.valueOf(notificationId)+" message: "+String.valueOf(message));
            if(messageCount>=2){
                for(int i = 0; i < simpleCount; i++){
                    Log.d("TESTING123", "Deleting notification with Id " + String.valueOf(simpleNotificationsIds[i]) + simpleNotificationTags[i]);
                    SceneDocNotification sdocn = new SceneDocNotification(context);
                    sdocn.cancel(simpleNotificationTags[i], simpleNotificationsIds[i]);
                }
                simpleCount = 0;
                clearSettings();
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                for (String inboxLine : simpleNotificationMessages) {
                    inboxStyle.addLine(inboxLine);
                }
                inboxStyle.setBigContentTitle("New Notifications");
                inboxStyle.setSummaryText("You have " + messageCount + " new notifications from SceneDoc.");
                tag = "inboxstyle";
                notificationId = 4289304;
                this.builder.setStyle(inboxStyle);
                this.builder.setAutoCancel(true);
                this.builder.setSmallIcon(smallIcon);
                this.builder.setContentTitle("SceneDoc");
                this.builder.setContentText("Drag down to view your notifications.");
                simpleNotificationsIds[simpleCount] = notificationId;
                simpleNotificationTags[simpleCount] = tag;
                simpleCount++;
                sn = new SimpleNotification(builder, notificationId, tag);
            }
            else{
                sn = new SimpleNotification(builder, notificationId, tag);
                notificationShallContainAtLeastThoseSmallIconValid();
            }
        }
        return sn;
    }
    private SimpleNotification ongoingSimpleNotification(){
        SimpleNotification sn = new SimpleNotification(builder, notificationId, tag);
        notificationShallContainAtLeastThoseSmallIconValid();
        return sn;
    }
    private void notificationShallContainAtLeastThoseSmallIconValid() {
        if (smallIcon <= 0) {
            throw new IllegalArgumentException("This is required. Notifications with an invalid icon resource will not be shown.");
        }
    }
}
