package com.github.dat210_teamone.skolerute.data;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.HashMap;

/**
 * Created by Fredrik Wigsnes on 05.10.2016.
 */

public class NotificationUtil extends MainActivity {

    private static HashMap<Integer, PageInfo> infoCache = new HashMap<>();

    public NotificationUtil() {

    }
    //Get a schoolVacationDay and create a notification for it.
    public void createNotification(SchoolVacationDay SVD) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(SVD.getName()).setContentText(SVD.getDate().toString());

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(10001, mBuilder.build());
    }

    //Get a schoolObj and create a notification for all the VacationDays.

    //get a list of SchoolObj and create a notification for all school VacationDays.
}
