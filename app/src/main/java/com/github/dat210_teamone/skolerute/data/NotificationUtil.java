package com.github.dat210_teamone.skolerute.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.dat210_teamone.skolerute.data.interfaces.INotificationUpdate;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by Fredrik Wigsnes on 05.10.2016.
 */

public class NotificationUtil implements INotificationUpdate {
    private static NotificationUtil defaultManager;

    private Context con;
    private SchoolManager SM;

    public NotificationUtil(Context con) {
        this.con = con;
        this.SM = SchoolManager.getDefault();
        this.SM.subscribe(this);
        defaultManager = this;
    }

    public static NotificationUtil getDefault() {
        return defaultManager;
    }

    @Override
    public void preNotify(UpdateType type, String name) {
        removeAllNotifications();
    }

    @Override
    public void postNotify(UpdateType type, String name, boolean result) {
        createNotification();
    }

    @Override
    public void globalNotifyChange(boolean newValue) {
        if (newValue) {
            createNotification();
        } else {
            removeAllNotifications();
        }
    }

    //This will run when you turn on all notifications
    public void createNotification() {
        if (!SM.getGlobalNotification()) {
            return;
        }
        HashSet hs = new HashSet();
        for (SchoolVacationDay svd : SM.getNextVacationDays(SM.getNotifySchools())) {
            if (!hs.contains(svd.getDate())) {
                hs.add(svd.getDate());
                createNotification(svd);
            }
        }
    }

    //Get a schoolVacationDay and create a Alarmnotification for it.
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

    //This will run when you turn off all notifications.
    public void removeAllNotifications() {
        HashSet hs = new HashSet();
        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, NotificationReceiver.class);
        PendingIntent pi;
        for (SchoolVacationDay svd : SM.getNextVacationDays(SM.getNotifySchools())) {
            if (!hs.contains(svd.getDate())) {
                pi = PendingIntent.getBroadcast(con, (int)svd.getDate().getTime(), i, PendingIntent.FLAG_CANCEL_CURRENT);
                am.cancel(pi);
            }
        }
    }
}
