package com.github.dat210_teamone.skolerute.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Date;

/**
 * Created by Fredrik Wigsnes on 17.10.2016.
 */

public class NotificationReceiver extends BroadcastReceiver {
    SchoolManager SM;

    public NotificationReceiver() {
        SM = SchoolManager.getDefault();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        String title = "";
        SchoolVacationDay SVD = new SchoolVacationDay();
        for (SchoolInfo x : SM.getSelectedSchools()) {
            SVD = SM.getNextVacationDay(x.getSchoolName());
            if (SVD.getDate().equals(new Date(System.currentTimeMillis() + 86400))) {
                title += SVD.getName();
            }
        }
        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(SVD.getComment());
        mNotificationManager.notify(0, mBuilder.build());
    }
}
