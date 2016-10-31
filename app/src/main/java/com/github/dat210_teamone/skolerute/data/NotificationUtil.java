package com.github.dat210_teamone.skolerute.data;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Calendar;

import java.util.HashSet;

/**
 * Created by Fredrik Wigsnes on 05.10.2016.
 */

public class NotificationUtil {
    private static NotificationUtil defaultManager;

    private Context con;
    private int notiId;
    private SchoolManager SM;
    private NotificationManager mNotificationManager;
    private NotificationReceiver NR;

    public NotificationUtil(Context con) {
        this.con = con;
        this.notiId = 0;
        this.SM = SchoolManager.getDefault();
        defaultManager = this;
        NR = new NotificationReceiver();
    }

    public static NotificationUtil getDefault() {
        return defaultManager;
    }

    public void createNotification() {
        HashSet hs = new HashSet();
        for (SchoolInfo s : SM.getSelectedSchools()) {
            for (SchoolVacationDay v : SM.getNextVacationDays(s.getSchoolName())) {
                if (!hs.contains(v.getDate())) {
                    hs.add(v.getDate());
                    createNotification(v);
                }
            }
        }
    }


    //Get a schoolVacationDay and create a notification for it.
    public void createNotification(SchoolVacationDay SVD) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(SVD.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent i = new Intent(con, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(con, (int)SVD.getDate().getTime(), i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public void removeAllNotifications() {
        HashSet hs = new HashSet();
        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, NotificationReceiver.class);
        PendingIntent pi;
        for (SchoolInfo SI : SM.getSelectedSchools()) {
            for (SchoolVacationDay SVD : SM.getNextVacationDays(SI.getSchoolName())) {
                if (!hs.contains(SVD.getDate())) {
                    pi = PendingIntent.getBroadcast(con, (int)SVD.getDate().getTime(), i, PendingIntent.FLAG_CANCEL_CURRENT);
                    am.cancel(pi);
                }
            }
        }
    }
}
