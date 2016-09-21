package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.IStorage;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Fredrik Wigsnes on 21.09.2016.
 */

public class SchoolManagerTest {
    IStorage si = new DummyStorage();
    SchoolManager sm;
    public SchoolManagerTest() {
        this.sm = new SchoolManager(si);
    }

    @Test
    public void TestGetSelectedSchools() {
        SchoolInfo[] si = new SchoolInfo[10];
        si = sm.GetSelectedSchools();
        Assert.assertEquals(2,si.length);
    }

    @Test
    public void TestGetSelectedSchoolDays() {
        SchoolVacationDay[] si = new SchoolVacationDay[10];
        si = sm.GetSelectedSchoolDays();
        Assert.assertEquals(10,si.length);
    }
}
