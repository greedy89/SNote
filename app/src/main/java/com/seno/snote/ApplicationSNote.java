package com.seno.snote;

import android.app.Application;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

@Database(name = ApplicationSNote.NAME, version = ApplicationSNote.VERSION)
public class ApplicationSNote extends Application {
    public static final String NAME = "SNoteDb";
    public static final int VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
