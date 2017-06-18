package com.illalabs.testing.business.sync.elements;

import android.content.Context;
import android.support.annotation.NonNull;

import com.illalabs.testing.business.model.Entry;
import com.illalabs.testing.business.sync.elements.contract.AbsSync;
import com.illalabs.testing.business.sync.event.SyncEvent;
import com.illalabs.testing.business.sync.event.SyncStatus;
import com.illalabs.testing.business.sync.event.SyncType;

import java.util.List;

/**
 * Created by hei on 17/06/17.
 * Class that sync the entries
 */
public class EntriesSync extends AbsSync {
    public EntriesSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.ENTRIES;
    }

    @Override
    protected void post(Context context) {
        SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
        List<Entry> entries = Entry.listAll(Entry.class);
    }
}