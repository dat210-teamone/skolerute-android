package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Nicolas on 24.10.2016.
 * Part of project skolerute-android
 */

@SuppressWarnings("Convert2streamapi")
public class SchoolStorage implements IStorage {
    private ArrayList<SchoolInfo> schoolInfos = new ArrayList<>();
    private ArrayList<SchoolVacationDay> schoolVacationDays = new ArrayList<>();
    private boolean useCache;

    public SchoolStorage(){
        this(InterfaceManager.getSchoolGetters());

    }

    private SchoolStorage(ISchoolInfoGetter[] schoolGetters){
        this.schoolInfoGetters = schoolGetters;
    }

    private final ISchoolInfoGetter[] schoolInfoGetters;

    private enum SerializeType {
        SCHOOL_INFO,
        VACATION_DAYS,
    }

    public SchoolStorage initializeStorage(){
        return initializeStorage(true);
    }

    private SchoolStorage initializeStorage(boolean useCache) {
        // TODO: CHECK: File may need to be stored somewhere else on Android
        this.useCache = useCache;
        if (useCache) {
            File dir = InterfaceManager.getStoragePath();
            File serialisedSchoolInfo = new File(dir, "schoolInfo.ser");
            File serializedVacationDays = new File(dir, "vacationDays.ser");

            if (serializedVacationDays.exists() && !serializedVacationDays.isDirectory()) {
                deserializeSchoolObjects(SerializeType.VACATION_DAYS);
            } else {
                loadSchoolVacationDays();
                //readSchoolVacationDayCsv(bufferGetter.getSchoolDayReader());
            }

            if (serialisedSchoolInfo.exists() && !serialisedSchoolInfo.isDirectory()) {
                deserializeSchoolObjects(SerializeType.SCHOOL_INFO);
            } else {
                loadSchoolInfo();
                //readSchoolInfoCsv(bufferGetter.getSchoolReader());
            }
        }
        else{
            loadSchoolVacationDays();
            loadSchoolInfo();

            //readSchoolInfoCsv(bufferGetter.getSchoolReader());
            //readSchoolVacationDayCsv(bufferGetter.getSchoolDayReader());
        }
        return this;
    }

    @Override
    public void forceUpdate(){
        initializeStorage(false);
    }

    private void loadSchoolInfo(){
        for (ISchoolInfoGetter schoolInfoGetter : schoolInfoGetters) {
            schoolInfos.addAll(OneUtils.toArrayList(schoolInfoGetter.getAllSchoolInfo()));
        }
        serializeSchoolObjects(SerializeType.SCHOOL_INFO);
    }

    private void loadSchoolVacationDays(){
        for (ISchoolInfoGetter schoolInfoGetter : schoolInfoGetters) {
            schoolVacationDays.addAll(OneUtils.toArrayList(schoolInfoGetter.getAllSchoolVacationDays()));
        }
        serializeSchoolObjects(SerializeType.VACATION_DAYS);
    }

    private void serializeSchoolObjects(SerializeType selector) {
        if (!useCache)
            return;
        try {
            String filename = (selector == SerializeType.SCHOOL_INFO) ? "schoolInfo.ser" : "vacationDays.ser";
            File dir = InterfaceManager.getStoragePath();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(dir, filename)));
            objectOutputStream.writeObject((selector == SerializeType.SCHOOL_INFO) ? schoolInfos : schoolVacationDays);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void deserializeSchoolObjects(SerializeType selector) {
        try {
            String filename = (selector == SerializeType.SCHOOL_INFO) ? "schoolInfo.ser" : "vacationDays.ser";
            File dir = InterfaceManager.getStoragePath();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(dir, filename)));

            if(selector == SerializeType.SCHOOL_INFO) {
                //noinspection unchecked
                schoolInfos = (ArrayList<SchoolInfo>)objectInputStream.readObject();
            }
            else if(selector == SerializeType.VACATION_DAYS) {
                //noinspection unchecked
                schoolVacationDays = (ArrayList<SchoolVacationDay>) objectInputStream.readObject();
            }
            else {
                System.err.println("Deserialize error");
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SchoolInfo[] getSchoolInfo() {
        return schoolInfos.toArray(new SchoolInfo[schoolInfos.size()]);
    }

    @Override
    public SchoolVacationDay[] getVacationDays() {
        return schoolVacationDays.toArray(new SchoolVacationDay[schoolVacationDays.size()]);
    }

    @Override
    public SchoolInfo[] getSchoolInfo(Predicate<SchoolInfo> func) {
        ArrayList<SchoolInfo> filter = new ArrayList<>();
        for (SchoolInfo obj : schoolInfos)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return filter.toArray(new SchoolInfo[filter.size()]);
    }

    @Override
    public SchoolVacationDay[] getVacationDays(Predicate<SchoolVacationDay> func) {
        ArrayList<SchoolVacationDay> filter = new ArrayList<>();
        for (SchoolVacationDay obj : schoolVacationDays)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return filter.toArray(new SchoolVacationDay[filter.size()]);
    }
}
