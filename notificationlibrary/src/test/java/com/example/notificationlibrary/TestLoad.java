package com.example.notificationlibrary;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.test.mock.MockContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static java.security.AccessController.getContext;
public class TestLoad {

    private Context context = new MockContext();
    @Test
    public void test_load_constructor(){
        SceneDocNotification sceneDocNotification= new SceneDocNotification(context);
        sceneDocNotification.with(context);
        Load load = new Load(context);
        Assert.assertNotNull(load.getContext());
        Assert.assertEquals(0, load.getSimpleCount());
        Assert.assertEquals(0, load.getMessageCount());
        Assert.assertEquals(100, load.getSimpleNotificationMessages().length);
        Assert.assertEquals(100, load.getSimpleNotificationsIds().length);
        Assert.assertEquals(100, load.getSimpleNotificationTags().length);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_load_constructor_with_null(){
        Load load = new Load(null);
        Assert.assertNull(load.getContext());
    }
}
