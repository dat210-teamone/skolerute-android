package com.github.dat210_teamone.skolerute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public void changeView(View view) {
        Intent myIntent = new Intent(this, CalendarList.class);
        startActivity(myIntent);
    }

    public void showSchools(View view) {

    }
}
