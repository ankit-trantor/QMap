package qmap.amoralprog.com.qmap.pojo;

import com.google.android.gms.maps.model.LatLng;

public class Point {
    private String help;
    private String answer;
    private LatLng point;
    private int score;
    public Point() {
    }

    public Point(String help, String answer, LatLng point) {
        this.help = help;
        this.answer = answer;
        this.point = point;
    }

    public String getHelp() {
        return this.help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
