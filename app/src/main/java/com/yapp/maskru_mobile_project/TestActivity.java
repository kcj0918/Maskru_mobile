package com.yapp.maskru_mobile_project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.yapp.maskru_mobile_project.java.NMapPOIflagType;
import com.yapp.maskru_mobile_project.java.NMapViewerResourceProvider;

public class TestActivity  extends NMapActivity implements LocationListener{





    private NMapView mapView;
    private double latPoint;
    private double lngPoint;
    private final String CLIENT_ID = "cMyLnJ1Z1LyXaPq1Rv62";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mapView = new NMapView(this);
        mapView.setApiKey(CLIENT_ID);


        mapView = (NMapView)findViewById(R.id.mapView);
        //mapView.setBuiltInZoomControls(true,null);
        //동현
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                Log.e("e","e");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



    }
    private void showMyLocation(double latitude, double longitude) {
        NMapViewerResourceProvider mapViewerResourceProvider = null;
        NMapOverlayManager nMapOverlayManager;
        mapViewerResourceProvider = new NMapViewerResourceProvider(this);
        nMapOverlayManager = new NMapOverlayManager(this,mapView,mapViewerResourceProvider);

        NGeoPoint myPoint = new NGeoPoint(latitude,longitude);
        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(1,mapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(myPoint,"good",markerId,0);
        poiData.endPOIdata();
        NMapPOIdataOverlay poIdataOverlay = nMapOverlayManager.createPOIdataOverlay(poiData,null);
        poIdataOverlay.showAllPOIdata(0);

        NMapController controller = mapView.getMapController();
        controller.animateTo(myPoint);

    }



    @Override
    public void onLocationChanged(Location location) {
        Log.e("lati",location.getLatitude() + "");
        Log.e("Longi",location.getLongitude() + "");
        showMyLocation(location.getLatitude(), location.getLongitude());
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
}
