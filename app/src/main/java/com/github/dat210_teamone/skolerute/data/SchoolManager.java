package com.github.dat210_teamone.skolerute.data;


import android.location.Location;

import com.github.dat210_teamone.skolerute.data.interfaces.INotificationUpdate;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;
import com.github.dat210_teamone.skolerute.model.PostLink;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Nicolas on 21.09.2016.
 * Part of project skolerute-android
 */

@SuppressWarnings("Convert2streamapi")
public class SchoolManager {
    private static SchoolManager defaultManager;
    private final IStorage storage;
    private final ISettingStorage settings;
    private final ArrayList<String> selectedSchools;
    private Location knownPosition;

    private SchoolManager()
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
        selectedSchools.addAll(Arrays.asList(names));
    }

    public boolean checkName(String name){
        for (String val : selectedSchools) {
            if (name.toUpperCase().equals(val.toUpperCase()))
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

    private SchoolVacationDay[] getNextVacationDays(String name, boolean includeToday) {
        return storage.getVacationDays(info -> info.getName().toUpperCase().equals(name.toUpperCase()) && info.getDate().after(new Date(System.currentTimeMillis() - ( includeToday ? 86400000 : 0)))); // removed one day;
    }

    public SchoolVacationDay[] getNextVacationDays(Date date){
        ArrayList<SchoolVacationDay> allDays = new ArrayList<>();
        for (SchoolVacationDay day : getSelectedSchoolDays()){
            if (OneUtils.sameDay(day.getDate(), date)) {
                allDays.add(day);
            }
        }
        return allDays.toArray(new SchoolVacationDay[allDays.size()]);
    }

    public SchoolVacationDay[] getNextVacationDays(String[] names){
        return getNextVacationDays(names, true);
    }

    private SchoolVacationDay[] getNextVacationDays(String[] names, boolean includeToday){
        ArrayList<SchoolVacationDay> filter = new ArrayList<>();
        for (String name : names) {
            filter.addAll(OneUtils.toArrayList(getNextVacationDays(name, includeToday)));
        }
        SchoolVacationDay[] days = filter.toArray(new SchoolVacationDay[filter.size()]);
        Arrays.sort(days, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return  days;
    }

    public void addDefault(String name) {
        settings.addSelectedSchool(name);
        addNotifySchool(name);
        addAll(settings.getSelectedSchools());
    }

    public void removeDefault(String name){
        settings.deleteSelectedSchool(name);
        removeNotifySchool(name);
        addAll(settings.getSelectedSchools());
    }

    public SchoolInfo[] getSchoolInfo(){
        if (knownPosition != null){
            return getClosestSchools(knownPosition);
        }
        return storage.getSchoolInfo();
    }

    public SchoolInfo getSchoolInfo(String name){
        return OneUtils.firstOrNull(storage.getSchoolInfo((s) -> s.getSchoolName().equals(name)));
    }

    public SchoolInfo[] getSchoolInfo(Date date){
        ArrayList<SchoolInfo> allDays = new ArrayList<>();
        for (SchoolVacationDay day : getSelectedSchoolDays()){
            if (OneUtils.sameDay(day.getDate(), date)) {
                allDays.add(getSchoolInfo(day.getName()));
            }
        }
        return allDays.toArray(new SchoolInfo[allDays.size()]);
    }

    public SchoolVacationDay[] getSchoolVacationInfo()
    {
        return storage.getVacationDays();
    }

    private SchoolInfo[] getClosestSchools(Location location) {
        SchoolInfo[] data = storage.getSchoolInfo();
        Arrays.sort(data, (a, b) -> Float.compare(a.getLocation().distanceTo(location), b.getLocation().distanceTo(location)));
        return data;
    }

    public List<SchoolInfo> getMatchingSchools(String query) {
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
                m.addAll(Arrays.asList(all));
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

    public String[] getNotifySchools(){
        return settings.getNotifySchools();
    }

    public boolean getNotifySchool(String name){
        String[] schools = getNotifySchools();
        for (String s : schools) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private final ArrayList<INotificationUpdate> allUpdates = new ArrayList<>();

    public void addNotifySchool(String school){
        runEvent(allUpdates, n -> n.preNotify(INotificationUpdate.UpdateType.ADD, school));
        settings.addNotifySchool(school);
        runEvent(allUpdates, n -> n.postNotify(INotificationUpdate.UpdateType.ADD, school, true));
    }

    public boolean removeNotifySchool(String school){
        runEvent(allUpdates, n -> n.preNotify(INotificationUpdate.UpdateType.REMOVE, school));
        boolean result = settings.deleteNotifySchool(school);
        runEvent(allUpdates, n -> n.postNotify(INotificationUpdate.UpdateType.REMOVE, school, result));
        return result;
    }

    public void setGlobalNotification(boolean value){
        settings.setGlobalNotify(value);
        runEvent(allUpdates, n -> n.globalNotifyChange(value));
    }

    public boolean getGlobalNotification(){
        return settings.getGlobalNotify();
    }

    private <T>  void runEvent(ArrayList<T> list, ActionEvent<T> event) {
        for (T t : list){
            event.action(t);
        }
    }

    public void subscribe(INotificationUpdate update) {
        allUpdates.add(update);
    }

    public void unSubscribe(INotificationUpdate update){
        allUpdates.remove(update);
    }
    interface ActionEvent<T>{
        void action(T t);
    }
}
