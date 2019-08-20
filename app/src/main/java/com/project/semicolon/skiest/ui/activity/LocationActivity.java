package com.project.semicolon.skiest.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dmgdesignuk.locationutils.easyaddressutility.EasyAddressUtility;
import com.dmgdesignuk.locationutils.easylocationutility.EasyLocationUtility;
import com.dmgdesignuk.locationutils.easylocationutility.LocationRequestCallback;
import com.project.semicolon.skiest.Constants;
import com.project.semicolon.skiest.R;
import com.project.semicolon.skiest.databinding.LocationActivityBinding;
import com.project.semicolon.skiest.util.SharedPrefUtil;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = LocationActivity.class.getSimpleName();
    private LocationActivityBinding binding;
    private ProgressDialog progressDialog;
    private EasyAddressUtility addressUtility;
    private EasyLocationUtility locationUtility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching your location");
        progressDialog.setTitle("Pick location");

        locationUtility = new EasyLocationUtility(this);
        addressUtility = new EasyAddressUtility(this);


        binding.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                getLastLocation();


            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                    binding.etLocation.setError("Location is required...");
                    return;
                }

                Intent in = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

    }

    private void getLastLocation() {
        if (locationUtility.permissionIsGranted()) {
            locationUtility.checkDeviceSettings(EasyLocationUtility.RequestCodes.LAST_KNOWN_LOCATION);

            locationUtility.getLastKnownLocation(new LocationRequestCallback() {
                @Override
                public void onLocationResult(Location location) {
                    Log.d(TAG, "onLocationResult: location: " + location.toString());
                    getAddressElement(location);
                }

                @Override
                public void onFailedRequest(String s) {
                    Log.e(TAG, "onFailedRequest: location failed" + s);

                }
            });
        } else {
            locationUtility.requestPermission(EasyLocationUtility.RequestCodes.LAST_KNOWN_LOCATION);
        }
    }

    private void getAddressElement(Location location) {
        progressDialog.dismiss();
        String subAdmin = addressUtility.getAddressElement(EasyAddressUtility.AddressCodes.SUB_ADMIN_AREA, location);
        String countyName = addressUtility.getAddressElement(EasyAddressUtility.AddressCodes.COUNTRY_NAME, location);
        String fullAddress = subAdmin + ", " + countyName;
        binding.etLocation.setText(fullAddress);
        SharedPrefUtil.save(this, Constants.KEY_ADDRESS, countyName);
        SharedPrefUtil.save(this, Constants.KEY_LAT, location.getLatitude());
        SharedPrefUtil.save(this, Constants.KEY_LONG, location.getLongitude());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean requestGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (requestGranted) {
            switch (requestCode) {
                case EasyLocationUtility.RequestCodes.LAST_KNOWN_LOCATION:
                    getLastLocation();
                    break;
                default:
                    break;
            }
        } else {
            Log.d(TAG, "onRequestPermissionsResult: You denied the permission request");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult: Unable to process: required device settings not satisfied");
        } else {
            switch (requestCode) {
                case EasyLocationUtility.RequestCodes.LAST_KNOWN_LOCATION:
                    getLastLocation();
                    break;
            }
        }
    }
}
