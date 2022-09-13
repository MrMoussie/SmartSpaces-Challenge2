package com.example.localization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LocationFinder {

    ArrayList<iBeacon> connectedBeacons;
    int myFloor = 0;
    Location nextLocation;

    final double stepSize = 0.00001;

    /**
     * Calculates the distance between two coordinate pairs
     * @param loc1 coordinate pair of the first beacon
     * @param loc2 coordinate pair of the second beacon
     * @return distance between two beacons
     */
    public double calculateDistance(Location loc1, Location loc2){
        //calculate distance between two coordinates
        final double R = 6371000;
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double latDiff = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double longDiff = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());

        double a = Math.pow(Math.sin(latDiff / 2), 2) + Math.pow(Math.sin(longDiff / 2), 2) *
                Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    private double calculateDistanceCircle(Location loc1, Location loc2, double radius){
        double distance = calculateDistance(loc1, loc2);
        return distance - radius;
    }

    /**
     * Calculate the average location using the list with active beacons
     * @param beacons ArrayList of active beacons
     * @return average location derived from the list of active beacons
     */
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

    /**
     * Method for calculating the error between the active beacons
     * @param location
     * @param beacons
     * @return
     */
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

    /**
     * Method for determining, where should the localization be updated
     * @param location coordinate pair of the beacon
     * @param error value of the error calculated
     * @param beacons list of currently available beacons
     * @return location where the localization should be updated
     */
    private Location compareNeighbours(Location location, double error, ArrayList<iBeacon> beacons){
        HashMap<Double, Location> beaconList = new HashMap<Double, Location>();
        ArrayList<Double> errorList = new ArrayList<Double>();

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

        // adds the potential neighbours with their errors to a hashmap (creates a pair of (errorValue,Neighbour))
        beaconList.put(errorNorth, NeighbourNorth);
        beaconList.put(errorSouth, NeighbourSouth);
        beaconList.put(errorEast, NeighbourEast);
        beaconList.put(errorWest, NeighbourWest);

        // adds the errors to the arrayList
        errorList.add(errorNorth);
        errorList.add(errorSouth);
        errorList.add(errorEast);
        errorList.add(errorWest);
        errorList.add(error);

        // gets the smallest error number
        double smallestValue = Collections.min(errorList);

        // extracts the beacon with the smallest error value
        Location nextLocation = beaconList.get(smallestValue);
        this.nextLocation = nextLocation;
        // clears the arraylist and hashmap
        beaconList.clear();
        errorList.clear();

        // return the next location
        return nextLocation;
    }

    // TODO: gotta check for the floor in the excel sheet
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
}
