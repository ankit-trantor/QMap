package qmap.amoralprog.com.qmap.pojo;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String name;
    private String description;
    private ArrayList<Point> pointList;

    public Item() {
    }

    public Item(String name,String description) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }
}
