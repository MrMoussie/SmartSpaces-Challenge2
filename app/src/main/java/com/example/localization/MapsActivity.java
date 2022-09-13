package com.example.localization;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

import java.util.List;

/**
 * This class contains the main Google Maps activity.
 */
public class MapsActivity extends AppCompatActivity {

    private static final String TAG = "[SYSTEM]";
    private static final String IBEACON = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";


    private SupportMapFragment smf;
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        Dexter.withContext(getApplicationContext())
                .withPermissions(
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            System.out.println("[SYSTEM] PERMISSION GRANTED!");
                            init();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Initialization method for this class.
     * This method sets up permissions, tasks and initializes event listeners and managers.
     */
    private void init() {
        this.smf.getMapAsync(this::onMapReady);
        this.setupBeaconDetection();
    }

    /**
     * Sets up the BeaconManager and range notifier
     */
    private void setupBeaconDetection() {
        this.beaconManager =  BeaconManager.getInstanceForApplication(this);
        this.beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON));
        beaconManager.addRangeNotifier((beacons, region) -> {
            if (beacons.size() > 0) {
                Beacon beacon = beacons.iterator().next();
                System.out.println("[SYSTEM] list size: " + beacons.size());
                Log.i(TAG, "Beacon detected: "+beacon.getDistance() +
                        " meters away and the name is " + beacon.getBluetoothName() +
                        " and RSSI is " + beacon.getRssi() + ".");
                // TODO
            }
        });

        beaconManager.startRangingBeacons(new Region("myRangingUniqueId", null, null, null));
    }

    /**
     * @param googleMap maps object passed when maps is ready
     */
    private void onMapReady(GoogleMap googleMap) {
    }
}