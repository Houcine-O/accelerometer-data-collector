package com.example.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;


public class MainActivity extends AppCompatActivity  {
    boolean b=true;
    String TAG;
    Button btn ;
    ServiceFalse SF;
    MyDatabase db;
    LocationCallback locationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= Room.databaseBuilder(getApplicationContext(), MyDatabase.class, MyDatabase.DB_Racolt).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        TAG="MyActivity";
        SF = new ServiceFalse();
        btn = findViewById(R.id.save);
        final Intent False = new Intent(MainActivity.this,ServiceFalse.class);
        startService(False);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "AWDIIII");
            }
        });
    }
}
