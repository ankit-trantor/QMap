package qmap.amoralprog.com.qmap.pojo;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class NewGpsLocation{

    public NewGpsLocation(LatLng latLng, List<LatLng> list)
    {
        location = latLng;
        latLngList = list;
    }

    private LatLng location;
    private List<LatLng> latLngList;

    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }

    public LatLng getLocation()
    {
        return location;
    }

    public void setLocation(LatLng location)
    {
        this.location = location;
    }

}
