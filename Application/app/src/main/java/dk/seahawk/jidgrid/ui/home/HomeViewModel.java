package dk.seahawk.jidgrid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.seahawk.jidgrid.util.Waldo;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> latitudeText;
    private final MutableLiveData<String> longitudeText;
    private Waldo waldo;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        latitudeText = new MutableLiveData<>();
        latitudeText.setValue(String.valueOf(waldo.getLocation().getLatitude()));

        longitudeText = new MutableLiveData<>();
        longitudeText.setValue(String.valueOf(waldo.getLocation().getLongitude()));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getLat() {
        return latitudeText;
    }

    public LiveData<String> getLon() {
        return longitudeText;
    }
}