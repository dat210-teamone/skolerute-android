package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.dummy.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class SettingStorageTest {
    private ISettingStorage settingStorage = new DummySettingStorage();

    @Test
    public void TestGet() throws Exception {
        String[] testNames = {"Skole 2", "Skole 6"};
        Assert.assertArrayEquals(testNames, settingStorage.get());
    }

    @Test
    public void TestAdd() throws Exception {
        String[] testNames = {"Skole 2", "Skole 6", "Skole 4"};
        settingStorage.add("Skole 4");
        Assert.assertArrayEquals(testNames, settingStorage.get());
    }

    @Test
    public void TestDelete() throws Exception {
        String[] testNames = {"Skole 6"};
        settingStorage.delete("Skole 2");
        Assert.assertArrayEquals(testNames, settingStorage.get());
    }
}
