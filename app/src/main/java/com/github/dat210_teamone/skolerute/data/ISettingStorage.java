package com.github.dat210_teamone.skolerute.data;

/**
 * Created by Nicolas on 21.09.2016.
 */

public interface ISettingStorage {
    public String[] get();
    public void add(String s);
    public boolean delete(String s);
}
