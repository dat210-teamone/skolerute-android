package com.github.dat210_teamone.skolerute.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSchools.OnAddSchoolsInteractionListener} interface
 * to handle interaction events.
 */
public class AddSchools extends Fragment {

    private ListView schoolsList;
    private LinearLayout finished;
    private MainActivity mainActivity;

    private final String INACTIVE = "inactive";
    private final String ACTIVE = "active";



    private OnAddSchoolsInteractionListener mListener;

    public AddSchools() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_schools, container, false);
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

        int color = ContextCompat.getColor(getContext(), R.color.colorGreyText);
        textView.setTextColor(color);
        textView.setHintTextColor(color);

        searchView.setIconifiedByDefault(false);


        return searchView;
    }

    public void setupSearchListeners(View view, SearchView searchView, MainActivity mainActivity, AddSchoolsAdapter itemsAdapter){

        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = (EditText) searchView.findViewById(searchTextId);

        int searchCloseId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView searchCloseButton = (ImageView) searchView.findViewById(searchCloseId);

        searchCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
                searchView.setQuery("", false);
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
        List<SchoolInfo> searchResult = mainActivity.schoolManager.getMatchingSchools(query);

        String[] searchSchoolName = new String[searchResult.size()];
        for(int i=0; i<searchResult.size();i++){
            searchSchoolName[i] = searchResult.get(i).getSchoolName();
        }
        itemsAdapter.setSchoolsToView(searchSchoolName);
    }

    public void setupCloseKeyboardOnTouch(View view) {
        MainActivity mainActivity = (MainActivity)getActivity();

        if(!(view instanceof EditText)) {   //SearchView instanceOf EditText
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

                    if(mainActivity.isKeyboardShown()) {
                        mainActivity.hideKeyboard();
                    }

                    return false;
                }
            });
        }
    }

    public void updateFinishedButton() {
        int colorInactive = ContextCompat.getColor(getContext(), R.color.colorClickableSecondary);
        int colorActive = ContextCompat.getColor(getContext(), R.color.colorClickable);
        if (SchoolManager.getDefault().getSelectedSchools().length < 1){
            finished.setTag(INACTIVE);
            finished.setBackgroundColor(colorInactive);
        } else {
            finished.setTag(ACTIVE);
            finished.setBackgroundColor(colorActive);
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