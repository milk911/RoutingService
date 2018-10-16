package milk.models;

public class Point {
    String userID;
    String id;
    double lat;
    double lon;
    int count;

    public String getUserID() {
        return userID;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Point(String id, String userID, double lat, double lon, int count) {
        this.userID = userID;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.count = count;
    }

    public Point() {
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return (this.id.equals(point.getId()));
    }

    @Override
    public String toString() {
        return getId();
    }
}
