package qmap.amoralprog.com.qmap.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import qmap.amoralprog.com.qmap.BuilderList;
import qmap.amoralprog.com.qmap.DistanceMapCalculator;
import qmap.amoralprog.com.qmap.pojo.DataRequest;
import qmap.amoralprog.com.qmap.pojo.NewGpsLocation;
import qmap.amoralprog.com.qmap.pojo.Point;

public class MapViewModel extends ViewModel {

    public MapViewModel()
    {
        distanceMapCalculator = new DistanceMapCalculator();
        builderList = new BuilderList();
    }

    private  boolean isAllowed = true;
    private BuilderList builderList;
    private DistanceMapCalculator distanceMapCalculator;
    private int pointIndex = 0;

    private List<Point> list = new ArrayList<>();

    private MutableLiveData<List<Point>> pointList = new MutableLiveData<>();

    public MutableLiveData<List<Point>> getPointList() {
        return pointList;
    }

    private List<LatLng> latLngList = null;

    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public void incPointIndex()
    {
        pointIndex++;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    private MutableLiveData<List<Point>> pointMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Point>> getPointMutableLiveData() {
        return pointMutableLiveData;
    }
    private MutableLiveData<List<LatLng>>  listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<LatLng>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void prepareAsyncMapPoint() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                latLngList = new ArrayList<>();

                for (int i = 0; i < pointIndex + 1; i++) {
                    latLngList.add(builderList.pointIndexAt(pointIndex).getPoint());
                }
                listMutableLiveData.postValue(latLngList);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Subscribe
   public void onNewGpsLocation(NewGpsLocation newGpsLocation) {
        latLngList = newGpsLocation.getLatLngList();
        Log.e("Debug", newGpsLocation.getLocation().toString());
        if(isAllowed&&pointIndex < builderList.getItemArrayList().get(0).getPointList().size() &&distanceMapCalculator.isInRange(newGpsLocation.getLocation(), builderList.pointIndexAt(pointIndex).getPoint()))
        {
            list.add(builderList.pointIndexAt(pointIndex));
            pointMutableLiveData.postValue(list);
            pointIndex++;
            isAllowed = false;
        }

    }
    public boolean isCorrectAnswer(String str)
    {
        return builderList.pointIndexAt(pointIndex).getAnswer().toLowerCase().equals(str.toLowerCase());
    }

   public  void subscribe() {
       if(!EventBus.getDefault().isRegistered(this))
       {
           EventBus.getDefault().register(this);
           EventBus.getDefault().post(new DataRequest());
       }
   }

   public void unsubscribe()
   {
       if(EventBus.getDefault().isRegistered(this))
       {
           EventBus.getDefault().unregister(this);
       }
   }



}






