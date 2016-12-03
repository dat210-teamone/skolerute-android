package com.github.dat210_teamone.skolerute.data;

import android.content.SharedPreferences;

import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nicolas on 28.09.2016.
 * Part of project skolerute-android
 */

public class SettingManager implements ISettingStorage {
    private final SharedPreferences preferences;
    private static final String SELECTED_SCHOOLS = "SelectedSchools";
    private static final String LAST_UPDATE_DATE = "LastUpdateDate";
    private static final String NOTIFY_SCHOOLS = "NotifySchools";
    private static final String NOTIFY_ENABLED = "NotifyEnabled";

    private final Set<String> defaults = new HashSet<>();


    public SettingManager(SharedPreferences preferences){
        this.preferences = preferences;
    }

    @Override
    public String[] getSelectedSchools() {
        Set<String> set = preferences.getStringSet(SELECTED_SCHOOLS, defaults);
        String[] array = new String[set.size()];
        return set.toArray(array);
    }

    @Override
    public void addSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTED_SCHOOLS, defaults);
        set.add(s);
        putStringSet(SELECTED_SCHOOLS, set);
    }

    @Override
    public boolean deleteSelectedSchool(String s) {
        Set<String> set = preferences.getStringSet(SELECTED_SCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        putStringSet(SELECTED_SCHOOLS, set);
        return found;
    }

    @Override
    public String getLastUpdateTime() {
        return preferences.getString(LAST_UPDATE_DATE, "");
    }

    @Override
    public void setLastUpdateTime(String time) {
        putString(LAST_UPDATE_DATE, time);
    }

    @Override
    public String[] getNotifySchools() {
        Set<String> set = preferences.getStringSet(NOTIFY_SCHOOLS, defaults);
        String[] array = new String[set.size()];
        return set.toArray(array);
    }

    @Override
    public void addNotifySchool(String s) {
        Set<String> set = preferences.getStringSet(NOTIFY_SCHOOLS, defaults);
        set.add(s);
        putStringSet(NOTIFY_SCHOOLS, set);
    }

    @Override
    public boolean deleteNotifySchool(String s) {
        Set<String> set = preferences.getStringSet(NOTIFY_SCHOOLS, defaults);
        boolean found = false;
        found = set.remove(s);
        putStringSet(NOTIFY_SCHOOLS, set);
        return found;
    }

    @Override
    public void setGlobalNotify(boolean value) {
        SharedPreferences.Editor edit = getEditor(NOTIFY_ENABLED);
        edit.putBoolean(NOTIFY_ENABLED, value);
        edit.apply();
    }

    @Override
    public boolean getGlobalNotify() {
        return preferences.getBoolean(NOTIFY_ENABLED, false);
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
