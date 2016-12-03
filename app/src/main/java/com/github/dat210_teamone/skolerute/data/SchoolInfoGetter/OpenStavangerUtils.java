package com.github.dat210_teamone.skolerute.data.SchoolInfoGetter;

import android.os.AsyncTask;

import com.github.dat210_teamone.skolerute.data.GetPageInfoTask;
import com.github.dat210_teamone.skolerute.data.InterfaceManager;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.model.PageInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Nicolas on 24.10.2016.
 * Part of project skolerute-android
 */

public class OpenStavangerUtils {

    private static final HashMap<String, PageInfo> infoCache = new HashMap<>();

    private static void initData(){
        try {
            String s1 = "http://open.stavanger.kommune.no/dataset/skoler-stavanger";
            String s2 = "http://open.stavanger.kommune.no/dataset/skolerute-stavanger";
            String g1 = "http://open.stavanger.kommune.no/dataset/skoler-i-gjesdal-kommune";
            String g2 = "http://open.stavanger.kommune.no/dataset/skoleruten-for-gjesdal-kommune";
            //PageInfo info1 = new PageInfo(u, "http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skoler.csv", "");
            //PageInfo info2 = new PageInfo(u2, "http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skolerute-2016-17.csv", "");
            PageInfo info1 = new PageInfo(s1, "http://nicroware.com/skoler.csv", "");
            PageInfo info2 = new PageInfo(s2, "http://nicroware.com/skolerute-2016-17.csv", "");
            PageInfo info3 = new PageInfo(g1, "http://nicroware.com/barne--og-ungdomsskoler-gjesdal-kommune.csv", "");
            PageInfo info4 = new PageInfo(g2, "http://nicroware.com/skolerute-gjesdal-kommune2.csv", "");
            infoCache.put(s1, info1);
            infoCache.put(s2, info2);
            infoCache.put(g1, info3);
            infoCache.put(g2, info4);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  BufferedReader getFileReader(String url){
        return getFileReader(url, "UTF-8");
    }

    public static BufferedReader getFileReader(String url, String encoding) {
        try {
            URL u = new URL(url);
            String fileName = OneUtils.getFileName(u);
            File file = new File(InterfaceManager.getStoragePath(), fileName);
            if (!file.exists())
            {
                FileOutputStream writeStream = new FileOutputStream(file);
                GetPageInfoTask task = new GetPageInfoTask();
                task.execute(u);

                writeStream.write(task.get());
                writeStream.close();
            }

            return new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
        } catch (Exception e) {
            System.out.println("file is null.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String lastCsvUrl(String s){
        String searchString = "class=\"resource-item\" data-id=\"";
        int i = s.lastIndexOf(searchString);
        int begin = s.indexOf("<a href=\"http://open.stavanger.kommune.no/", i) + 9;
        int end = s.indexOf('"', begin);
        return s.substring(begin, end);
    }

    private static String lastUpdated(String s){
        String searchString = "<th scope=\"row\" class=\"dataset-label\">Last Updated</th>";
        int i = s.indexOf(searchString);
        int begin = s.indexOf("<span class=\"automatic-local-datetime\" data-datetime=\"", i) + 80;
        int end = s.indexOf("<", begin);
        return s.substring(begin, end).trim();
    }

    public static PageInfo getInfo(String url) {
        if (infoCache.isEmpty()){
            initData();
        }
        if (infoCache.containsKey(url))
            return infoCache.get(url);
        else
        {
            try {
                URL u = new URL(url);

                GetPageInfoTask task = new GetPageInfoTask();
                AsyncTask<URL, Void, byte[]> test =  task.execute(u);
                String s = new String(test.get());
                PageInfo info = new PageInfo(url,lastCsvUrl(s) , lastUpdated(s));

                infoCache.put(url, info);
                return info;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new PageInfo(url, null, null);
        }
    }

    public static String getFileUrl(String url) {
        return getInfo(url).getCsvURL();
    }

    public static boolean fileHasBeenUpdated(String url) {
        return !(getInfo(url).getLastUpdated().equals(InterfaceManager.getSettings().getLastUpdateTime()));
    }

}
