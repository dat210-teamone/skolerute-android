package com.github.dat210_teamone.skolerute.data;


import android.util.Log;

import android.location.Location;


import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.PostLink;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.lang.reflect.Array;
import java.util.List;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Nicolas on 21.09.2016.
 */

public class SchoolManager {
    static SchoolManager defaultManager;
    IStorage storage;
    ISettingStorage settings;
    ArrayList<String> selectedSchools;
    Location knownPosition;

    public SchoolManager()
    {
        this(InterfaceManager.getStorage(), InterfaceManager.getSettings());
    }

    public static SchoolManager getDefault() {
        if (defaultManager == null){
            defaultManager = new SchoolManager();
        }
        return defaultManager;
    }

    public SchoolManager(IStorage storage, ISettingStorage settings){
        this.storage = storage;
        this.settings = settings;
        selectedSchools = new ArrayList<>();
        addAll(settings.getSelectedSchools());
    }

    private void addAll(String[] names){
        selectedSchools.clear();
        for(String s : names){
            selectedSchools.add(s);
        }
    }

    public boolean checkName(String name){
        for (String val : selectedSchools) {
            if (name.equals(val))
                return true;
        }
        return false;
    }

    public SchoolInfo[] getSelectedSchools(){
        return  storage.getSchoolInfo(info -> checkName(info.getSchoolName()));
    }

    public SchoolVacationDay[] getSelectedSchoolDays(){
        SchoolVacationDay[] days = storage.getVacationDays(info -> checkName(info.getName()));
        Arrays.sort(days, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return days;
    }

    public SchoolVacationDay getNextVacationDay(String name) {
        return getNextVacationDays(name, true)[0];
    }

    public SchoolVacationDay getNextVacationDay(String name, boolean includeToday) {
        return getNextVacationDays(name, includeToday)[0];
    }

    public SchoolVacationDay[] getNextVacationDays(String name) {
        return getNextVacationDays(name, true);
    }

    public SchoolVacationDay[] getNextVacationDays(String name, boolean includeToday) {
        SchoolVacationDay[] svd = storage.getVacationDays(info -> info.getName().equals(name) && info.getDate().after(new Date(System.currentTimeMillis() - ( includeToday ? 86400000 : 0)))); // removed one day
        return svd;
    }

    public void addDefault(String name) {
        settings.addSelectedSchool(name);
        addAll(settings.getSelectedSchools());
    }

    public void removeDefault(String name){
        settings.deleteSelectedSchool(name);
        addAll(settings.getSelectedSchools());
    }

    public SchoolInfo[] getSchoolInfo(){
        if (knownPosition != null){
            return getClosestSchools(knownPosition);
        }
        return storage.getSchoolInfo();
    }


    public SchoolVacationDay[] getSchoolVecationInfo()
    {
        return storage.getVacationDays();
    }

    public SchoolInfo[] getClosestSchools(Location location) {
        SchoolInfo[] data = storage.getSchoolInfo();
        Arrays.sort(data, (a, b) -> Float.compare(a.getLocation().distanceTo(location), b.getLocation().distanceTo(location)));
        return data;
    }

    public List getMatchingSchools(String query) {
        ArrayList<SchoolInfo> m = new ArrayList<>();
        if (query.length() == 0)
            m.addAll(OneUtils.toArrayList(getSchoolInfo()));
        else if (OneUtils.isNumber(query)){
            PostLink link = OneUtils.Find(PostLink.getDefaultArray(), (p) -> p.getPostNumber().equals(query));
            if (link != null)
            {
                Location l = new Location("Closes school");
                l.setLongitude(link.getLng());
                l.setLatitude(link.getLat());
                SchoolInfo[] all = getClosestSchools(l);
                for (int i = 0; i < all.length; i++){
                    m.add(all[i]);
                }
            }
        }
        else {
            try {
                Pattern p = Pattern.compile("(?i)" + query);
                for (SchoolInfo s : getSchoolInfo()) {
                    if (p.matcher(s.getSchoolName()).find() || p.matcher(s.getAddress()).find()) {
                        m.add(s);
                    }
                }
            }catch (Exception e){
                m.clear();
            }
        }
        return m;
    }

    public String getLastUpdateTime() {
        return settings.getLastUpdateTime();
    }

    public void setLastUpdateTime(String time) {
        settings.setLastUpdateTime(time);
    }

    public void setKnownPosition(Location knownPosition) {
        this.knownPosition = knownPosition;
    }

    public Location getKnownPosition(){
        return this.knownPosition;
    }
}
