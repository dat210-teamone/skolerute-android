package com.github.dat210_teamone.skolerute.data.SchoolInfoGetter;

import android.os.AsyncTask;

import com.github.dat210_teamone.skolerute.data.CsvFileReader;
import com.github.dat210_teamone.skolerute.data.GetPageInfoTask;
import com.github.dat210_teamone.skolerute.data.InterfaceManager;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Nicolas on 24.10.2016.
 */

public class StavangerSchoolInfoGetter implements ISchoolInfoGetter {

    private String schoolInfoURL = "http://open.stavanger.kommune.no/dataset/skoler-stavanger";
    private String schoolVacationURL = "http://open.stavanger.kommune.no/dataset/skolerute-stavanger";

    @Override
    public boolean IsUpToDate() {
        return true;
    }

    @Override
    public SchoolInfo[] getAllSchoolInfo() {
        PageInfo info = OpenStavangerUtils.getInfo(schoolInfoURL);
        ArrayList<SchoolInfo> allInfos = readSchoolInfoCsv(OpenStavangerUtils.getFileReader(info.getCsvURL()));

        return allInfos.toArray(new SchoolInfo[allInfos.size()]);
    }

    @Override
    public SchoolVacationDay[] getAllSchoolVacationDays() {
        PageInfo info = OpenStavangerUtils.getInfo(schoolVacationURL);
        ArrayList<SchoolVacationDay> allInfos = readSchoolVacationDayCsv(OpenStavangerUtils.getFileReader(info.getCsvURL()));

        return allInfos.toArray(new SchoolVacationDay[allInfos.size()]);
    }

    private ArrayList<SchoolInfo> readSchoolInfoCsv(BufferedReader reader) {
        ArrayList<SchoolInfo> schoolInfos = new ArrayList<>();
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
            //serializeSchoolObjects(CsvFileReader.SerializeType.SCHOOL_INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schoolInfos;
    }

    private String[] loadedSchools = new String[0];
    public ArrayList<SchoolVacationDay> readSchoolVacationDayCsv(BufferedReader reader) {
        ArrayList<SchoolVacationDay> vacationDays = new ArrayList<>();
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
            loadedSchools = tempSchools.toArray(loadedSchools);
            reader.close();
            //serializeSchoolObjects(CsvFileReader.SerializeType.VACATION_DAYS);
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
        return  vacationDays;
    }
}
