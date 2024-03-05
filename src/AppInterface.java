import java.io.FileNotFoundException;
import java.util.Scanner;

public class AppInterface {
    public static void main(String[] args) throws FileNotFoundException {
        // Scanner construction
        Scanner input = new Scanner(System.in);

        // Make a graph, PLEASE EDIT THE FILE PATH TO BE YOUR TEXT.TXT FILE
        Graph graph = new Graph(
                "C:\\Users\\GAMER\\OneDrive\\University\\Year 4\\Semester 2\\CPCS405 Software\\Projects\\Geolocation\\src\\text.txt");

        // User input for display
        while (true) {
            // Initial message printed
            System.out.print("Available choices:\n\t(1) Display all categories\n\t(2) Search the graph for " +
                    "places based on their categories\n\t(3) Display the reviews of a place\n\t(4) Calculate the path between two nodes"
                    +
                    "\n\t(5) Exit the program\nEnter choice number: ");
            // Take user input
            int userChoice = input.nextInt();

            switch (userChoice) {
                case 1: // (1) Display all categories
                    Category c = new Category();
                    c.printAllCategories();
                    break;
                case 2: // (2) Search the graph for places based on their categories
                    System.out.print("Enter category id: ");
                    String userCategoryId = input.next();
                    graph.printPlaces(userCategoryId);
                    break;
                case 3: // (3) Display the reviews of a place
                    System.out.print("Enter place id: ");
                    String placeId = input.next();
                    System.out.print("Do you want to display the reviews backwards (from newest to oldest) [y/n]: ");
                    String backward = input.next();
                    boolean choice;
                    if (backward == "y")
                        choice = true;
                    else
                        choice = false;
                    graph.printReviews(placeId, choice);
                    break;
                case 4: // (4) Calculate the path between two nodes
                    // Get starting node
                    System.out.print("Enter starting node id: ");
                    String startingNode = input.next();
                    // Get destination node
                    System.out.print("Enter destination node id: ");
                    String destinationNode = input.next();
                    // Calculate the trip given the starting node and destination node
                    Trip trip = graph.calcTrip(startingNode, destinationNode);
                    trip.print(); // Print the path
                    break;
                case 5:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Wrong choice, please enter a number from 1 to 4.\n\n");
                    break;
            }
        }
    }

    private void findPlaces() {

    }

    private void findNodeOfPlace() {

    }

    private void calcTrip() {

    }
}
