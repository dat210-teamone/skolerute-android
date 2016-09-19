package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.ArrayList;

/**
 * Created by espen on 19.09.16.
 */
public class DummyStorage implements StorageInterface {
    private ArrayList<SchoolInfo> schoolInfo;
    private ArrayList<SchoolVacationDay> schoolVacationDay;

    public DummyStorage() {
        for(int i = 0; i < 10; i++) {
            SchoolInfo dummySchool = new SchoolInfo();
            dummySchool.setNorth(1234567.11);
            dummySchool.setEast(7654321.99);
            dummySchool.setLatitude(58.946442);
            dummySchool.setLongitude(5.726693);
            dummySchool.setSchoolName("Skole " + i);
            dummySchool.setAddress("Address " + i);
            dummySchool.setHomePage("www.school" + i +".com");
            dummySchool.setSudents("ELEVER/TRINN 1.-51  2.-68  3.-51  4.- 69  5.-49  6.-45  7.-41");
            dummySchool.setCapacity("" + 10+i + "klasserom");
        }
    }

    @Override
    public SchoolInfo[] GetSchoolInfo() {
        
        return (SchoolInfo[])schoolInfo.toArray();
    }

    @Override
    public SchoolVacationDay[] GetVacationDays() {
        return (SchoolVacationDay[])schoolVacationDay.toArray();
    }

    @Override
    public SchoolInfo[] GetSchoolInfo(Predicate<SchoolInfo> func) {
        ArrayList<SchoolInfo> filter = new ArrayList<SchoolInfo>();
        for (SchoolInfo obj : schoolInfo)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return (SchoolInfo[])filter.toArray();
    }

    @Override
    public SchoolVacationDay[] GetVacationDays(Predicate<SchoolVacationDay> func) {
        ArrayList<SchoolVacationDay> filter = new ArrayList<SchoolVacationDay>();
        for (SchoolVacationDay obj : schoolVacationDay)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return (SchoolVacationDay[])filter.toArray();
    }
}
