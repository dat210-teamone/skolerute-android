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

public class CsvFileGetter {

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
}
