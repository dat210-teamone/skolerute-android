package com.github.dat210_teamone.skolerute.data;

import com.github.dat210_teamone.skolerute.model.PageInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Fredrik Wigsnes on 26.09.2016.
 */

public class CsvReaderGetter implements ICsvGetter {
    private static HashMap<String, PageInfo> infoCache = new HashMap<>();
    static BufferedReader getFileReader(String url) {
        try {
            URL u = new URL(url);
            //String test = u.getFile();
            InputStream input = u.openStream();
            return new BufferedReader(new InputStreamReader(input,"UTF-8"));
        } catch (IOException e) {
            System.out.println("file is null.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static PageInfo getInfo(String url)
    {
        if (infoCache.containsKey(url))
            return infoCache.get(url);
        else
        {
            try {
                URL u = new URL(url);
                InputStream input = u.openStream();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(input));
                byte[] bytes =  new byte[input.available()];
                dis.readFully(bytes);
                String s = new String(bytes);



                PageInfo info = new PageInfo(url,lastCsvUrl(s) , lastUpdated(s));

                infoCache.put(url, info);
                return info;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new PageInfo(url, null, null);
        }
    }

    public static String getFileUrl(String url) {
        return getInfo(url).getCsvURL();
    }

    public static boolean fileHasBeenUpdated(String url) {
        return (getInfo(url).getLastUpdated() == "september 21, 2016, 14:46 (CEST)");
    }

    private static String lastCsvUrl(String s){
        String searchString = "class=\"resource-item\" data-id=\"";
        int i = s.lastIndexOf(searchString);
        int begin = s.indexOf("<a href=\"http://open.stavanger.kommune.no/", i) + 9;
        int end = s.indexOf('"', begin);
        return s.substring(begin, end);
    }

    public static String lastUpdated(String s){
        String searchString = "<th scope=\"row\" class=\"dataset-label\">Last Updated</th>";
        int i = s.indexOf(searchString);
        int begin = s.indexOf("<span class=\"automatic-local-datetime\" data-datetime=\"", i) + 80;
        int end = s.indexOf("<", begin);
        return s.substring(begin, end);
    }

    @Override
    public BufferedReader getSchoolReader() {
        return getFileReader(getFileUrl("http://open.stavanger.kommune.no/dataset/skoler-stavanger"));
    }

    @Override
    public BufferedReader getSchoolDayReader() {
        return getFileReader(getFileUrl("http://open.stavanger.kommune.no/dataset/skolerute-stavanger"));
    }
}