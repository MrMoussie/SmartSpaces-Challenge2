package com.example.localization;

public class Location {
    double longitude;
    double latitude;

    public Location() {
        this.longitude = 0;
        this.latitude = 0;
    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location(String name, double longitude, double latitude, double radius) {

    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }
}
