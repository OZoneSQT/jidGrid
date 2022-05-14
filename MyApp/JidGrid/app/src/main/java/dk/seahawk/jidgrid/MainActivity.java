package dk.seahawk.jidgrid;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dk.seahawk.jidgrid.databinding.ActivityMainBinding;
import dk.seahawk.jidgrid.util.Waldo;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.ResolvableApiException;

public class MainActivity extends AppCompatActivity implements Waldo.OnLocationCompleteListener {

    private AppBarConfiguration appBarConfiguration;
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

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        setUpLocationServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpLocationServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpLocationServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waldo.stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        waldo.stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waldo.stopLocationUpdates();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    /**
     * Location
     */

    //Method used for checking permissions and initializing location service
    private void setUpLocationServices() {
        int hasGetLocationPermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasGetLocationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
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
        if (requestCode == Waldo.REQUEST_CODE_RESOLVABLE_API) waldo.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_ASK_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) initializeLocationHelper();
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