package com.example.proyectomultimediamaps;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.Marker;
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
                    Places place = (Places) i.getSerializableExtra("place");
                    Log.w("place",place.getNombre());
                    if (place != null) {
                        getActivity().setTitle(place.getNombre());
                        double latitude = place.getLat();
                        double longitude = place.getLongi();
                        GeoPoint startPoint = new GeoPoint(latitude, longitude);
                        mapController.setZoom(15);
                        mapController.setCenter(startPoint);

                        mapController.setZoom(15);
                        Marker marker = new Marker(map);
                        marker.setPosition(startPoint);
                        marker.setTitle(place.getNombre());
                        map.getOverlays().add(marker);
                    } else {
                        GeoPoint startPoint = new GeoPoint(41.39, 2.154);
                        mapController.setZoom(15);
                        mapController.setCenter(startPoint);

                        Marker marker = new Marker(map);
                        marker.setPosition(startPoint);
                        marker.setTitle("Barcelona");
                        map.getOverlays().add(marker);
                    }
                }else{
                    GeoPoint startPoint = new GeoPoint(41.39, 2.154);
                    mapController.setZoom(15);
                    mapController.setCenter(startPoint);

                    Marker marker = new Marker(map);
                    marker.setPosition(startPoint);
                    marker.setTitle("Barcelona");
                    map.getOverlays().add(marker);
                }

        return view;

       // private final LocationListener

    }

}