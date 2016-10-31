package com.github.dat210_teamone.skolerute.data;

/*import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.GjesdalSchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.OpenStavangerUtils;
import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.StavangerSchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.PageInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;*/

/**
 * Created by espen on 10.10.16.
 */

// This is your only warning
// DO NOT USE THIS!
/*public class UpdateService extends IntentService {
    private CsvFileReader gjesdalCsvReader;
    private CsvFileReader csvFileReader;

    public UpdateService() {
        super("UpdateServiceName");
        //csvFileReader = new CsvFileReader();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Calendar calendar = Calendar.getInstance();
        Log.d("UpdateService", "About to execute UpdateTask at: " + calendar.getTime());
        //TODO: Fix this, see bug on trello for more details
        new UpdateTask().doInBackground();

    }

    public void doUpdateCheck() {
        new UpdateTask().doInBackground();
    }

    public static class UpdateTask extends AsyncTask<String, Void, Boolean> {
        // This shit is on life support, checks for updates each cold start of the app
        @Override
        protected Boolean doInBackground(String... strings) {
            // TODO: This probably needs some serious refactoring
            Log.d("UpdateTask", "Checking for updated schools");
            SchoolStorage schoolStorage = new SchoolStorage();
            HashMap<String, String[]> schoolUrls = new HashMap<>();
            String skoler[] = new String[]{
                    "http://open.stavanger.kommune.no/dataset/skoler-stavanger",
                    "http://open.stavanger.kommune.no/dataset/skoler-i-gjesdal-kommune"
            };
            String ruter[] = new String[]{
                    "http://open.stavanger.kommune.no/dataset/skolerute-stavanger",
                    "http://open.stavanger.kommune.no/dataset/skoleruten-for-gjesdal-kommune",
            };
            Log.d("UpdateService", "Calling doInBackground within UpdateTask");

            if (OpenStavangerUtils.fileHasBeenUpdated(skoler[0]) || OpenStavangerUtils.fileHasBeenUpdated(skoler[1]))
                schoolStorage.loadSchoolInfo();

            if (OpenStavangerUtils.fileHasBeenUpdated(ruter[0]) || OpenStavangerUtils.fileHasBeenUpdated(ruter[1]))
                schoolStorage.loadSchoolVacationDays();

            return true;
        }
    }

    public static void setUpUpdateService() {
        // TODO: Need to actually set initial update time somewhere, maybe not here
        if(InterfaceManager.getSettings().getLastUpdateTime().equals("")) {
            String lastUpdated = CsvReaderGetter
                    .getInfo("http://open.stavanger.kommune.no/dataset/skolerute-stavanger")
                    .getLastUpdated();
            Log.d("UpdateService", "Setting initial update date: " + lastUpdated);
            InterfaceManager.getSettings().setLastUpdateTime(lastUpdated);
        }
        // TODO: This probably needs some serious refactoring
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());

        // TODO: set check interval to One Month
        //updateTime.add(Calendar.MONTH, 1);

        updateTime.set(Calendar.HOUR_OF_DAY, 8);
        updateTime.set(Calendar.MINUTE, 0);

        Context context = InterfaceManager.getContext();
        Intent downloader = new Intent(context, UpdateReceiver.class);
        downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(),
                1000 * 60 ,// TODO Switch with proper value, AlarmManager.INTERVAL_DAY
                pendingIntent);
        Log.d("MainActivity", "Set alarmManager.setInexactRepeating to: " + updateTime.getTime().toString());
    }

} */
