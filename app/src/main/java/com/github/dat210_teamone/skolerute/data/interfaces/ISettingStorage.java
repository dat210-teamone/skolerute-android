package com.github.dat210_teamone.skolerute.data.interfaces;

/**
 * Created by Nicolas on 21.09.2016.
 */

public interface ISettingStorage {
    String[] getSelectedSchools();
    void addSelectedSchool(String s);
    boolean deleteSelectedSchool(String s);

    String getLastUpdateTime();
    void setLastUpdateTime(String time);
}
