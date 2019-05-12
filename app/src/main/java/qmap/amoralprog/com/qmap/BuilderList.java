package qmap.amoralprog.com.qmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import qmap.amoralprog.com.qmap.pojo.Item;
import qmap.amoralprog.com.qmap.pojo.Point;

public class BuilderList {
    //при отримінні індекса верне item
    ArrayList<Item> itemArrayList;
    public BuilderList(){
        itemArrayList = new ArrayList<>();
    itemArrayList.add(new Item("IT quest","HOW GOOD YOU KNOW LVIV IT?"));
     Item item =itemArrayList.get(0);
        ArrayList<Point> pointList=new ArrayList<>();
        pointList.add(new Point("Назва цієї компанії була утворена від: ефективного програмування для Америки","Epam",new LatLng(49.844641, 23.996734)));
        pointList.add(new Point("«Щоб отримати правильні відповіді, потрібно ставити правильні запитання» Дана цитата належить засновнику невеликої компанії, яка почалась із групки студентів","KindGeeks",new LatLng(49.841798, 24.000416)));
        pointList.add(new Point("Яка компанія за останні три роки виросла на 331% і є технологічним партнером для інноваційних компаній Америки, Великої Британії, Швеції та країн Європи?","N-iX",new LatLng(49.842684, 24.001127)));
        itemArrayList.get(0).setPointList(pointList);
    }

    public Item itemAt(int index){
        return itemArrayList.get(0);
    }
    public Point pointIndexAt(int index){
        return itemArrayList.get(0).getPointList().get(index);
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }
}
