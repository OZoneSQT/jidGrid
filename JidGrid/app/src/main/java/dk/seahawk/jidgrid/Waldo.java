package dk.seahawk.jidgrid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

// https://developer.android.com/training/location

// https://developer.android.com/training/location/permissions
// https://developer.android.com/training/location/retrieve-current.html#java

// https://developer.android.com/training/location/change-location-settings.html
// https://developer.android.com/training/location/request-updates
// https://developer.android.com/training/location/retrieve-current.html

public class Waldo extends AppCompatActivity {

    private static final String TAG = Waldo.class.getSimpleName();

    private static final int UPDATE_INTERVAL = 5 * 60 * 1000;       // 5 minute (default = 10 second)
    private static final int UPDATE_FAST_INTERVAL = 1 * 60 * 1000;  // 1 minute (default = 5 second)
    private static final int MAX_WAIT_TIME = 60 * 60 * 1000;        // 1 minute (default = 5 second)

    /**
     * High accuracy is important for the use of the app, but when in use the update frequency is less important
     * https://developer.android.com/guide/topics/location/battery
     * PRIORITY_NO_POWER
     * PRIORITY_BALANCED_POWER_ACCURACY
     * PRIORITY_LOW_POWER
     * (avoid using) PRIORITY_HIGH_ACCURACY
     */
    private int PRIORITY_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    private FusedLocationProviderClient locationProviderClient; // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderClient
    private LocationRequest locationRequest;                    // https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest
    private LocationCallback locationCallback;                  // https://developers.google.com/android/reference/com/google/android/gms/location/LocationCallback
    private Location currentLocation;                           // https://developer.android.com/reference/android/location/Location.html

    public Waldo() {
    }

    // Change location settings
    // https://developer.android.com/training/location/change-location-settings.html
    // https://developer.android.com/guide/topics/location/battery#best-practices
    public void startLocationService(){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(UPDATE_FAST_INTERVAL);
        locationRequest.setMaxWaitTime(MAX_WAIT_TIME);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (locationAvailability.isLocationAvailable()) {
                    Log.i(TAG, "Location is available");
                } else {
                    Log.i(TAG, "Location is unavailable");
                }
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.i(TAG, "Location result is available");
            }
        };

    }

    @SuppressLint("MissingPermission")
    public void startGettingLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, this.getMainLooper());
            locationProviderClient.getLastLocation().addOnSuccessListener(location -> currentLocation = location);
            locationProviderClient.getLastLocation().addOnFailureListener(e -> Log.i(TAG, "Exception while getting the location: " + e.getMessage()));
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Permission needed", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, PRIORITY_ACCURACY);
            }
        }
    }

    public void stopLocationService(){
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    // TODO has this to be moved/called from lifecycle method in MainActivity
    // https://developer.android.com/reference/androidx/core/app/ActivityCompat.OnRequestPermissionsResultCallback
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startGettingLocation();
    }

    public Location getLocation(){
        return currentLocation;
    }

}
