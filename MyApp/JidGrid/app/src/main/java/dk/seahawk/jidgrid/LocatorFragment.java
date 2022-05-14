package dk.seahawk.jidgrid;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.api.ResolvableApiException;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;
import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;
import dk.seahawk.jidgrid.util.Waldo;

public class LocatorFragment extends Fragment implements Waldo.OnLocationCompleteListener {

    private FragmentLocatorBinding binding;
    private GridAlgorithmInterface gridAlgorithm;

    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtAltitude;
    private TextView txtJid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        gridAlgorithm = new GridAlgorithm();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtLatitude = view.findViewById(R.id.txt_latitude);
        txtLongitude = view.findViewById(R.id.txt_longitude);
        txtAltitude = view.findViewById(R.id.txt_altitude);
        txtJid = view.findViewById(R.id.txt_jid);

        binding.buttonLocator.setOnClickListener(view1 -> NavHostFragment
                .findNavController(LocatorFragment.this)
                .navigate(R.id.action_LocatorFragment_to_MapsFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //TODO get text on fragment

    @Override
    public void getLocationUpdate(Location location) {
        txtLongitude.setText(new StringBuilder().append(getString(R.string.gridlocator_content)).append(" ").append(gridAlgorithm.getGridLocation(location)).toString());
        txtLatitude.setText(new StringBuilder().append(getString(R.string.latitudefield_content)).append(" ").append(location.getLatitude()).toString());
        txtLongitude.setText(new StringBuilder().append(getString(R.string.longitudefield_content)).append(" ").append(location.getLongitude()).toString());
        txtLatitude.setText(new StringBuilder().append(getString(R.string.altitudefield_content)).append(" ").append(location.getAltitude()).toString());

        // TextClocks set in layout
    }

    @Override
    public void onError(ResolvableApiException resolvableApiException, String error) {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvableApiException.startResolutionForResult(getActivity(), Waldo.REQUEST_CODE_RESOLVABLE_API);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }
    }

    @Override
    public void onResolvableApiResponseFailure() {}
}