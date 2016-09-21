package com.github.dat210_teamone.skolerute.data;

/**
 * Created by Nicolas on 21.09.2016.
 */

public interface ISettingStorage {
    public String[] Get();
    public void Add(String s);
    public boolean Delete(String s);
}
