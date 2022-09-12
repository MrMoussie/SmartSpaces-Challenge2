package com.example.localization;

public class locationFinder {

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        //calculate distance between two coordinates
         final double R = 6378.137;
         double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
         double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
         double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) +
                    Math.sin(dLon/2) * Math.sin(dLon/2);
         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
         double d = R * c;
         return d * 1000;
    }

    private double calculateDistanceCircle(double lat1, double lon1, double lat2, double lon2, double radius){
        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        return distance - radius;
    }

}
