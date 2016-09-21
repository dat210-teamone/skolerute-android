package com.github.dat210_teamone.skolerute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.dat210_teamone.skolerute.data.DummyStorage;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(5>2){
            setContentView(R.layout.activity_main);
        }else {
            setContentView(R.layout.activity_calendar);
        }

    }

    public void changeActivity(View view) {
        Intent myIntent = new Intent(this, AddSchoolActivity.class);
        startActivity(myIntent);
    }

    public void findSchool(View view) {
        Intent myIntent = new Intent(this, AddSchoolActivity.class);
        startActivity(myIntent);
    }

    public void changeView(View view) {
        Intent myIntent = new Intent(this, CalendarList.class);
        startActivity(myIntent);
    }

    public void showSchools(View view) {

    }
}