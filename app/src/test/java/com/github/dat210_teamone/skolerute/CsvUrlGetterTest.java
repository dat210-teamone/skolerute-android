package com.github.dat210_teamone.skolerute;

import com.github.dat210_teamone.skolerute.data.CsvReaderGetter;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Fredrik Wigsnes on 26.09.2016.
 */

public class CsvUrlGetterTest {

    /*@Test
    public void TestGetFileUrl(){
        String url = CsvReaderGetter.getFileUrl("http://open.stavanger.kommune.no/dataset/skoler-stavanger");
        Assert.assertEquals("http://open.stavanger.kommune.no/dataset/8f8ac030-0d03-46e2-8eb7-844ee11a6203/resource/0371a1db-7074-4568-a0cc-499a5dccfe98/download/skoler.csv", url);
        String url2 = CsvReaderGetter.getFileUrl("http://open.stavanger.kommune.no/dataset/skolerute-stavanger");
        Assert.assertEquals("http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skolerute-2016-17.csv", url2);
    }*/

    @Test
    public void TestLastUpdated() {
        String date = CsvReaderGetter.lastUpdated("<tr><th scope=\"row\" class=\"dataset-label\">Last Updated</th><td class=\"dataset-details\"><span class=\"automatic-local-datetime\" data-datetime=\"2016-09-21T14:46:33+0200\">september 21, 2016, 14:46 (CEST)</span></td>");
        Assert.assertEquals("september 21, 2016, 14:46 (CEST)", date);
    }

}
