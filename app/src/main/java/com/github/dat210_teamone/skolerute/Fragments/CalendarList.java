package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
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
    private ListView calendarList;
    private SchoolVacationDay[] vacationDays;

    private OnCalendarListInteractionListener mListener;

    public CalendarList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        String[] checkedSchools = mainActivity.schoolsToView.toArray(new String[mainActivity.schoolsToView.size()]);
        vacationDays = SchoolManager.getDefault().getNextVacationDays(checkedSchools);

        // Generate objects to display based on selected schools
        VacationDaysListAdapter calendarListAdapter = new VacationDaysListAdapter(mainActivity, vacationDays);

        calendarList = (ListView)view.findViewById(R.id.calendar_list);
        calendarList.setAdapter(calendarListAdapter);

        calendarList.setOnItemClickListener((parent, view1, position, id) -> {
            SchoolVacationDay day = (SchoolVacationDay) parent.getAdapter().getItem(position);
            DateFormat df = SimpleDateFormat.getDateInstance();
            Date date = day.getDate();
            //SchoolInfo info = SchoolManager.getDefault().getSchoolInfo(day.getName());
            AlertDialog alertDialog = new AlertDialog.Builder(CalendarList.super.getContext()).create();
            alertDialog.setTitle(df.format(date) + ((day.getComment().length() > 0) ?  "\n" + day.getComment() : ""));
            alertDialog.setMessage(day.getName()+ "\n" + (day.isStudentDay() ? "" : "- Skole stengt\n") + (day.isSfoDay() ? "" : "- SFO stengt"));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> {
                        dialog.dismiss();
                    });
            alertDialog.setIcon(R.drawable.calendar_icon_white);
            alertDialog.show();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalendarListInteractionListener) {
            mListener = (OnCalendarListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCalendarListInteractionListener {
        // TODO: Update argument type and name
        void onCalendarListInteraction(Uri uri);
    }
}