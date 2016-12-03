package com.github.dat210_teamone.skolerute.data.dummy;

import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;

import java.util.ArrayList;

/**
 * Created by espen on 21.09.16.
 * Part of project skolerute-android
 */

public class DummySettingStorage implements ISettingStorage {
    private final ArrayList<String> selectedSchools;
    private String lastUpdate;

    private DummySettingStorage() {
        selectedSchools = new ArrayList<>();
    }

    @SuppressWarnings("SameParameterValue")
    public DummySettingStorage(boolean initTest)
    {
        this();
        if (initTest)
            initTestData();
    }

    @SuppressWarnings("UnusedReturnValue")
    private DummySettingStorage initTestData(){
        selectedSchools.clear();
        selectedSchools.add("Skole 2");
        selectedSchools.add("Skole 6");
        lastUpdate = "21.09.2016";

        return this;
    }

    @Override
    public String[] getSelectedSchools() {
        return selectedSchools.toArray(new String[selectedSchools.size()]);
    }

    @Override
    public void addSelectedSchool(String s) {
        selectedSchools.add(s);
    }

    @Override
    public boolean deleteSelectedSchool(String s) {
        return selectedSchools.remove(s);
    }

    @Override
    public String getLastUpdateTime() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdateTime(String time) {
        lastUpdate = time;
    }

    @Override
    public String[] getNotifySchools() {
        return new String[0];
    }

    @Override
    public void addNotifySchool(String s) {

    }

    @Override
    public boolean deleteNotifySchool(String s) {
        return false;
    }

    @Override
    public void setGlobalNotify(boolean value) {
        //TODO: Implement dummy settings
    }

    @Override
    public boolean getGlobalNotify() {
        return false;
    }
}
