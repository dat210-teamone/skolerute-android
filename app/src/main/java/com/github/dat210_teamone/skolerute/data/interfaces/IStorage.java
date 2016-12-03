package com.github.dat210_teamone.skolerute.data.interfaces;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

/**
 * Created by Nicolas on 19.09.2016.
 */
public interface IStorage {
    SchoolInfo[] getSchoolInfo();
    SchoolVacationDay[] getVacationDays();

    SchoolInfo[] getSchoolInfo(Predicate<SchoolInfo> func);
    SchoolVacationDay[] getVacationDays(Predicate<SchoolVacationDay> func);

    void forceUpdate();
}
