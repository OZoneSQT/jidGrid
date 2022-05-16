package dk.seahawk.jidgrid.algorithm;

import android.location.Location;

import dk.seahawk.jidgrid.algorithm.gridcalc.Field;
import dk.seahawk.jidgrid.algorithm.gridcalc.MaidenheadLatitude;
import dk.seahawk.jidgrid.algorithm.gridcalc.MaidenheadLongitude;
import dk.seahawk.jidgrid.algorithm.gridcalc.Square;
import dk.seahawk.jidgrid.algorithm.gridcalc.SubSquare;


// https://en.wikipedia.org/wiki/Maidenhead_Locator_System
public class GridAlgorithm implements GridAlgorithmInterface {
    private double longitude, latitude, altitude;
    private int longitudeField, latitudeField, longitudeSquare, latitudeSquare, longitudeSubSquare, latitudeSubSquare;
    private Field field;
    private Square square;
    private SubSquare subSquare;
    private MaidenheadLongitude maidenheadLongitude;
    private MaidenheadLatitude maidenheadLatitude;

    public GridAlgorithm() {
        field = new Field();
        square = new Square();
        subSquare = new SubSquare();
        maidenheadLongitude = new MaidenheadLongitude();
        maidenheadLatitude = new MaidenheadLatitude();
    }

    public String getGridLocation(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.altitude = location.getAltitude();
        parseLocation();

        return getGridSquares();
    }

    @Override
    public String getGridLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        parseLocation();

        return getGridSquares();
    }

    // https://www.dxzone.com/grid-square-locator-system-explained/
    private void parseLocation() {
        // Field
        this.longitudeField = maidenheadLongitude.calcLongitudeField(longitude);
        this.latitudeField = maidenheadLatitude.calcLatitudeField(latitude);
        // Square
        this.longitudeSquare = maidenheadLongitude.calcLongitudeSquare();
        this.latitudeSquare = maidenheadLatitude.calcLatitudeSquare();
        // SubSquare
        this.longitudeSubSquare = maidenheadLongitude.calcLongitudeSubSquare();
        this.latitudeSubSquare = maidenheadLatitude.calcLatitudeSubSquare();
    }

    private String getGridSquares() {
        return field.getField(longitudeField, latitudeField) +
                square.getSquare(longitudeSquare, latitudeSquare) +
                subSquare.getSubSquare(longitudeSubSquare, latitudeSubSquare);
    }

    public String getAltitude() {
        return String.valueOf(altitude);
    }

}
