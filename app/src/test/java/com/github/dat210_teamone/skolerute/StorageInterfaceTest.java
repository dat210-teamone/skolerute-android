package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.dummy.DummyStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nicolas on 19.09.2016.
 * Part of project skolerute-android
 */
public class StorageInterfaceTest {
    private final IStorage si = new DummyStorage();
    public StorageInterfaceTest()
    {

    }

    @Test
    public void TestRetrieveAllSchools() throws Exception {
        SchoolInfo[] test = si.getSchoolInfo();
        Assert.assertEquals(10, test.length);
    }

    @Test
    public void TestSchoolFilter() throws Exception {
        SchoolInfo[] infos = si.getSchoolInfo((test) -> test.getSchoolName().equals("Skole 2"));
        Assert.assertEquals(1, infos.length);
    }

    @Test
    public void TestManySchoolFilter() throws Exception {
        SchoolInfo[] infos = si.getSchoolInfo((test) -> test.getInformation().equals("Kommunal"));
        Assert.assertEquals(5, infos.length);
    }

    @Test
    public void TestRetrieveAllVacationDays() throws Exception {
        SchoolVacationDay[] test = si.getVacationDays();
        Assert.assertEquals(50, test.length);
    }

    @Test
    public void TestVacationDaysFilter() throws Exception {
        SchoolVacationDay[] infos = si.getVacationDays((test) -> test.getName().equals("Skole 3"));
        Assert.assertEquals(5, infos.length);
    }
    @Test
    public void TestManyVacationDaysFilter() throws Exception {
        SchoolVacationDay[] infos = si.getVacationDays(SchoolVacationDay::isStudentDay);
        Assert.assertEquals(25, infos.length);
    }

}
