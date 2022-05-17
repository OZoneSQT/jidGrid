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


    //TODO Move FusedLocationProviderClient to a separate class to avoid violating MVVM
    // https://proandroiddev.com/android-tutorial-on-location-update-with-livedata-774f8fcc9f15
    private final MutableLiveData<String> jidText;
    private final MutableLiveData<String> lonText;
    private final MutableLiveData<String> latText;
    private final MutableLiveData<String> altText;

    public LocatorViewModel() {


        jidText = new MutableLiveData<>();
        lonText = new MutableLiveData<>();
        latText = new MutableLiveData<>();
        altText = new MutableLiveData<>();

        //TODO NULL Pointer Exception, Location is not set
        jidText.setValue("init. grid locator");
        lonText.setValue("init. longitude");
        latText.setValue("init. latitude");
        altText.setValue("init. altitude");
    }

    public LiveData<String> getJid() {
        return jidText;
    }

    public LiveData<String> getLon() {
        return lonText;
    }

    public LiveData<String> getLat() {
        return latText;
    }

    public LiveData<String> getAlt() {
        return altText;
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
