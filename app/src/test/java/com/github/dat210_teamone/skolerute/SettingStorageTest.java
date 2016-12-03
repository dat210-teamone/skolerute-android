package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.dummy.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class SettingStorageTest {
    private final ISettingStorage settingStorage = new DummySettingStorage(true);

    public SettingStorageTest(){
    }

    @Test
    public void TestGet() throws Exception {
        String[] testNames = {"Skole 2", "Skole 6"};
        Assert.assertArrayEquals(testNames, settingStorage.getSelectedSchools());
    }

    @Test
    public void TestAdd() throws Exception {
        String[] testNames = {"Skole 2", "Skole 6", "Skole 4"};
        settingStorage.addSelectedSchool("Skole 4");
        Assert.assertArrayEquals(testNames, settingStorage.getSelectedSchools());
    }

    @Test
    public void TestDelete() throws Exception {
        String[] testNames = {"Skole 6"};
        settingStorage.deleteSelectedSchool("Skole 2");
        Assert.assertArrayEquals(testNames, settingStorage.getSelectedSchools());
    }
}
