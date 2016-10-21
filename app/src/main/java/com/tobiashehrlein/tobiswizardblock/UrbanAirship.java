package com.tobiashehrlein.tobiswizardblock;

import android.app.Application;

import com.urbanairship.UAirship;

/**
 * Created by Tobias on 16.10.2016.
 */

public class UrbanAirship extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        UAirship.takeOff(this, new UAirship.OnReadyCallback()
        {
            @Override
            public void onAirshipReady(UAirship uAirship)
            {
                uAirship.getPushManager().setUserNotificationsEnabled(true);
            }
        });
    }
}
