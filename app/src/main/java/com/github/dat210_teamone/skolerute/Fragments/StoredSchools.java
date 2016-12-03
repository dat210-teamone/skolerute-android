package com.github.dat210_teamone.skolerute.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.StoredSchoolsAdapter;

import java.util.Date;

public class StoredSchools extends Fragment {

    private ListView storedSchoolsList;
    private TextView finished;
    private LinearLayout storedSchoolsListContainer;
    private int containerHeight;
    private LinearLayout.LayoutParams listParameters;

    private final String EXPANDED = "expanded";
    private final String MINIMIZED = "minimized";

    private MainActivity mainActivity;

    public StoredSchools() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stored_schools, container, false);

        mainActivity = (MainActivity) getActivity();


        mainActivity.selectedSchools = mainActivity.schoolManager.getSelectedSchools();

        String[] storedSchoolNames = mainActivity.getAllStoredSchoolNames();
        Date[] storedSchoolVacationDays = mainActivity.getAllStoredSchoolDates();

        setupContainer(view);


        StoredSchoolsAdapter storedSchoolsAdapter = new StoredSchoolsAdapter(mainActivity, storedSchoolNames, storedSchoolVacationDays);
        mainActivity.storedSchoolsAdapter = storedSchoolsAdapter;
        storedSchoolsList = (ListView) view.findViewById(R.id.storedSchoolsList);
        storedSchoolsList.setAdapter(storedSchoolsAdapter);

        setupExpandButton(view);
        setupPopupMenu(view, mainActivity);

        return view;
    }

    private void setupContainer(View view) {
        storedSchoolsListContainer = (LinearLayout) view.findViewById(R.id.stored_schools_list_container);
        listParameters = (LinearLayout.LayoutParams) storedSchoolsListContainer.getLayoutParams();
        containerHeight = getContainerHeight();
        setContainerHeight(containerHeight);
    }

    private void setContainerHeight(int newHeight) {
        listParameters.height = newHeight;
        storedSchoolsListContainer.setLayoutParams(listParameters);
    }


    private void setupExpandButton(View view) {
        ImageView expandContainerButton = (ImageView) view.findViewById(R.id.stored_schools_expand);
        expandContainerButton.setTag(EXPANDED);
        expandContainerButton.setImageResource(R.drawable.ic_expand_less_white_24dp);
        expandContainerButton.setOnClickListener(v -> {
            if (expandContainerButton.getTag() == EXPANDED) {
                expandContainerButton.setTag(MINIMIZED);
                expandContainerButton.setImageResource(R.drawable.ic_expand_more_white_24dp);
                setContainerHeight(0);
            } else {
                expandContainerButton.setTag(EXPANDED);
                expandContainerButton.setImageResource(R.drawable.ic_expand_less_white_24dp);
                setContainerHeight(getContainerHeight());
            }
        });
    }

    private int getContainerHeight() {
        int schoolNameHeight = (int) mainActivity.getResources().getDimension(R.dimen.school_name_height);
        int numberOfSchools = mainActivity.schoolManager.getSelectedSchools().length;
        if (numberOfSchools > 3) {
            return (int) (3.5 * schoolNameHeight);
        } else if (numberOfSchools > 0) {
            return numberOfSchools * schoolNameHeight;
        }
        return schoolNameHeight;
    }


    private void setupPopupMenu(View view, MainActivity mainActivity) {
        ImageView schoolSettingsBtn = (ImageView) view.findViewById(R.id.stored_schools_menu);
        schoolSettingsBtn.setOnClickListener(v -> {
            PopupMenu settingsMenu = new PopupMenu(mainActivity, schoolSettingsBtn);
            settingsMenu.inflate(R.menu.stored_schools_popup_menu);

            //If school should notify
            if (mainActivity.schoolManager.getGlobalNotification()) {
                settingsMenu.getMenu().findItem(R.id.itemNotification).setChecked(true);
            } else {
                settingsMenu.getMenu().findItem(R.id.itemNotification).setChecked(false);
            }
            settingsMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.itemNotification) {
                    if (item.isChecked()) {
                        item.setChecked(false);
                        mainActivity.schoolManager.setGlobalNotification(false);
                    } else {
                        item.setChecked(true);
                        mainActivity.schoolManager.setGlobalNotification(true);
                    }
                }

                // start http://stackoverflow.com/a/31727213
                // Keep the popup menu open
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                item.setActionView(new View(mainActivity));
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return false;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return false;
                    }
                });
                //end http://stackoverflow.com/a/31727213

                return false;
            });

            settingsMenu.show();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity mainActivity = (MainActivity) getActivity();
        TextView addSchoolButton = (TextView) mainActivity.findViewById(R.id.go_to_add);
        ImageView calendarViewToggle = (ImageView) mainActivity.findViewById(R.id.calendar_view_toggle);
        addSchoolButton.setVisibility(View.INVISIBLE);
        calendarViewToggle.setVisibility(View.INVISIBLE);
        mainActivity.resetCalendarViewToggle();
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity mainActivity = (MainActivity) getActivity();
        TextView addSchoolButton = (TextView) mainActivity.findViewById(R.id.go_to_add);
        ImageView calendarViewToggle = (ImageView) mainActivity.findViewById(R.id.calendar_view_toggle);
        addSchoolButton.setVisibility(View.VISIBLE);
        calendarViewToggle.setVisibility(View.VISIBLE);
    }
}
