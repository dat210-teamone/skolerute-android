package com.github.dat210_teamone.skolerute.data;

import android.app.Activity;
import android.content.Context;

import com.github.dat210_teamone.skolerute.data.interfaces.ICsvGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.ISchoolInfoGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;

import java.io.File;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class InterfaceManager {
    private static Activity mainActivity;
    private static Context context;

    private  InterfaceManager(){

    }

    public static void SetMainActivity(Activity activity){
        mainActivity = activity;
        context = activity.getApplicationContext();
    }

    public static void SetMainActivity(Context context){
        InterfaceManager.context = context;
    }

    public static File getStoragePath(){
        return getContext().getDir("data", 0);
    }

    public static Context getContext() {
        return context;
    }

    public static IStorage getStorage() {
        //return new DummyStorage();
        //return new CsvFileReader().initializeReader();
        return new SchoolStorage().initializeStorage();
    }

    public static ISettingStorage getSettings(){
        //return new DummySettingStorage(true);

        return new SettingManager(getContext().getSharedPreferences("data", 0));
    }

    public static ICsvGetter getBufferGetter() {
        return new CsvReaderGetter();
    }

    public static ISchoolInfoGetter[] getSchoolGetters()
    {
        return new ISchoolInfoGetter[]
        {
            new StavangerSchoolInfoGetter(),
            new GjesdalSchoolInfoGetter()
        };
    }
}
