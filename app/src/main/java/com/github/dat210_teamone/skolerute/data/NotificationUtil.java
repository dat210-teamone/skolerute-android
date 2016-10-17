package com.github.dat210_teamone.skolerute.data;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    //Get a schoolVacationDay and create a notification for it.
    public void createNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);

        Intent i = new Intent(con, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(con, 1233, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    //Remove all notifications
    public void removeAllNoddtifications() {
        NotificationManager mNotificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
