package com.example.lostandfound.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.ui.PlaceSearchTask;
import com.google.android.gms.maps.model.LatLng;

public class SearchFragment extends Fragment implements PlaceSearchListener {

    private EditText editTextPlaceName;
    private Button buttonSearch;
    NavController navController ;
    String placeName;
    Bundle args;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        args = getArguments();
        // Find views
        editTextPlaceName = view.findViewById(R.id.edit_text_place_name);
        buttonSearch = view.findViewById(R.id.button_search);

        // Set click listener for the search button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered place name
                placeName = editTextPlaceName.getText().toString();

                // Execute PlaceSearchTask to search for the place
                new PlaceSearchTask(SearchFragment.this).execute(placeName);
            }
        });

        return view;
    }

    @Override
    public void onPlaceFound(LatLng latLng) {
        Toast.makeText(requireContext(),"place found successfully",Toast.LENGTH_SHORT).show();
        //Bundle bundle = new Bundle();
        args.putDouble("latitude", latLng.latitude);
        args.putDouble("longitude", latLng.longitude);
        args.putString("placeName", placeName);
        navController.navigate(R.id.action_searchFragment_to_createAdvertFragment,args);
    }

    @Override
    public void onPlaceNotFound() {
        Toast.makeText(requireContext(),"place not found",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= NavHostFragment.findNavController(this);
    }
}