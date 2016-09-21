package com.github.dat210_teamone.skolerute;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.data.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.IStorage;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import org.junit.Assert;
import org.junit.Test;

import static com.github.dat210_teamone.skolerute.data.OneUtils.Contains;

/**
 * Created by Fredrik Wigsnes on 21.09.2016.
 */

public class SchoolManagerTest {
    IStorage si = new DummyStorage();
    ISettingStorage iss = new DummySettingStorage();
    public SchoolManagerTest() {

    }

    @Test
    public void TestAddDefault() throws Exception{
        SchoolManager sm = new SchoolManager(si, iss);
        Assert.assertFalse("Skole 4 is already in the array", Contains(sm.getSelectedSchools(), item -> item.getSchoolName().equals("Skole 4")));
        sm.addDefault("Skole 4");
        Assert.assertTrue("Skole 4 is not added to array", Contains(sm.getSelectedSchools(), item -> item.getSchoolName().equals("Skole 4")));
    }
}
