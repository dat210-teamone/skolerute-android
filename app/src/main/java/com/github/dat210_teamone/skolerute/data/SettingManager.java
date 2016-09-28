package com.github.dat210_teamone.skolerute.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nicolas on 28.09.2016.
 */

public class SettingManager implements ISettingStorage {
    SharedPreferences preferences;
    private static final String SELECTEDSCHOOLS = "SelectedSchools";
    private static final String LASTUPDATEDATE = "LastUpdateDate";

    private Set<String> defaults = new HashSet<>();


    public SettingManager(SharedPreferences preferences){
        this.preferences = preferences;
    }

    @Override
    public String[] getSelectedSchools() {
//        mainActivity.getPreferences(Context.MODE_PRIVATE)

        preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        return new String[0];
    }

    @Override
    public void addSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        set.add(s);
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putStringSet(SELECTEDSCHOOLS, set);
        editor.commit();
    }

    @Override
    public boolean deleteSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putStringSet(SELECTEDSCHOOLS, set);
        editor.commit();
        return found;
    }

    @Override
    public String getLastUpdateTime() {
        return preferences.getString(LASTUPDATEDATE, "");
    }

    @Override
    public void setLastUpdateTime(String time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTEDSCHOOLS, time);
        editor.commit();
    }
}
