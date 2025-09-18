package com.trulydesignfirm.namaha.util;

public class GeoUtils {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.floor(EARTH_RADIUS_KM * c);
    }

    public static double[] boundingBox(double lat, double lon, double radiusKm) {
        double latDelta = radiusKm / EARTH_RADIUS_KM * (180 / Math.PI);
        double lonDelta = radiusKm / (EARTH_RADIUS_KM * Math.cos(Math.toRadians(lat))) * (180 / Math.PI);
        double minLat = lat - latDelta;
        double maxLat = lat + latDelta;
        double minLon = lon - lonDelta;
        double maxLon = lon + lonDelta;
        return new double[]{minLat, maxLat, minLon, maxLon};
    }
}