package com.example.amov.test;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import com.example.amov.test.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by mgois on 26-12-2014.
 */
public class MapPositionActivity extends Activity {

    private static final float MAP_ZOOM = 16;

    GoogleMap map;
    Marker marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_position_activity);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapPosition_map)).getMap();
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // On map load, zoom on the user's location...
                final Location location = map.getMyLocation();
                if (location != null) {
                    final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM));

                    // ... and create a marker in that location
                    marker = map.addMarker(new MarkerOptions().position(latLng));
                }
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                // When the user clicks on the map, move the existing marker to that position
                // or create one if it doesn't exist
                if (marker != null) {
                    marker.setPosition(latLng);
                } else {
                    marker = map.addMarker(new MarkerOptions().position(latLng));
                }
            }
        });
    }
}