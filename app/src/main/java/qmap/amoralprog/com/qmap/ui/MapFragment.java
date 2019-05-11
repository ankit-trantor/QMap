package qmap.amoralprog.com.qmap.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import qmap.amoralprog.com.qmap.R;
import qmap.amoralprog.com.qmap.pojo.Point;
import qmap.amoralprog.com.qmap.ui.main.MainFragment;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mViewModel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }
    ConstraintLayout helpLayout;
    TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.map_fragment, container, false);
         helpLayout= rootView.findViewById(R.id.helpLayout);
            helpLayout.setVisibility(View.GONE);
        textView= rootView.findViewById(R.id.textView);
        final EditText editText = rootView.findViewById(R.id.editText);
        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.isCorrectAnswer(editText.getText().toString()))
                {
                    if(!mViewModel.isLastPoint()) {
                        mViewModel.incPointIndex();
                        mViewModel.setAllowed(true);
                        mViewModel.prepareAsyncMapPoint();
                        editText.setText("");
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Wow!");
                        alertDialog.setMessage("You won!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    helpLayout.setVisibility(View.GONE);

                }
            }
        });
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if(mapFragment != null)
        {
            mapFragment.getMapAsync(this);

        }


        return  rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mViewModel != null)
        {
            mViewModel.unsubscribe();
        }
    }
    SupportMapFragment mapFragment = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        mViewModel.subscribe();
        mViewModel.prepareAsyncMapPoint();




    }


    GoogleMap googleMap;
    Polyline route;

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady( GoogleMap map) {

        Log.e("Debug", "map ready");

        this.googleMap = map;
        map.setMyLocationEnabled(true);
        route = this.googleMap.addPolyline(new PolylineOptions()
                .width(5.0f)
                .color(Color.RED)
                .geodesic(true)
                .zIndex(1.0f));

        mViewModel.getListMutableLiveData().observe(this, new Observer<List<LatLng>>() {
            @Override
            public void onChanged(@Nullable List<LatLng> list) {
                if(list != null) {
                    if (googleMap != null) {


                            MarkerOptions markerOptions = new MarkerOptions(); // just added to clear thingsyou you can add this where instance creates only once
                            markerOptions.position(list.get(list.size()-1));
                            markerOptions.title(String.valueOf(list.size()  - 1));

                            googleMap.addMarker(markerOptions);

                            route.setPoints(list);
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(list.get(list.size() - 1))
                                    .zoom(15.0f)
                                    .build();

                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                        }
                }
            }
        });
        mViewModel.getPointMutableLiveData().observe(this, new Observer<List<Point>>() {
            @Override
            public void onChanged(@Nullable List<Point> points) {
                if (points != null) {
                    helpLayout.setVisibility(View.VISIBLE);
                    textView.setText(points.get(points.size()  -1).getHelp());

                }
            }
        });



                // Log.e("Debug", latLngs.toString() + " " + latLngs.size());


    }

}
