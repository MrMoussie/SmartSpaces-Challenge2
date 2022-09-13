package com.example.localization;

public class iBeacon {
    private int id;
    private String name;
    private String mac;
    private double longitude;
    private double latitude;
    private int floor;

    private iBeacon() {}

    public iBeacon(int id, String name, String mac, double longitude, double latitude, int floor) {
        this.id = id;
        this.name = name;
        this.mac = mac;
        this.longitude = longitude;
        this.latitude = latitude;
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getFloor() {
        return floor;
    }
}
