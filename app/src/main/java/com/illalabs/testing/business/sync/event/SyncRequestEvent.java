package com.illalabs.testing.business.sync.event;

import android.support.annotation.NonNull;

/**
 * Created by hei on 17/06/17.
 * handles a sync request
 */
public class SyncRequestEvent {
    private SyncType syncType;

    public SyncRequestEvent(@NonNull SyncType syncType) {
        this.syncType = syncType;
    }

    public SyncType getSyncType() {
        return syncType;
    }
}
