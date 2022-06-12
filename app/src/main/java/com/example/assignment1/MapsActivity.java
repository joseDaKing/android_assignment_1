package com.example.assignment1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment1.client.ClientService;
import com.example.assignment1.client.ClientServiceConnection;
import com.example.assignment1.client.requests.register.RegisterRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.assignment1.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    private AppViewModel appViewModel;

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), (isGranted) -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(view -> {
            startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
        });

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        ClientServiceConnection clientServiceConnection = appViewModel.getClientServiceConnection();

        ClientService clientService = clientServiceConnection.getClientService();

        if (clientService != null) {

            clientService.client.execute(new RegisterRequest().group("abra-gang").member("Abra"));

            clientService.client.responseHandler.getRegisterResponse().observe(this, registerResponseData -> {

                Toast.makeText(this, registerResponseData.id, Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setZoomGesturesEnabled(true);

        requestForPermissions();
    }

    private void requestForPermissions() {

        boolean hasFineLocationAccess = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean hasCoarseLocationAccess = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean hasAccess = hasFineLocationAccess && hasCoarseLocationAccess;

        if (hasAccess) {

            mMap.setMyLocationEnabled(true);
        }
        else {

            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        startService();
    }

    @Override
    protected void onStop() {

        super.onStop();

        unbindService(appViewModel.getClientServiceConnection());
    }

    private void startService(){

        Intent serviceIntent = new Intent(this, ClientService.class);

        startService(serviceIntent);

        bindService();
    }

    private void bindService(){

        Intent serviceBindIntent =  new Intent(this, ClientService.class);

        bindService(serviceBindIntent, appViewModel.getClientServiceConnection(), Context.BIND_AUTO_CREATE);
    }
}