import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeHandler {
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public EmployeeHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
    }

    public void handleUserOperations() throws IOException {
        while (true) {
            String menuOption = in.readLine(); // Read the menu from the server
            System.out.println(menuOption); // Display the menu option received from the server

            // Prompt user to enter their choice
            System.out.print("Enter your choice: ");
            String choice = stdIn.readLine();
            out.println(choice); // Send user choice back to server

            switch (choice) {
                case "1":
                    viewNextDayRecommendation();
                    break;
                case "2":
                    giveFeedback();
                    break;
                case "3":
                    selectMenuItems();
                    break;
                case "4":
                    viewFoodMenu();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                    break;
            }
        }
    }

    private void selectMenuItems() throws IOException {
        String response;
        while (!(response = in.readLine()).equals("END_OF_MESSAGE")) {
            System.out.println(response);
        }

        selectMealItems("breakfast", 2);
        selectMealItems("lunch", 2);
        selectMealItems("dinner", 2);
        String endMessage = in.readLine();
        System.out.println("Employee Selection Completed");
    }

    private void selectMealItems(String mealType, int itemCount) throws IOException {
        if (itemCount > 0) {
            System.out.println("Select " + itemCount + " items for " + mealType + " by entering their IDs:");
            for (int i = 0; i < itemCount; i++) {
                String itemId = stdIn.readLine();
                out.println(itemId);
            }
        }
    }

    private void viewNextDayRecommendation() throws IOException {
        String response = readServerResponse();
        System.out.println("Next Day Recommendations:");
        System.out.println(response);
    }

    private void viewFoodMenu() throws IOException {
        String response = readServerResponse();
        System.out.println("Food Menu: " + response);
    }

    private void giveFeedback() throws IOException {
        System.out.print("Enter the ID of the menu item you want to give feedback for: ");
        String itemID = stdIn.readLine();
        out.println(itemID);

        System.out.print("Enter your feedback for item ID " + itemID + ": ");
        String feedback = stdIn.readLine();
        out.println(feedback);

        System.out.print("Please provide rating (1-5): ");
        String rating = stdIn.readLine();
        out.println(rating);

        String response = readServerResponse();
        System.out.println(response);
    }

    private String readServerResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            if (line.equals("END_OF_MESSAGE")) {
                break;
            }
            response.append(line).append("\n");
        }
        return response.toString().trim(); // Trim to remove extra new line at the end
    }
}
