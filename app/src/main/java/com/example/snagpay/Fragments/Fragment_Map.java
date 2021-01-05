package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.Adapter.CustomInfoWindowGoogleMap;
import com.example.snagpay.Model.MapModel;
import com.example.snagpay.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_Map extends Fragment implements  OnMapReadyCallback {

    private ArrayList<MapModel> mapModelArrayList;
    public static HashMap<Marker, MapModel> mRestaurantMap = new HashMap<>();
    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mapModelArrayList = new ArrayList<>();

        Marker marker = null;

        /*HashMap<Marker, JSONObject> stopsMarkersInfo = new HashMap<>(); // created the HashMap
        JSONArray stops = new File().getStops(lineId);
        for (int i = 0; i < stops.length(); i++) {
            BitmapDescriptor stopMarkerIcon = new File().getStopMarkerIcon(color, Integer.parseInt(stops.getJSONObject(i).getString("sequence")));
            LatLng coordinate = new LatLng(Double.parseDouble(stops.getJSONObject(i).getString("lat")), Double.parseDouble(stops.getJSONObject(i).getString("lng")));
            MarkerOptions markerOptions = new MarkerOptions().icon(stopMarkerIcon).position(coordinate).anchor(.5f, .5f);
            Marker marker = googleMap.addMarker(markerOptions);
            stopsMarkersInfo.put(marker, stops.getJSONObject(i)); // added each marker and
            // his information to the HashMap
        }
        googleMap.setInfoWindowAdapter(new StopsInfoWindow(stopsMarkersInfo));*/

        MapModel mapModel = new MapModel();
        mapModel.setName("Germany");
        mapModel.setPlace("Arizona : 22");
        mapModel.setRating("220");
        mapModel.setPrice("29.95");
        mapModel.setOffer("62");
        mapModel.setBought("590");
        mapModel.setLat(47.22);
        mapModel.setLng(3);
        mapModelArrayList.add(mapModel);

        MapModel mapModel1 = new MapModel();
        mapModel.setName("Austria");
        mapModel.setPlace("Phoenix : 14");
        mapModel.setRating("287");
        mapModel.setPrice("18.2");
        mapModel.setOffer("48");
        mapModel.setBought("369");
        mapModel.setLat(30.85);
        mapModel.setLng(2);
        mapModelArrayList.add(mapModel1);

        MapModel mapModel2 = new MapModel();
        mapModel.setName("France");
        mapModel.setPlace("Florida : 32");
        mapModel.setRating("175");
        mapModel.setPrice("14.5");
        mapModel.setOffer("36");
        mapModel.setBought("788");
        mapModel.setLat(37.22);
        mapModel.setLng(1);
        mapModelArrayList.add(mapModel2);

        pinMarkers(mapModelArrayList);
        /*for (int i = 0; i < mapModelArrayList.size(); i++) {

            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mapModelArrayList.get(i).getLat(), mapModelArrayList.get(i).getLng()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer)));

            marker.setTag(mapModel);
            marker.showInfoWindow();
        }
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getContext(), mapModelArrayList);
        mMap.setInfoWindowAdapter(customInfoWindow);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.22, 37.22)));*/
    }

    public void pinMarkers(ArrayList<MapModel> restaurants) {
        for (MapModel restaurant : restaurants) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(restaurant.getLat(), restaurant.getLng()));

            // This is optional, only if you want a custom image for your pin icon
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));

            Marker marker = mGoogleMap.addMarker(markerOptions);
            mRestaurantMap.put(marker, restaurant);

            // This is where we set our custom Info Adapter to the Google Map
            mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));

            // This is a bit of a hack to get custom images loading. If you
            // don't have images in your custom info windows, you do not need
            // the following two lines
            marker.showInfoWindow();
            marker.hideInfoWindow();
        }
    }
}