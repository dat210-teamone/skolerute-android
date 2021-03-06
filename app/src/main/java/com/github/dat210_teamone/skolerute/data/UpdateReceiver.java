package com.github.dat210_teamone.skolerute.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by espen on 10.10.16.
 * Part of project skolerute-android
 */

public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dailyUpdater = new Intent(context, UpdateService.class);
        context.startService(dailyUpdater);
        Log.d("UpdateReceiver", "Called context.startService from UpdateReceiver.onReceive");
    }
}
