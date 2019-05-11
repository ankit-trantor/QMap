package qmap.amoralprog.com.qmap.ui.main;
import com.orm.SugarRecord;

import java.util.List;

public class Item extends SugarRecord{

    private String description;
    private String name;
    private List<Point> pointList;

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

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

}
