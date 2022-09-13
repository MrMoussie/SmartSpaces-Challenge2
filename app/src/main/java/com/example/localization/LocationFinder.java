package com.example.localization;

import java.util.ArrayList;

public class LocationFinder {

    ArrayList<iBeacon> connectedBeacons;
    int myFloor = 0;

    final double stepSize = 0.00001;

    public double calculateDistance(Location loc1, Location loc2){
        //calculate distance between two coordinates
        final double R = 6371000;
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double latDiff = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double longDiff = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());

//        double a = Math.sin(latDiff/2) * Math.sin(latDiff/2) + Math.cos(lat1) * Math.cos(lat2) *
//                Math.sin(longDiff/2) * Math.sin(longDiff/2);

        double a = Math.pow(Math.sin(latDiff / 2), 2) + Math.pow(Math.sin(longDiff / 2), 2) *
                Math.cos(lat1) * Math.cos(lat2);

//        double dLat = loc2.getLatitude() * (Math.PI / 180) - loc1.getLatitude() * (Math.PI / 180);
//        double dLon = loc2.getLongitude() * (Math.PI / 180) - loc1.getLongitude() * (Math.PI / 180);
//        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                Math.cos(loc1.getLatitude() * Math.PI / 180) * Math.cos(loc2.getLatitude() * Math.PI / 180) +
//                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    private double calculateDistanceCircle(Location loc1, Location loc2, double radius){
        double distance = calculateDistance(loc1, loc2);
        return distance - radius;
    }

    private Location averageLocation(ArrayList<iBeacon> beacons) {    // arrayList of active beacons
        Location thisLocation = new Location();
        double avgLat = 0;
        double avgLon = 0;
        int N = beacons.size();

        for(iBeacon beacon: beacons){
            avgLat += beacon.getLocation().getLatitude();
            avgLon += beacon.getLocation().getLongitude();
        }
        avgLat/=N;
        avgLon/=N;
        thisLocation.setLatitude(avgLat);
        thisLocation.setLongitude(avgLon);

        return thisLocation;
    }

    private double calculateError(Location location, ArrayList<iBeacon> beacons) {
        double error = 0.0;
        int N = beacons.size();
        for(iBeacon beacon: beacons){
            double distance = calculateDistanceCircle(location, beacon.getLocation(), beacon.getDistance());
            error += distance*distance;
        }
        error/= N;
        return error;
    }

    private Location compareNeighbours(Location location, double error, ArrayList<iBeacon> beacons){
        //calculate position of 4 neighbours using stepSize
        Location NeighbourNorth = new Location(location.getLongitude(), location.getLatitude()+stepSize);
        Location NeighbourSouth = new Location(location.getLongitude(), location.getLatitude()-stepSize);
        Location NeighbourEast = new Location(location.getLongitude()+stepSize, location.getLatitude());
        Location NeighbourWest = new Location(location.getLongitude()-stepSize, location.getLatitude());

        //calculate the four errors
        double errorNorth = calculateError(NeighbourNorth, beacons);
        double errorSouth = calculateError(NeighbourSouth, beacons);
        double errorEast = calculateError(NeighbourEast, beacons);
        double errorWest = calculateError(NeighbourWest, beacons);
        ArrayList<Double> errorList = new ArrayList<Double>();
        errorList.add(errorNorth);
        errorList.add(errorSouth);
        errorList.add(errorEast);
        errorList.add(errorWest);
        errorList.add(error);

    }

    private int findFloor(ArrayList<iBeacon> beacons){
        return 0;
    }

    public Location optimisation(ArrayList<iBeacon> beacons) {
        //Fill list with new beacons
        connectedBeacons = beacons;

        //Find on which floor you are

        //Remove all other floors

        //find average location of beacons
        Location lastLocation = averageLocation(beacons);
        double lastError = calculateError(lastLocation, beacons);

        //check neighbours of starting location error

        //move to lowest error => check those neighbours

        //if all neighbours higher error return that location

        return null;
    }

    //TODO: figure out the frequency of beacon probing
    //TODO: how are we going to fill the beacon list

}
