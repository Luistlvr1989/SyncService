package com.illalabs.testing.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.illalabs.testing.R;
import com.illalabs.testing.business.services.SyncService;
import com.illalabs.testing.business.sync.event.SyncType;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View v) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> SyncService.request(SyncType.ENTRIES)).start();
        }
    }
}
