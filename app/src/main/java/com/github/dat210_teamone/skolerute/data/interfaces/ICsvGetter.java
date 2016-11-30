package com.github.dat210_teamone.skolerute.data.interfaces;

import java.io.BufferedReader;

/**
 * Created by Nicolas on 26.09.2016.
 */

@Deprecated
public interface ICsvGetter {
    BufferedReader getSchoolReader();
    BufferedReader getSchoolDayReader();
}
