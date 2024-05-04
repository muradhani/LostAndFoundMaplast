package com.example.lostandfound.ui.fragment;

import com.google.android.gms.maps.model.LatLng;

public interface PlaceSearchListener {
    void onPlaceFound(LatLng latLng);
    void onPlaceNotFound();
}
