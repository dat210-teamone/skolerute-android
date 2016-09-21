package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class SettingStorageTest {
    ISettingStorage settingStorage = new DummySettingStorage();


    @Test
    public void TestGet() throws Exception {
        String[] names = {"Skole 1", "Skole 5", "Skole 3"};
        Assert.assertArrayEquals(names, settingStorage.get());
    }
}
