package com.github.dat210_teamone.skolerute.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.GjesdalSchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.SchoolInfoGetter.StavangerSchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;

import java.io.File;

/**
 * Created by Nicolas on 21.09.2016.
 * Part of project skolerute-android
 */

public final class InterfaceManager {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static SharedPreferences preferences;
    //private static File path;
    private static Resources resources;

    private  InterfaceManager(){

    }

    public static void setMainContext(Activity activity){
        setMainContext((Context)activity);
    }

    public static void setMainContext(Context context){
        InterfaceManager.context = context;
        //path = context.getDir("data", 0);
        preferences = context.getSharedPreferences("data", 0);
        resources = context.getResources();
    }

    public static File getStoragePath(){
        return context.getDir("data", 0);
        //return path;
    }

    public static Context getContext() {
        return context;
    }

    public static Resources getResources(){
        return resources;
    }

    public static IStorage getStorage() {
        //return new DummyStorage();
        return new SchoolStorage().initializeStorage();
    }

    public static ISettingStorage getSettings(){
        //return new DummySettingStorage(true);
        //return new SettingManager(getContext().getSharedPreferences("data", 0));
        return new SettingManager(preferences);
    }

    /*@Deprecated
    public static ICsvGetter getBufferGetter() {
        return new CsvReaderGetter();
    }*/

    public static ISchoolInfoGetter[] getSchoolGetters()
    {
        return new ISchoolInfoGetter[]
        {
            new StavangerSchoolInfoGetter(),
            new GjesdalSchoolInfoGetter()
        };
    }
}
