package com.example.rianamcbride.testnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notificationlibrary.ClickActivity;
import com.example.notificationlibrary.DismissActivity;
import com.example.notificationlibrary.ImageLoader;
import com.example.notificationlibrary.Load;
import com.example.notificationlibrary.OnImageLoaded;
import com.example.notificationlibrary.SceneDocNotification;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity implements ImageLoader{

    private EditText mEdtTitle, mEdtMessage, mEdtBigText;
    private Button mBtnNotifySimple, mBtnNotifyCustom, mBtnNotifyOngoingStart, mBtnNotifyOngoingStop, mBtnNotifySimple2;
    private Context mContext;
    // Keep a strong reference to keep it from being garbage collected inside into method
    private com.squareup.picasso.Target viewTarget;
    private Spinner mSpnType;
    private RelativeLayout mContentBigText;
    private int mPosSelected = 0;
    private Load mLoad;
    private static com.squareup.picasso.Target getViewTarget(final OnImageLoaded onCompleted) {
        return new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                onCompleted.imageLoadingCompleted(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception exception, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        loadInfoComponents();
        loadListeners();
        mLoad = SceneDocNotification.with(MainActivity.this).load();
    }

    private void loadInfoComponents() {
        this.mEdtTitle = (EditText) findViewById(R.id.edt_title);
        this.mEdtMessage = (EditText) findViewById(R.id.edt_message);
        this.mEdtBigText = (EditText) findViewById(R.id.edt_bigtext);
        this.mBtnNotifySimple = (Button) findViewById(R.id.btn_notify_simple);
        this.mBtnNotifyCustom = (Button) findViewById(R.id.btn_notify_custom);
        this.mBtnNotifyOngoingStart = findViewById(R.id.btn_notify_ongoing_start);
        this.mBtnNotifyOngoingStop = findViewById(R.id.btn_notify_ongoing_stop);
        this.mSpnType = (Spinner) findViewById(R.id.spn_notification_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.scenedoc_notification_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mSpnType.setAdapter(adapter);
        this.mBtnNotifySimple2 = findViewById(R.id.btn_notify_simple_1);
        this.mContentBigText = (RelativeLayout) findViewById(R.id.content_bigtext);
    }

    private void loadListeners() {
        mSpnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosSelected = position;
                switch (position) {
                    case 0:
                        mContentBigText.setVisibility(View.GONE);
                        break;
                    case 1:
                        mContentBigText.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mContentBigText.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBtnNotifySimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEdtTitle.getText().toString();
                String message = mEdtMessage.getText().toString();
                String bigtext = mEdtBigText.getText().toString();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("identifier", 43789057);
                if (title.length() > 0 && message.length() > 0) {
                    mLoad.identifier(43789057)
                            .smallIcon(R.drawable.ic_launcher)
                            .autoCancel(true)
                            .largeIcon(R.drawable.ic_launcher)
                            .title(title)
                            .button(R.drawable.ic_launcher_foreground, "View", new ClickActivity(bundle1, 43789057, MainActivity.class))
                            .button(R.drawable.ic_launcher_foreground, "Dismiss", new DismissActivity(bundle1, 43789057, mContext))
                            .message(message)
                            .flags(android.app.Notification.FLAG_AUTO_CANCEL);

                    switch (mPosSelected) {
                        case 0:
                            mLoad.simple()
                                    .build();
                            break;
                        case 1:
                            if (bigtext.length() > 0) {
                                mLoad.bigTextStyle(bigtext, message)
                                        .simple()
                                        .build();
                            } else {
                                notifyEmptyFields();
                            }
                            break;
                        case 2:
                            mLoad.inboxStyle(getResources().
                                            getStringArray(R.array.scenedoc_notification_inbox_lines),
                                    title,
                                    getResources().getString(R.string.scenedoc_text_summary))
                                    .simple()
                                    .build();
                            break;
                    }
                } else {
                    notifyEmptyFields();
                }
            }
        });
        mBtnNotifySimple2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEdtTitle.getText().toString();
                String message = mEdtMessage.getText().toString();
                String bigtext = mEdtBigText.getText().toString();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("identifier", 47580475);
                if (title.length() > 0 && message.length() > 0) {
                    mLoad.identifier(47580475)
                            .smallIcon(R.drawable.ic_launcher)
                            .autoCancel(true)
                            .largeIcon(R.drawable.ic_launcher)
                            .tag("dismissable notification")
                            .title(title)
                            .message(message)
                            .flags(android.app.Notification.FLAG_AUTO_CANCEL);

                    switch (mPosSelected) {
                        case 0:
                            mLoad.simple()
                                    .build();
                            break;
                        case 1:
                            if (bigtext.length() > 0) {
                                mLoad.bigTextStyle(bigtext, message)
                                        .simple()
                                        .build();
                            } else {
                                notifyEmptyFields();
                            }
                            break;
                        case 2:
                            mLoad.inboxStyle(getResources().
                                            getStringArray(R.array.scenedoc_notification_inbox_lines),
                                    title,
                                    getResources().getString(R.string.scenedoc_text_summary))
                                    .simple()
                                    .build();
                            break;
                    }
                    mLoad.clearSettings();
                } else {
                    notifyEmptyFields();
                }
            }
        });
        mBtnNotifyOngoingStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = mEdtTitle.getText().toString();
                String message = mEdtMessage.getText().toString();
                String bigtext = mEdtBigText.getText().toString();
                if (title.length() > 0 && message.length() > 0) {
                    mLoad.smallIcon(R.drawable.ic_launcher)
                            .autoCancel(true)
                            .largeIcon(R.drawable.ic_launcher)
                            .title(title)
                            .ongoing(true)
                            .tag("ongoing")
                            .identifier(12345)
                            .message(message)
                            .bigTextStyle(bigtext, message)
                            .flags(android.app.Notification.DEFAULT_ALL)
                            .simple()
                            .build();
                    mLoad.clearSettings();
                } else {
                    notifyEmptyFields();
                }
            }
        });
        mBtnNotifyOngoingStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneDocNotification sceneDocNotification = new SceneDocNotification(MainActivity.this);
                sceneDocNotification.cancel("ongoing", 12345);
            }
        });
        mBtnNotifyCustom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String title = mEdtTitle.getText().toString();
                String message = mEdtMessage.getText().toString();
                String bigtext = mEdtBigText.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(message) || TextUtils.isEmpty(bigtext)) {
                    mSpnType.setSelection(1);
                    notifyEmptyFields();
                    return;
                }
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                mLoad.title(title)
                        .message(message)
                        .identifier(4890)
                        .bigTextStyle(bigtext)
                        .smallIcon(R.drawable.ic_launcher)
                        .largeIcon(R.drawable.ic_launcher)
                        .color(android.R.color.background_dark)
                        .custom()
                        .setImageLoader(MainActivity.this)
                        .background(bytes)
                        .setPlaceholder(R.drawable.ic_launcher)
                        .build();
            }
        });
    }

    private void notifyEmptyFields() {
        Toast.makeText(getApplicationContext(),
                R.string.scenedoc_text_empty_fields,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void load(String uri, final OnImageLoaded onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.get().load(uri).into(viewTarget);
    }

    @Override
    public void load(int imageResId, OnImageLoaded onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.get().load(imageResId).into(viewTarget);
    }
}
