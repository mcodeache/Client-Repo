import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class EmployeeHandler {
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;
    private Map<String, Runnable> menuOptions;

    public EmployeeHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
        initializeMenuOptions();
    }

    private void initializeMenuOptions() {
        menuOptions = new HashMap<>();
        menuOptions.put("1", this::viewNextDayRecommendation);
        menuOptions.put("2", this::giveFeedback);
        menuOptions.put("3", this::selectMenuItems);
        menuOptions.put("4", this::viewFoodMenu);
        menuOptions.put("5", this::showNotification);
        menuOptions.put("6", () -> {
            System.out.println("Exiting...");
        });
    }

    public void handleUserOperations() {
        while (true) {
            try {
                String menuOption = in.readLine();
                System.out.println(menuOption);
                System.out.print("Enter your choice: ");
                String choice = stdIn.readLine();
                out.println(choice);

                Runnable action = menuOptions.get(choice);
                if (action != null) {
                    action.run();
                    if ("6".equals(choice)) {
                        return;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                }
            } catch (IOException e) {
                System.err.println("Error handling user operations: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showNotification() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println("Notifications :");
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Error showing notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void selectMenuItems() {
        try {
            String response;
            while (!(response = in.readLine()).equals("END_OF_MESSAGE")) {
                System.out.println(response);
            }
            int itemCount = 1;
            selectMealItems("breakfast", itemCount);
            selectMealItems("lunch", itemCount);
            selectMealItems("dinner", itemCount);
            String endMessage = in.readLine();
            System.out.println("Employee Selection Completed");
        } catch (IOException e) {
            System.err.println("Error selecting menu items: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void selectMealItems(String mealType, int itemCount) {
        if (itemCount > 0) {
            System.out.println("Select " + itemCount + " items for " + mealType + " by entering their IDs:");
            try {
                for (int i = 0; i < itemCount; i++) {
                    String itemId = stdIn.readLine();
                    out.println(itemId);
                }
            } catch (IOException e) {
                System.err.println("Error selecting meal items for " + mealType + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void viewNextDayRecommendation() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println("Next Day Recommendations:");
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Error viewing next day recommendation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void viewFoodMenu() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println("Food Menu: " + response);
        } catch (IOException e) {
            System.err.println("Error viewing food menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void giveFeedback() {
        try {
            System.out.print("Enter the ID of the menu item you want to give feedback for: ");
            String itemID = stdIn.readLine();
            out.println(itemID);

            System.out.print("Enter your feedback for item ID " + itemID + ": ");
            String feedback = stdIn.readLine();
            out.println(feedback);

            System.out.print("Please provide rating (1-5): ");
            String rating = stdIn.readLine();
            out.println(rating);

            String response = ClientUtils.readServerResponse();
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Error giving feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
