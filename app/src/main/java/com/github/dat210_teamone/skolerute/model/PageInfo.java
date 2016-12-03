package com.github.dat210_teamone.skolerute.model;

/**
 * Created by Nicolas on 26.09.2016.
 */

@SuppressWarnings("unused")
public class PageInfo {
    private String baseURL;
    private String csvURL;
    private String lastUpdated;

    public PageInfo(String baseURL, String csvURL, String lastUpdated){
        this.baseURL = baseURL;
        this.csvURL = csvURL;
        this.lastUpdated = lastUpdated;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getCsvURL() {
        return csvURL;
    }

    public void setCsvURL(String csvURL) {
        this.csvURL = csvURL;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
