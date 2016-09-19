package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.*;

import java.util.function.Function;

/**
 * Created by Nicolas on 19.09.2016.
 */
public interface StorageInterface {
    SchoolInfo[] GetSchoolInfo();
    SchoolVacationDay[] GetVacationDays();

    SchoolInfo[] GetSchoolInfo(Predicate<SchoolInfo> func);
    SchoolVacationDay[] GetVacationDays(Predicate<SchoolVacationDay> func);
}
