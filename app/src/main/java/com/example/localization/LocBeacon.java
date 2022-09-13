package com.example.localization;

public class LocBeacon {
    Location location;
    String name;
    double radius;

    public LocBeacon() {}

    public LocBeacon(String name, double longitude, double latitude, double radius) {
        this.name = name;
        this.location.setLongitude(longitude);
        this.location.setLatitude(latitude);
        this.radius = radius;
    }

    public LocBeacon(String name, Location location, double radius) {
        this.name = name;
        this.location = location;
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation(){
        return this.location;
    }
}
