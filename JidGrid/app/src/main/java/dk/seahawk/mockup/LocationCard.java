package dk.seahawk.mockup;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.TimeZone;

import dk.seahawk.jidgrid.algorithm.GridAlgorithm;
import dk.seahawk.jidgrid.algorithm.GridAlgorithmInterface;


public class LocationCard extends LiveData<List<LocationCard>> {
    private GridAlgorithmInterface gridAlgorithmInterface = new GridAlgorithm();

    // Null pattern
    private String gridID = "UNKNOWN";
    private double longitude = 0.000000;
    private double latitude = 0.000000;
    private double altitude = 00.0;

    private String utcTime;
    private String localTime = "UNKNOWN";

    public LocationCard() {}

    public LocationCard(String gridID, double longitude, double latitude, double altitude) {
        this.gridID = gridID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;

        this.utcTime = String.valueOf(TimeZone.getTimeZone("UTC"));
        this.localTime = String.valueOf(TimeZone.getDefault());
    }

    public LocationCard(LocationCard locationCard) {
        this.gridID = locationCard.getGridID();
        this.longitude = locationCard.getLongitudeNum();
        this.latitude = locationCard.getLatitudeNum();
        this.altitude = locationCard.getAltitudeNum();
        this.utcTime = locationCard.getUtcTime();
        this.localTime = locationCard.getLocalTime();
    }

    public LocationCard(double longitude, double latitude, double altitude) {
        this.gridID = gridAlgorithmInterface.getGridLocation(longitude, latitude);
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;

        this.utcTime = String.valueOf(TimeZone.getTimeZone("UTC"));
        this.localTime = String.valueOf(TimeZone.getDefault());
    }

    public LocationCard(String gridID, double longitude, double latitude, double altitude, String utcTime, String localTime) {
        this.gridID = gridID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.utcTime = utcTime;
        this.localTime = localTime;
    }

    public String getGridID() {
        return gridID;
    }

    public double getLongitudeNum() {
        return longitude;
    }

    public String getLongitude() {
        return convert(longitude);
    }

    public double getLatitudeNum() {
        return latitude;
    }

    public String getLatitude() {
        return convert(latitude);
    }

    public double getAltitudeNum() {
        return altitude;
    }

    public String getAltitude() {
        return convert(altitude);
    }

    public String getUtcTime() {
        return utcTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    private String convert(double value) {
        return String.valueOf(value);
    }

}
