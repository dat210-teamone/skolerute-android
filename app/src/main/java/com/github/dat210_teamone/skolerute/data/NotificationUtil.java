package com.github.dat210_teamone.skolerute.data;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.HashMap;

/**
 * Created by Fredrik Wigsnes on 05.10.2016.
 */

public class NotificationUtil {
    private static NotificationUtil defaultManager;

    private Context con;
    private HashMap<String, Integer> dateToId;
    private HashMap<String, String> dateToTitle;
    private int notiId;
    private SchoolManager SM;

    public NotificationUtil(Context con) {
        this.con = con;
        this.dateToId = new HashMap<>();
        this.dateToTitle = new HashMap<>();
        this.notiId = 0;
        this.SM = SchoolManager.getDefault();
        defaultManager = this;
    }

    public static NotificationUtil getDefault() {
        return defaultManager;
    }

    //Get a schoolVacationDay and create a notification for it.
    public void createNotification(SchoolVacationDay SVD) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(con);
        NotificationManager mNotificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(con, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(con, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        if (dateToTitle.containsKey(SVD.getDate().toString())) {
            dateToTitle.put(SVD.getDate().toString(), dateToTitle.get(SVD.getDate().toString()) + ", " + SVD.getName());
            mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(dateToTitle.get(SVD.getDate().toString())).setContentText(SVD.getComment());
        } else {
            dateToTitle.put(SVD.getDate().toString(), SVD.getName());
            mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(SVD.getName()).setContentText(SVD.getDate().toString());
        }
        dateToId.put(SVD.getDate().toString(), notiId);
        mNotificationManager.notify(dateToId.get(SVD.getDate().toString()), mBuilder.build());
    }

    //Get a schoolObj and create a notification for all the VacationDays.
    public void createNotificationSchool(SchoolInfo SI) {
        for (SchoolVacationDay x : SM.getNextVacationDays(SI.getSchoolName())) {
            createNotification(x);
        }
    }

    //get a list of SchoolObj and create a notification for all school VacationDays.
    public void createNotificationSchools(SchoolInfo[] SIs) {
        for (SchoolInfo x : SIs) {
            createNotificationSchool(x);
        }
    }

    //Remove all notifications
    public void removeAllNotifications() {
        NotificationManager mNotificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
