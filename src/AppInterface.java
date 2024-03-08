import java.util.Scanner;

public class AppInterface {
    // Scanner construction
    private static Scanner input = new Scanner(System.in);

    // Make a graph, PLEASE EDIT THE FILE PATH TO BE YOUR TEXT.TXT FILE
    private static Graph graph = new Graph(
            "C:\\Users\\GAMER\\OneDrive\\University\\Year 4\\Semester 2\\CPCS405 Software\\Projects\\Geolocation\\src\\text.txt");


    public static void main(String[] args) {
        

        // User input for display
        while (true) {
            // Initial message printed
            System.out.print("""
                    Available choices:
                    \t(1) Display all categories
                    \t(2) Search the graph for places based on their categories
                    \t(3) Display the reviews of a place
                    \t(4) Calculate the path between two nodes
                    \t(5) Exit the program
                    Enter choice number:\s""");
            // Take user input
            int userChoice = input.nextInt();

            switch (userChoice) {
                case 1: // (1) Display all categories
                    Category c = new Category(null, null);
                    c.printAllCategories();
                    break;
                case 2: // (2) Search the graph for places based on their categories
                    findNodeOfPlace();
                    break;
                case 3: // (3) Display the reviews of a place
                    findPlaces();
                    break;
                case 4: // (4) Calculate the path between two nodes
                    calcTrip();
                    break;
                case 5:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Wrong choice, please enter a number from 1 to 5.\n\n");
                    break;
            }
        }
    }

    private static void findPlaces() {
        System.out.print("Enter place id: ");
        String placeId = input.next();
        boolean choice;
        while (true) {
            System.out.print("Do you want to display the reviews backwards (from newest to oldest) [y/n]: ");
            String backward = input.next();
            if (backward.equals("y")) {
                choice = true;
                break;
            }
            else if (backward.equals("n")) {
                choice = false;
                break;
            }
            else {
                System.out.println("Wrong choice, please try again.");
            }
        }
        graph.printReviews(placeId, choice);
    }

    private static void findNodeOfPlace() {
        System.out.print("Enter category id: ");
        String userCategoryId = input.next();
        graph.printPlaces(userCategoryId);
    }

    private static void calcTrip() {
        // Get starting node
        System.out.print("Enter starting node id: ");
        String startingNode = input.next();
        // Get destination node
        System.out.print("Enter destination node id: ");
        String destinationNode = input.next();
        // Calculate the trip given the starting node and destination node
        Trip trip = graph.calcTrip(startingNode, destinationNode);
        trip.print(); // Print the path
    }
}
