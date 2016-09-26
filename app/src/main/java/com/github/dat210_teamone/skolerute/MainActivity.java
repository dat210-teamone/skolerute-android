package com.github.dat210_teamone.skolerute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(5>2){
            setContentView(R.layout.activity_main);
        }else {
            setContentView(R.layout.activity_calendar);
        }

    }

    public void findSSchool(View view) {
        Intent myIntent = new Intent(this, AddSchoolActivity.class);
        startActivity(myIntent);
    }

    public void findSchool(View view) {
        Intent myIntent = new Intent(this, NearestSchool.class);
        startActivity(myIntent);
    }

    public void changeView(View view) {
        Intent myIntent = new Intent(this, CalendarList.class);
        startActivity(myIntent);
    }

    public void showSchools(View view) {

    }
}