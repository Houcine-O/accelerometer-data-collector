package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;

@Entity(tableName = "recolts")
public class Recolt implements Serializable {

    public boolean isMarkable() {
        return markable;
    }

    @PrimaryKey(autoGenerate = true)
  int a;
    @ColumnInfo(name = "laltitude")
    double laltitude;

    @ColumnInfo(name = "longitude")
    double longitude;

    @ColumnInfo(name = "speed")
    double speed;

    @ColumnInfo(name = "speedAcc")
    double speedAcc;

    @ColumnInfo(name = "direction")
    double direction;

    @ColumnInfo(name = "directAcc")
    double directAcc;
    @ColumnInfo(name = "vilocity")
    double vilocity;
    @ColumnInfo(name = "x")
    double x;

    @ColumnInfo(name = "y")
    double y;

    @ColumnInfo(name = "z")
    double z;

    @ColumnInfo(name="mark")
    boolean markable;



    public Recolt( double laltitude, double longitude, double speed, double speedAcc, double direction, double directAcc,double vilocity ,double x, double y, double z,boolean markable) {
        this.laltitude = laltitude;
        this.longitude = longitude;
        this.speed = speed;
        this.speedAcc = speedAcc;
        this.direction = direction;
        this.directAcc = directAcc;
        this.vilocity = vilocity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.markable=markable;
    }

    public double getLaltitude() {
        return laltitude;
    }

    public  double getLongitude() {
        return longitude;
    }

    public  double getSpeed() {
        return speed;
    }

    public double getSpeedAcc() { return speedAcc; }

    public double getDirection() {
        return direction;
    }

    public double getDirectAcc() { return directAcc; }

    public double getVilocity() {
        return vilocity;
    }

    public double getX() { return x; }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setMarkable(boolean markable) {
        this.markable = markable;
    }

    public void setLaltitude(double laltitude) {this.laltitude = laltitude; }

    public  void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public  void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) { this.direction = direction; }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }



    public String toString(){
        return "Altitude : "+getLaltitude()+"  Longitude : "+getLongitude()+" Direction : "+getDirection()+" Direction Accuracy : "+getDirectAcc()+"  Speed : "+getSpeed()+" Speed Accuracy : "+getSpeedAcc()+" Vilocity : "+getVilocity()+"  X : "+getX()+"  Y : "+getY()+" Z : "+getZ()+" Mark : "+markable;
    }
}

