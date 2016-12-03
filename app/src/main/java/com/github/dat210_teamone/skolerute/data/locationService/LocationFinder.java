package com.github.dat210_teamone.skolerute.data.locationService;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.github.dat210_teamone.skolerute.data.SchoolManager;

/**
 * Created by Nicolas on 05.10.2016.
 * Part of project skolerute-android
 */

public class LocationFinder implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        SchoolManager manager = SchoolManager.getDefault();
        manager.setKnownPosition(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
