package qmap.amoralprog.com.qmap;

import android.location.Location;

public class DistanceMapCalculator {
    private final float MAXDISTANCE=50;//in metres
    public static final float MINIMUM_BELIEVABLE_ACCURACY = 15.0F;
    private float getDistance(Location loc1, Location loc2) {
        return loc1.distanceTo(loc2);
    }
    public boolean isInRange(Location loc1, Location loc2){
    float distance=loc1.distanceTo(loc2);
    return (distance<MAXDISTANCE);
    }
}
