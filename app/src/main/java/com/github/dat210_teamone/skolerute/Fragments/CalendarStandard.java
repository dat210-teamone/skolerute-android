package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.CalendarViewer;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarStandard.OnCalendarStandardInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarStandard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarStandard extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CalendarViewer calView;
    private HashSet<Date> events ;

    private String mParam1;
    private String mParam2;

    private OnCalendarStandardInteractionListener mListener;

    public CalendarStandard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarStandard.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarStandard newInstance(String param1, String param2) {
        CalendarStandard fragment = new CalendarStandard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar_standard, container, false);

        events = new HashSet<>();
        events.add(new Date());

        MainActivity mainActivity = (MainActivity)getActivity();

        SchoolInfo school=mainActivity.selectedSchools[0];
        SchoolVacationDay vacationDays[] = mainActivity.schoolManager.getSelectedSchoolDays();
        Date[] days=new Date[vacationDays.length];


        for (int x=0; x<vacationDays.length; x++){
            days[x]=vacationDays[x].getDate();
        }

        calView= ((CalendarViewer)view.findViewById(R.id.cal_view));
        calView.updateCalendar(events);


        calView.setEventHandler(new CalendarViewer.EventHandler() {
            @Override
            public void onDayLongPress(Date date)
            {
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(mainActivity, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCalendarStandardInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalendarStandardInteractionListener) {
            mListener = (OnCalendarStandardInteractionListener) context;
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
    public interface OnCalendarStandardInteractionListener {
        // TODO: Update argument type and name
        void onCalendarStandardInteraction(Uri uri);
    }
}
