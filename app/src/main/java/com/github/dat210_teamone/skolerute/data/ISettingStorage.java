package com.github.dat210_teamone.skolerute.data;

/**
 * Created by Nicolas on 21.09.2016.
 */

public interface ISettingStorage {
    String[] get();
    void add(String s);
    boolean delete(String s);
}
