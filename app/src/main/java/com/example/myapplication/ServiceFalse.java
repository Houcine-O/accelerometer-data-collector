package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;

public class ServiceFalse extends Service implements SensorEventListener{
    private static final String TAG = "MyActivity";
    private static final int sh=1000;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Sensor accelerometer;
    SensorManager sm;
    double x,y,z,vilocity;
    double xAccel,yAccel,zAccel;
    double xPreviousAccel,yPreviousAccel,zPreviousAccel;
    boolean firstUpdate = true;
    double directprec,speedprec,alt ,longi,sped,dir;
    boolean markable=false;
    MyDatabase db ;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        db= Room.databaseBuilder(getApplicationContext(), MyDatabase.class, MyDatabase.DB_Racolt).allowMainThreadQueries().fallbackToDestructiveMigration().build();


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location:locationResult.getLocations()) {
                    alt = location.getLatitude();
                    longi = location.getLongitude();
                    sped = location.getSpeed();
                    dir = location.getBearing();
                    if (Build.VERSION.SDK_INT < 26) {
                        speedprec = 0;
                        directprec = 0;
                    }
                    else {
                        speedprec = location.getSpeedAccuracyMetersPerSecond();
                        directprec = location.getBearingAccuracyDegrees();
                    }

                    Recolt donnees = new Recolt(alt, longi, sped, speedprec, dir, directprec,vilocity ,x, y, z,false);
                    db.daoAccess().insertRacolt(donnees);
                    int h=db.daoAccess().loadalldatas().size()-1;
                    Log.i(TAG, ""+ db.daoAccess().loadalldatas().get(h));

                    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
                    if (!exportDir.exists()) {
                        exportDir.mkdirs();
                    }
                    File file = new File(context.getExternalCacheDir(), "Hambouk" + ".csv");
                    try {
                        file.createNewFile();
                        CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                        Cursor curCSV = db.query("SELECT * FROM recolts" , null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while (curCSV.moveToNext()) {
                            String arrStr[] = new String[curCSV.getColumnCount()];
                            for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                                arrStr[i] = curCSV.getString(i);
                            csvWrite.writeNext(arrStr);
                        }
                        csvWrite.close();
                        curCSV.close();
                    } catch (Exception sqlEx) {
                        Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                    }

                }
            }
        };
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];



        updateAccel(x,y,z);

        double deltaX = Math.abs(xPreviousAccel - xAccel);
        double deltaY = Math.abs(yPreviousAccel - yAccel);
        double deltaZ = Math.abs(yPreviousAccel - yAccel);
        vilocity = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2)+Math.pow(deltaZ,2))-SensorManager.GRAVITY_EARTH;

    }
    private void updateAccel(double xNew, double yNew, double zNew) {
        if(firstUpdate){
            xPreviousAccel = xNew;
            yPreviousAccel = yNew;
            zPreviousAccel = zNew;
            firstUpdate = false ;
        }
        else {
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
        }

        xAccel = xNew;
        yAccel = yNew;
        zAccel = zNew;
    }


    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(9);
        locationRequest.setSmallestDisplacement(10);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }
}
