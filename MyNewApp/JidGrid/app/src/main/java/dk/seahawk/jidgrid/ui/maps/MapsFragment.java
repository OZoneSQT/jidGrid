package dk.seahawk.jidgrid.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dk.seahawk.jidgrid.R;

public class MapsFragment extends Fragment implements LocationSource {

    //TODO https://developers.google.com/maps/documentation/android-sdk/
    // https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/gms/java/com/example/mapdemo/CameraDemoActivity.java
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            // LatLng sydney = new LatLng(-34, 151);
            // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.getUiSettings().setMyLocationButtonEnabled( true );
            googleMap.getUiSettings().setZoomControlsEnabled( true );
            googleMap.getUiSettings().setCompassEnabled( true );
            googleMap.getUiSettings().setZoomControlsEnabled(true);


         /*
            // When map is loaded
            googleMap.setOnMapClickListener(latLng -> {
                // When clicked on map
                // Initialize marker options
                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                markerOptions.position(latLng);
                // Set title of marker
                markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                // Remove all marker
                googleMap.clear();
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                // Add marker on map
                googleMap.addMarker(markerOptions);
            }); */
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(callback);
    }
// https://developers.google.com/maps/documentation/android-sdk/
// https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/gms/java/com/example/mapdemo/CameraDemoActivity.java
// https://developers.google.com/maps/documentation/android-sdk/views

//TODO https://github.com/googlemaps/android-samples/blob/build/update-required-checks/ApiDemos/java/app/src/v3/java/com/example/mapdemo/LocationSourceDemoActivity.java
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void activate(@NonNull OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}