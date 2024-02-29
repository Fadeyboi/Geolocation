import java.util.LinkedHashSet;

public class Trip {
    // Static LinkedHashSet, no duplicates but maintains insertion order
    private LinkedHashSet<Node> path = new LinkedHashSet<>();

    // Constructor
    public Trip() {
    }

    // Adds a node
    public void addNode(Node node) {
        path.add(node);
    }

    // Prints node
    public void print() {
        for (Node node : path) {
            System.out.printf("<%s, %3.6f, %3.6f>\n", node.getId(), node.getLatitude(), node.getLongitude());
        }

    }

}