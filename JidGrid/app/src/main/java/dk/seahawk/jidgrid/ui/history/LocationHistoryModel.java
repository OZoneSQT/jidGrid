package dk.seahawk.jidgrid.ui.history;

import java.util.ArrayList;
import java.util.List;

import dk.seahawk.jidgrid.MainActivity;

public class LocationHistoryModel implements MainActivity.ILocationHistory {

    public static List<PlaceholderItem> items = new ArrayList<>();

    public LocationHistoryModel() {
        items = getHistoryList();
    }

    /**
     * Add elements to list
     * @param item
     */
    public void addItemToList(PlaceholderItem item) {
        items.add(item);
        addHistoryList(items);
    }

    @Override
    public void addHistoryList(List<PlaceholderItem> placeholderItemList) {
        items = placeholderItemList;
    }

    @Override
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