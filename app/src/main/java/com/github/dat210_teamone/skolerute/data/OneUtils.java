package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;

import java.net.URL;

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

    public static String getFileName(URL url){
        String[] test = url.getFile().split("/");
        return test[test.length - 1];

    }
}
