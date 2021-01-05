package com.example.snagpay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.snagpay.Fragments.Fragment_Map;
import com.example.snagpay.Model.MapModel;
import com.example.snagpay.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private View mRestaurantInfoView;
    private Context mContext;

    public CustomInfoWindowGoogleMap(Context mContext){
        this.mContext = mContext;
    }

    /*@Override
    public View getInfoWindow(Marker marker) {
        return null;
    } mRestaurantInfoView = getActivity().getLayoutInflater().inflate(R.layout.map_marker_info_window, null);

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.map_marker_info_window, null);

        TextView itemName = view.findViewById(R.id.itemName);
       *//* TextView itemPlace = view.findViewById(R.id.itemPlace);
        TextView itemRating = view.findViewById(R.id.itemRating);
        TextView itemPrice = view.findViewById(R.id.itemPrice);
        TextView itemOffer = view.findViewById(R.id.itemOffer);
        TextView itemBought = view.findViewById(R.id.itemBought);*//*

            MapModel mapModel = (MapModel) marker.getTag();

            itemName.setText(mapModel.getName());
          *//*  itemPlace.setText(mapModel.getPlace() + "mi");
            itemRating.setText(mapModel.getRating() + " Rating");
            itemPrice.setText("$" + mapModel.getPrice());
            itemOffer.setText("Your price is " + mapModel.getOffer() + " % Less");
            itemBought.setText(mapModel.getBought() + "+ bought");*//*


        *//*int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
                "drawable", context.getPackageName());
        img.setImageResource(imageId);

       *//*

        return view;
    }*/

    /*private HashMap<Marker, JSONObject> stopsMarkersInfo;
    private View view;

    public StopsInfoWindow(HashMap<Marker, JSONObject> stopsMarkersInfo) {
        this.stopsMarkersInfo = stopsMarkersInfo;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        JSONObject stop = stopsMarkersInfo.get(marker);
        if (stop != null) {
            LayoutInflater inflater = (LayoutInflater) Controller.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_stop_marker_info, null);

            TextView stopName = (TextView) view.findViewById(R.id.stop_name);
            stopName.setText(stop.getString("name"));

            TextView stopLine = (TextView) view.findViewById(R.id.stop_line);
            stopLine.setText(stop.getString("line"));
        }
        return view;
    }*/


    @Override
    public View getInfoWindow(Marker marker) {

        mRestaurantInfoView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.map_marker_info_window, null);
        
        MapModel restaurant = Fragment_Map.mRestaurantMap.get(marker);
       /* ImageView imageView = (ImageView) mRestaurantInfoView.findViewById(R.id.image_view);

        Picasso.with(getContext()).load(restaurant.getPhotoUrl())
                .fit()
                .centerCrop()
                .transform(new RoundedCornersTransformation(12, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(imageView, new MarkerCallback(marker));*/

        TextView nameTextView = (TextView) mRestaurantInfoView.findViewById(R.id.itemName);
        nameTextView.setText(restaurant.getName());

        TextView descriptionTextView = (TextView) mRestaurantInfoView.findViewById(R.id.itemPrice);
        descriptionTextView.setText(restaurant.getPrice());

        return mRestaurantInfoView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}