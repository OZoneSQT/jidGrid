package dk.seahawk.jidgrid.ui.locator;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;

public class LocatorViewModel extends ViewModel {

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;
    private GridAlgorithmInterface gridAlgorithm;

    private final MutableLiveData<String> jidText;
    private final MutableLiveData<String> lonText;
    private final MutableLiveData<String> latText;
    private final MutableLiveData<String> altText;

    public LocatorViewModel() {
        gridAlgorithm = new GridAlgorithm();

        jidText = new MutableLiveData<>();
        lonText = new MutableLiveData<>();
        latText = new MutableLiveData<>();
        altText = new MutableLiveData<>();

        //TODO NULL Pointer Exception, Location is not set
        jidText.setValue(/* gridAlgorithm.getGridLocation(lastKnownLocation)*/ "jid test");
        lonText.setValue(/* String.valueOf(lastKnownLocation.getLongitude())*/ "lon test");
        latText.setValue(/* String.valueOf(lastKnownLocation.getLatitude())*/ "lat test");
        altText.setValue(/* String.valueOf(lastKnownLocation.getAltitude())*/ "alt test");
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

}