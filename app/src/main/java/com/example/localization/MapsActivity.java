package com.example.localization;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.os.Bundle;
import android.view.WindowManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains the main Google Maps activity.
 */
public class MapsActivity extends AppCompatActivity {

    private SupportMapFragment smf;
    private static final String FILEPATH = "beacon_list.xlsx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        // Needed to make Excel reading possible
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        init();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Initialization method for this class.
     */
    private void init() {
        this.smf.getMapAsync(this::onMapReady);

        try (InputStream in = getResources().getAssets().open(FILEPATH)) {
            new ExcelReader(in).fetchAllBeacons();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param googleMap maps object passed when maps is ready
     */
    private void onMapReady(GoogleMap googleMap) {

    }
}