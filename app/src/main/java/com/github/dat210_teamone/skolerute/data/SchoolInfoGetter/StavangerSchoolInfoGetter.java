package com.github.dat210_teamone.skolerute.data.SchoolInfoGetter;

import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Nicolas on 24.10.2016.
 * Part of project skolerute-android
 */

public class StavangerSchoolInfoGetter implements ISchoolInfoGetter {

    @Override
    public boolean IsUpToDate() {
        return true;
    }

    @Override
    public SchoolInfo[] getAllSchoolInfo() {
        String schoolInfoURL = "http://open.stavanger.kommune.no/dataset/skoler-stavanger";
        PageInfo info = OpenStavangerUtils.getInfo(schoolInfoURL);
        ArrayList<SchoolInfo> allInfos = readSchoolInfoCsv(OpenStavangerUtils.getFileReader(info.getCsvURL()));

        return allInfos.toArray(new SchoolInfo[allInfos.size()]);
    }

    @Override
    public SchoolVacationDay[] getAllSchoolVacationDays() {
        String schoolVacationURL = "http://open.stavanger.kommune.no/dataset/skolerute-stavanger";
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
                String[] values = line.split(",");
                SchoolInfo tmpInfo = new SchoolInfo();
                tmpInfo.setNorth(Double.parseDouble(values[0]));
                tmpInfo.setEast(Double.parseDouble((values[1])));
                tmpInfo.setLatitude(Double.parseDouble(values[2]));
                tmpInfo.setLongitude(Double.parseDouble(values[3]));
                tmpInfo.setId(Integer.parseInt(values[4]));
                tmpInfo.setObjectType(values[5]);
                tmpInfo.setKomm(Integer.parseInt(values[6]));
                tmpInfo.setByggTyp_NBR(Integer.parseInt(values[7]));
                tmpInfo.setInformation(values[8]);
                tmpInfo.setSchoolName(values[9].trim());
                tmpInfo.setAddress(values[10]);
                tmpInfo.setHomePage(values[11]);
                tmpInfo.setSudents(values[12]);
                tmpInfo.setCapacity(values[13]);
                if (loadedSchools.length == 0 || OneUtils.Contains(loadedSchools, (a) -> a.toUpperCase().equals(tmpInfo.getSchoolName().toUpperCase()))) {
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
    private ArrayList<SchoolVacationDay> readSchoolVacationDayCsv(BufferedReader reader) {
        ArrayList<SchoolVacationDay> vacationDays = new ArrayList<>();
        String line;
        try {
            ArrayList<String> tempSchools = new ArrayList<>();
            reader.readLine();
            String last = "";
            while ((line = reader.readLine()) != null) {
                if(line.equals(""))
                    break;

                String[] values = line.split(",");
                SchoolVacationDay tmpVacationDay = new SchoolVacationDay();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                tmpVacationDay.setDate(formatter.parse(values[0]));
                tmpVacationDay.setName(values[1].trim());
                tmpVacationDay.setStudentDay(values[2].trim().toUpperCase().equals("JA"));
                tmpVacationDay.setTeacherDay(values[3].trim().toUpperCase().equals("JA"));
                tmpVacationDay.setSfoDay(values[4].trim().toUpperCase().equals("JA"));
                if(values.length > 5) {
                    tmpVacationDay.setComment(values[5]);
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
