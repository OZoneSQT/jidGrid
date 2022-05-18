package dk.seahawk.jidgrid.ui.locator;

import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.Executor;

import dk.seahawk.jidgrid.MainActivity;
import dk.seahawk.jidgrid.R;
import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;

// https://www.geeksforgeeks.org/how-to-get-current-location-inside-android-fragment/

// View
public class LocatorFragment extends Fragment {

    private static final int RESULT_CANCELED = 0;
    private static final int RESULT_OK = 1;
    private FragmentLocatorBinding binding;
    private GridAlgorithmInterface gridAlgorithm;
    private static final String TAG = LocatorFragment.class.getSimpleName();
    private TextView jidField, lonField, latField, altField;


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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocatorViewModel locatorViewModel = new ViewModelProvider(this).get(LocatorViewModel.class);
     //   startLocationService();

        // Initialize view
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridAlgorithm = new GridAlgorithm();

        jidField = root.findViewById(R.id.txt_jid);
        lonField = root.findViewById(R.id.txt_longitude);
        latField = root.findViewById(R.id.txt_latitude);
        altField = root.findViewById(R.id.txt_altitude);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
   //     createLocationRequest();
    //    settingsCheck();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
/*
    // Check for location settings
    public void settingsCheck() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(requireActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener((Executor) this, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            Log.d(TAG, "onSuccess: settingsCheck");
            getCurrentLocation();
        });

        task.addOnFailureListener((Executor) this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                Log.d(TAG, "onFailure: settingsCheck");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {} // Ignore the error.
            }
        });
    }
*/
    /*
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener((Executor) this, location -> {
                    Log.d(TAG, "onSuccess: getLastLocation");
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        currentLocation = location;

                        jidField.setText(gridAlgorithm.getGridLocation(location));
                        lonField.setText(String.valueOf(location.getLongitude()));
                        latField.setText(String.valueOf(location.getLatitude()));
                        altField.setText(String.valueOf(location.getAltitude()));


                        // currentLocationListener.getLocationUpdate(location);
                        Log.d(TAG, "onSuccess: latitude " + location.getLatitude() + " longitude " + location.getLongitude());
                    } else {
                        Log.d(TAG, "location is null");
                        buildLocationCallback();
                    }
                });
    }

     */
/*
    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    currentLocation = location;

                    jidField.setText(gridAlgorithm.getGridLocation(location));
                    lonField.setText(String.valueOf(location.getLongitude()));
                    latField.setText(String.valueOf(location.getLatitude()));
                    altField.setText(String.valueOf(location.getAltitude()));
                    // currentLocationListener.getLocationUpdate(location);
                    Log.d(TAG, "onLocationResult: latitude " + currentLocation.getLatitude() + " longitude " + currentLocation.getLongitude());
                }
            }
        };
    }
    */
/*
    //called after user responds to location permission popup
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_GRANT_PERMISSION) getCurrentLocation();
    }

 */
/*
    //called after user responds to location settings popup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: ");
        if(requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) getCurrentLocation();
        if(requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_CANCELED) Toast.makeText(requireContext(), getString(R.string.req_location_settings), Toast.LENGTH_SHORT).show();
    }
*/
    /*
    @SuppressLint("MissingPermission")
    public void startLocationService() {
        Log.i(TAG, "Start Location service");
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_GRANT_PERMISSION);
            return;
        }
        if(locationCallback == null) buildLocationCallback();
        if(currentLocation == null) fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
    */
/*
    public void stopLocationService() {
        Log.i(TAG, "Stop Location service");
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_GRANT_PERMISSION);
            return;
        }
        if(locationCallback!=null) fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
