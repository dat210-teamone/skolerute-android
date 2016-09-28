package com.github.dat210_teamone.skolerute.data;

import android.app.Activity;

import com.github.dat210_teamone.skolerute.data.dummy.DummySettingStorage;
import com.github.dat210_teamone.skolerute.data.dummy.DummyStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.ICsvGetter;
import com.github.dat210_teamone.skolerute.data.interfaces.ISettingStorage;
import com.github.dat210_teamone.skolerute.data.interfaces.IStorage;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class InterfaceManager {
    private static Activity mainActivity;
    private  InterfaceManager(){

    }

    public static void SetMainActivity(Activity activity){
        mainActivity = activity;
    }

    public static IStorage getStorage()
    {
        return new DummyStorage();
    }



    public static ISettingStorage getSettings(){
        return new DummySettingStorage();
    }

    public static ICsvGetter getBufferGetter() {
        return new CsvReaderGetter();
    }
}
