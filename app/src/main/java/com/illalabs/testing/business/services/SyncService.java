package com.illalabs.testing.business.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.illalabs.testing.business.sync.SyncManager;
import com.illalabs.testing.business.sync.event.EventBusManager;
import com.illalabs.testing.business.sync.event.SyncRequestEvent;
import com.illalabs.testing.business.sync.event.SyncType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SyncService extends Service {
    /** Server status */
    private boolean isServerReachable = true;
    private Disposable subscription;

    /** Sync handlers */
    private ExecutorService executor = null;
    private SyncManager syncManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("onCreate");
        executor = Executors.newSingleThreadExecutor();
        syncManager = new SyncManager(getApplicationContext());
        EventBusManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("onDestroy");
        EventBusManager.unregister(this);

        // Clean observer
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEvent(@NonNull final SyncRequestEvent event) {
        // Only sync if the server is reachable
        if (isServerReachable && (subscription == null || subscription.isDisposed())) {
            Timber.i("Subscription: " + (subscription == null || subscription.isDisposed()));
            subscription = ReactiveNetwork.observeInternetConnectivity()
                    .subscribeOn(Schedulers.io())
                    .subscribe(isConnectedToInternet -> {
                        Timber.i("Status: " + isConnectedToInternet);
                        if (isConnectedToInternet) {
                            subscription.dispose();
                            isServerReachable = true;
                            executor.execute(() -> syncManager.doSync(event.getSyncType()));
                        } else {
                            isServerReachable = false;
                        }
                    });
        }
    }

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    public static void request(@NonNull SyncType type) {
        EventBusManager.send(new SyncRequestEvent(type));
    }
}
