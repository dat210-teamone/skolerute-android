package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.data.StorageInterface;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Nicolas on 19.09.2016.
 */
public class StorageInterfaceTest {
    @Test
    public void TestRetrieveAll() throws Exception{
        StorageInterface si = new DummyStorage();

        SchoolInfo[] test = si.GetSchoolInfo();
        Assert.assertEquals(10, test.length);
    }

    @Test
    public void TestSchoolFilter() throws Exception{
        StorageInterface si = new DummyStorage();
        SchoolInfo[] infos = si.GetSchoolInfo((test) -> { return test.getSchoolName().equals("Skole 2"); });
        Assert.assertEquals(1, infos.length);
    }
}
