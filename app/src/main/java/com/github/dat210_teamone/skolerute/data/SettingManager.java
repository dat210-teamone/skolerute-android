package com.github.dat210_teamone.skolerute.data;

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
    private static final String NOTIFYSCHOOLS = "NotifySchools";
    private static final String NOTIFYENABLED = "NotifyEnabled";

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
        putStringSet(SELECTEDSCHOOLS, set);
    }

    @Override
    public boolean deleteSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTEDSCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        putStringSet(SELECTEDSCHOOLS, set);
        return found;
    }

    @Override
    public String getLastUpdateTime() {
        return preferences.getString(LASTUPDATEDATE, "");
    }

    @Override
    public void setLastUpdateTime(String time) {
        putString(LASTUPDATEDATE, time);
    }

    @Override
    public String[] getNotifySchools() {
        Set<String> set = preferences.getStringSet(NOTIFYSCHOOLS, defaults);
        String[] array = new String[set.size()];
        return set.toArray(array);
    }

    @Override
    public void addNotifySchool(String s) {
        Set<String> set = preferences.getStringSet(NOTIFYSCHOOLS, defaults);
        set.add(s);
        putStringSet(NOTIFYSCHOOLS, set);
    }

    @Override
    public boolean deleteNotifySchool(String s) {
        Set<String> set = preferences.getStringSet(NOTIFYSCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        putStringSet(NOTIFYSCHOOLS, set);
        return found;
    }

    @Override
    public void setGlobalNotify(boolean value) {
        SharedPreferences.Editor edit = getEditor(NOTIFYENABLED);
        edit.putBoolean(NOTIFYENABLED, value);
        edit.apply();
    }

    @Override
    public boolean getGlobalNotify() {
        return preferences.getBoolean(NOTIFYENABLED, false);
    }

    private void putString(String key, String value){
        SharedPreferences.Editor editor = getEditor(key);
        editor.putString(key, value);
        editor.apply();
    }

    private void putStringSet(String key, Set<String> set){
        SharedPreferences.Editor editor = getEditor(key);
        editor.putStringSet(key, set);
        editor.apply();
    }

    private SharedPreferences.Editor getEditor(String removeKey){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(removeKey);
        editor.apply();
        return editor;
    }
}
