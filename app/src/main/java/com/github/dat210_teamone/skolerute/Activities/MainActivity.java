package com.github.dat210_teamone.skolerute.Activities;


import android.content.Context;
import android.content.SharedPreferences;


import android.location.LocationManager;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Fragments.AddSchools;
import com.github.dat210_teamone.skolerute.Fragments.CalendarList;
import com.github.dat210_teamone.skolerute.Fragments.CalendarStandard;
import com.github.dat210_teamone.skolerute.Fragments.SearchSchools;
import com.github.dat210_teamone.skolerute.Fragments.StoredSchools;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.InterfaceManager;

import com.github.dat210_teamone.skolerute.data.NotificationUtil;

import com.github.dat210_teamone.skolerute.data.positionService.LocationFinder;

import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;


public class MainActivity extends AppCompatActivity implements AddSchools.OnAddSchoolsInteractionListener, SearchSchools.OnSearchSchoolsInteractionListener, CalendarList.OnCalendarListInteractionListener, StoredSchools.OnStoredSchoolsInteractionListener, CalendarStandard.OnCalendarStandardInteractionListener{

    public FragmentManager manager = getSupportFragmentManager();
    public Fragment fragment = manager.findFragmentById(R.id.fragment_container);
    public FragmentTransaction fragTrans =  manager.beginTransaction();
    private int posisjon;

    public SchoolManager schoolManager;// = SchoolManager.getDefault();
    public SchoolInfo[] allSchools;// = schoolManager.getSchoolInfo();
    public SchoolInfo[] selectedSchools;// = schoolManager.getSelectedSchools();
    public String[] allSchoolNames;// = new String[allSchools.length];
    public InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        /*SharedPreferences.Editor edit = this.getPreferences(0).edit();
        edit.clear();
        edit.apply();*/


        //LocationFinder finder = new LocationFinder();
        //LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 100, finder);

        InterfaceManager.SetMainActivity(this);
        schoolManager = SchoolManager.getDefault();
        allSchools = schoolManager.getSchoolInfo();
        selectedSchools = schoolManager.getSelectedSchools();
        allSchoolNames = new String[allSchools.length];
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        setContentView(R.layout.activity_main);

        TextView goToAdd = (TextView)findViewById(R.id.go_to_add);
        goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddSchools();
            }
        });

        if (fragment == null) {
            if(selectedSchools.length == 0) {
                fragment = new AddSchools();
            } else {
                fragment = new StoredSchools();
            }
            // Add container and fragment for the container
            fragTrans.add(R.id.fragment_container, fragment);
            fragTrans.commit();
        }
        NotificationUtil NU = new NotificationUtil(this);
    }

    public void showSchools(View view) {

    }

    public void hideKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0);
    }

    public void showKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public void goToStoredSchools() {
        fragment = new StoredSchools();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
        fragTrans.commit();
    }

    public void goToAddSchools() {
        fragment = new AddSchools();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
        fragTrans.commit();
    }

    public void goToCalendarList() {
        fragment = new CalendarList();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
        fragTrans.commit();
    }

    public void goToCalendarView() {
        fragment = new CalendarStandard();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
        fragTrans.commit();
    }

    public void goToSearchSchool() {
        fragment = new SearchSchools();
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container,fragment);
        fragTrans.commit();
    }

    public void setPosisjon(int a){
        posisjon=a;
    }

    public int getPosisjon() {
        return posisjon;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && fragment.getClass() != StoredSchools.class){
            goToStoredSchools();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    // Abstract methods from fragments
    @Override
    public void onAddSchoolsInteraction(Uri uri){

    }

    @Override
    public void onCalendarListInteraction(Uri uri){

    }

    @Override
    public void onStoredSchoolsInteraction(Uri uri){

    }

    @Override
    public void onCalendarStandardInteraction(Uri uri){

    }

    @Override
    public void onSearchSchoolsInteraction(Uri uri) {

    }
}