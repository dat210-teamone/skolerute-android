package com.github.dat210_teamone.skolerute.data;

import android.location.Location;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class OneUtils {
    public static <T> boolean Contains(T[] data, Predicate<T> check){
        for (T t : data){
            if (check.apply(t)){
                return true;
            }
        }
        return false;
    }

    public static <T> T Find(T[] data, Predicate<T> check){
        for (T t : data){
            if (check.apply(t)){
                return t;
            }
        }
        return null;
    }

    public static String getFileName(URL url){
        String[] test = url.getFile().split("/");
        return test[test.length - 1];
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws Exception{
        int available = inputStream.available();
        while (available > 0){
            byte[] bytes;
            if (available > 2048) {
                bytes = new byte[2048];
            }else{
                bytes = new byte[available];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            available = inputStream.available();
        }
    }

    public static Location getLocationFromSchool(SchoolInfo info){
        Location location = new Location("SchoolInfo model");
        location.setLatitude(info.getLatitude());
        location.setLongitude(info.getLongitude());
        return location;
    }

    public static boolean isNumber(String s){

        for (int i = 0; i < s.length(); i++)
        {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    public static <T> ArrayList<T> toArrayList(T[] items)
    {
        ArrayList<T> returnItems = new ArrayList<T>();
        for (int i = 0; i < items.length; i++){
            returnItems.add(items[i]);
        }
        return returnItems;
    }
}
