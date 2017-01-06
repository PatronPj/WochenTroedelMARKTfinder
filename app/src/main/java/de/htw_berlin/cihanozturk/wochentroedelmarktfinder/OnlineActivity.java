package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

import java.util.ArrayList;




public class OnlineActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private Button btn_gps;
    private LocationManager locationManger;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private GeoPoint geoPoint;
    private MapView mMapView;
    private MapController mc;
    private SimpleLocationOverlay mMyLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    ArrayList<OverlayItem> overlayItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        mc = (MapController) mMapView.getController();
        mc.setZoom(12);

        geoPoint = new GeoPoint(52.520007, 13.404954);
        mc.animateTo(geoPoint);

        //-----Test(1)------
        overlayItemArray = new ArrayList<OverlayItem>();
        OverlayItem linkopingItem = new OverlayItem("Artemis", "Berlin",
                new GeoPoint(52.4993660, 13.2823210));
        OverlayItem stockholmItem = new OverlayItem("ZwanglosII", "Berlin",
                new GeoPoint(52.4918500, 13.3911100));
        // Add the init objects to the ArrayList overlayItemArray
        overlayItemArray.add(linkopingItem);
        overlayItemArray.add(stockholmItem);

        // Add the Array to the IconOverlay
        ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);

        // Add the overlay to the MapView
        mMapView.getOverlays().add(itemizedIconOverlay);



        //----Test End(1)----
        //addMarket(geopoint);

        btn_gps = (Button) findViewById(R.id.btn_gps);
        btn_gps.setOnClickListener(this);


    }


    public void addMarket(GeoPoint center) {
        Marker marker = new Marker(mMapView);
        marker.setPosition(center);
       // marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
       // marker.setIcon(getResources().getDrawable(R.drawable.ic_menu_mylocation));

        //------

        //------
       // mc.setZoom(20);
        //entfernt die alle Marker
        //mMapView.getOverlays().clear();
        mMapView.getOverlays().add(marker);
        mMapView.invalidate();
    }


    /* public void warnAndEnd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();

        builder.setTitle(res.getString(R.string.str_btn_start));
        builder.setMessage(res.getString(R.string.str_hint_search));
        builder.setPositiveButton("Einstellungen", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent3 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent3);

            }

        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

*/

    @Override
    public void onLocationChanged(Location location) {
        double latitude=52.520007;
        double longitude=13.404954;
        GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
        mc.animateTo(center);
        mc.setZoom(20);
        addMarket(center);



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManger != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManger.removeUpdates(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_gps:
                boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionLocation) {
                    ActivityCompat.requestPermissions(OnlineActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION);
                }


                locationManger = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                locationManger.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);


                break;
        }


    }

}