package com.github.dat210_teamone.skolerute.data;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by espen on 10.10.16.
 */

public class UpdateService extends IntentService {
    private CsvFileReader csvFileReader;

    public UpdateService() {
        super("UpdateServiceName");
        csvFileReader = new CsvFileReader();
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
            // TODO: This probably needs some serious refactoring
            Log.d("UpdateService", "Calling doInBackground within UpdateTask");

            // TODO: Need to actually set inital update time somewhere
            //InterfaceManager.getSettings().setLastUpdateTime("september 21, 2016, 14:46 (CEST)");

            Log.d("UpdateService", "Last updated: "+ InterfaceManager.getSettings().getLastUpdateTime());
            if(CsvReaderGetter.fileHasBeenUpdated("http://open.stavanger.kommune.no/dataset/skolerute-stavanger")) {
                Log.d("UpdateService", "New CSV update found");
                csvFileReader.readSchoolInfoCsv(new CsvReaderGetter().getSchoolReader());
                csvFileReader.readSchoolVacationDayCsv(new CsvReaderGetter().getSchoolDayReader());
                Log.d("UpdateService", "Got updated CSV files");
                String lastUpdated = CsvReaderGetter.getInfo("http://open.stavanger.kommune.no/dataset/skolerute-stavanger").getLastUpdated();
                InterfaceManager.getSettings().setLastUpdateTime(lastUpdated);
            } else {
                Log.d("UpdateService", "CSV already at latest version");
            }
            return true;
        }
    }
}
