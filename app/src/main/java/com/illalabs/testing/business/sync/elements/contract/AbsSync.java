package com.illalabs.testing.business.sync.elements.contract;

import android.content.Context;
import android.support.annotation.NonNull;

import com.illalabs.testing.business.sync.event.SyncEvent;
import com.illalabs.testing.business.sync.event.SyncStatus;
import com.illalabs.testing.business.sync.event.SyncType;

/**
 * Created by hei on 17/06/17.
 * Abstract class that contains the basic logic for the sync
 */
public abstract class AbsSync {
    private Context context;

    public AbsSync(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Syncs the data with the cloud
     */
    public void sync() {
        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);
        post(context);
    }

    protected abstract SyncType getSyncType();

    protected abstract void post(Context context);
}
