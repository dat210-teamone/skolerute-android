package com.github.dat210_teamone.skolerute.data.SchoolInfoGetter;

import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.model.PageInfo;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.SortedMap;

/**
 * Created by Nicolas on 24.10.2016.
 */

public class GjesdalSchoolInfoGetter implements ISchoolInfoGetter {

    private final String schoolInfoURL = "http://open.stavanger.kommune.no/dataset/skoler-i-gjesdal-kommune";
    private final String schoolVacationURL = "http://open.stavanger.kommune.no/dataset/skoleruten-for-gjesdal-kommune";

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
        ArrayList<SchoolVacationDay> allInfos = readSchoolVacationDayCsv(OpenStavangerUtils.getFileReader(info.getCsvURL(), "ISO-8859-1"));

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
                tmpInfo.setLongitude(Double.parseDouble(attribs[2] + "." + attribs[3]));
                tmpInfo.setLatitude(Double.parseDouble(attribs[4] + "." + attribs[5]));
                tmpInfo.setSchoolName(attribs[6].trim());
                tmpInfo.setAddress(attribs[7]);
                tmpInfo.setHomePage(attribs[9]);
                tmpInfo.setInformation(attribs[11] + " " + attribs[12]);

                /*tmpInfo.setId(Integer.parseInt(attribs[4]));
                tmpInfo.setObjectType(attribs[5]);
                tmpInfo.setKomm(Integer.parseInt(attribs[6]));
                tmpInfo.setByggTyp_NBR(Integer.parseInt(attribs[7]));
                tmpInfo.setSudents(attribs[12]);
                tmpInfo.setCapacity(attribs[13]);*/

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
                SortedMap<String, Charset> map = Charset.availableCharsets();
                String[] attrib = line.split(",");
                SchoolVacationDay tmpVacationDay = new SchoolVacationDay();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                tmpVacationDay.setDate(formatter.parse(attrib[0]));
                tmpVacationDay.setName(attrib[1].trim());
                tmpVacationDay.setStudentDay(attrib[2].trim().toUpperCase().equals("JA"));
                //tmpVacationDay.setTeacherDay(attrib[3].equals("Ja"));
                tmpVacationDay.setSfoDay(attrib[3].trim().toUpperCase().equals("JA"));
                if(attrib.length > 4) {
                    tmpVacationDay.setComment(attrib[4].trim());
                } else {
                    tmpVacationDay.setComment("");
                }
                if (tmpVacationDay.isStudentDay())
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
