package qmap.amoralprog.com.qmap.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.PolyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import qmap.amoralprog.com.qmap.MainActivity;
import qmap.amoralprog.com.qmap.R;
import qmap.amoralprog.com.qmap.pojo.DataRequest;
import qmap.amoralprog.com.qmap.pojo.NewGpsLocation;


public class MyLocationService extends Service implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static boolean stop = true;



   private void stopTracking() {
       if(mGoogleApiClient!=null) {
           mGoogleApiClient.disconnect();
       }
       if(fusedLocationProviderClient!=null&&mLocationCallback!=null){
           fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
       }
       mLocationCallback = null;
   }

   private void startTracking() {
       mGoogleApiClient = new GoogleApiClient.Builder(this)
               .addApi(LocationServices.API)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .build();
       mGoogleApiClient.connect();

       mLocationCallback = locationCallback;

   }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopTracking();
        stop = true;


        unsubscribeBUS();
        super.onDestroy();
    }

    public static boolean isStop() {
        return stop;
    }

    private float getDistance(Location loc1, Location loc2) {
        return loc1.distanceTo(loc2);
    }


    GoogleApiClient mGoogleApiClient;
   LatLng tempLocation = null;

    @Override
    public void onCreate() {




        subscribeBUS();
        super.onCreate();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // You need to ask the user to enable the permissions
            //Todo send an error message
        } else {


           startTracking();
        }


    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

            positionList.add(latLng);
            tempLocation = latLng;

            EventBus.getDefault().postSticky(new NewGpsLocation(latLng, positionList));
        }

        ;
    };


    private void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }

    List<LatLng> positionList;
    Handler handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent != null && intent.getBooleanExtra("STOP", false)) {
            stop = true;
            handler = new Handler();

            stopSelf();

        }

        else {

            positionList = new ArrayList<>();

            stop = false;
            initChannels(this);
            int NOTIFICATION_ID = 85748547;
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Resources res = getResources();
            Notification notification = new NotificationCompat.Builder(this, "default")
                    .setContentTitle(getString(R.string.app_name))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setContentText(getString(R.string.app_name) + " is working")
                    .setContentIntent(pendingIntent).build();

            startForeground(NOTIFICATION_ID, notification);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void unsubscribeBUS() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void subscribeBUS() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe()
    public void DataRequest(DataRequest dataRequest)
    {
        EventBus.getDefault().postSticky(new NewGpsLocation(tempLocation, positionList));
    }

    private long INTERVAL = 1000 * 5;
    private long FASTEST_INTERVAL = 1000;
    LocationRequest mLocationRequest;

    private LocationCallback mLocationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            fusedLocationProviderClient = new FusedLocationProviderClient(this);
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,null);
        }

        Log.e("Debug", "onConnected");


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
