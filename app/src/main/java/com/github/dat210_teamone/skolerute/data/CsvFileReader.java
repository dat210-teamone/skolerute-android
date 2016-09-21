package com.github.dat210_teamone.skolerute.data;

import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by espen on 21.09.16.
 */

public class CsvFileReader {
    private ArrayList<SchoolInfo> schoolInfo;
    private ArrayList<SchoolVacationDay> schoolVacationDay;

    public CsvFileReader(String fileName) {
        schoolInfo = new ArrayList<>();
        readSchoolInfoCsv(fileName);
    }

    private void readSchoolInfoCsv(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while((line = br.readLine()) != null) {
                SchoolInfo tmpInfo = new SchoolInfo();
                String[] attribs = line.split(",");
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
                schoolInfo.add(tmpInfo);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
