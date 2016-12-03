package com.github.dat210_teamone.skolerute.data.interfaces;

/**
 * Created by Fredrik Wigsnes on 01.11.2016.
 * Part of project skolerute-android
 */

@SuppressWarnings("UnusedParameters")
public interface INotificationUpdate {

    void preNotify(UpdateType type, String name);
    void postNotify(UpdateType type, String name, boolean result);

    void globalNotifyChange(boolean newValue);
    enum UpdateType{
        ADD,
        REMOVE
    }
}
