package com.github.dat210_teamone.skolerute.data;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
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
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);

        Intent i = new Intent(con, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(con, SVD.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    //Remove all notifications
    public void removeAllNotifications() {
        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);

        Intent updateServiceIntent = new Intent(con, MainActivity.class);
        PendingIntent pendingUpdateIntent = PendingIntent.getService(con, 1233, updateServiceIntent, 0);

        // Cancel alarms
        try {
            alarmManager.cancel(pendingUpdateIntent);
        } catch (Exception e) {

        }
    }
}
