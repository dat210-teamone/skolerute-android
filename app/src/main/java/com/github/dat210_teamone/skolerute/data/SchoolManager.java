package com.github.dat210_teamone.skolerute.data;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Nicolas on 21.09.2016.
 */

public class SchoolManager {
    static SchoolManager defaultManager;
    IStorage storage;
    ISettingStorage settings;
    ArrayList<String> selectedSchools;

    public SchoolManager()
    {
        this(InterfaceManager.getStorage(), InterfaceManager.getSettings());
    }

    public static SchoolManager getDefault() {
        if (defaultManager == null){
            defaultManager = new SchoolManager();
        }
        return defaultManager;
    }

    public SchoolManager(IStorage storage, ISettingStorage settings){
        this.storage = storage;
        this.settings = settings;
        selectedSchools = new ArrayList<>();
        addAll(settings.get());
    }

    private void addAll(String[] names){
        selectedSchools.clear();
        for(String s : names){
            selectedSchools.add(s);
        }
    }

    public boolean checkName(String name){
        for (String val : selectedSchools) {
            if (name.equals(val))
                return true;
        }
        return false;
    }

    public SchoolInfo[] getSelectedSchools(){
        return  storage.getSchoolInfo(info -> checkName(info.getSchoolName()));
    }

    public SchoolVacationDay[] getSelectedSchoolDays(){
        SchoolVacationDay[] days = storage.getVacationDays(info -> checkName(info.getName()));
        Arrays.sort(days, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return days;
    }

    public SchoolVacationDay getNextVacationDay(String name) {
        SchoolVacationDay[] svd = storage.getVacationDays(info -> info.getName().equals(name) && info.getDate().after(new Date(System.currentTimeMillis())));
        if (svd.length == 0) {
            return null;
        }
        return svd[0];
    }

    public void addDefault(String name) {
        settings.add(name);
        addAll(settings.get());
    }

    public void removeDefault(String name){
        settings.delete(name);
        addAll(settings.get());
    }

    public SchoolInfo[] getSchoolInfo(){
        return storage.getSchoolInfo();
    }
}
