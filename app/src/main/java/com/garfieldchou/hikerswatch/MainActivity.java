package com.garfieldchou.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    LocationListener locationListener;

    TextView latitudeTextView, longitudeTextView, accuracyTextView, altitudeTextView, addressTextView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (TextView)findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView)findViewById(R.id.longitudeTextView);
        accuracyTextView = (TextView)findViewById(R.id.accuracyTextView);
        altitudeTextView = (TextView)findViewById(R.id.altitudeTextView);
        addressTextView = (TextView)findViewById(R.id.addressTextView);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());

                latitudeTextView.setText("Latitude: " + String.valueOf(location.getLatitude()));
                longitudeTextView.setText("Longitude: " + String.valueOf(location.getLongitude()));
                accuracyTextView.setText("Accuracy: " + String.valueOf(location.getAccuracy()));
                altitudeTextView.setText("Altitude: " + String.valueOf(location.getAltitude()));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if(Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                latitudeTextView.setText("Latitude: " + String.valueOf(userLocation.getLatitude()));
                longitudeTextView.setText("Longitude: " + String.valueOf(userLocation.getLongitude()));
                accuracyTextView.setText("Accuracy: " + String.valueOf(userLocation.getAccuracy()));
                altitudeTextView.setText("Altitude: " + String.valueOf(userLocation.getAltitude()));

            }

        }
    }
}
