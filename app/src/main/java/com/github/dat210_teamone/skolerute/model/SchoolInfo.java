package com.github.dat210_teamone.skolerute.model;

import android.location.Location;

/**
 * Created by Nicolas on 14.09.2016.
 * Part of project skolerute-android
 */
@SuppressWarnings("unused")
public class SchoolInfo implements java.io.Serializable {
    private static final long serialVersionUID = 8001578383494727861L;
    private int id;
    private double north;
    private double east;
    private double latitude;
    private double longitude;
    private String objectType;
    private int komm;
    private int byggTyp_NBR;
    private String information;
    private String schoolName;
    private String address;
    private String homePage;
    private String sudents;
    private String capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    public Location getLocation(){
        Location location = new Location("SchoolInfo model");
        location.setLatitude(getLatitude());
        location.setLongitude(getLongitude());
        return location;
    }

}
