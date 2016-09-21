package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.CsvFileReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by espen on 21.09.16.
 */

public class CsvFileReaderTest {
    private CsvFileReader csvFileReader = new CsvFileReader("/home/espen/Downloads/skoler.csv", "/home/espen/Downloads/skolerute-2016-17.csv");

    @Test
    public void TestCsvFileReader() throws Exception {
        Assert.assertEquals(1,1);
    }
}
