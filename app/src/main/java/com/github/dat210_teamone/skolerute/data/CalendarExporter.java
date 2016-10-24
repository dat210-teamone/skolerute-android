package com.github.dat210_teamone.skolerute.data;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by espen on 24.10.16.
 */

public class CalendarExporter {
    private Activity activity;
    private SchoolManager schoolManager;
    private SchoolVacationDay schoolVacationDay[];
    private final int MY_PERMISSIONS_REQUEST_READ_WRITE_CALENDAR = 1;

    String headers[] = {
            "Subject,Start date,Start time,End date,End time,All day event,description,Location,Private"
    };

    public CalendarExporter(Activity activity) {
        this.activity = activity;
        schoolManager = SchoolManager.getDefault();
        schoolVacationDay = schoolManager.getSelectedSchoolDays();
    }

    public void exportToGoogle() {
        try {
            String eventUriString = "content://com.android.calendar/events";

            for(int i = 0; i < 3 ; i++) {
                Date schoolDate = schoolVacationDay[i].getDate();
                Calendar startDate = new GregorianCalendar();
                startDate.setTime(schoolDate);

                if (!checkIfExists("Fridag - " + schoolVacationDay[i].getName(), schoolDate)) {
                    String description = "";
                    description = schoolVacationDay[i].isSfoDay() ? description + "SFO dag" : description + "";
                    description = schoolVacationDay[i].getComment().isEmpty() ? "" : description + "\n" + schoolVacationDay[i].getComment();
                    ContentValues values = new ContentValues();
                    values.put(CalendarContract.Events.CALENDAR_ID, 1);
                    values.put(CalendarContract.Events.TITLE, "Fridag - " + schoolVacationDay[i].getName());
                    values.put(CalendarContract.Events.DESCRIPTION, description);
                    values.put(CalendarContract.Events.ALL_DAY, true);


                    values.put(CalendarContract.Events.DTSTART, startDate.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, startDate.getTimeInMillis());
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

                    Log.d("CalendarExporter", "Adding " + "Fridag - " + schoolVacationDay[i].getName());
                    Uri eventUri = InterfaceManager.getContext()
                            .getContentResolver()
                            .insert(Uri.parse(eventUriString), values);
                    Long eventID = Long.parseLong(eventUri.getLastPathSegment());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Log.d("CalendarExporter", "Added event with ID " + eventID + "at " + sdf.format(schoolVacationDay[i].getDate()));
                } else {
                    Log.d("CalendarExporter", "Entry exists, skipping");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfExists(String eventTitle, Date eventDate) {
        Calendar startDate = new GregorianCalendar();
        startDate.setTime(eventDate);
        String[] entry = new String[] {
                CalendarContract.Instances._ID,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Instances.EVENT_ID
        };
        Cursor cursor = CalendarContract.Instances.query(InterfaceManager.getContext().getContentResolver(), entry, startDate.getTimeInMillis(), startDate.getTimeInMillis(), eventTitle);
        return cursor.getCount() > 0;
    }

    public void getCalendarPermissions() {
        if (ContextCompat.checkSelfPermission(InterfaceManager.getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.READ_CALENDAR,
                    },
                    MY_PERMISSIONS_REQUEST_READ_WRITE_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(InterfaceManager.getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.WRITE_CALENDAR,
                    },
                    MY_PERMISSIONS_REQUEST_READ_WRITE_CALENDAR);
        }
    }
}
