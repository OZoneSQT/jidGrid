package dk.seahawk.jidgrid.ui.locator;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.LocationListener;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;

public class LocatorViewModel extends ViewModel implements LocationListener {

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    public LocatorViewModel() {
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastKnownLocation = location;

        //TODO NULL Pointer Exception, Location is not set
        jidText.setValue(gridAlgorithm.getGridLocation(lastKnownLocation));
        lonText.setValue(String.valueOf(lastKnownLocation.getLongitude()));
        latText.setValue(String.valueOf(lastKnownLocation.getLatitude()));
        altText.setValue(String.valueOf(lastKnownLocation.getAltitude()));
    }
}
