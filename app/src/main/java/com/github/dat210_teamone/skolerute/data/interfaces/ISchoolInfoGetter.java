package com.github.dat210_teamone.skolerute.data.interfaces;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

/**
 * Created by Nicolas on 24.10.2016.
 * Part of project skolerute-android
 */

public interface ISchoolInfoGetter {
    @SuppressWarnings({"unused", "SameReturnValue"})
    boolean IsUpToDate();
    SchoolInfo[] getAllSchoolInfo();
    SchoolVacationDay[] getAllSchoolVacationDays();
}
