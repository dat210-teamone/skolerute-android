package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.StorageInterface;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Nicolas on 19.09.2016.
 */
public class StorageInterfaceTest {
    @Test
    public void TestRetrieveAllSchools() throws Exception{
        StorageInterface si = new DummyStorage();

        SchoolInfo[] test = si.GetSchoolInfo();
        Assert.assertEquals(10, test.length);
    }

    @Test
    public void TestRetrieveAllVacationDays() throws Exception{
        StorageInterface si = new DummyStorage();

        SchoolVacationDay[] test = si.GetVacationDays();
        Assert.assertEquals(10, test.length);
    }
}
