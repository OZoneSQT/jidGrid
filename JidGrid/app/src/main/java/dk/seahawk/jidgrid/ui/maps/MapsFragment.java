package dk.seahawk.jidgrid.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import dk.seahawk.jidgrid.R;

// https://stackoverflow.com/questions/18532581/how-to-retrieve-current-device-location-show-it-on-map-fragment-in-a-fragment
// https://medium.com/@bariskarapinar/location-updates-implementing-google-maps-fragment-android-app-d83a0b2d9914
public class MapsFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap googleMap;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.

    // https://medium.com/@bariskarapinar/location-updates-implementing-google-maps-fragment-android-app-d83a0b2d9914
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

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

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {

            // https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap

            // LatLng sydney = new LatLng(-34, 151);
            // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            //TODO Add map stuff
            try {
                 /*
                    MAP_TYPE_HYBRID 	Satellite maps with a transparent layer of major streets.
                    MAP_TYPE_NONE 	    No base map tiles.
                    MAP_TYPE_NORMAL 	Basic maps.
                    MAP_TYPE_SATELLITE 	Satellite maps with no labels.
                    MAP_TYPE_TERRAIN 	Terrain maps.
                  */
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled( true );
                googleMap.getUiSettings().setZoomControlsEnabled( false );
                googleMap.getUiSettings().setCompassEnabled( true );
                googleMap.getUiSettings().setAllGesturesEnabled( true );
            } catch (InflateException e) {
                Toast.makeText(requireActivity(), "Problems inflating the view !", Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                Toast.makeText(requireActivity(), "Google Play Services missing !", Toast.LENGTH_LONG).show();
            }
            // https://stackoverflow.com/questions/48688419/getcontext-in-fragment
            // use requireContext() or requireActivity() will not return null
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Init location service
        fusedLocationProviderClient = new FusedLocationProviderClient(requireContext());

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(callback);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

}