package com.github.dat210_teamone.skolerute.data;

import com.github.dat210_teamone.skolerute.data.dummy.DummyStorage;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class InterfaceManager {
    private  InterfaceManager(){

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
