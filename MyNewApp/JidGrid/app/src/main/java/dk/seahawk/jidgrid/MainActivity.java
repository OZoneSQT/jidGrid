package dk.seahawk.jidgrid;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import dk.seahawk.jidgrid.databinding.ActivityMainBinding;
import dk.seahawk.jidgrid.util.Waldo;

import com.google.android.gms.common.api.ResolvableApiException;

public class MainActivity extends AppCompatActivity implements Waldo.OnLocationCompleteListener {

    private ActivityMainBinding binding;
    private Waldo waldo;
    private Location currentLocation;

    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;


    /**
     * Lifecycles
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_locator_home, R.id.navigation_maps).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setUpLocationServices();
    }


    protected void onStart() {
        super.onStart();
        setUpLocationServices();
    }

    protected void onResume() {
        super.onResume();
        setUpLocationServices();
    }

    protected void onPause() {
        super.onPause();
        waldo.stopLocationUpdates();
    }

    protected void onStop() {
        super.onStop();
        waldo.stopLocationUpdates();
    }

    protected void onDestroy() {
        super.onDestroy();
        waldo.stopLocationUpdates();
    }


    /**
     * Location
     */

    //Method used for checking permissions and initializing location service
    private void setUpLocationServices() {
        int hasGetLocationPermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasGetLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            initializeLocationHelper();
        }
    }

    private void initializeLocationHelper() {
        waldo = new Waldo(this, this);
        waldo.startLocationUpdates();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Waldo.REQUEST_CODE_RESOLVABLE_API)
            waldo.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_ASK_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initializeLocationHelper();
    }

    @Override
    public void getLocationUpdate(Location location) {
        currentLocation = location;
    }

    @Override
    public void onError(ResolvableApiException resolvableApiException, String error) {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvableApiException.startResolutionForResult(this, Waldo.REQUEST_CODE_RESOLVABLE_API);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }
    }

    @Override
    public void onResolvableApiResponseFailure() {}

}