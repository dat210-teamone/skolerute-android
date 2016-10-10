package com.github.dat210_teamone.skolerute.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            Calendar calendar = Calendar.getInstance();
            SchoolManager schoolManager = SchoolManager.getDefault();

            schoolManager.settings.setLastUpdateTime("Sun Oct 30 12:00:00 GMT+02:00 2099");
            //schoolManager.settings.setLastUpdateTime("Sun Oct 9 10:00:00 GMT+02:00 2016");

            Log.d("UpdateService", "Last updated: "+ schoolManager.settings.getLastUpdateTime());
            if(newCsvUpdate()) {
                Log.d("UpdateService", "New CSV update found");
                CsvFileReader csvFileReader = new CsvFileReader();
                csvFileReader.readSchoolInfoCsv(new CsvReaderGetter().getSchoolReader());
                csvFileReader.readSchoolVacationDayCsv(new CsvReaderGetter().getSchoolDayReader());
                Log.d("UpdateService", "Got updated CSV files");
            } else {
                Log.d("UpdateService", "CSV already at latest version");
            }
            return true;
        }

        private boolean newCsvUpdate() {
            SchoolManager schoolManager = SchoolManager.getDefault();
            String lastUpdate = schoolManager.settings.getLastUpdateTime();
            Calendar lastUpdateCal = Calendar.getInstance();
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
            try {
                lastUpdateCal.setTime(sdf.parse(lastUpdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return now.after(lastUpdateCal.getTime());
        }
    }
}
