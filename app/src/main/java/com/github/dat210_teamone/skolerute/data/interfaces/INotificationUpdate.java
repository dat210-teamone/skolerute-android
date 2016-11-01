package com.github.dat210_teamone.skolerute.data.interfaces;

/**
 * Created by Fredrik Wigsnes on 01.11.2016.
 */

public interface INotificationUpdate {

    void preNotifyAdd(String name);
    void postNotifyAdd(String name);
    void preNotifyRemove(String name);
    void postNotifyRemove(String name, boolean result);

    void globalNotifyChange(boolean newValue);

}
