package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Model.PlaceAutocompleteAdapter;
import com.example.osori.hopelinev1.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int DEFAULT_ZOOM = 15;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71,136)
    );
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLocationPermission();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        mMap = googleMap;
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "OnMapReady after map");
        LatLng seneca = new LatLng(43.7713923, -79.4987939);
        mMap.addMarker(new MarkerOptions().position(seneca).title("Seneca"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seneca));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        Log.d(TAG, "OnMapReady marker added");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        Log.d(TAG, "OnMapReady after googleaptclient");
//        PlaceAutocompleteAdapter placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
//        placeAutocompleteAdapter.getCount();

//        GoogleApiClient.Builder mGoogleApiClient = new GoogleApiClient.Builder().build();
        //LatLngBounds mBounds = new LatLngBounds(new LatLng(45.7713923, -76.4987939), new LatLng(44.7713923, -80.4987939));
//        AutocompleteFilter.Builder mPlaceFilter = new AutocompleteFilter.Builder();
//        mPlaceFilter
//                .setCountry("CA")
//                .build();
        PendingResult<AutocompletePredictionBuffer> results =
                Places.GeoDataApi
                        .getAutocompletePredictions(mGoogleApiClient, "Hospital",
                                LAT_LNG_BOUNDS, null);

        Log.d(TAG, "OnMapReady after pendingreuslt: " + results);
        // This method should have been called off the main UI thread. Block and wait for at most 60s
        // for a result from the API.

        AutocompletePredictionBuffer autocompletePredictions = results.await();

        Log.d(TAG, "OnMapReady after buffer");
        Log.d(TAG, "Getting hospital locations");
//        if(autocompletePredictions.getStatus().isSuccess()){
//            Log.d(TAG, "Getting hospital locations Success: " + autocompletePredictions.toString());
//
//        }else{
//            Log.d(TAG, "Getting hospital locations Failed");
//        }

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            //Getting current location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
//            ImageView mPlacePicker = (ImageView)findViewById(R.id.mPlacePicker);
            mMap.setMyLocationEnabled(true);

//            mPlacePicker.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                    try {
//                        startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
//                        Log.d(TAG, "After placepicker");
//                    } catch (GooglePlayServicesRepairableException e) {
//                        e.printStackTrace();
//                    } catch (GooglePlayServicesNotAvailableException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Log.d(TAG, "In place picker" + requestCode + resultCode);
//        if (requestCode == PLACE_PICKER_REQUEST) {
//
//            Log.d(TAG, "placepicker passed1");
//            if (resultCode == RESULT_OK) {
//                Log.d(TAG, "placepicker passed");
//                Place place = PlacePicker.getPlace(this, data);
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moving camera to: " + latLng.latitude + ", " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDevice Location: getting location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                            Log.d(TAG, "onComplete: added current marker");

                        }else{
                            Log.d(TAG, "onComplete: not found");
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "");
        }
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        Log.d(TAG, "getLocationPermissions: getting permissions");


        if(ContextCompat.checkSelfPermission(this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "getLocationPermissions: passed");
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                Log.d(TAG, "getLocationPermissions: COARSE failed");
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            Log.d(TAG, "getLocationPermissions: FINE failed");
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap(){
        Log.d(TAG, "init map: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d(TAG, "init map: initializing map done");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: passed");
                    mLocationPermissionsGranted = true;
                    //init map
                    initMap();
                }
            }
        }
    }


}
