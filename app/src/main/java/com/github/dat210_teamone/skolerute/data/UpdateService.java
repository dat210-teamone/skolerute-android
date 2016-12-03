package com.github.dat210_teamone.skolerute.data;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.OpenStavangerUtils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by espen on 10.10.16.
 */

public class UpdateService extends IntentService {
    private final String[] schools = new String[]{
            "http://open.stavanger.kommune.no/dataset/schools-stavanger",
            "http://open.stavanger.kommune.no/dataset/schools-i-gjesdal-kommune"
    };
    private final String[] routes = new String[]{
            "http://open.stavanger.kommune.no/dataset/skolerute-stavanger",
            "http://open.stavanger.kommune.no/dataset/skoleruten-for-gjesdal-kommune",
    };

    public UpdateService() {
        super("UpdateServiceName");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InterfaceManager.SetMainContext(this);
        Calendar calendar = Calendar.getInstance();
        Log.d("UpdateService", "About to execute UpdateTask at: " + calendar.getTime());
        new UpdateTask().doInBackground();
    }

    private class UpdateTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d("UpdateService", "Calling doInBackground within UpdateTask");
            Log.d("UpdateService", "Last updated: "+ InterfaceManager.getSettings().getLastUpdateTime());
            if(OpenStavangerUtils.fileHasBeenUpdated(schools[0])
                    || OpenStavangerUtils.fileHasBeenUpdated(schools[1])
                    || OpenStavangerUtils.fileHasBeenUpdated(routes[0])
                    || OpenStavangerUtils.fileHasBeenUpdated(routes[1]))
            {
                Log.d("UpdateService", "New CSV update found");
                InterfaceManager.getStorage().forceUpdate();
                Log.d("UpdateService", "Got updated CSV files");
                String lastUpdated = OpenStavangerUtils.getInfo("http://open.stavanger.kommune.no/dataset/skolerute-stavanger").getLastUpdated();
                InterfaceManager.getSettings().setLastUpdateTime(lastUpdated);
            } else {
                Log.d("UpdateService", "CSV already at latest version");
            }
            return true;
        }
    }

    public static void setUpUpdateService() {
        if(InterfaceManager.getSettings().getLastUpdateTime().equals("")) {
            String lastUpdated = OpenStavangerUtils
                    .getInfo("http://open.stavanger.kommune.no/dataset/skolerute-stavanger")
                    .getLastUpdated();
            Log.d("UpdateService", "Setting initial update date: " + lastUpdated);
            InterfaceManager.getSettings().setLastUpdateTime(lastUpdated);
        }
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());

        updateTime.set(Calendar.HOUR_OF_DAY, 8);
        updateTime.set(Calendar.MINUTE, 0);

        Context context = InterfaceManager.getContext();
        Intent downloader = new Intent(context, UpdateReceiver.class);
        downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
        Log.d("MainActivity", "Set alarmManager.setInexactRepeating to: " + updateTime.getTime().toString());
    }
}
