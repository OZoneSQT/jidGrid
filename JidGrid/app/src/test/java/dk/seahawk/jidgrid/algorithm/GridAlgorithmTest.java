package dk.seahawk.jidgrid.algorithm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dk.seahawk.mockup.LocationCard;

class GridAlgorithmTest {
    private LocationCard muckUpLocation;
    private GridAlgorithm gridAlgorithm;

    @Test   // Signal Hill, Cape Town - https://www.karhukoti.com/maidenhead-grid-square-locator/?grid=JF96eb
    void getGridLocation() {
        String CALL = "OZ1SQT";
        double longitude = 18.388544;
        double latitude = -33.934562;
        double altitude = 57.3;

        muckUpLocation = new LocationCard(CALL, longitude, latitude, altitude);
        gridAlgorithm = new GridAlgorithm();

        assertEquals("JF96eb", gridAlgorithm.getGridLocationTestMethod(muckUpLocation));
        assertEquals(String.valueOf(altitude), gridAlgorithm.getAltitude());
    }

    @Test   // My QTH - https://www.karhukoti.com/maidenhead-grid-square-locator/?grid=JO45sv
    void getGridLocationA() {
        String CALL = "OZ1SQT";
        double longitude = 9.563663;
        double latitude = 55.887282;
        double altitude = 57.3;

        muckUpLocation = new LocationCard(CALL, longitude, latitude, altitude);
        gridAlgorithm = new GridAlgorithm();

        assertEquals("JO45sv", gridAlgorithm.getGridLocationTestMethod(muckUpLocation));
        assertEquals(String.valueOf(altitude), gridAlgorithm.getAltitude());
    }

    @Test   // VIA, center of E - https://www.karhukoti.com/maidenhead-grid-square-locator/?grid=JO45sv
    void getGridLocationB() {
        String CALL = "OZ1SQT";
        double longitude = 9.8860909;
        double latitude = 55.8719648;
        double altitude = 63.2;

        muckUpLocation = new LocationCard(CALL, longitude, latitude, altitude);
        gridAlgorithm = new GridAlgorithm();

        assertEquals("JO45wu", gridAlgorithm.getGridLocationTestMethod(muckUpLocation));
        assertEquals(String.valueOf(altitude), gridAlgorithm.getAltitude());
    }

    @Test   // Qasigiannguit - https://www.karhukoti.com/maidenhead-grid-square-locator/?grid=GP48jt
    void getGridLocationC() {
        String CALL = "OZ1SQT";
        double longitude = -51.1967887;
        double latitude = 68.8197614;
        double altitude = 26.8;

        muckUpLocation = new LocationCard(CALL, longitude, latitude, altitude);
        gridAlgorithm = new GridAlgorithm();

        assertEquals("GP48jt", gridAlgorithm.getGridLocationTestMethod(muckUpLocation));
        assertEquals(String.valueOf(altitude), gridAlgorithm.getAltitude());
    }

}