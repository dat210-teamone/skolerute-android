package com.github.dat210_teamone.skolerute.model;

/**
 * Created by Nicolas on 14.09.2016.
 */
public class SchoolInfo {
    int id;
    int north;
    int east;
    int latitude;
    int longitude;
    String objectType;
    int komm;
    int byggTyp_NBR;
    String information;
    String schoolName;
    String address;
    String homePage;
    String sudents;
    String capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getKomm() {
        return komm;
    }

    public void setKomm(int komm) {
        this.komm = komm;
    }

    public int getByggTyp_NBR() {
        return byggTyp_NBR;
    }

    public void setByggTyp_NBR(int byggTyp_NBR) {
        this.byggTyp_NBR = byggTyp_NBR;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getSudents() {
        return sudents;
    }

    public void setSudents(String sudents) {
        this.sudents = sudents;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


}
