package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.CustomInfoWindowGoogleMap;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.MapModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Map extends Fragment implements  OnMapReadyCallback {

    private ArrayList<MapModel> mapModelArrayList;
    public static HashMap<Marker, MapModel> mRestaurantMap = new HashMap<>();
    private GoogleMap mGoogleMap;
    private UserSession session;
    private String category_id;
    public Fragment_Map(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);

        session = new UserSession(getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getCategoriesDetails(category_id, "", "", "", "", "1");

        return view;
    }


    public void getCategoriesDetails(String category_id, String mShort, String mCategory, String startPrice, String endPrice, String page){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "category-details?category_id="+category_id
                + "&sort_by_deals="+mShort
                + "&filter_category_id="+mCategory
                + "&from_price_range="+startPrice
                + "&to_price_range="+endPrice
                + "?page=" + page, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());



                } catch (Exception e) {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + session.getAPITOKEN());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
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
        mapModel.setLng(47.00);
        mapModelArrayList.add(mapModel);

        MapModel mapModel1 = new MapModel();
        mapModel.setName("Austria");
        mapModel.setPlace("Phoenix : 14");
        mapModel.setRating("287");
        mapModel.setPrice("18.2");
        mapModel.setOffer("48");
        mapModel.setBought("369");
        mapModel.setLat(47.22);
        mapModel.setLng(47.11);
        mapModelArrayList.add(mapModel1);

        MapModel mapModel2 = new MapModel();
        mapModel.setName("France");
        mapModel.setPlace("Florida : 32");
        mapModel.setRating("175");
        mapModel.setPrice("14.5");
        mapModel.setOffer("36");
        mapModel.setBought("788");
        mapModel.setLat(47.22);
        mapModel.setLng(47.22);
        mapModelArrayList.add(mapModel2);

        MapModel mapModel3 = new MapModel();
        mapModel.setName("France22");
        mapModel.setPlace("Florida : 32");
        mapModel.setRating("175");
        mapModel.setPrice("14.5");
        mapModel.setOffer("36");
        mapModel.setBought("788");
        mapModel.setLat(47.22);
        mapModel.setLng(47.266);
        mapModelArrayList.add(mapModel3);

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