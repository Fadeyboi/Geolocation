import java.util.*;
import java.io.*;

public class Graph {
    // Key is a String, will be ID in this case, value is Node
    private HashMap<String, Node> nodes = new HashMap<>();
    // Sorted set
    private Map<Node, TreeSet<Node>> adjacentNodes = new HashMap<>();

    // Constructors
    public Graph(String filePath) {
        // Get the file and scan it, so I can use the input
        File inputFile = new File(filePath);
        HashSet<Category> tempCategories = new HashSet<>();
        Map<Node, Place> PlacesInNode = new HashMap<>();

        // Exception handling for files
        try (Scanner input = new Scanner(inputFile)) {
            String currentLine = input.nextLine(); // Get the next line
            while (input.hasNextLine()) {
                switch (currentLine) {
                    case "//Category Definitions": // If it's Categories, then add them
                        currentLine = input.nextLine(); // Move the cursor
                        while (!currentLine.matches("//.*")) { // According to the text
                            // formatting, this is the breaker
                            String[] currentLineArr = currentLine.split(","); // Split the line into an array
                            new Category(currentLineArr[0], currentLineArr[1]); // Add the categories
                            tempCategories.add(new Category(currentLineArr[0], currentLineArr[1]));
                            if (input.hasNextLine())
                                currentLine = input.nextLine(); // Move the cursor
                            else
                                break;
                        }
                        break;
                    case "//Node Definitions": // If it's Node, then add them
                        currentLine = input.nextLine(); // Move the cursor
                        while (!currentLine.matches("//.*")) { // According to the text
                            // formatting, this is the breaker
                            String[] currentLineArr = currentLine.split(","); // Split the line into an array
                            nodes.put(currentLineArr[0], new Node(currentLineArr[0],
                                    Double.parseDouble(currentLineArr[1]), Double.parseDouble(currentLineArr[2]))); // Add
                            // the
                            // nodes
                            if (input.hasNextLine())
                                currentLine = input.nextLine(); // Move the cursor
                            else
                                break;
                        }
                        break;
                    case "//Adjacent Nodes": // If it's adjacent nodes, then add them
                        currentLine = input.nextLine(); // Move the cursor
                        while (!currentLine.matches("//.*")) { // According to the text
                            // formatting, this is the breaker
                            String[] currentLineArr = currentLine.split("->"); // Split the line into an array
                            Node keyNode = nodes.get(currentLineArr[0]); // Get the keynode
                            // Override compare
                            TreeSet<Node> nodeSet = adjacentNodes.computeIfAbsent(keyNode, n -> new TreeSet<>((o1, o2) -> {
                                double distance1 = Node.calcDistance(n, o1); // Compare distance between
                                // keynode and object 1
                                double distance2 = Node.calcDistance(n, o2); // Compare distance between
                                // keynode and object 2
                                return Double.compare(distance1, distance2);
                            })); // Make a TreeSet and check if the node
                            // already exists in adjacentNodes
                            // If the node doesn't exist, then we'll create it, otherwise we just
                            // add it to adjacentNodes
                            // After we create it, we add it to adjacentNodes
                            nodeSet.add(nodes.get(currentLineArr[1]));
                            if (input.hasNextLine())
                                currentLine = input.nextLine(); // Move the cursor
                            else
                                break;
                        }
                        break;

                    case "//Places at nodes": // If it was Places at nodes, add the place to the node
                        currentLine = input.nextLine(); // Move the cursor
                        while (!currentLine.matches("//.*")) { // According to the text
                            // formatting, this is the breaker
                            String[] currentLineNodeId = currentLine.split("->"); // Split the line into an array to get
                            // the id
                            String[] currentLineArr = currentLineNodeId[1].split(","); // Split the line into an array
                            Node currentNode = nodes.get(currentLineNodeId[0]); // Save the current node to make it
                            // easier to work on it
                            Place place = new Place(currentLineArr[0], currentLineArr[1]); // Create a place object to store it

                            String[] currentLineCategory = currentLineArr[2].split(";"); // Split the line into an array

                            Category[] categoryArr = new Category[currentLineCategory.length];

                            // Iterate through the currentLineCategory array in case there are multiple
                            // categories and add them to categoryArr
                            for (int i = 0; i < currentLineCategory.length; i++) {
                                for (Category category : tempCategories) {
                                    if (category.getId().equals(currentLineCategory[i])) {
                                        categoryArr[i] = category;
                                    }
                                }
                            }

                            for (Category category : categoryArr) {
                                place.addCategory(category);
                            }
                            currentNode.addPlace(place); // Finally, add place to the current node
                            PlacesInNode.put(currentNode, place);
                            if (input.hasNextLine())
                                currentLine = input.nextLine(); // Move the cursor
                            else
                                break;
                        }
                        break;

                    case "//Place Reviews": // If it was Place Reviews, then add the review to the place
                        currentLine = input.nextLine(); // Move the cursor
                        while (!currentLine.matches("//.*")) { // According to the text formatting, this is the breaker
                            String[] currentLinePlaceId = currentLine.split("->"); // Split the line into an array to get the id
                            String[] currentLineArr = currentLinePlaceId[1].split(","); // Split the line into an array
                            Review review = new Review(currentLineArr[0], currentLineArr[1], Integer.parseInt(currentLineArr[2])); // Make the review object
                            for (Map.Entry<String, Node> entry : nodes.entrySet()) {
                                for (Map.Entry<Node, Place> entry2 : PlacesInNode.entrySet()) {
                                    if (entry.getValue().equals(entry2.getKey())) {
                                        if (entry2.getValue().getId().equals(currentLinePlaceId[0])) {
                                            entry2.getValue().addReview(review);
                                        }
                                    }
                                }
                            }
                            if (input.hasNextLine())
                                currentLine = input.nextLine(); // Move the cursor
                            else
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("text file not found, please add the file and try again. " + e);
        } catch (
                IndexOutOfBoundsException e) {
            System.out.println("There was an error with the file formatting, please check the formatting. " + e);
        } catch (
                Exception e) {
            System.out.println("Unknown error occurred, check terminal and solve. " + e);
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
        boolean found = dfs(startNode, endNode, pathStack, isVisited);

        // If the endNode was found, then add pathStack to trip
        if (found) {
            for (Node node : pathStack) {
                trip.addNode(node);
            }
        }
        return trip;
    }

    // Helper method for calcTrip
    private boolean dfs(Node current, Node endNode, Stack<Node> pathStack, Set<Node> isVisited) {
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
                boolean found = dfs(node, endNode, pathStack, isVisited); // Recursive search
                if (found) {
                    return true; // Path to end node found in recursion
                }
            }
        }

        // If no path found from current node, backtrack and pop nodes from stack
        pathStack.pop();
        return false;
    }

    // System.out.printf("%s has %s (placeId=%s)\n", node.getId(), place.getName(), place.getId());
// PrintPlaces method that prints places that have a specific category
    public void printPlaces(String categoryId) {
        // We have to use Entry to unpack the HashMap
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue(); // Get the node only
            node.printPlaces(categoryId);
        }
    }

    // PrintReviews method that just prints the reviews of a specific place
    public void printReviews(String placeId, boolean backward) {
        // We have to use Entry to unpack the HashMap
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue(); // Get the node only\
            node.printReviews(placeId, backward);
        }
    }
}