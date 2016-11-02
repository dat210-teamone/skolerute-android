package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.AddSchoolsAdapter;
import com.github.dat210_teamone.skolerute.adapters.SearchSchoolsAdapter;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.ArrayList;
import java.util.List;

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
    private LinearLayout finished;
    private MainActivity mainActivity;

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

        View view = inflater.inflate(R.layout.fragment_search_schools, container, false);
        mainActivity = (MainActivity)getActivity();

        // Generate list of all schools
        for (int x = 0; x< mainActivity.allSchools.length; x++){
            mainActivity.allSchoolNames[x]=mainActivity.allSchools[x].getSchoolName();
        }

        AddSchoolsAdapter itemsAdapter =
                new AddSchoolsAdapter(mainActivity, mainActivity.allSchoolNames, AddSchools.this);

        finished = (LinearLayout)view.findViewById(R.id.finished_container);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.hideKeyboard();
                mainActivity.goToStoredSchools();
            }
        });

        schoolsList = (ListView)view.findViewById(R.id.schoolsList);
        schoolsList.setAdapter(itemsAdapter);

        //search box and its listeners
        SearchView searchView = setupSearchView(view);
        setupSearchListeners(view, searchView, mainActivity, itemsAdapter);

        // mainActivity.showKeyboard();
        return view;




     /*   View view = inflater.inflate(R.layout.fragment_add_schools, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();

        // Generate list of all schools
        for (int x = 0; x< mainActivity.allSchools.length; x++){
            mainActivity.allSchoolNames[x]=mainActivity.allSchools[x].getSchoolName();
        }

        AddSchoolsAdapter itemsAdapter =
                new AddSchoolsAdapter(mainActivity, mainActivity.allSchoolNames);



        finished = (LinearLayout)view.findViewById(R.id.finished_container);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goToStoredSchools();
            }
        });

        schoolsList = (ListView)view.findViewById(R.id.schoolsList);
        schoolsList.setAdapter(itemsAdapter);

        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        int editTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(editTextId);
        textView.setTextSize(getResources().getDimension(R.dimen.search_hint_size));
        textView.setTextColor(getResources().getColor(R.color.colorGreyText));
        textView.setHintTextColor(getResources().getColor(R.color.colorGreyText));

        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean has_focus){
                mainActivity.goToSearchSchool();
            }
        });

        // Inflate the layout for this fragment
        return view; */
    }


    public SearchView setupSearchView(View view){

        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        //text Settings
        int editTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(editTextId);
        textView.setTextSize(getResources().getDimension(R.dimen.search_hint_size));
        textView.setTextColor(getResources().getColor(R.color.colorGreyText));
        textView.setHintTextColor(getResources().getColor(R.color.colorGreyText));

        searchView.setIconifiedByDefault(false);
        // searchView.requestFocus();

        return searchView;
    }

    public void setupSearchListeners(View view, SearchView searchView, MainActivity mainActivity, AddSchoolsAdapter itemsAdapter){
        // Toggle keyboard based on searchView focus
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mainActivity.hideKeyboard();
                } else {
                    mainActivity.showKeyboard();
                }
            }
        });

        // Handlers for searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String submitText) {
                doSearch(submitText, mainActivity, itemsAdapter);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                TextView textTitle = (TextView) view.findViewById(R.id.textView);
                if(newText.equals("")){
                    textTitle.setText(getString(R.string.add_schools_nearby_title));
                } else{
                    textTitle.setText(getString(R.string.add_schools_results_title));}
                doSearch(newText, mainActivity, itemsAdapter);
                return true;
            }
        });
    }

    public void doSearch(String query, MainActivity mainActivity, AddSchoolsAdapter itemsAdapter) {
        List<SchoolInfo> searchResult = new ArrayList<>();
        searchResult = mainActivity.schoolManager.getMatchingSchools(query);

        String[] searchSchoolName = new String[searchResult.size()];
        for(int i=0; i<searchResult.size();i++){
            searchSchoolName[i] = searchResult.get(i).getSchoolName();
        }
        itemsAdapter.setSchoolsToView(searchSchoolName);
    }

    public void updateFinishedButton() {
        if (mainActivity.selectedSchools.length < 1){
            finished.setTag("inactive");
            finished.setBackgroundColor(getResources().getColor(R.color.colorClickableSecondary));
        } else {
            finished.setTag("active");
            finished.setBackgroundColor(getResources().getColor(R.color.colorClickable));
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddSchoolsInteraction(uri);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        MainActivity mainActivity = (MainActivity)getActivity();
        // if(mainActivity.inputMethodManager.isAcceptingText()){
            mainActivity.hideKeyboard();
        // }
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

    public void onAddClicked () {

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