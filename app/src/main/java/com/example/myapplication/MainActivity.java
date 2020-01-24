package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;


public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    boolean b=true;
    Button btn,btn2 ;
    Context context;
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
    MyDatabase db ;
    EditText et;
    private static final String PREFS = "PREFS";
    int Interval,FastestInterval,SmallestDisplacement;
    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        db= Room.databaseBuilder(getApplicationContext(), MyDatabase.class, MyDatabase.DB_Racolt).allowMainThreadQueries().fallbackToDestructiveMigration().build();


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener( this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        buildLocationRequest();
        buildLocationCallBack();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
        context = this;
        btn = findViewById(R.id.save);
        btn2 = findViewById(R.id.save2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

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
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Recolt donnees = new Recolt(alt, longi, sped, speedprec, dir, directprec,vilocity ,x, y, z,true);
                            db.daoAccess().insertRacolt(donnees);
                            int h=db.daoAccess().loadalldatas().size()-1;
                            Log.i(TAG, ""+ db.daoAccess().loadalldatas().get(h));
                        }
                    });
                    Recolt donnees = new Recolt(alt, longi, sped, speedprec, dir, directprec,vilocity ,x, y, z,false);
                    db.daoAccess().insertRacolt(donnees);
                    int h=db.daoAccess().loadalldatas().size()-1;
                    Log.i(TAG, ""+ db.daoAccess().loadalldatas().get(h));
                }
            }
        };
    }

    public void saveData(){
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdir();
        }
        File file = new File(context.getExternalFilesDir(""), "Infos" + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = db.query("SELECT * FROM recolts" , null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String arrStr[] = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() ; i++)
                    arrStr[i] = curCSV.getString(i);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }

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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
        sharedPreferences
                .edit()
                .putInt(Interval,0)
                .putInt(FastestInterval, 0)
                .putInt(SmallestDisplacement, 0)
                .apply();
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(9);
        locationRequest.setSmallestDisplacement(10);
    }
}
