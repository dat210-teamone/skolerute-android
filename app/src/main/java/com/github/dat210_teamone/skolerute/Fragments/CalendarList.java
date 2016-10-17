package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.StoredSchoolsAdapter;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Date;

public class CalendarList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView calendarList;
    private Button list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCalendarListInteractionListener mListener;

    public CalendarList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarList.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarList newInstance(String param1, String param2) {
        CalendarList fragment = new CalendarList();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_list, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();

        int number=mainActivity.getPosisjon();

        SchoolInfo school=mainActivity.selectedSchools[number];
        SchoolVacationDay vacationDays[] = mainActivity.schoolManager.getNextVacationDays(school.getSchoolName());
        Date[] days=new Date[vacationDays.length];
        String date[]=new String[days.length];

        for (int x=0; x<vacationDays.length; x++){
            days[x]=vacationDays[x].getDate();
            date[x]=days[x].toString();
        }

        ArrayAdapter calendarListAdapter = new ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, date);

        list = (Button) view.findViewById(R.id.button_liste);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.goToCalendarView();

            }
        });

        calendarList = (ListView)view.findViewById(R.id.calendar_list);
        calendarList.setAdapter(calendarListAdapter);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCalendarListInteraction(uri);
        }
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