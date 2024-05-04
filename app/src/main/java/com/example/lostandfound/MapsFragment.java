package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostandfound.data.local.database.AppDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import com.example.lostandfound.data.local.entity.CaseEntity;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize database
        database = AppDatabase.getDatabase(requireContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Execute database query on a background thread
        new Thread(() -> {
            List<CaseEntity> cases = database.caseDao().getAll();
            requireActivity().runOnUiThread(() -> {
                for (CaseEntity caseEntity : cases) {
                    LatLng location = new LatLng(caseEntity.getLatitude(), caseEntity.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).title(caseEntity.name));
                }
                if (!cases.isEmpty()) {
                    CaseEntity firstCase = cases.get(0); // Just to move the camera to the first case
                    LatLng firstLocation = new LatLng(firstCase.getLatitude(), firstCase.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
                } else {
                    // Handle case when there are no cases
                    Log.d("MapsFragment", "No cases found");
                }
            });
        }).start();
    }

}
