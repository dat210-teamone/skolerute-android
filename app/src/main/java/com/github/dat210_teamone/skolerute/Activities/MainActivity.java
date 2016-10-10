package com.github.dat210_teamone.skolerute.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Fragments.AddSchools;
import com.github.dat210_teamone.skolerute.Fragments.CalendarList;
import com.github.dat210_teamone.skolerute.Fragments.CalendarStandard;
import com.github.dat210_teamone.skolerute.Fragments.StoredSchools;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.UpdateReceiver;
import com.github.dat210_teamone.skolerute.data.InterfaceManager;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.Calendar;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements AddSchools.OnAddSchoolsInteractionListener, CalendarList.OnCalendarListInteractionListener, StoredSchools.OnStoredSchoolsInteractionListener, CalendarStandard.OnCalendarStandardInteractionListener{

    public FragmentManager manager = getSupportFragmentManager();
    public Fragment fragment = manager.findFragmentById(R.id.fragment_container);
    public FragmentTransaction fragTrans =  manager.beginTransaction();

    public SchoolManager schoolManager;// = SchoolManager.getDefault();
    public SchoolInfo[] allSchools;// = schoolManager.getSchoolInfo();
    public SchoolInfo[] selectedSchools;// = schoolManager.getSelectedSchools();
    public String[] allSchoolNames;// = new String[allSchools.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /*SharedPreferences.Editor edit = this.getPreferences(0).edit();
        edit.clear();
        edit.apply();*/
        InterfaceManager.SetMainActivity(this);
        schoolManager = SchoolManager.getDefault();
        allSchools = schoolManager.getSchoolInfo();
        selectedSchools = schoolManager.getSelectedSchools();
        allSchoolNames = new String[allSchools.length];

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


        // START - Set up AlarmManager update service
        setUpUpdateService();
        // END - Set up AlarmManager update service
    }
    
    public void showSchools(View view) {

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

    public void setUpUpdateService() {
        // TODO: This probably needs some serious refactoring
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());

        // TODO: set check interval to One Month
        //updateTime.add(Calendar.MONTH, 1);

        updateTime.set(Calendar.HOUR_OF_DAY, 8);
        updateTime.set(Calendar.MINUTE, 0);

        Intent downloader = new Intent(this, UpdateReceiver.class);
        downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(),
                1000 * 60 /* TODO Switch with proper value, AlarmManager.INTERVAL_DAY */,
                pendingIntent);
        Log.d("MainActivity", "Set alarmManager.setInexactRepeating to: " + updateTime.getTime().toString());
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

}