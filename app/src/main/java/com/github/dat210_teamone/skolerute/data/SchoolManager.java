package com.github.dat210_teamone.skolerute.data;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nicolas on 21.09.2016.
 */

public class SchoolManager {
    IStorage storage;
    ArrayList<String> selectedSchools;

    public SchoolManager(IStorage storage){
        this.storage = storage;
        selectedSchools = new ArrayList<>();
        selectedSchools.add("Skole 2");
        selectedSchools.add("Skole 6");
    }

    private boolean CheckName(String name){
        for (String val : selectedSchools) {
            if (name.equals(val))
                return true;
        }
        return false;
    }

    public SchoolInfo[] GetSelectedSchools(){
        return  storage.getSchoolInfo(info -> CheckName(info.getSchoolName()));
    }

    public SchoolVacationDay[] GetSelectedSchoolDays(){
        SchoolVacationDay[] days = storage.getVacationDays(info -> CheckName(info.getName()));
        Arrays.sort(days, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return days;
    }
}
