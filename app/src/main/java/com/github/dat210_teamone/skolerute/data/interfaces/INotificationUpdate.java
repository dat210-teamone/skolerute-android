package com.github.dat210_teamone.skolerute.data.interfaces;

/**
 * Created by Fredrik Wigsnes on 01.11.2016.
 */

public interface INotificationUpdate {

    void preNotify(UpdateType type, String name);
    void postNotify(UpdateType type, String name, boolean result);
    //void preNotifyRemove(String name);
    //void postNotifyRemove(String name, boolean result);

    void globalNotifyChange(boolean newValue);
    enum UpdateType{
        ADD,
        REMOVE
    }
}
