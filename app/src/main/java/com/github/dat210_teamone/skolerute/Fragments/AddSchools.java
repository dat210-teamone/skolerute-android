package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.AddSchoolsAdapter;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

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

    private final String INACTIVE = "inactive";
    private final String ACTIVE = "active";



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
        setupFinishedListener(finished);

        schoolsList = (ListView)view.findViewById(R.id.schoolsList);
        schoolsList.setAdapter(itemsAdapter);

        //search box and its listeners
        SearchView searchView = setupSearchView(view);
        setupSearchListeners(view, searchView, mainActivity, itemsAdapter);

        updateFinishedButton();
        //closes soft keyboard when touching outside of text box or other relevant buttons
        setupCloseKeyboardOnTouch(view);

        return view;
    }

    public void setupFinishedListener(LinearLayout object) {
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object.getTag() == ACTIVE) {
                    mainActivity.goToStoredSchools();
                } else {
                    // No schools stored, display message
                    Toast.makeText(mainActivity, getResources().getString(R.string.no_schools_stored), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = (EditText) searchView.findViewById(searchTextId);
        searchText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                delayShowHideFinishedButton(view, false, 150);
                return false;
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

    public void setupCloseKeyboardOnTouch(View view) {
        MainActivity mainActivity = (MainActivity)getActivity();
        View finishedButton = (View)view.findViewById(R.id.finished);
        if(!(view instanceof EditText)) {   //SearchView instanceOf EditText
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if(view instanceof TextView) {
                        if (!getResources().getResourceEntryName(view.getId()).toString().equals("finished")) {
                                //searchView.clearFocus();
                            if(mainActivity.getKeyboardShown()) {
                                finishedButton.setVisibility(View.VISIBLE);
                                mainActivity.hideKeyboard();
                                delayShowHideFinishedButton(view, true, 100);

                            }
                        }
                    } else {
                            //searchView.clearFocus();
                            if(mainActivity.getKeyboardShown()) {
                                finishedButton.setVisibility(View.VISIBLE);
                                mainActivity.hideKeyboard();
                                delayShowHideFinishedButton(view, true, 100);
                            }
                    }
                    return false;
                }
            });
        }
    }

    public void updateFinishedButton() {
        if (SchoolManager.getDefault().getSelectedSchools().length < 1){
            finished.setTag(INACTIVE);
            finished.setBackgroundColor(getResources().getColor(R.color.colorClickableSecondary));
        } else {
            finished.setTag(ACTIVE);
            finished.setBackgroundColor(getResources().getColor(R.color.colorClickable));
        }
    }

    public void delayShowHideFinishedButton(View view, boolean show, int milliseconds){
        final LinearLayout finishedButtonLayout = (LinearLayout) view.findViewById(R.id.finished_container);
        new CountDownTimer(milliseconds, 10) {
            public void onFinish() {
                //Log.i("onFinish", "finished");
                if(show){
                    finishedButtonLayout.setVisibility(LinearLayout.VISIBLE);
                } else{
                    finishedButtonLayout.setVisibility(LinearLayout.GONE);
                }
            }
            public void onTick(long millisUntilFinished) {
                //Log.i("onTick", "tick");
            }
        }.start();
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