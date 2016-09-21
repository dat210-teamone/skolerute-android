package com.github.dat210_teamone.skolerute.data;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class InterfaceManager {
    private  InterfaceManager(){

    }

    public static IStorage GetStorage()
    {
        return new DummyStorage();
    }
}
