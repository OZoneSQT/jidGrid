package dk.seahawk.jidgrid.algorithm;

import android.location.Location;

import dk.seahawk.mockup.LocationCard;

public interface GridAlgorithmInterface {

    /**
     * Get grid location in maidenhead-grid-square-locator-format
     * @param location
     * @return
     */
    String getGridLocation(Location location);

    /**
     * Get grid location in maidenhead-grid-square-locator-format
     * @param longitude
     * @param latitude
     * @return
     */
    String getGridLocation(double longitude, double latitude);

    /**
     * Get altitude, from gps
     * @return
     */
    String getAltitude();

    /**
     * Test method
     * @param muckUpLocation
     * @return
     */
    String getGridLocationTestMethod(LocationCard muckUpLocation);
}
