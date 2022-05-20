package dk.seahawk.jidgrid.ui.locator;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import dk.seahawk.jidgrid.MainActivity;
import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;

public class LocatorViewModel extends AndroidViewModel {

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
 /*   private Location lastKnownLocation;
    private GridAlgorithmInterface gridAlgorithm;

    private final MutableLiveData<String> jidText;
    private final MutableLiveData<String> lonText;
    private final MutableLiveData<String> latText;
    private final MutableLiveData<String> altText;*/

    @SuppressLint("MissingPermission")
    public LocatorViewModel(Application application) {
        super(application);
    /*    gridAlgorithm = new GridAlgorithm();

        jidText = new MutableLiveData<>();
        lonText = new MutableLiveData<>();
        latText = new MutableLiveData<>();
        altText = new MutableLiveData<>();  */
/*
        //TODO NULL Pointer Exception, Location is not set
        jidText.setValue("init. grid locator");
        lonText.setValue("init. longitude");
        latText.setValue("init. latitude");
        altText.setValue("init. altitude");
*/
        /*
                    lastKnownLocation = location;

                    jidText.setValue(gridAlgorithm.getGridLocation(lastKnownLocation));
                    lonText.setValue(String.valueOf(lastKnownLocation.getLongitude()));
                    latText.setValue(String.valueOf(lastKnownLocation.getLatitude()));
                    altText.setValue(String.valueOf(lastKnownLocation.getAltitude()));
        */

        // mResultReceiver = new AddressResultReceiver(null);
        // cemented as above explained



    }
/*
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
*/
}