package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class SettingStorageTest {
    ISettingStorage settingStorage;

    public SettingStorageTest {
        settingStorage = new DummySettingStorage();
        settingStorage.add("Skole 1");
        settingStorage.add("Skole 5");
        settingStorage.add("Skole 3");
    }

    @Test

}
