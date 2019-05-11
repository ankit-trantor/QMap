package qmap.amoralprog.com.qmap;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

public class DistanceMapCalculator {

    private final float MAXDISTANCE=15;//in metres
    public static final float MINIMUM_BELIEVABLE_ACCURACY = 15.0F;
    private float getDistance(Location loc1, Location loc2) {
        return loc1.distanceTo(loc2);
    }
    public boolean isInRange(LatLng loc1, LatLng loc2){

        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(loc1.latitude);
        location.setLongitude(loc1.longitude);
        Location location1 = new Location(LocationManager.GPS_PROVIDER);
        location1.setLatitude(loc2.latitude);
        location1.setLongitude(loc2.longitude);

        float distance= getDistance(location, location1);

        return (distance<MAXDISTANCE);
    }
}
