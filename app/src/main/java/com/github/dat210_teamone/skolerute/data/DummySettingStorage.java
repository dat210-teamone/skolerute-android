package com.github.dat210_teamone.skolerute.data;

import java.util.ArrayList;

/**
 * Created by espen on 21.09.16.
 */

public class DummySettingStorage implements ISettingStorage {
    private ArrayList<String> selectedSchools;

    public DummySettingStorage() {
        selectedSchools = new ArrayList<>();
        selectedSchools.add("Skole 2");
        selectedSchools.add("Skole 6");
    }

    @Override
    public String[] get() {
        return selectedSchools.toArray(new String[selectedSchools.size()]);
    }

    @Override
    public void add(String s) {
        selectedSchools.add(s);
    }

    @Override
    public boolean delete(String s) {
        return selectedSchools.remove(s);
    }
}
