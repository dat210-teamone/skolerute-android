package com.github.dat210_teamone.skolerute.data.interfaces;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

/**
 * Created by Nicolas on 24.10.2016.
 */

public interface ISchoolInfoGetter {
    boolean IsUpToDate();
    SchoolInfo[] getAllSchoolInfo();
    SchoolVacationDay[] getAllSchoolVacationDays();
}
