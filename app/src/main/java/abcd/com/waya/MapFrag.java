package abcd.com.waya;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import abcd.com.waya.entities.singleton.DataBarSingleton;

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
        Bitmap cover = getBitmap(view.getContext(), R.drawable.ic_checkout);
        Bitmap normal = getBitmap(view.getContext(), R.drawable.ic_beers);
        Bitmap libre = getBitmap(view.getContext(), R.drawable.ic_drinks);
        for (int i = 0; i < dbs.getData().size(); i++){
            if(dbs.getData().get(i).tipo.equals("normal") || dbs.getData().get(i).tipo.contains("ormal")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                Double.parseDouble(dbs.getData().get(i).longitud),
                                Double.parseDouble(dbs.getData().get(i).latitud)))
                        .title(dbs.getData().get(i).title).icon(BitmapDescriptorFactory.fromBitmap(normal))
                        .snippet(tenCharStr(dbs.getData().get(i).title, i)));
            }
            if(dbs.getData().get(i).tipo.equals("Cover") || dbs.getData().get(i).tipo.contains("over")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                Double.parseDouble(dbs.getData().get(i).longitud),
                                Double.parseDouble(dbs.getData().get(i).latitud)))
                        .title(dbs.getData().get(i).title).icon(BitmapDescriptorFactory.fromBitmap(cover))
                        .snippet(tenCharStr(dbs.getData().get(i).title, i)));
            }
            if(dbs.getData().get(i).tipo.equals("free") || dbs.getData().get(i).tipo.contains("bar")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                Double.parseDouble(dbs.getData().get(i).longitud),
                                Double.parseDouble(dbs.getData().get(i).latitud)))
                        .title(dbs.getData().get(i).title).icon(BitmapDescriptorFactory.fromBitmap(libre))
                        .snippet(tenCharStr(dbs.getData().get(i).title, i)));
            }
        }
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String position = marker.getSnippet().substring(marker.getSnippet().lastIndexOf(".")+1,marker.getSnippet().length());
                System.out.println("INDICE DE BAR EN MAPA --> " + position);
                Intent intent = new Intent(view.getContext(), BarInformationActivity.class);
                intent.putExtra("position",Integer.parseInt(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    private String tenCharStr(String title, int i) {
        String text = "";
        text = title.substring(0,5);
        text = text + "..." + Integer.toString(i);
        return text;
    }


    public Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
