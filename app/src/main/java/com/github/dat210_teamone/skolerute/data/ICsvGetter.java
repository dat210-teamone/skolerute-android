package com.github.dat210_teamone.skolerute.data;

import java.io.BufferedReader;

/**
 * Created by Nicolas on 26.09.2016.
 */

public interface ICsvGetter {
    BufferedReader getSchoolReader();
    BufferedReader getSchoolDayReader();
}
