package com.github.dat210_teamone.skolerute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(5<2){
            setContentView(R.layout.activity_main);
        }else {
            setContentView(R.layout.activity_calendar);
        }

    }

    /** Called when the user clicks the Send button */
    public void changeActivity(View view) {
        Intent myIntent = new Intent(this, AddSchoolActivity.class);
        startActivity(myIntent);
    }
}