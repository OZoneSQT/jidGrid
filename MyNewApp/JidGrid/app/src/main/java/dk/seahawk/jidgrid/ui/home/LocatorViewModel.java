package dk.seahawk.jidgrid.ui.home;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.ResolvableApiException;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.util.Waldo;

public class LocatorViewModel extends ViewModel implements Waldo.OnLocationCompleteListener {

    private Location currentLocation;
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
        jidText.setValue(/* gridAlgorithm.getGridLocation(currentLocation)*/ "jid test");
        lonText.setValue(/* String.valueOf(currentLocation.getLongitude())*/ "lon test");
        latText.setValue(/* String.valueOf(currentLocation.getLatitude())*/ "lat test");
        altText.setValue(/* String.valueOf(currentLocation.getAltitude())*/ "alt test");
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
    public void getLocationUpdate(Location location) {
        currentLocation = location;
    }

    @Override
    public void onError(ResolvableApiException resolvableApiException, String error) {}

    @Override
    public void onResolvableApiResponseFailure() {}


}