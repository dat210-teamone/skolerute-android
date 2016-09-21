package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.IStorage;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Fredrik Wigsnes on 21.09.2016.
 */

public class SchoolManagerTest {

    private SchoolManager sm;

    public SchoolManagerTest() {
        IStorage ds = new DummyStorage();
        ISettingStorage dss = new DummySettingStorage();
        this.sm = new SchoolManager(ds,dss);
    }

    @Test
    public void TestCheckName() {
        boolean cn1 = sm.checkName("Skole 2");
        boolean cn2 = sm.checkName("Skole 3");
        Assert.assertTrue("Name not in selected schools", cn1);
        Assert.assertFalse("Name in selected schools", cn2);
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

    @Test
    public void TestGetNextVacationDay() {
        SchoolVacationDay svd = sm.getNextVacationDay("Skole 2");
        Assert.assertTrue("Date comes before today", new Date(System.currentTimeMillis()).before(svd.getDate()));
        Assert.assertNotNull("Date is set to null", svd);
    }
}
