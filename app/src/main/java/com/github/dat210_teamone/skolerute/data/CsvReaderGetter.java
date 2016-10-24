package com.github.dat210_teamone.skolerute.data;

import android.os.AsyncTask;

import com.github.dat210_teamone.skolerute.data.interfaces.ICsvGetter;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.data.SchoolManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Fredrik Wigsnes on 26.09.2016.
 */

public class CsvReaderGetter implements ICsvGetter {

    public static void initData(){
        try {
            String u = "http://open.stavanger.kommune.no/dataset/skoler-stavanger";
            String u2 = "http://open.stavanger.kommune.no/dataset/skolerute-stavanger";
            //PageInfo info1 = new PageInfo(u, "http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skoler.csv", "");
            //PageInfo info2 = new PageInfo(u2, "http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skolerute-2016-17.csv", "");
            PageInfo info1 = new PageInfo(u, "http://nicroware.com/skoler.csv", "");
            PageInfo info2 = new PageInfo(u2, "http://nicroware.com/skolerute-2016-17.csv", "");
            infoCache.put(u, info1);
            infoCache.put(u2, info2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static HashMap<String, PageInfo> infoCache = new HashMap<>();


    public static BufferedReader getFileReader(String url) {
        try {
            URL u = new URL(url);
            String fileName = OneUtils.getFileName(u);
            File file = new File(InterfaceManager.getStoragePath(), fileName);
            if (!file.exists())
            {
                FileOutputStream writeStream = new FileOutputStream(file);
                GetPageInfoTask task = new GetPageInfoTask();
                task.execute(u);

                writeStream.write(task.get().getBytes());
                writeStream.close();
            }

            return new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        } catch (Exception e) {
            System.out.println("file is null.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
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
                AsyncTask<URL, Void, String> test =  task.execute(u);
                String s = test.get();
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

    public static String lastCsvUrl(String s){
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
        String date = s.substring(begin, end).trim();
        //SchoolManager.getDefault().setLastUpdateTime(date);
        return date;

    }

    @Override
    public BufferedReader getSchoolReader() {
        //http://open.stavanger.kommune.no/dataset/dfb9b81c-d9a2-4542-8f63-7584a3594e02/resource/b55f5f5a-ffac-47f2-ad57-d439f696cc87/download/barne--og-ungdomsskoler-gjesdal-kommune.csv
        //http://open.stavanger.kommune.no/dataset/skoler-i-gjesdal-kommune
        return getFileReader(getFileUrl("http://open.stavanger.kommune.no/dataset/skoler-stavanger"));
    }

    @Override
    public BufferedReader getSchoolDayReader() {
        //http://open.stavanger.kommune.no/dataset/c1a060b6-350c-433d-ac78-964ae8b0a9e3/resource/667ed24a-d3a0-4210-9086-f1d336429081/download/skolerute-gjesdal-kommune2.csv
        //http://open.stavanger.kommune.no/dataset/skoleruten-for-gjesdal-kommune
        return getFileReader(getFileUrl("http://open.stavanger.kommune.no/dataset/skolerute-stavanger"));
    }
}
