package com.github.dat210_teamone.skolerute.data;

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

public class CsvFileReader {
    private ArrayList<SchoolInfo> schoolInfos;
    private ArrayList<SchoolVacationDay> schoolVacationDays;

    public CsvFileReader(String schoolInfoFileName, String vacationDayFileName) {
        schoolInfos = new ArrayList<>();
        schoolVacationDays = new ArrayList<>();
        readSchoolInfoCsv(schoolInfoFileName);
        readSchoolVacationDayCsv(vacationDayFileName);
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

    private void readSchoolVacationDayCsv(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
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
                schoolVacationDays.add(tmpVacationDay);
            }
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
