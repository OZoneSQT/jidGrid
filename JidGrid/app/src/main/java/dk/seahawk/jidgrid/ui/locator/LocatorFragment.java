package dk.seahawk.jidgrid.ui.locator;

import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import dk.seahawk.jidgrid.R;
import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;

// https://www.geeksforgeeks.org/how-to-get-current-location-inside-android-fragment/

// View
public class LocatorFragment extends Fragment {

    private FragmentLocatorBinding binding;
    private GridAlgorithmInterface gridAlgorithm;

    private TextView jidField, lonField, latField, altField;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private com.google.android.gms.location.LocationRequest locationRequest;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocatorViewModel locatorViewModel = new ViewModelProvider(this).get(LocatorViewModel.class);

        // Initialize view
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridAlgorithm = new GridAlgorithm();

        jidField = root.findViewById(R.id.txt_jid);
        lonField = root.findViewById(R.id.txt_longitude);
        latField = root.findViewById(R.id.txt_latitude);
        altField = root.findViewById(R.id.txt_altitude);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // check condition
        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            getCurrentLocation();
        }
        else {
            // When permission is not granted
            requestPermissions(new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, LocationRequest.QUALITY_HIGH_ACCURACY);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == LocationRequest.QUALITY_HIGH_ACCURACY && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            getCurrentLocation();
        } else {
            // When permission are denied
            Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        // Initialize Location manager
        LocationManager locationManager = (LocationManager)requireActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                // Check condition
                if (location != null) {
                    // When location result is not null set location
                    jidField.setText(gridAlgorithm.getGridLocation(location));
                    latField.setText(String.valueOf(location.getLatitude()));
                    lonField.setText(String.valueOf(location.getLongitude()));
                    altField.setText(String.valueOf(location.getAltitude()));
                } else {
                    // When location result is null
                    LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY)
                                    .setInterval(30000)
                                    .setFastestInterval(5000)
                                    .setNumUpdates(1);

                    // Initialize location call back
                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult( LocationResult locationResult) {
                            Location location = locationResult.getLastLocation();
                            // When location result is not null set location
                            jidField.setText(gridAlgorithm.getGridLocation(location));
                            latField.setText(String.valueOf(location.getLatitude()));
                            lonField.setText(String.valueOf(location.getLongitude()));
                            altField.setText(String.valueOf(location.getAltitude()));
                        }
                    };

                    // Request location updates
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                }
            });
        } else {
            // When location service is not enabled
            startActivity( new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
