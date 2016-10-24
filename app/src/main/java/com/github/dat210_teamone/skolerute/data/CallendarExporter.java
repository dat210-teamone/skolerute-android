package com.github.dat210_teamone.skolerute.data;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;

import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

/**
 * Created by espen on 24.10.16.
 */

public class CallendarExporter {
    private SchoolManager schoolManager;
    private SchoolVacationDay schoolVacationDay[];

    String headers[] = {
            "Subject,Start date,Start time,End date,End time,All day event,description,Location,Private"
    };

    public CallendarExporter() {
        schoolManager = SchoolManager.getDefault();
        schoolVacationDay = schoolManager.getSelectedSchoolDays();
    }

    @RequiresPermission(Manifest.permission.WRITE_CALENDAR)
    public void exportToGoogle() {
        ContentResolver contentResolver = InterfaceManager.getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "Planleggingsdag");
        values.put(CalendarContract.Events.ALL_DAY, true);
        values.put(CalendarContract.Events.DTSTART, "2016-10-24");
        if (ActivityCompat.checkSelfPermission(InterfaceManager.getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
    }
}
