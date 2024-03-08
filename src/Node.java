import java.util.LinkedHashSet;

public class Node {
    private String id;
    private double latitude;
    private double longitude;
    // Static LinkedHashSet, no duplicates but maintains insertion order
    private LinkedHashSet<Place> places = new LinkedHashSet<>();

    // Constructor
    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Adds a place
    public void addPlace(Place place) {
        places.add(place);
    }

    // Prints places
    public void printPlaces(String categoryId) {
        for (Place place : places) {
            if (place.hasCategory(categoryId))
                System.out.printf("%s has %s (placeId=%s)\n", this.id, place.getName(), place.getId());
        }
    }

    public void printReviews(String placeId, boolean backward) {
        for (Place place : places) {
            if (place.getId().equals(placeId)) { // Use .equals() for string comparison
                place.printReviews(backward);
            }
        }
    }


    // Calculates the distance between 2 nodes given only 1 node as a parameter
    public double calcDistance(Node otherNode) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(this.latitude - otherNode.latitude);
        double lonDistance = Math.toRadians(this.longitude - otherNode.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(otherNode.latitude)) *
                Math.cos(Math.toRadians(this.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    // Calculates the distance between 2 nodes given both nodes as a parameter
    public static double calcDistance(Node start, Node end) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(end.latitude - start.latitude);
        double lonDistance = Math.toRadians(end.longitude - start.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.latitude)) *
                Math.cos(Math.toRadians(end.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    @Override
    public String toString(){
        return this.id + " : " + this.latitude + ", " + this.longitude + " : " + this.places;
    }

    // =============SETTERS AND GETTERS=============
    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}