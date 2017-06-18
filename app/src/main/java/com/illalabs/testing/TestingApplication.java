package com.illalabs.testing;

import android.app.Application;

import com.illalabs.testing.business.services.SyncService;

import timber.log.Timber;

/**
 * Created by hei on 17/06/17.
 * Application for the app
 */
public class TestingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        SyncService.start(this);
    }
}
