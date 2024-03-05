import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.io.*;

public class Graph {
    // Key is a String, will be ID in this case, value is Node
    private HashMap<String, Node> nodes = new HashMap<>();
    // Sorted set
    private Map<Node, TreeSet<Node>> adjacentNodes = new HashMap<>();

    // Constructors
    public Graph(String filePath) {
        // Get the file and scan it so I can use the input
        File inputFile = new File(filePath);

        // Exception handling for files
        try (Scanner input = new Scanner(inputFile)) {
            if(input.hasNextLine()) {
                // While the file is not empty
                String currentLine = input.nextLine(); // Get the next line
                while (input.hasNext()) {

                    switch (currentLine) {
                        case "//Category Definitions": // If it's Categories, then add them
                            currentLine = input.nextLine(); // Move the cursor
                            while (input.hasNextLine() && !currentLine.matches("//.*") && currentLine != null) { // According to the text
                                // formatting, this is the breaker
                                String currentLineArr[] = currentLine.split(","); // Split the line into an array
                                new Category(currentLineArr[0], currentLineArr[1]); // Add the categories
                                currentLine = input.nextLine(); // Move the cursor
                            }
                            break;
                        case "//Node Definitions": // If it's Node, then add them
                            currentLine = input.nextLine(); // Move the cursor
                            while (input.hasNextLine() && !currentLine.matches("//.*") && currentLine != null) { // According to the text
                                // formatting, this is the breaker
                                String currentLineArr[] = currentLine.split(","); // Split the line into an array
                                nodes.put(currentLineArr[0], new Node(currentLineArr[0],
                                        Double.parseDouble(currentLineArr[1]), Double.parseDouble(currentLineArr[2]))); // Add
                                // the
                                // nodes
                                currentLine = input.nextLine(); // Move the cursor
                            }
                            break;
                        case "//Adjacent Nodes": // If it's adjacent nodes, then add them
                            currentLine = input.nextLine(); // Move the cursor
                            while (input.hasNextLine() && !currentLine.matches("//.*") && currentLine != null) { // According to the text
                                // formatting, this is the breaker
                                String currentLineArr[] = currentLine.split("->"); // Split the line into an array
                                Node keyNode = nodes.get(currentLineArr[0]); // Get the keynode
                                TreeSet<Node> nodeSet = adjacentNodes.get(keyNode); // Make a TreeSet and check if the node
                                // already exists in adjacentNodes
                                if (nodeSet == null) { // If the node doesn't exist, then we'll create it, otherwise we just
                                    // add it to adjacentNodes
                                    nodeSet = new TreeSet<>(new Comparator<Node>() {
                                        @Override // Override compare
                                        public int compare(Node o1, Node o2) {
                                            double distance1 = Node.calcDistance(keyNode, o1); // Compare distance between
                                            // keynode and object 1
                                            double distance2 = Node.calcDistance(keyNode, o2); // Compare distance between
                                            // keynode and object 2
                                            if (distance1 > distance2)
                                                return 1;
                                            else if (distance2 > distance1)
                                                return -1;
                                            else
                                                return 0;
                                        }
                                    });
                                    adjacentNodes.put(keyNode, nodeSet); // After we create it, we add it to adjacentNodes
                                }
                                nodeSet.add(nodes.get(currentLineArr[1]));
                                currentLine = input.nextLine(); // Move the cursor
                            }
                            break;

                        case "//Places at nodes": // If it was Places at nodes, add the place to the node
                            currentLine = input.nextLine(); // Move the cursor
                            while (input.hasNextLine() && !currentLine.matches("//.*") && currentLine != null) { // According to the text
                                // formatting, this is the breaker
                                String currentLineNodeId[] = currentLine.split("->"); // Split the line into an array to get
                                // the id
                                String currentLineArr[] = currentLineNodeId[1].split(","); // Split the line into an array
                                Node currentNode = nodes.get(currentLineNodeId[0]); // Save the current node to make it
                                // easier to work on it
                                Place place = new Place(currentLineArr[0], currentLineArr[1]); // Create a place object to
                                // store it later using ID
                                // and Name
                                String currentLineCategory[] = currentLineArr[2].split(";"); // Split the line into an array

                                // Iterate through the currentLineCategory array incase there are multiple
                                // categories and add them to place
                                for (int i = 0; i < currentLineCategory.length; i++) {
                                    for (Category c : Category.getAllCategories()) {
                                        if(c.getId().equals(currentLineCategory[i])){
                                            place.addCategory(c);
                                        }
                                    }
                                }
                                currentNode.addPlace(place); // Finally, add place to the current node
                                currentLine = input.nextLine(); // Move the cursor
                            }
                            break;

                        case "//Place Reviews": // If it was Place Reviews, then add the review to the place
                            currentLine = input.nextLine(); // Move the cursor
                            while (input.hasNextLine() && !currentLine.matches("//.*") && currentLine != null) { // According to the text
                                // formatting, this is the breaker
                                String currentLinePlaceId[] = currentLine.split("->"); // Split the line into an array to get the id
                                String currentLineArr[] = currentLinePlaceId[1].split(","); // Split the line into an array
                                Review review = new Review(currentLineArr[0], currentLineArr[1],
                                        Integer.parseInt(currentLineArr[2])); // Make the review object
                                for (Map.Entry<String, Node> entry : nodes.entrySet()) {
                                    Node node = entry.getValue(); // Get the node only
                                    for (Place place : node.getPlaces()) { // Iterate through all the places in the node
                                        if (place.getId().equals(currentLinePlaceId[0])) { // If the placeId is equal to the currentLinePlaceId, add the review
                                            place.addReview(review);
                                        }
                                    }
                                }
                                currentLine = input.nextLine(); // Move the cursor
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
          catch (FileNotFoundException e) {
            System.out.println("text file not found, please add the file and try again. " + e);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There was an error with the file formatting, please check the formatting. " + e);
        } catch (Exception e) {
            System.out.println("Unknown error occured, check terminal and solve. " + e);
        }
    }
    

    // Calculates the trip using DFS
    public Trip calcTrip(String startN, String endN) {
        Node startNode = nodes.get(startN); // Starting node
        Node endNode = nodes.get(endN); // Destination
        Trip trip = new Trip(); // The path
        Stack<Node> pathStack = new Stack<>(); // Stack to track the current path
        Set<Node> isVisited = new HashSet<>(); // A set for visited nodes

        // Perform dfs
        boolean found = dfs(startNode, endNode, pathStack, isVisited, trip);

        // If the endNode was found, then add pathStack to trip
        if (found) {
            for (Node node : pathStack) {
                trip.addNode(node);
            }
        }
        return trip;
    }

    // Helper method for calcTrip
    private boolean dfs(Node current, Node endNode, Stack<Node> pathStack, Set<Node> isVisited, Trip trip) {
        // Start search, add current node to pathStack and isVisited
        pathStack.push(current);
        isVisited.add(current);

        // Check if the current node is endNode
        if (current.equals(endNode)) {
            return true; // Found the path
        }

        // Check adjacentNodes for path
        for (Node node : adjacentNodes.get(current)) {
            if (!isVisited.contains(node)) {
                boolean found = dfs(node, endNode, pathStack, isVisited, trip); // Recursive search
                if (found) {
                    return true; // Path to end node found in recursion
                }
            }
        }

        // If no path found from current node, backtrack and pop nodes from stack
        pathStack.pop();
        return false;
    }

    // PrintPlaces method that prints places that have a specific category
    public void printPlaces(String categoryId) {
        int count = 0; // Count checking if we printed anything or not
        // We have to use Entry to unpack the HashMap
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue(); // Get the node only
            for (Place place : node.getPlaces()) { // Iterate through all the places in the node
                if(place.hasCategory(categoryId)){
                    System.out.printf("%s has %s (placeId=%s)\n", node.getId(), place.getName(), place.getId());
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("There are no places with this category.");
        }
    }

    // PrintReviews method that just prints the reviews of a specific place
    public void printReviews(String placeId, boolean backward) {
        int count = 0; // Count checking if we printed anything or not
        // We have to use Entry to unpack the HashMap
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue(); // Get the node only
            for (Place place : node.getPlaces()) { // Iterate through all the places in the node
                if(place.getId().equals(placeId)){
                    count++;
                    if(backward){
                        place.printReviews(backward);
                    }
                    else
                        place.printReviews(!backward);
                }
            }
        }
        if (count == 0) {
            System.out.println("There are no reviews for this place.");
        }
    }

    // =============SETTERS AND GETTERS=============

    /**
     * @return HashMap<String, Node> return the nodes
     */
    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(HashMap<String, Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return Map<Node, TreeSet<Node>> return the adjacentNodes
     */
    public Map<Node, TreeSet<Node>> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * @param adjacentNodes the adjacentNodes to set
     */
    public void setAdjacentNodes(Map<Node, TreeSet<Node>> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

}