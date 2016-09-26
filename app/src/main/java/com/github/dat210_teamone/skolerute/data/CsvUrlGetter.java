package com.github.dat210_teamone.skolerute.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fredrik Wigsnes on 26.09.2016.
 */

public class CsvUrlGetter {

    static BufferedReader getFileReader(String url) {
        try {
            URL u = new URL(url);
            InputStream input = u.openStream();
            return new BufferedReader(new InputStreamReader(input,"UTF-8"));
        } catch (IOException e) {
            System.out.println("file is null.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getFileUrl(String url) {
        try {
            URL u = new URL(url);
            InputStream input = u.openStream();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(input));
            byte[] bytes =  new byte[input.available()];
            dis.readFully(bytes);
            String s = new String(bytes);
            String searchString = "class=\"resource-item\" data-id=\"";
            int i = s.lastIndexOf(searchString);
            int begin = s.indexOf("<a href=\"http://open.stavanger.kommune.no/", i) + 9;
            int end = s.indexOf('"', begin);
            return s.substring(begin, end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
