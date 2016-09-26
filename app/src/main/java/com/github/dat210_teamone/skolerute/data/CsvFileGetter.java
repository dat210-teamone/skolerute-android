package com.github.dat210_teamone.skolerute.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fredrik Wigsnes on 26.09.2016.
 */

public class CsvFileGetter implements ICsvGetter {

    public static BufferedReader getFileReader(String url) {
        try {
            URL u = new URL(url);
            InputStream input = u.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input,"UTF-8"));
            return br;
        } catch (IOException e) {
            System.out.println("file is null.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public BufferedReader getSchoolReader() {
        return CsvFileGetter.getFileReader("http://open.stavanger.kommune.no/dataset/8f8ac030-0d03-46e2-8eb7-844ee11a6203/resource/0371a1db-7074-4568-a0cc-499a5dccfe98/download/skoler.csv");
    }

    @Override
    public BufferedReader getSchoolDayReader() {
        return CsvFileGetter.getFileReader("http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skolerute-2016-17.csv");
    }
}
