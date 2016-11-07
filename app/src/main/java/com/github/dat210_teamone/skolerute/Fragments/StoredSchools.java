package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.StoredSchoolsAdapter;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoredSchools.OnStoredSchoolsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoredSchools#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoredSchools extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView storedSchoolsList;
    private TextView finished;


    private OnStoredSchoolsInteractionListener mListener;

    public StoredSchools() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoredSchools.
     */
    // TODO: Rename and change types and number of parameters
    public static StoredSchools newInstance(String param1, String param2) {
        StoredSchools fragment = new StoredSchools();
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
        View view = inflater.inflate(R.layout.fragment_stored_schools, container, false);
/*
        public SchoolManager schoolManager = SchoolManager.getDefault();
        public SchoolInfo[] allSchools = schoolManager.getSchoolInfo();
        public SchoolInfo[] selectedSchools = schoolManager.getSelectedSchools();
        public String[] allSchoolNames = new String[allSchools.length];
*/

        MainActivity mainActivity = (MainActivity) getActivity();

        //  mainActivity.inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0);

        mainActivity.selectedSchools = mainActivity.schoolManager.getSelectedSchools();

        String[] storedSchoolNames = mainActivity.getAllStoredSchoolNames();
        Date[] storedSchoolVacationDays = mainActivity.getAllStoredSchoolDates();

     /*   for (int x = 0; x < mainActivity.selectedSchools.length; x++) {
            storedSchoolNames[x] = mainActivity.selectedSchools[x].getSchoolName();
            storedSchoolVacationDays[x] = mainActivity.schoolManager.getNextVacationDay(storedSchoolNames[x]).getDate();
        } */

        /*for (int x = 0; x < mainActivity.selectedSchools.length; x++) {
            storedSchoolNames[x] = mainActivity.selectedSchools[x].getSchoolName();
        }*/

        StoredSchoolsAdapter storedSchoolsAdapter = new StoredSchoolsAdapter(mainActivity, storedSchoolNames, storedSchoolVacationDays);
        mainActivity.storedSchoolsAdapter = storedSchoolsAdapter;
        storedSchoolsList = (ListView) view.findViewById(R.id.storedSchoolsList);
        storedSchoolsList.setAdapter(storedSchoolsAdapter);

        ImageView settingBtn = (ImageView) view.findViewById(R.id.stored_schools_menu);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu settingsMenu = new PopupMenu(mainActivity, settingBtn);

                settingsMenu.inflate(R.menu.stored_school_popup_menu);
                settingsMenu.show();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onStoredSchoolsInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStoredSchoolsInteractionListener) {
            mListener = (OnStoredSchoolsInteractionListener) context;
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

    @Override
    public void onPause() {
        super.onPause();
        MainActivity mainActivity = (MainActivity)getActivity();
        TextView addSchoolButton = (TextView) mainActivity.findViewById(R.id.go_to_add);
        ImageView calendarViewToggle = (ImageView) mainActivity.findViewById(R.id.calendar_view_toggle);
        addSchoolButton.setVisibility(View.INVISIBLE);
        calendarViewToggle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity mainActivity = (MainActivity)getActivity();
        TextView addSchoolButton = (TextView) mainActivity.findViewById(R.id.go_to_add);
        ImageView calendarViewToggle = (ImageView) mainActivity.findViewById(R.id.calendar_view_toggle);
        addSchoolButton.setVisibility(View.VISIBLE);
        calendarViewToggle.setVisibility(View.VISIBLE);
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
    public interface OnStoredSchoolsInteractionListener {
        // TODO: Update argument type and name
        void onStoredSchoolsInteraction(Uri uri);
    }
}
