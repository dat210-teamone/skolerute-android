package com.github.dat210_teamone.skolerute.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.CalendarViewer;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class CalendarStandard extends Fragment {

    private CalendarViewer calView;
    private HashSet<Date> events;

    public CalendarStandard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar_standard, container, false);

        events = new HashSet<>();

        MainActivity mainActivity = (MainActivity) getActivity();

        SchoolVacationDay vacationDays[] = mainActivity.schoolManager.getSelectedSchoolDays();
        Date[] days = new Date[vacationDays.length];
        Date maxDay = new Date();

        for (int i = 0; i < vacationDays.length; i++) {
            days[i] = vacationDays[i].getDate();
            events.add(days[i]);
            if (days[i].after(maxDay)) {
                maxDay = days[i];
            }
        }

        calView = ((CalendarViewer) view.findViewById(R.id.calendar_view));
        calView.setMaxDate(maxDay);
        calView.updateCalendar(events);

        calView.setEventHandler(date -> {
            DateFormat df = SimpleDateFormat.getDateInstance();
            SchoolVacationDay info[] = SchoolManager.getDefault().getNextVacationDays(date);
            if (info.length > 0) {
                String schools = "";
                AlertDialog alertDialog = new AlertDialog.Builder(CalendarStandard.super.getContext()).create();
                alertDialog.setTitle(df.format(date) + ((info[0].getComment().length() > 0) ? "\n" + info[0].getComment() : ""));
                for (SchoolVacationDay anInfo : info) {
                    schools += (anInfo.isSfoDay()) ? anInfo.getName() + "\n" : anInfo.getName() + " - SFO stengt" + "\n";
                }
                alertDialog.setMessage(schools);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        (dialog, which) -> {
                            dialog.dismiss();
                        });
                alertDialog.setIcon(R.drawable.calendar_icon_white);
                alertDialog.show();
            }
        });

        return view;
    }
}
