package com.github.dat210_teamone.skolerute.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by espen on 10.10.16.
 */

public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateServiceName");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        Log.d("UpdateService", "About to execute UpdateTask at: " + calendar.getTime());
        new UpdateTask().doInBackground();
    }

    private class UpdateTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d("UpdateService", "Calling doInBackground within UpdateTask");
            return false;
        }
    }
}
