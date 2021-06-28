package com.demo.demo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_PERMISSION_CODE = 1240;
    String[] appPermissions={Manifest.permission.ACCESS_FINE_LOCATION};
    String latitude1, longitude1;
    LocationTrack locationTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getPermission();



        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {



                    LocationManager nManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!nManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        OnGPS();
                    }else {

                        getDeviceLocation();
                    }




                handler.postDelayed(this, 600000);
                //handler.postDelayed(this, 900000);
            }
        };
        handler.post(run);
    }

    private void OnGPS() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean getPermission() {


/*        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
        } else {
                   *//* Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, STORAGE_REQUEST);*//*


        }*/

    /*    if (ContextCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(Dashboard.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(Dashboard.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }

        }

        if (ContextCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(Dashboard.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }else{
                ActivityCompat.requestPermissions(Dashboard.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

            }
        }*/


        ////////////////////////////////////////



        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:appPermissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_PERMISSION_CODE);
            return false;
        }
        return true;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

      /*  if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "storage permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "storage permission denied", Toast.LENGTH_LONG).show();
            }
        }*/




       /* switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(Dashboard.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

                return;
            }

            case 2:{
                if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_DENIED){
                    if (ContextCompat.checkSelfPermission(Dashboard.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        Toast.makeText(Location, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Location, "Permission Denide", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

            }
        }*/


        switch (requestCode) {
            case PERMISSION_PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permissions granted.
                } else {
                    // no permissions granted.
                    Toast.makeText(MainActivity.this, "Permission denied....! Please give location permissions...", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }



    }

    private void getDeviceLocation() {

        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_PERMISSION_CODE);
        } else {

            locationTrack = new LocationTrack(MainActivity.this);


            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                latitude1=String.valueOf(latitude);
                longitude1=String.valueOf(longitude);

                System.out.println(longitude+"#######################################################"+latitude);
                Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {

                locationTrack.showSettingsAlert();
            }

        }




    }
}