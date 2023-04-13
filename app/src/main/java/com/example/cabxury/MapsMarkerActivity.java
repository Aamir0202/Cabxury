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
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;
import java.util.List;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    String[] karachiAreas;
    LatLng sydney= new LatLng(34,151);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_drivers_maps);



        // Now you can use this string array as per your requirement
        // For example, to display all the areas in a list:
       /* karachiAreas = getResources().getStringArray(R.array.karachi_areas);

        ListView listView = findViewById(R.id.search_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, karachiAreas);
        listView.setAdapter(adapter);

        */



        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5));

                // Initialize googleMap object
                SearchView searchView = findViewById(R.id.search_view);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        // Convert the query string to a location using Geocoder
                        Geocoder geocoder = new Geocoder(MapsMarkerActivity.this);
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocationName(query, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addresses != null && addresses.size() > 0) {
                            Address address = addresses.get(0);
                            LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                            // Add a marker for the location and move the camera
                            googleMap.addMarker(new MarkerOptions().position(location).title(query));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

            }
        });
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

        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(24.89686387016082, 67.02255719253037);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Karachi"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Geocoder geocoder = new Geocoder(this);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SearchView searchView = findViewById(R.id.search_view);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Convert the query string to a location using Geocoder
                    Geocoder geocoder = new Geocoder(MapsMarkerActivity.this);
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocationName(query, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                        // Add a marker for the location and move the camera
                        googleMap.addMarker(new MarkerOptions().position(location).title(query));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        }
                    }
                });


    }
}