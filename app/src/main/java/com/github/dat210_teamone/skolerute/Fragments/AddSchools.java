package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.AddSchoolsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSchools.OnAddSchoolsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSchools#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSchools extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView schoolsList;


    private OnAddSchoolsInteractionListener mListener;

    public AddSchools() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSchools.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSchools newInstance(String param1, String param2) {
        AddSchools fragment = new AddSchools();
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

        View view = inflater.inflate(R.layout.fragment_add_schools, container, false);


        MainActivity mainActivity = (MainActivity)getActivity();

        for (int x = 0; x< mainActivity.allSchools.length; x++){
            mainActivity.allSchoolNames[x]=mainActivity.allSchools[x].getSchoolName();
        }

        AddSchoolsAdapter itemsAdapter =
                new AddSchoolsAdapter(mainActivity, mainActivity.allSchoolNames);





        schoolsList = (ListView)view.findViewById(R.id.schoolsList);
        schoolsList.setAdapter(itemsAdapter);



        AdapterView.OnItemClickListener
                mMessageClickedHandler =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent,
                                            View v,
                                            int position,
                                            long id) {
                        String schoolName = mainActivity.allSchoolNames[(int)id];
                        ((TextView)v).setText(schoolName + " er valgt");
                    }
                };

        schoolsList.setOnItemClickListener(
                mMessageClickedHandler);



        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddSchoolsInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddSchoolsInteractionListener) {
            mListener = (OnAddSchoolsInteractionListener) context;
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
    public interface OnAddSchoolsInteractionListener {
        // TODO: Update argument type and name
        void onAddSchoolsInteraction(Uri uri);
    }
}




/*  Gammel kode fra "Nearest School"-activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DummyStorage schoolManager=new DummyStorage();
        SchoolInfo[] allSchools=schoolManager.getSchoolInfo();
        String[] allSchoolNames= new String[allSchools.length];
        for(int x=0; x<allSchools.length; x++){
            allSchoolNames[x]=allSchools[x].getSchoolName();
        }

        ListView listet=(ListView)findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < allSchoolNames.length; ++i) {
            list.addSelectedSchool(allSchoolNames[i]);
        }
        StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listet.setAdapter(adapter);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.getSelectedSchools(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.getSelectedSchools(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
 */
