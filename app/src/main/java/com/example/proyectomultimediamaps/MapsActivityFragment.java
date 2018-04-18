package com.example.proyectomultimediamaps;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

public class MapsActivityFragment extends Fragment {

    private MapView map;
    OverlayItem overItem;
    Drawable marker;

    public MapsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();


        Intent i = getActivity().getIntent();
                if (i != null) {
                    double latitude = (double) i.getSerializableExtra("lat");
                    double longitude = (double) i.getSerializableExtra("longi");
                    GeoPoint startPoint = new GeoPoint(latitude, longitude);
                    mapController.setZoom(15);
                    mapController.setCenter(startPoint);
                    overItem = new OverlayItem("Favorite", "Position", startPoint);
                    marker= this.getResources().getDrawable(R.drawable.marker_default);
                    overItem.setMarker(marker);
                }else {
                    GeoPoint startPoint = new GeoPoint(41.39, 2.154);
                    mapController.setZoom(9);
                    mapController.setCenter(startPoint);
                    overItem = new OverlayItem("BCN", "Barcelona", startPoint);
                    marker = this.getResources().getDrawable(R.drawable.marker_default);
                    overItem.setMarker(marker);
                }
        return view;
    }

}