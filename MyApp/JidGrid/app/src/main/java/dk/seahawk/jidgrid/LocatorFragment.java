package dk.seahawk.jidgrid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import dk.seahawk.jidgrid.databinding.FragmentLocatorBinding;

public class LocatorFragment extends Fragment {

    private FragmentLocatorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocatorBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLocator.setOnClickListener(view1 -> NavHostFragment
                .findNavController(LocatorFragment.this)
                .navigate(R.id.action_LocatorFragment_to_MapsFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}