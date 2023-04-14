// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.cabxury;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private Polyline currentPolyline;

    SupportMapFragment mapFragment;
    SearchView searchView;
    GoogleMap map;
    private LatLng currentLocation;
    private LatLng destinationLocation;
    private double fare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_drivers_maps);
        searchView=findViewById(R.id.search_view);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList=null;

                if(location!=null || !location.equals("")){
                    Geocoder geocoder= new Geocoder((MapsMarkerActivity.this));
                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address=addressList.get(0);
                    LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latlng).title(location));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


        Button button = findViewById(R.id.BookCab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MapsMarkerActivity.this, BillingActivity.class); //create new intent
                startActivity(myIntent); //start the new activity
            }
        });

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10));
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    String location = searchView.getQuery().toString();
                                    List<Address> addressList=null;

                                    if(location!=null || !location.equals("")){
                                        Geocoder geocoder= new Geocoder((MapsMarkerActivity.this));
                                        try {
                                            addressList=geocoder.getFromLocationName(location,1);
                                        }
                                        catch (IOException e){
                                            e.printStackTrace();
                                        }
                                        Address address=addressList.get(0);
                                        LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
                                        map.addMarker(new MarkerOptions().position(latlng).title(location));
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                                        LatLng destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude()); // Replace with the destination's latitude and longitude

                                        PolylineOptions polylineOptions = new PolylineOptions()
                                                .add(currentLocation, destinationLatLng)
                                                .width(5)
                                                .color(Color.RED);
                                        currentPolyline = map.addPolyline(polylineOptions);
                                        // Calculate the distance between the current location and destination location
                                        double distance = SphericalUtil.computeDistanceBetween(currentLocation, destinationLatLng);

                                        // Calculate the fare based on the distance
                                        if (distance <= 5000) {
                                            fare = 100.0;
                                        } else if (distance <= 10000) {
                                            fare = 150.0;
                                        } else if (distance <= 15000) {
                                            fare = 200.0;
                                        } else {
                                            fare = 250.0;
                                        }

                                        // Update the UI with the fare
                                        TextView distanceTextView = findViewById(R.id.distanceTextView);
                                        distanceTextView.setText(String.format("Distance: %.2f meters\nFare: %.2f PKR", distance, fare));


                                    }
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    return false;
                                }
                            });
                        }
                    }
                });



    }
}