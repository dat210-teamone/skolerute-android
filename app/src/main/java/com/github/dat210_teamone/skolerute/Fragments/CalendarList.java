package com.github.dat210_teamone.skolerute.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

    private boolean scrollUpdate = false;
    private int maxHidden = 0;

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

        calendarList.setOnItemClickListener(this::showSchoolBox);
        calendarList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!scrollUpdate) {
                    scrollUpdate = true;
                    if (firstVisibleItem > 5) {
                        mainActivity.collapseMainFragment();
                    }
                    else if(firstVisibleItem < 1 && maxHidden > 5) {
                        maxHidden = 0;
                        mainActivity.expandMainFragment();
                }
                    maxHidden = Math.max(maxHidden, firstVisibleItem);
                    Log.d("SCROLLER", "onScroll: " + firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);
                    scrollUpdate = false;
                }
            }
        });

        return view;
    }



    @SuppressWarnings("UnusedParameters")
    private void showSchoolBox(AdapterView<?> parent, View view, int position, long id){
        SchoolVacationDay day = (SchoolVacationDay) parent.getAdapter().getItem(position);
        DateFormat df = SimpleDateFormat.getDateInstance();
        Date date = day.getDate();

        AlertDialog alertDialog = new AlertDialog.Builder(CalendarList.super.getContext()).create();
        alertDialog.setTitle(df.format(date) + ((day.getComment().length() > 0) ? "\n" + day.getComment() : ""));
        alertDialog.setMessage(day.getName() + "\n" + (day.isStudentDay() ? "" : "- Skole stengt\n") + (day.isSfoDay() ? "" : "- SFO stengt"));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                });
        alertDialog.setIcon(R.drawable.calendar_icon_white);
        alertDialog.show();
    }
}