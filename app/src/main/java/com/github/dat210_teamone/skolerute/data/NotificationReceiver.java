package com.github.dat210_teamone.skolerute.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Date;

/**
 * Created by Fredrik Wigsnes on 17.10.2016.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        InterfaceManager.SetMainContext(context);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1233, i, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        SchoolVacationDay[] days = SchoolManager.getDefault().getNextVacationDays(new Date());
        SchoolVacationDay day = OneUtils.firstOrNull(days);
        String title = context.getResources().getString(R.string.NotiTitle);
        if (day != null){
            title = "Fridag;" + day.getName();
        }
        if (days.length > 1) {
            title += "...";
        }

        String comment = context.getResources().getString(R.string.NotiComment);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(comment);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
