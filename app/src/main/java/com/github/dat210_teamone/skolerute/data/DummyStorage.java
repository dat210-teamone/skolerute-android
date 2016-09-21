package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by espen on 19.09.16.
 */
public class DummyStorage implements IStorage {
    private ArrayList<SchoolInfo> schoolInfo;
    private ArrayList<SchoolVacationDay> schoolVacationDay;

    public DummyStorage() {
        schoolInfo = new ArrayList<>();
        schoolVacationDay = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            SchoolInfo dummySchool = new SchoolInfo();
            SchoolVacationDay dummyVacationDay = new SchoolVacationDay();

            dummySchool.setNorth(1234567.11);
            dummySchool.setEast(7654321.99);
            dummySchool.setLatitude(58.946442);
            dummySchool.setLongitude(5.726693);
            dummySchool.setId(i+1);
            dummySchool.setObjectType("Bygning");
            dummySchool.setKomm(1103);
            dummySchool.setByggTyp_NBR(613);
            dummySchool.setInformation(((i+1) % 2) == 0 ? "Kommunal" : "Fylkeskommunal");
            dummySchool.setSchoolName("Skole " + i);
            dummySchool.setAddress("Address " + i);
            dummySchool.setHomePage("www.school" + i +".com");
            dummySchool.setSudents("ELEVER/TRINN 1.-51  2.-68  3.-51  4.- 69  5.-49  6.-45  7.-41");
            dummySchool.setCapacity("" + 10+i + "klasserom");

            Date todayDate = new Date(System.currentTimeMillis());
            dummyVacationDay.setDate(todayDate);
            dummyVacationDay.setName("Skole " + i);
            dummyVacationDay.setStudentDay(i % 2 == 0);
            dummyVacationDay.setSfoDay(i % 2 != 0);
            dummyVacationDay.setTeacherDay(true);

            schoolInfo.add(dummySchool);
            schoolVacationDay.add(dummyVacationDay);
        }
    }

    @Override
    public SchoolInfo[] getSchoolInfo() {
        
        return schoolInfo.toArray(new SchoolInfo[schoolInfo.size()]);
    }

    @Override
    public SchoolVacationDay[] getVacationDays() {
        return (SchoolVacationDay[])schoolVacationDay.toArray(new SchoolVacationDay[schoolVacationDay.size()]);
    }

    @Override
    public SchoolInfo[] getSchoolInfo(Predicate<SchoolInfo> func) {
        ArrayList<SchoolInfo> filter = new ArrayList<SchoolInfo>();
        for (SchoolInfo obj : schoolInfo)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return filter.toArray(new SchoolInfo[filter.size()]);
    }

    @Override
    public SchoolVacationDay[] getVacationDays(Predicate<SchoolVacationDay> func) {
        ArrayList<SchoolVacationDay> filter = new ArrayList<SchoolVacationDay>();
        for (SchoolVacationDay obj : schoolVacationDay)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return (SchoolVacationDay[])filter.toArray(new SchoolVacationDay[filter.size()]);
    }
}
