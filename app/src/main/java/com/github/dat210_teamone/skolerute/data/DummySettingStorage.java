package com.github.dat210_teamone.skolerute.data;

import java.util.ArrayList;

/**
 * Created by espen on 21.09.16.
 */

public class DummySettingStorage implements ISettingStorage {
    private ArrayList<String> selectedSchools;

    @Override
    public String[] Get() {
        return selectedSchools.toArray(new String[selectedSchools.size()]);
    }

    @Override
    public void Add(String s) {
        selectedSchools.add(s);
    }

    @Override
    public boolean Delete(String s) {
        return selectedSchools.remove(s);
    }
}
