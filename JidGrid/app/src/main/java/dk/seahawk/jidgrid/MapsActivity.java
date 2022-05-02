package dk.seahawk.jidgrid;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;

import dk.seahawk.jidgrid.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Context context;

    // Location
    public Location location = null;
    private FusedLocationProviderClient fusedLocationClient;

    // https://developers.google.com/android/reference/com/google/android/gms/tasks/CancellationToken
    CancellationTokenSource cancellationToken;

    // https://developer.android.com/training/location/permissions
    // https://developer.android.com/training/location/retrieve-current.html#java

    // https://developer.android.com/training/location/change-location-settings.html
    // https://developer.android.com/training/location/request-updates
    // https://developer.android.com/training/location/retrieve-current.html

    // Lifecycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set lifecycle token for "running" requests for an updated location
        cancellationToken = new CancellationTokenSource();

        // https://developers.google.com/android/reference/com/google/android/gms/tasks/CancellationToken
        // That source's token can be passed to multiple calls.
        // doSomethingCancellable(cancellationToken.getToken()).onSuccessTask(result -> doSomethingElse(result, cancellationToken.getToken()));

        // Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected  void  onStart(){
        super.onStart();

        // set lifecycle token for "running" requests for an updated location
        cancellationToken = new CancellationTokenSource();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // set lifecycle token for "running" requests for an updated location
        cancellationToken = new CancellationTokenSource();

        // Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermission();

    }

    @Override
    protected void onPause() {
        super.onPause();
        cancellationToken.cancel(); // Stop request for updated location
    }

    @Override
    protected void onStop(){
        super.onStop();
        cancellationToken.cancel(); // Stop request for updated location
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    // Permission request
    // https://developer.android.com/training/location/permissions#java
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            getLocation();

        //request for the user to give the consent to access
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    // getLastLocation() gets a location estimate more quickly and minimizes battery usage that can be attributed to your app.
    // However, the location information might be out of date, if no other clients have actively used location recently.
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> updateLocation(location));
        fusedLocationClient.getLastLocation().addOnFailureListener(e -> Toast.makeText(context,"Something went wrong "+e.getMessage(),Toast.LENGTH_SHORT).show()); // GPS off
    }

    // getCurrentLocation() gets a fresher, more accurate location more consistently. However, this method can cause active location computation to occur on the device
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Task<Location> locationTask = null;
        // https://stackoverflow.com/questions/44992014/how-to-get-current-location-in-googlemap-using-fusedlocationproviderclient
        // https://github.com/DeLaSalleUniversity-Manila/getcurrentlocation-BananaSpoon/blob/master/app/src/main/java/com/christian/mylocation/MapsActivity.java

        locationTask = fusedLocationClient.getCurrentLocation(2, cancellationToken.getToken()).addOnSuccessListener(location -> updateLocation(location));
        locationTask = fusedLocationClient.getCurrentLocation(2, cancellationToken.getToken()).addOnFailureListener(e -> Toast.makeText(context,"Something went wrong "+e.getMessage(),Toast.LENGTH_SHORT).show()); // GPS off
    }

    // Note:
    // This is the recommended way to get a fresh location, whenever possible, and is safer than alternatives like starting and managing location updates yourself using
    // requestLocationUpdates(). If your app calls requestLocationUpdates(), your app can sometimes consume large amounts of power if location isn't available, or if
    // the request isn't stopped correctly after obtaining a fresh location.

    private void updateLocation(Location location){
        this.location = location;
    }



/*
    // Change location settings
    // https://developer.android.com/training/location/change-location-settings.html

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); // accuracy of approximately 100 meters
    }

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
     .addLocationRequest(locationRequest);


    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    // ...
    SettingsClient client = LocationServices.getSettingsClient(this);
    Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


*/

/*
// Request location updates
// https://developer.android.com/training/location/request-updates#java


// Make a location request
    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }


// Define the location update callback
private LocationCallback locationCallback;

// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }
            }
        };
    }


// Stop location updates
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }
*/

}