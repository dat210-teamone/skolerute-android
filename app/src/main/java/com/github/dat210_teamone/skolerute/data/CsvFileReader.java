package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.data.interfaces.ICsvGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by espen on 21.09.16.
 */

public class CsvFileReader implements IStorage {
    private ArrayList<SchoolInfo> schoolInfos;
    private ArrayList<SchoolVacationDay> vacationDays;

    private ICsvGetter bufferGetter;
    private boolean useCache = false;

    private enum SerializeType {
        SCHOOL_INFO,
        VACATION_DAYS,
    }

    public CsvFileReader() {
        this(InterfaceManager.getBufferGetter());
    }

    public CsvFileReader(ICsvGetter getter) {
        schoolInfos = new ArrayList<>();
        vacationDays = new ArrayList<>();
        this.bufferGetter = getter;
    }

    public CsvFileReader initializeReader(){
        return initializeReader(true);
    }

    public CsvFileReader initializeReader(boolean useCache) {
        // TODO: CHECK: File may need to be stored somewhere else on Android
        this.useCache = useCache;
        if (useCache) {
            File dir = InterfaceManager.getStoragePath();
            File serialisedSchoolInfo = new File(dir, "schoolInfo.ser");
            File serializedVacationDays = new File(dir, "vacationDays.ser");

            if (serializedVacationDays.exists() && !serializedVacationDays.isDirectory()) {
                deserializeSchoolObjects(SerializeType.VACATION_DAYS);
            } else {
                readSchoolVacationDayCsv(bufferGetter.getSchoolDayReader());
            }

            if (serialisedSchoolInfo.exists() && !serialisedSchoolInfo.isDirectory()) {
                deserializeSchoolObjects(SerializeType.SCHOOL_INFO);
            } else {
                readSchoolInfoCsv(bufferGetter.getSchoolReader());
            }
        }
        else{
            readSchoolInfoCsv(bufferGetter.getSchoolReader());
            readSchoolVacationDayCsv(bufferGetter.getSchoolDayReader());
        }
        return this;
    }

    private void readSchoolInfoCsv(BufferedReader reader) {
        String line;
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] attribs = line.split(",");
                SchoolInfo tmpInfo = new SchoolInfo();
                tmpInfo.setNorth(Double.parseDouble(attribs[0]));
                tmpInfo.setEast(Double.parseDouble((attribs[1])));
                tmpInfo.setLatitude(Double.parseDouble(attribs[2]));
                tmpInfo.setLongitude(Double.parseDouble(attribs[3]));
                tmpInfo.setId(Integer.parseInt(attribs[4]));
                tmpInfo.setObjectType(attribs[5]);
                tmpInfo.setKomm(Integer.parseInt(attribs[6]));
                tmpInfo.setByggTyp_NBR(Integer.parseInt(attribs[7]));
                tmpInfo.setInformation(attribs[8]);
                tmpInfo.setSchoolName(attribs[9]);
                tmpInfo.setAddress(attribs[10]);
                tmpInfo.setHomePage(attribs[11]);
                tmpInfo.setSudents(attribs[12]);
                tmpInfo.setCapacity(attribs[13]);
                if (loadedSchools.length == 0 || OneUtils.Contains(loadedSchools, (a) -> a.equals(tmpInfo.getSchoolName()))) {
                    schoolInfos.add(tmpInfo);
                }

            }
            reader.close();
            serializeSchoolObjects(SerializeType.SCHOOL_INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String[] loadedSchools = new String[0];
    private void readSchoolVacationDayCsv(BufferedReader reader) {
        String line;
        try {
            ArrayList<String> tempSchools = new ArrayList<>();
            reader.readLine();
            String last = "";
            while ((line = reader.readLine()) != null) {
                if(line.equals(""))
                    break;

                String[] attrib = line.split(",");
                SchoolVacationDay tmpVacationDay = new SchoolVacationDay();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                tmpVacationDay.setDate(formatter.parse(attrib[0]));
                tmpVacationDay.setName(attrib[1]);
                tmpVacationDay.setStudentDay(attrib[2].equals("Ja"));
                tmpVacationDay.setTeacherDay(attrib[3].equals("Ja"));
                tmpVacationDay.setSfoDay(attrib[4].equals("Ja"));
                if(attrib.length == 6) {
                    tmpVacationDay.setComment(attrib[5]);
                } else {
                    tmpVacationDay.setComment("");
                }
                if (tmpVacationDay.isStudentDay() && tmpVacationDay.isTeacherDay())
                    continue;
                if (tmpVacationDay.getComment().length() == 6)
                    continue;
                if (!last.equals(tmpVacationDay.getName())) {
                    tempSchools.add(tmpVacationDay.getName());
                    last = tmpVacationDay.getName();
                }
                vacationDays.add(tmpVacationDay);
            }
            loadedSchools = new String[tempSchools.size()];
            tempSchools.toArray(loadedSchools);
            reader.close();
            serializeSchoolObjects(SerializeType.VACATION_DAYS);
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void serializeSchoolObjects(SerializeType selector) {
        if (!useCache)
            return;
        try {
            String filename = (selector == SerializeType.SCHOOL_INFO) ? "schoolInfo.ser" : "vacationDays.ser";
            File dir = InterfaceManager.getStoragePath();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(dir, filename)));
            objectOutputStream.writeObject((selector == SerializeType.SCHOOL_INFO) ? schoolInfos : vacationDays);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserializeSchoolObjects(SerializeType selector) {
        try {
            String filename = (selector == SerializeType.SCHOOL_INFO) ? "schoolInfo.ser" : "vacationDays.ser";
            File dir = InterfaceManager.getStoragePath();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(dir, filename)));
            if(selector == SerializeType.SCHOOL_INFO)
                schoolInfos = (ArrayList<SchoolInfo>) objectInputStream.readObject();
            else if(selector == SerializeType.VACATION_DAYS)
                vacationDays = (ArrayList<SchoolVacationDay>) objectInputStream.readObject();
            else
                System.err.println("Deserialize error");
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
        return vacationDays.toArray(new SchoolVacationDay[vacationDays.size()]);
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
        for (SchoolVacationDay obj : vacationDays)
        {
            if (func.apply(obj)) {
                filter.add(obj);
            }
        }
        return filter.toArray(new SchoolVacationDay[filter.size()]);
    }
}
