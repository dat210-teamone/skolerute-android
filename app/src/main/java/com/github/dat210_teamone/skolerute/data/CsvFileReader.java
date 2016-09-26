package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public CsvFileReader() {
        schoolInfos = new ArrayList<>();
        vacationDays = new ArrayList<>();
        BufferedReader schoolInfoReader = CsvFileGetter.getFileReader("http://open.stavanger.kommune.no/dataset/8f8ac030-0d03-46e2-8eb7-844ee11a6203/resource/0371a1db-7074-4568-a0cc-499a5dccfe98/download/skoler.csv");
        readSchoolInfoCsv(schoolInfoReader);
        BufferedReader vacationDayReader = CsvFileGetter.getFileReader("http://open.stavanger.kommune.no/dataset/86d3fe44-111e-4d82-be5a-67a9dbfbfcbb/resource/32d52130-ce7c-4282-9d37-3c68c7cdba92/download/skolerute-2016-17.csv");
        readSchoolVacationDayCsv(vacationDayReader);
    }

    public CsvFileReader(String schoolInfoFileName, String vacationDayFileName) {
        schoolInfos = new ArrayList<>();
        vacationDays = new ArrayList<>();
        readSchoolInfoCsv(schoolInfoFileName);
        readSchoolVacationDayCsv(vacationDayFileName);
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
                schoolInfos.add(tmpInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSchoolInfoCsv(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while((line = br.readLine()) != null) {
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
                schoolInfos.add(tmpInfo);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void readSchoolVacationDayCsv(BufferedReader reader) {
        String line;
        try {
            reader.readLine();
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
                vacationDays.add(tmpVacationDay);
            }
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void readSchoolVacationDayCsv(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
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
                vacationDays.add(tmpVacationDay);
            }
        } catch(IOException | ParseException e) {
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
