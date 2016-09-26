package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.CsvFileReader;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class CsvFileReaderTest {
    private CsvFileReader csvFileReader = new CsvFileReader();

    @Test
    public void TestSchoolInfoFilter() {
        SchoolInfo[] infos = csvFileReader.getSchoolInfo((test) -> test.getSchoolName().equals("Auglend skole"));
        Assert.assertEquals(1, infos.length);
    }

    @Test
    public void TestVacationDaysFilter() {
        SchoolVacationDay[] infos = csvFileReader.getVacationDays((test) -> test.getName().equals("Auglend skole"));
        Assert.assertEquals(365, infos.length);
    }
}
