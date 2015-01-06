package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by mgois on 30-12-2014.
 */
public class SelectPostLocationActivity extends Activity {

    public static final String RESULT_LATITUDE = "lat";
    public static final String RESULT_LONGITUDE = "lng";

    private final float MAP_ZOOM = 16;
    GoogleMap map;
    Marker marker;
    Button btnPinLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_post_location_activity);

        btnPinLocation = (Button) findViewById(R.id.select_post_location_btnPinLocation);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.select_post_location_frgLocation)).getMap();
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        btnPinLocation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent result = new Intent();
                        if(marker != null) {
                            double lat, lng;
                            lat = marker.getPosition().latitude;
                            lng = marker.getPosition().longitude;
                            result.putExtra(RESULT_LATITUDE, lat);
                            result.putExtra(RESULT_LONGITUDE, lng);
                            setResult(Activity.RESULT_OK, result);
                        } else {
                            setResult(Activity.RESULT_CANCELED, result);
                        }
                        finish();
                    }
                }
        );

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