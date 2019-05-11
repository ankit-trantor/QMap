package qmap.amoralprog.com.qmap.pojo;

import com.google.android.gms.maps.model.LatLng;

public class Point {
    private String Help;
    private LatLng point;
    
    public String getHelp() {
        return Help;
    }

    public void setHelp(String help) {
        Help = help;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }
}
