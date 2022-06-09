package com.example.assignment1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.assignment1.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.Serializable;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Intent clientIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        clientIntent = new Intent(this, ClientService.class);

        startService(clientIntent);

        addListeners();
    }

    private void addListeners() {

        Callback<UserPosition[]> onLocation = userPositions -> {

            mMap.clear();

            for (int i = 0; i < userPositions.length; i++) {

                UserPosition userPosition = userPositions[i];

                MarkerOptions options = new MarkerOptions();

                options.title(options.getTitle());

                options.position(userPosition.getLatLng());

                mMap.addMarker(options);
            }
        };

        clientIntent.putExtra(
                ClientManager.LOCATION_TYPE,
                onLocation
        );

        Callback<String> onError = errorMessage -> {

            CharSequence text = errorMessage;

            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);

            toast.show();
        };

        clientIntent.putExtra(
                ClientManager.EXCEPTIONS_TYPE,
                onError
        );
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setZoomGesturesEnabled(true);

    }
}