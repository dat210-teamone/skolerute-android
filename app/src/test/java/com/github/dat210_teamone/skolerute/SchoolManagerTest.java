package com.github.dat210_teamone.skolerute;

import com.android.internal.util.Predicate;

import com.github.dat210_teamone.skolerute.data.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.IStorage;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.junit.Assert;
import org.junit.Test;

import static com.github.dat210_teamone.skolerute.data.OneUtils.Contains;

/**
 * Created by Fredrik Wigsnes on 21.09.2016.
 */

public class SchoolManagerTest {

    IStorage si = new DummyStorage();
    ISettingStorage iss = new DummySettingStorage();

    private SchoolManager sm;

    public SchoolManagerTest() {
        this.sm = new SchoolManager(si,iss);
    }

    @Test
    public void TestCheckName() {
        boolean cn1 = sm.checkName("Skole 2");
        boolean cn2 = sm.checkName("Skole 3");
        Assert.assertEquals(true, cn1);
        Assert.assertEquals(false, cn2);
    }

    @Test
    public void TestAddDefault() throws Exception {
        Assert.assertFalse("Skole 4 is already in the array", Contains(sm.getSelectedSchools(), item -> item.getSchoolName().equals("Skole 4")));
        sm.addDefault("Skole 4");
        Assert.assertTrue("Skole 4 is not added to array", Contains(sm.getSelectedSchools(), item -> item.getSchoolName().equals("Skole 4")));
    }

    @Test
    public void TestGetSelectedSchools() {
        SchoolInfo[] si;
        si = sm.getSelectedSchools();
        Assert.assertEquals(2,si.length);
    }

    @Test
    public void TestGetSelectedSchoolDays() {
        SchoolVacationDay[] svd;
        svd = sm.getSelectedSchoolDays();
        Assert.assertEquals(10,svd.length);
    }
}
