package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.dummy.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.dummy.DummyStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static com.github.dat210_teamone.skolerute.data.OneUtils.Contains;

/**
 * Created by Fredrik Wigsnes on 21.09.2016.
 */

public class SchoolManagerTest {

    private final IStorage si = new DummyStorage();
    private final ISettingStorage iss = new DummySettingStorage(true);

    private final SchoolManager sm;

    public SchoolManagerTest() {
        this.sm = new SchoolManager(si,iss);
    }
    

    @Test
    public void TestCheckName() {
        boolean cn1 = sm.checkName("Skole 2");
        boolean cn2 = sm.checkName("Skole 3");
        Assert.assertTrue("Name not in selected schools", cn1);
        Assert.assertFalse("Name in selected schools", cn2);
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

    @Test
    public void TestGetNextVacationDay() {
        SchoolVacationDay svd = sm.getNextVacationDay("Skole 2");
        Assert.assertTrue("Date comes before today", new Date(System.currentTimeMillis() - 86400000).before(svd.getDate())); //Removed one day
        Assert.assertNotNull("Date is set to null", svd);
    }

    @Test
    public void TestaddDefault() {
        sm.addDefault("Skole 99");
        Assert.assertTrue("Skole 99 is not correctly added", sm.checkName("Skole 99"));
    }

    @Test
    public void TestRemoveDefault() {
        sm.addDefault("Skole 99");
        sm.removeDefault("Skole 99");
        Assert.assertFalse("Skole 99 is not correctly deleted", sm.checkName("Skole 99"));
    }

    @Test
    public void TestGetMatchingSchools() {
        String q = "Skole 0";
        List<SchoolInfo> l = sm.getMatchingSchools(q);

        if(l.size() == 0){
            Assert.assertTrue("No elements in arrayList.", false);
        }else {
            Assert.assertEquals(q, l.get(0).getSchoolName());
        }
    }


    @Test
    public void getSchoolInfo_gettoday() throws Exception{
        Date d = new Date();
        SchoolInfo[] infos = sm.getSchoolInfo(d);
        Assert.assertEquals(2, infos.length);
    }

    @Test
    public void getSchoolInfo_getanotherdaywithinfos() throws Exception{
        Date d = new Date(new Date().getTime() + 86400000 * 3);
        SchoolInfo[] infos = sm.getSchoolInfo(d);
        Assert.assertEquals(2, infos.length);
    }

    @Test
    public void getSchoolInfo_getanotherdaywithoutinfos() throws Exception{
        Date d = new Date(new Date().getTime() + 86400000 * 10);
        SchoolInfo[] infos = sm.getSchoolInfo(d);
        Assert.assertEquals(0, infos.length);
    }
}
