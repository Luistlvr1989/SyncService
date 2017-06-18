package com.illalabs.testing.business.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.illalabs.testing.business.sync.elements.contract.AbsSync;
import com.illalabs.testing.business.sync.elements.EntriesSync;
import com.illalabs.testing.business.sync.event.SyncType;

import java.util.HashMap;

/**
 * Created by hei on 17/06/17.
 * Handles the sync from the local database to the cloud
 */
public class SyncManager {
    private HashMap<SyncType, AbsSync> syncMap;

    public SyncManager(@NonNull Context context) {
        syncMap = new HashMap<>();
        syncMap.put(SyncType.ENTRIES, new EntriesSync(context));
    }

    public void doSync(@NonNull SyncType syncType) {
        syncMap.get(syncType).sync();
    }
}
