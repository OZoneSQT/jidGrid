package dk.seahawk.jidgrid.util;

import java.util.ArrayList;
import java.util.List;

public class LocationHistory {

    public static List<PlaceholderItem> items = new ArrayList<>();
    private LocationHistory instance = null;

    private LocationHistory() {}

    public LocationHistory getInstances(){
        if(instance==null) instance = new LocationHistory();
        return instance;
    }

    /**
     * Add elements to list
     * @param item
     */
    public void addItemToList(PlaceholderItem item) {
        items.add(item);
    }

    public void addHistoryList(List<PlaceholderItem> placeholderItemList) {
        items = placeholderItemList;
    }

    public List<PlaceholderItem> getHistoryList() {
        return items;
    }

    public static class PlaceholderItem {
        public final String jidId, locTime, utcTime, lat, lon, alt;

        public PlaceholderItem(String jidId, String locTime, String utcTime, String latitude, String longitude, String altitude) {
            this.jidId = jidId;
            this.locTime = locTime;
            this.utcTime = utcTime;
            this.lat = latitude;
            this.lon = longitude;
            this.alt = altitude;
        }

        public String getJidId() {
            return jidId;
        }

        public String getLocTime() {
            return locTime;
        }

        public String getUtcTime() {
            return utcTime;
        }

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public String getAlt() {
            return alt;
        }

    }

}