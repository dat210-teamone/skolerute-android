package com.github.dat210_teamone.skolerute;

import android.content.Intent;
import android.provider.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.app.ListActivity;

import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

public class NearestSchool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_school);

        DummyStorage alfa=new DummyStorage();
        Intent intent = getIntent();
        SchoolInfo[] beta=alfa.getSchoolInfo();
        int[] toViews = {android.R.id.text1};
        String[] noe= new String[5];
        noe[0]=beta[0].getSchoolName();
        ListActivity a=new ListActivity();

        ListAdapter zebra=new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, noe, toViews, 0);
        a.setListAdapter(zebra);
    }
}
