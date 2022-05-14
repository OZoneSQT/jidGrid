package dk.seahawk.jidgrid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;


public class LocatorFragment extends Fragment {

    private FragmentLocatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocatorViewModel locatorViewModel = new ViewModelProvider(this).get(LocatorViewModel.class);

        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView jidField = binding.txtJid;
        final TextView lonField = binding.txtLongitude;
        final TextView latField = binding.txtLatitude;
        final TextView altField = binding.txtAltitude;

        locatorViewModel.getText().observe(getViewLifecycleOwner(), jidField::setText);
        locatorViewModel.getText().observe(getViewLifecycleOwner(), lonField::setText);
        locatorViewModel.getText().observe(getViewLifecycleOwner(), latField::setText);
        locatorViewModel.getText().observe(getViewLifecycleOwner(), altField::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}