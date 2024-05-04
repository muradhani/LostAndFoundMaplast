package com.example.lostandfound.ui.fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.lostandfound.data.local.entity.CaseEntity;
import com.example.lostandfound.databinding.FragmentCreateAdvertBinding;
import com.example.lostandfound.ui.viewModels.CreateAdvertViewModel;
import com.example.lostandfound.R;
import com.example.lostandfound.ui.viewModels.OnEntitySavedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateAdvertFragment extends Fragment implements OnEntitySavedListener {

    private CreateAdvertViewModel mViewModel;
    private FragmentCreateAdvertBinding binding;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;
    NavController navController ;
    Double latitude ;
    Double longitude ;
    String placeName;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAdvertBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateAdvertViewModel.class);
        mViewModel.initiate(getContext(),this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            latitude = args.getDouble("latitude");
            longitude = args.getDouble("longitude");
            placeName = args.getString("placeName");
            binding.edTextLocation.setText(placeName);
            // Use latitude and longitude as needed
        }
        navController= NavHostFragment.findNavController(this);
        binding.AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edTextName.getText().toString();
                String phone = binding.edTextPhone.getText().toString();
                String description = binding.edTextDescription.getText().toString();
                String date = binding.edTextDate.getText().toString();
                String location = binding.edTextLocation.getText().toString();
                int selectedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();
                String selectedRadioButtonText = "";
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                }
                CaseEntity entity = new CaseEntity(name,selectedRadioButtonText, phone, description, date, placeName,latitude,longitude);
                mViewModel.saveEntity(entity);
            }
        });
        binding.edTextLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Execute your desired functionality here
                    navController.navigate(R.id.action_createAdvertFragment_to_searchFragment);
                    return true; // Consume the event
                }
                return false; // Allow other touch events to be handled
            }
        });
        binding.getCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for location permissions
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                    return;
                }

                // Permission is granted, proceed with getting the location
                // Initialize FusedLocationProviderClient
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

                // Check if location is available
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    // Location found, do something with it
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                    // Use Geocoder to get place name
                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                        if (addresses != null && addresses.size() > 0) {
                                            placeName = addresses.get(0).getAddressLine(0);
                                            binding.edTextLocation.setText(placeName);
                                            // Now you have the place name, use it as needed
                                            // For example, you can display it in a TextView or use it in further operations
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // Location is null, handle the case where location is not available
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onEntitySaved() {
        Toast.makeText(requireContext(),"saved successfully",Toast.LENGTH_SHORT).show();
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false) // Pop inclusive of the destinationId
                .build();

// Pop back to the specified destination fragment
        navController.popBackStack(R.id.homeFragment, false);
    }
}