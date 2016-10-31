package com.github.dat210_teamone.skolerute.data;

import android.os.AsyncTask;
import android.util.Log;

import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.OpenStavangerUtils;

import java.util.HashMap;

/**
 * Created by espen on 31.10.16.
 */

public class UpdateTask extends AsyncTask<String, Void, Boolean> {
    // This shit is on life support, checks for updates each cold start of the app
    @Override
    protected Boolean doInBackground(String... strings) {
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

        if (OpenStavangerUtils.fileHasBeenUpdated(skoler[0]) || OpenStavangerUtils.fileHasBeenUpdated(skoler[1]))
            schoolStorage.loadSchoolInfo();

        if (OpenStavangerUtils.fileHasBeenUpdated(ruter[0]) || OpenStavangerUtils.fileHasBeenUpdated(ruter[1]))
            schoolStorage.loadSchoolVacationDays();

        return true;
    }
}
