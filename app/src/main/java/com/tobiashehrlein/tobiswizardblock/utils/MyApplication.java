package com.tobiashehrlein.tobiswizardblock.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.tobiashehrlein.tobiswizardblock.BuildConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by Tobi-Laptop on 23.01.2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myRealm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
