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
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        String[] array = new String[set.size()];
        return set.toArray(array);
    }

    @Override
    public void addSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        set.add(s);
        SharedPreferences.Editor editor =  preferences.edit();
        editor.remove(SELECTEDSCHOOLS);
        editor.apply();
        editor.putStringSet(SELECTEDSCHOOLS, set);
        editor.apply();
    }

    @Override
    public boolean deleteSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        SharedPreferences.Editor editor =  preferences.edit();
        editor.remove(SELECTEDSCHOOLS);
        editor.apply();
        editor.putStringSet(SELECTEDSCHOOLS, set);
        editor.apply();
        return found;
    }

    @Override
    public String getLastUpdateTime() {
        return preferences.getString(LASTUPDATEDATE, "");
    }

    @Override
    public void setLastUpdateTime(String time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LASTUPDATEDATE);
        editor.putString(LASTUPDATEDATE, time);
        editor.apply();
    }
}
