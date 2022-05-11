package com.example.jidgrid;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.example.jidgrid.databinding.FragmentLocatorBinding;
import com.google.android.gms.common.api.ResolvableApiException;

import static com.example.jidgrid.Waldo.REQUEST_CODE_RESOLVABLE_API;

public class LocatorFragment extends Fragment {
    private FragmentLocatorBinding binding;

    private TextView txtLatitude;
    private TextView txtLongitude;

    private Waldo waldo;

    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO Set text
        txtLatitude = view.findViewById(R.id.txt_latitude);
        txtLongitude = view.findViewById(R.id.txt_longitude);

        binding.buttonLocator.setOnClickListener(view1 -> NavHostFragment
                .findNavController(LocatorFragment.this)
                .navigate(R.id.action_LocatorFragment_to_MapsFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Method used for checking permissions and initializing location service
    public void setUpLocationServices() {
        int hasGetLocationPermission = ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasGetLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            initializeLocationHelper();
        }
    }

    private void initializeLocationHelper() {
        waldo = new Waldo(this.getActivity(), (Waldo.OnLocationCompleteListener) this);
        waldo.startLocationUpdates();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RESOLVABLE_API) {
            waldo.onActivityResult(requestCode, resultCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_ASK_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeLocationHelper();
        }
    }

    //TODO start service
    public void getLocationUpdate(Location location) {
        txtLatitude.setText("Latitude: " + location.getLatitude());
        txtLongitude.setText("Longitude: " + location.getLongitude());
        //it is always good to stop location updates once job
        waldo.stopLocationUpdates();    //TODO ON STOP/PAUSE
    }

    public void onError(ResolvableApiException resolvableApiException, String error) {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvableApiException.startResolutionForResult(this.getActivity(), REQUEST_CODE_RESOLVABLE_API);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }
    }

    public void onResolvableApiResponseFailure() {

    }

}