package com.example.localization;

/**
 * This class represents an iBeacon object from the Excel document
 */
public class iBeacon {
    private int id;
    private String name;
    private String mac;
    private double longitude;
    private double latitude;
    private int floor;

    /**
     * Disallowing empty constructor usage.
     */
    private iBeacon() {}

    /**
     * This constructor initializes a beacon with the given properties.
     * @param id device_id
     * @param name device_name
     * @param mac mac_address
     * @param longitude longitude
     * @param latitude latitude
     * @param floor floor number
     */
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
