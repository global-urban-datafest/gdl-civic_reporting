package me.on247.civicreporting;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Alonso L on 2/22/2015.
 */
public abstract class LocationEvent<T extends Activity> implements LocationListener{
    T activity;
    LocationEvent(T activity){
        this.activity=activity;
    }
    public abstract void onLocationChanged(Location location);
    public abstract void onStatusChanged(String provider, int status, Bundle extras) ;
    public abstract void onProviderDisabled(String provider);
}
