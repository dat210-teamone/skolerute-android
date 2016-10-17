package com.github.dat210_teamone.skolerute.Activities;


import android.Manifest;
import android.app.Activity;
import android.content.Context;


import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.location.LocationProvider;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

import com.github.dat210_teamone.skolerute.data.NotificationReceiver;
import com.github.dat210_teamone.skolerute.data.NotificationUtil;

import com.github.dat210_teamone.skolerute.data.locationService.LocationFinder;

import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.List;


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
    Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        InterfaceManager.SetMainActivity(this);
        schoolManager = SchoolManager.getDefault();

        if (getAndCheckPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            getLastKnownPosition();
        }

        initSchoolData();

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        setContentView(R.layout.activity_main);

        TextView goToAdd = (TextView)findViewById(R.id.go_to_add);
        goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddSchools();
            }
        });

        if (selectedSchools.length == 0)
            goToAddSchools();
        else
            goToStoredSchools();

        NotificationUtil NU = new NotificationUtil(this);
        NU.createNotification();

    }

    private void initSchoolData(){
        allSchools = schoolManager.getSchoolInfo();
        selectedSchools = schoolManager.getSelectedSchools();
        allSchoolNames = new String[allSchools.length];
    }

    private boolean getAndCheckPermission(String permission) {
        int permissinCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissinCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
        return true;
    }

    private void getLastKnownPosition(){
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listner = new LocationFinder();
        List<String> providers = manager.getAllProviders();
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listner);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listner);
            for(String s: providers) {

                Location loc = manager.getLastKnownLocation(s);
                if (loc == null)
                    continue;;
                if (lastKnownLocation == null){
                    lastKnownLocation = loc;
                }
                else if (lastKnownLocation.getTime() < loc.getTime()){
                    lastKnownLocation = loc;
                }
            }
            schoolManager.setKnownPosition(lastKnownLocation);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastKnownPosition();
                }
                break;
        }
    }

    public void hideKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0);
    }

    public void showKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public void goToStoredSchools() {
        goTo(new StoredSchools());
    }

    public void goToAddSchools() {
        goTo(new AddSchools());
    }

    public void goToCalendarList() {
        goTo(new CalendarList());
    }

    public void goToCalendarView() {
        goTo(new CalendarStandard());
    }

    public void goToSearchSchool() {
        goTo(new SearchSchools());
    }

    public void goTo(Fragment fragment){
        this.fragment = fragment;
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
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