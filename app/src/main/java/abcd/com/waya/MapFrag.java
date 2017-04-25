package abcd.com.waya;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import abcd.com.waya.R;
import abcd.com.waya.entities.DataBarSingleton;

/**
 * Created by PERSONAL on 20/03/2017.
 */

public class MapFrag extends Fragment implements OnMapReadyCallback{

    DataBarSingleton dbs = DataBarSingleton.getInstance();
    GoogleMap mGoogleMap;
    MapView mapView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(view.getContext(), R.raw.style_json));
            if (!success) {
                System.out.println("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        MapsInitializer.initialize(view.getContext());
        mGoogleMap = googleMap;
        LatLng location = new LatLng(4.7826755,-74.0447828);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(location).title("ECI").snippet("Esta es la U :v"));
        CameraPosition Bogota = CameraPosition.builder().target(location).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Bogota));

        addMarkers(mGoogleMap);
    }

    private void addMarkers(GoogleMap map) {
        for (int i = 0; i < dbs.getData().size(); i++){
            if(dbs.getData().get(i).tipo.equals("normal") || dbs.getData().get(i).tipo.contains("ormal")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                Double.parseDouble(dbs.getData().get(i).longitud),
                                Double.parseDouble(dbs.getData().get(i).latitud)))
                        .title(dbs.getData().get(i).title));
            }
            if(dbs.getData().get(i).tipo.equals("Cover")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                Double.parseDouble(dbs.getData().get(i).longitud),
                                Double.parseDouble(dbs.getData().get(i).latitud)))
                        .title(dbs.getData().get(i).title));
            }
        }
    }

}
