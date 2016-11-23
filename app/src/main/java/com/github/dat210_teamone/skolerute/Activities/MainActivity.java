package com.github.dat210_teamone.skolerute.Activities;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Fragments.AddSchools;
import com.github.dat210_teamone.skolerute.Fragments.CalendarList;
import com.github.dat210_teamone.skolerute.Fragments.CalendarStandard;
import com.github.dat210_teamone.skolerute.Fragments.SearchSchools;
import com.github.dat210_teamone.skolerute.Fragments.StoredSchools;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.adapters.StoredSchoolsAdapter;
import com.github.dat210_teamone.skolerute.data.InterfaceManager;
import com.github.dat210_teamone.skolerute.data.NotificationUtil;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.data.UpdateService;
import com.github.dat210_teamone.skolerute.data.locationService.LocationFinder;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements AddSchools.OnAddSchoolsInteractionListener, SearchSchools.OnSearchSchoolsInteractionListener, CalendarList.OnCalendarListInteractionListener, StoredSchools.OnStoredSchoolsInteractionListener, CalendarStandard.OnCalendarStandardInteractionListener{

    public FragmentManager manager = getSupportFragmentManager();
    public Fragment fragment = manager.findFragmentById(R.id.fragment_container);
    public Fragment fragmentSecondary = manager.findFragmentById(R.id.fragment_container_secondary);
    public FragmentTransaction fragTrans =  manager.beginTransaction();
    private int posisjon;

    public SchoolManager schoolManager;// = SchoolManager.getDefault();
    public SchoolInfo[] allSchools;// = schoolManager.getSchoolInfo();
    public SchoolInfo[] selectedSchools;// = schoolManager.getSelectedSchools();
    public String[] allSchoolNames;// = new String[allSchools.length];
    public InputMethodManager inputMethodManager;
    Location lastKnownLocation;

    public Set<String> schoolsToView = new HashSet<>();
    public StoredSchoolsAdapter storedSchoolsAdapter;
    public ImageView calendarViewToggle;

    private final String LIST_VIEW = "list_view";
    private final String CALENDAR_VIEW = "calendar_view";
    private boolean keyboardShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        InterfaceManager.SetMainContext(this);
        schoolManager = SchoolManager.getDefault();

        if (getAndCheckPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            getLastKnownPosition();
        }

        initSchoolData();

        initCheckedSchools();


        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        setContentView(R.layout.activity_main);

        TextView goToAdd = (TextView)findViewById(R.id.go_to_add);
        goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddSchools();
            }
        });

        initCalendarViewToggle();

        if (selectedSchools.length == 0)
            goToAddSchools();
        else
            goToStoredSchools();

        schoolManager.subscribe(NotificationUtil.getDefault());
        setupKeyboardTracker();
        setupCloseKeyboardOnTouch();
    }

    private void initCalendarViewToggle(){
        calendarViewToggle = (ImageView) findViewById(R.id.calendar_view_toggle);
        calendarViewToggle.setTag(LIST_VIEW);
        calendarViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calendarToggleTag = (String) calendarViewToggle.getTag();
                if(calendarToggleTag.equals(LIST_VIEW)){
                    calendarViewToggle.setTag(CALENDAR_VIEW);
                    calendarViewToggle.setImageResource(R.drawable.list_button);
                    viewCalendar();
                } else{
                    calendarViewToggle.setTag(LIST_VIEW);
                    calendarViewToggle.setImageResource(R.drawable.calendar_icon_white);
                    viewCalendarList();
                }
            }
        });
    }

    public void resetCalendarViewToggle(){
        calendarViewToggle.setTag(LIST_VIEW);
        calendarViewToggle.setImageResource(R.drawable.calendar_icon_white);
    }

    private void initSchoolData(){
        allSchools = schoolManager.getSchoolInfo();
        updateSelectedSchools();
        allSchoolNames = new String[allSchools.length];
    }

    private void updateSelectedSchools(){
        selectedSchools = schoolManager.getSelectedSchools();
    }

    public void initCheckedSchools() {
        updateSelectedSchools();
        schoolsToView.clear();
         for (int i=0; i<selectedSchools.length;i++){
            schoolsToView.add(selectedSchools[i].getSchoolName());
         }
    }

    public void clearCheckedSchools(){
        schoolsToView.clear();
    }

    public void refreshCheckedSchools(){
        storedSchoolsAdapter.clear();
        storedSchoolsAdapter.addAll(getAllStoredSchoolNames());
        storedSchoolsAdapter.notifyDataSetChanged();
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


        // START - Set up AlarmManager update service
        UpdateService.setUpUpdateService();
        // END - Set up AlarmManager update service
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

    private void setupKeyboardTracker(){
        final View mainActivityRootView = findViewById(R.id.main_container);
        mainActivityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int heightDiff = mainActivityRootView.getRootView().getHeight() - mainActivityRootView.getHeight();
            int heightDiffNew = 0;
            @Override
            public void onGlobalLayout() {
                if(fragment.getClass() == AddSchools.class ) {

                    if (isKeyboardShown()) {
                        keyboardShown = true;
                        heightDiff = mainActivityRootView.getRootView().getHeight() - mainActivityRootView.getHeight();
                    } else {
                        keyboardShown = false;
                        heightDiffNew = mainActivityRootView.getRootView().getHeight() - mainActivityRootView.getHeight();
                        if(heightDiff != heightDiffNew) {
                            View addSchoolView = fragment.getView();
                            delayShowHideFinishedButton(addSchoolView, true, 100);
                        }
                        heightDiff = mainActivityRootView.getRootView().getHeight() - mainActivityRootView.getHeight();
                    }
                }
            }
        });
    }

    public void setupCloseKeyboardOnTouch() {
        final View mainActivityRootView = findViewById(R.id.main_container);
            mainActivityRootView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if(fragment.getClass() == AddSchools.class ) {
                        if (getKeyboardShown()) {
                            hideKeyboard();
                            View addSchoolView = fragment.getView();
                            delayShowHideFinishedButton(addSchoolView, true, 100);
                        }
                    }
                    return false;
                }
            });
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

    public void hideKeyboard() {
        if(keyboardShown) {
            inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
        }
    }

    public void showKeyboard() {
        if(!keyboardShown) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public boolean getKeyboardShown(){
        return keyboardShown;
    }

    public boolean isKeyboardShown(){
        final View mainActivityRootView = findViewById(R.id.main_container);
        int heightDiff = mainActivityRootView.getRootView().getHeight() - mainActivityRootView.getHeight();
        if (heightDiff > dpToPx(mainActivityRootView.getContext(), 200)) {
            return true;
        } else{
            return false;
        }
    }

    public void goToStoredSchools() {
        initCheckedSchools();
        replaceMainFragment(new StoredSchools());
        replaceSecondaryFragment(new CalendarList());
    }

    public void goToAddSchools() {
        replaceMainFragment(new AddSchools());
        // replaceMainFragment(new SearchSchools());
        clearSecondaryFragment();
    }

    public void goToCalendarList() {
        replaceMainFragment(new CalendarList());
    }

    public void goToCalendarView() {
        replaceMainFragment(new CalendarStandard());
    }

    public void goToSearchSchool() {
        replaceMainFragment(new SearchSchools());
        clearSecondaryFragment();
    }

    public void viewCalendar() {
        replaceSecondaryFragment(new CalendarStandard());
    }

    public void viewCalendarList() {
        replaceSecondaryFragment(new CalendarList());
    }

    public void replaceMainFragment(Fragment fragment){
        this.fragment = fragment;
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment);
        fragTrans.commit();
    }

    public void replaceSecondaryFragment(Fragment fragment){
        this.fragmentSecondary = fragment;
        fragTrans = manager.beginTransaction();
        fragTrans.replace(R.id.fragment_container_secondary, fragment);
        fragTrans.commit();
    }

    public void clearSecondaryFragment(){
        fragTrans = manager.beginTransaction();
        if(fragmentSecondary != null) {
            fragTrans.remove(fragmentSecondary);
        }
        fragTrans.commit();
    }

    public void setPosisjon(int a){
        posisjon=a;
    }

    public int getPosisjon() {
        return posisjon;
    }


    public String[] getAllStoredSchoolNames(){
        String[] storedSchoolNames = new String[selectedSchools.length];
        for (int x = 0; x < selectedSchools.length; x++) {
            storedSchoolNames[x] = selectedSchools[x].getSchoolName();
        }
        return storedSchoolNames;
    }

    public Date[] getAllStoredSchoolDates(){
        Date[] storedSchoolDates = new Date[selectedSchools.length];
        for (int x = 0; x < selectedSchools.length; x++) {
            storedSchoolDates[x] = schoolManager.getNextVacationDay(selectedSchools[x].getSchoolName()).getDate();
        }
        return storedSchoolDates;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && fragment.getClass() != StoredSchools.class){
            goToStoredSchools();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //http://stackoverflow.com/a/4737265
    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
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