package dk.seahawk.jidgrid.ui.locator;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;

// View
public class LocatorFragment extends Fragment {

    // private AddressResultReceiver mResultReceiver;
    // removed here because cause wrong code when implemented and
    // its not necessary like the author says

    //Define fields for Google API Client
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private LocationCallback mLocationCallback;

    private GridAlgorithmInterface gridAlgorithm;

    private FragmentLocatorBinding binding;

    TextView jidField,lonField,latField,altField;

    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocatorViewModel locatorViewModel = new ViewModelProvider(this).get(LocatorViewModel.class);
        gridAlgorithm = new GridAlgorithm();
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

    /*
        TextView jidField = binding.txtJid;
        TextView lonField = binding.txtLongitude;
        TextView latField = binding.txtLatitude;
        TextView altField = binding.txtAltitude;

        locatorViewModel.getJid().observe(getViewLifecycleOwner(), jidField::setText);
        locatorViewModel.getLon().observe(getViewLifecycleOwner(), lonField::setText);
        locatorViewModel.getLat().observe(getViewLifecycleOwner(), latField::setText);
        locatorViewModel.getAlt().observe(getViewLifecycleOwner(), altField::setText);


        TextView jidField = (TextView)root.findViewById(R.Id.txt_jid);
        TextView lonField = (TextView)root.findViewById(R.id.txt_latitude);
        TextView latField = (TextView)root.findViewById(R.id.txt_longitude);
        TextView altField = (TextView)root.findViewById(R.id.txt_altitude);

*/


        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            // Logic to handle location object
                            jidField.setText(gridAlgorithm.getGridLocation(location));
                            lonField.setText(String.valueOf(location.getLongitude()));
                            latField.setText(String.valueOf(location.getLatitude()));
                            altField.setText(String.valueOf(location.getAltitude()));
                        }
                    });

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        // Update UI with location data
                        jidField.setText(gridAlgorithm.getGridLocation(location));
                        lonField.setText(String.valueOf(location.getLongitude()));
                        latField.setText(String.valueOf(location.getLatitude()));
                        altField.setText(String.valueOf(location.getAltitude()));
                    }
                }

                ;
            };
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}