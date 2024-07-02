import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChefHandler {
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public ChefHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
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
                    viewFoodMenu();
                    break;
                case "2":
                    rollOutNextDayMenu();
                    break;
                case "3":
                    viewEmployeeSelection();
                    break;
                case "4":
                    viewRecommendations();
                    break;
                case "5":
                    viewDiscardedItems();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void viewDiscardedItems() throws IOException {
        String response = ClientUtils.readServerResponse();
        System.out.println(response);

        System.out.println("choose an option from the following:");
        System.out.println("1.) Delete an item from discarded list" + "\n" +
                "2) Get employee feedback for an item");

        int selectedOption = Integer.parseInt(stdIn.readLine());

        if(selectedOption == 1){
            out.println("1");
            System.out.println("Enter the ID of item to delete from menu");
            out.println(stdIn.readLine());
            System.out.println("item has been deleted");
            viewDiscardedItems();
        }
        else if (selectedOption == 2){
            out.println("2");
            System.out.println("Enter Item ID to roll out feedback for");
            out.println(stdIn.readLine());
            System.out.println("roll out " + in.readLine());
            viewDiscardedItems();

        }
        else if (selectedOption == 3){
            System.out.println("Returning back to menu...");
        }
        else{
            System.out.println("Enter valid option");
            viewDiscardedItems();
        }
    }

    private void viewRecommendations() throws IOException {
        String response = ClientUtils.readServerResponse();
        System.out.println(response);
    }

    private void viewFoodMenu() throws IOException {
        String response = ClientUtils.readServerResponse();
        System.out.println("Food Menu: " + response);
    }

//    private String readServerResponse() throws IOException {
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = in.readLine()) != null) {
//            if (line.equals("END_OF_MESSAGE")) {
//                break;
//            }
//            response.append(line).append("\n");
//        }
//        return response.toString().trim();
//    }

    private void rollOutNextDayMenu() throws IOException {

        // Prompt for number of items for each meal
        System.out.print("Enter number of items for Breakfast: ");
        int breakfastItems = Integer.parseInt(stdIn.readLine());
        out.println(breakfastItems);
        StringBuilder breakfast = new StringBuilder();
        for (int i = 0; i < breakfastItems; i++) {
            System.out.print("Enter Breakfast item ID: ");
            breakfast.append(stdIn.readLine()).append(",");
        }
        out.println(breakfast.toString());

        System.out.print("Enter number of items for Lunch: ");
        int lunchItems = Integer.parseInt(stdIn.readLine());
        out.println(lunchItems);
        StringBuilder lunch = new StringBuilder();
        for (int i = 0; i < lunchItems; i++) {
            System.out.print("Enter Lunch item ID: ");
            lunch.append(stdIn.readLine()).append(",");
        }
        out.println(lunch.toString());

        System.out.print("Enter number of items for Dinner: ");
        int dinnerItems = Integer.parseInt(stdIn.readLine());
        out.println(dinnerItems);
        StringBuilder dinner = new StringBuilder();
        for (int i = 0; i < dinnerItems; i++) {
            System.out.print("Enter Dinner item ID: ");
            dinner.append(stdIn.readLine()).append(",");
        }
        out.println(dinner.toString());

        String response = in.readLine();
        System.out.println("Roll Out Menu Response: " + response);
    }

    private void viewEmployeeSelection() throws IOException {
        String response = ClientUtils.readServerResponse();
        System.out.println("Daily Employee Selection Report:");
        System.out.println(response);
    }
}