package sg.edu.rp.c346.p08_map;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static sg.edu.rp.c346.p08_map.R.id.map;

public class MainActivity extends AppCompatActivity {

    Spinner spnDirection;
    private GoogleMap map;
    private boolean SpinnerInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnDirection = (Spinner)findViewById(R.id.spnDirection);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_north = new LatLng(1.437700, 103.795663);
                LatLng poi_central = new LatLng(1.300996, 103.838416);
                LatLng poi_east = new LatLng(1.372094, 103.947373);


                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,15));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,10));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east,10));

                //Compass
                UiSettings uiCompass = map.getUiSettings();
                uiCompass.setCompassEnabled(true);
                //Zoom Controls
                UiSettings uiZoom = map.getUiSettings();
                uiZoom.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                        return;
                    }
                }

                //LatLng poi_CausewayPoint = new LatLng(1.436065, 103.786263);
                Marker North = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 76565")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory. HUE_ORANGE)));


                //LatLng poi_RP = new LatLng(1.44224, 103.785733);
                Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                //LatLng poi_RP = new LatLng(1.44224, 103.785733);
                Marker East = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getBaseContext(),marker.getTitle().toString(),Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

            }
        });

        spnDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dir = (String) parent.getItemAtPosition(position);
                if (dir.equals("North")) {
                    LatLng poi_north = new LatLng(1.437700, 103.795663);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 16));
                } else if (dir.equals("Central")) {
                    LatLng poi_central = new LatLng(1.300996, 103.838416);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 16));
                } else if(dir.equals("East")){
                    LatLng poi_east = new LatLng(1.372094, 103.947373);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 16));
                    Toast.makeText(getBaseContext(), "East", Toast.LENGTH_SHORT).show();
                }else{

                }

            }

            //So that the the adapter will able to return the on selected Item.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });




    }


}
