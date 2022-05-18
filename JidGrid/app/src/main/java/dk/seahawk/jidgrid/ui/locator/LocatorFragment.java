package dk.seahawk.jidgrid.ui.locator;

import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;

import dk.seahawk.jidgrid.R;
import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;

// https://www.geeksforgeeks.org/how-to-get-current-location-inside-android-fragment/

// View
public class LocatorFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.

    // https://medium.com/@bariskarapinar/location-updates-implementing-google-maps-fragment-android-app-d83a0b2d9914
    private FragmentLocatorBinding binding;
    private GridAlgorithmInterface gridAlgorithm;
    private static final String TAG = LocatorFragment.class.getSimpleName();
    private TextView jidField, lonField, latField, altField;

    // Location
    private int PRIORITY = LocationRequest.QUALITY_HIGH_ACCURACY;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private com.google.android.gms.location.LocationRequest locationRequest;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init location service
        fusedLocationProviderClient = new FusedLocationProviderClient(requireContext());

        // Initialize view
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridAlgorithm = new GridAlgorithm();

        jidField = root.findViewById(R.id.txt_jid);
        lonField = root.findViewById(R.id.txt_longitude);
        latField = root.findViewById(R.id.txt_latitude);
        altField = root.findViewById(R.id.txt_altitude);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setPriority(PRIORITY);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        jidField.setText(gridAlgorithm.getGridLocation(location));
        lonField.setText(String.valueOf(location.getLongitude()));
        latField.setText(String.valueOf(location.getLatitude()));
        altField.setText(String.valueOf(location.getAltitude()));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

}
