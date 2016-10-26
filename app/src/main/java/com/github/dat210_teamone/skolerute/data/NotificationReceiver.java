package com.github.dat210_teamone.skolerute.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;

/**
 * Created by Fredrik Wigsnes on 17.10.2016.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //InterfaceManager.SetMainActivity((Activity) context);
        //SchoolManager SM = SchoolManager.getDefault();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1233, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        String title = "Test";
        String comment = "te";
        //SchoolVacationDay SVD;
        //for (SchoolInfo x : SM.getSelectedSchools()) {
        //    SVD = SM.getNextVacationDay(x.getSchoolName(),true);
        //    if (SVD.getDate().after(new Date(System.currentTimeMillis())) && SVD.getDate().before(new Date(System.currentTimeMillis() + 86400000 * 2))) {
        //        title += SVD.getName();
        //        comment += SVD.getComment();
        //    }
        //}
        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(comment);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
