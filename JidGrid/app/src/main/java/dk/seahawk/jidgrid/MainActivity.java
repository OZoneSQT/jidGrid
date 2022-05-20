package dk.seahawk.jidgrid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import dk.seahawk.jidgrid.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();

    // Location
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private static final int REQUEST_GRANT_PERMISSION = 2;
    private int interval = 30000;
    private int fastInterval = 5000;
    private int priority = LocationRequest.QUALITY_HIGH_ACCURACY;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private com.google.android.gms.location.LocationRequest locationRequest;
    private Location currentLocation;
    private LocationCallback locationCallback;



    /**
     * Lifecycles
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init location service
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        settingsCheck();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_locator_home, R.id.navigation_maps).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    protected void onStart() {
        super.onStart();
        startLocationService();
    }

    protected void onResume() {
        super.onResume();
        startLocationService();
    }

    protected void onPause() {
        super.onPause();
        stopLocationService();
    }

    protected void onStop() {
        super.onStop();
        stopLocationService();
    }

    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
    }


    /**
     * Location service
     *
     * User location: https://developer.android.com/training/location/retrieve-current
     * Maps SDK: https://developers.google.com/maps/documentation/android-sdk/overview
     * Interact with map: https://developers.google.com/maps/documentation/android-sdk/location
     *
     * Solved issue:
     * https://stackoverflow.com/questions/29441384/fusedlocationapi-getlastlocation-always-null
     * https://stackoverflow.com/questions/61777386/fusedlocationclient-getlastlocation-always-returns-null
     */

    /**
     * https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest
     * setFastestInterval(long millis) - Explicitly set the fastest interval for location updates, in milliseconds.
     * setInterval(long millis) - Set the desired interval for active location updates, in milliseconds.
     * setPriority(int) Options:
     *                          PRIORITY_HIGH_ACCURACY (100) - Used to request the most accurate locations available.
     *                          PRIORITY_BALANCED_POWER_ACCURACY (102) - Used to request "block" level accuracy.
     *                          PRIORITY_LOW_POWER (104) - Used to request "city" level accuracy.
     *                          PRIORITY_NO_POWER (105) - Used to request the best accuracy possible with zero additional power consumption.
     */
    protected void createLocationRequest() {
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(fastInterval);
        locationRequest.setPriority(priority);
    }

    // Check for location settings
    public void settingsCheck() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            Log.d(TAG, "onSuccess: settingsCheck");
            getCurrentLocation();
        });

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                Log.d(TAG, "onFailure: settingsCheck");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {} // Ignore the error.
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    Log.d(TAG, "onSuccess: getLastLocation");
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        currentLocation = location;
                       // currentLocationListener.getLocationUpdate(location);
                        Log.d(TAG, "onSuccess: latitude " + location.getLatitude() + " longitude " + location.getLongitude());
                    } else {
                        Log.d(TAG, "location is null");
                        buildLocationCallback();
                    }
                });
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    currentLocation = location;
                    // currentLocationListener.getLocationUpdate(location);
                    Log.d(TAG, "onLocationResult: latitude " + currentLocation.getLatitude() + " longitude " + currentLocation.getLongitude());
                }
            } /*;*/
        };
    }

    //called after user responds to location permission popup
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_GRANT_PERMISSION) getCurrentLocation();
    }

    //called after user responds to location settings popup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: ");
        if(requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) getCurrentLocation();
        if(requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_CANCELED)
            Toast.makeText(this, "Please enable Location settings...!!!", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    public void startLocationService() {
        Log.i(TAG, "Start Location service");
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_GRANT_PERMISSION);
            return;
        }
        if(locationCallback == null) buildLocationCallback();
        if(currentLocation == null) fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    public void stopLocationService() {
        Log.i(TAG, "Stop Location service");
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_GRANT_PERMISSION);
            return;
        }
        if(locationCallback!=null) fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

}