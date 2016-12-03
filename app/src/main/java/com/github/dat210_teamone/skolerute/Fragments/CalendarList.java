package com.github.dat210_teamone.skolerute.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.VacationDaysListAdapter;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarList extends Fragment {

    public CalendarList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        String[] checkedSchools = mainActivity.schoolsToView.toArray(new String[mainActivity.schoolsToView.size()]);
        SchoolVacationDay[] vacationDays = SchoolManager.getDefault().getNextVacationDays(checkedSchools);

        // Generate objects to display based on selected schools
        VacationDaysListAdapter calendarListAdapter = new VacationDaysListAdapter(mainActivity, vacationDays);

        ListView calendarList = (ListView) view.findViewById(R.id.calendar_list);
        calendarList.setAdapter(calendarListAdapter);

        calendarList.setOnItemClickListener((parent, view1, position, id) -> {
            SchoolVacationDay day = (SchoolVacationDay) parent.getAdapter().getItem(position);
            DateFormat df = SimpleDateFormat.getDateInstance();
            Date date = day.getDate();
            //SchoolInfo info = SchoolManager.getDefault().getSchoolInfo(day.getName());
            AlertDialog alertDialog = new AlertDialog.Builder(CalendarList.super.getContext()).create();
            alertDialog.setTitle(df.format(date) + ((day.getComment().length() > 0) ? "\n" + day.getComment() : ""));
            alertDialog.setMessage(day.getName() + "\n" + (day.isStudentDay() ? "" : "- Skole stengt\n") + (day.isSfoDay() ? "" : "- SFO stengt"));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> {
                        dialog.dismiss();
                    });
            alertDialog.setIcon(R.drawable.calendar_icon_white);
            alertDialog.show();
        });

        return view;
    }
}