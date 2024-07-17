package Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import Utils.ClientUtils;

public class ChefHandler {
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;
    private Map<String, Runnable> menuOptions;

    public ChefHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
        initializeMenuOptions();
        ClientUtils.initialize(in, out, stdIn);
    }

    private void initializeMenuOptions() {
        menuOptions = new HashMap<>();
        menuOptions.put("1", this::viewFoodMenu);
        menuOptions.put("2", this::rollOutNextDayMenu);
        menuOptions.put("3", this::viewEmployeeSelection);
        menuOptions.put("4", this::viewRecommendations);
        menuOptions.put("5", this::viewDiscardedItems);
        menuOptions.put("6", () -> System.out.println("Exiting"));
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
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.err.println("Error handling user operations: ");
                break;
            }
        }
    }

    private void viewFoodMenu() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println("Food Menu: " + response);
        } catch (IOException e) {
            System.err.println("Error viewing food menu: " + e.getMessage());
        }
    }

    private void rollOutNextDayMenu() {
        try {
            System.out.print("Enter number of items for Breakfast: ");
            int breakfastItems = Integer.parseInt(stdIn.readLine());
            out.println(breakfastItems);
            StringBuilder breakfast = new StringBuilder();
            for (int i = 0; i < breakfastItems; i++) {
                ClientUtils.SendInput("Enter Breakfast item ID: ", "numeric");
            }

            System.out.print("Enter number of items for Lunch: ");
            int lunchItems = Integer.parseInt(stdIn.readLine());
            out.println(lunchItems);
            StringBuilder lunch = new StringBuilder();
            for (int i = 0; i < lunchItems; i++) {
                ClientUtils.SendInput("Enter Lunch item ID: ", "numeric");
            }

            System.out.print("Enter number of items for Dinner: ");
            int dinnerItems = Integer.parseInt(stdIn.readLine());
            out.println(dinnerItems);
            StringBuilder dinner = new StringBuilder();
            for (int i = 0; i < dinnerItems; i++) {
                ClientUtils.SendInput("Enter Dinner item ID: ", "numeric");
            }

            String response = ClientUtils.readServerResponse();
            System.out.println("Roll Out Menu Response: " + response);
        } catch (IOException e) {
            System.err.println("Error rolling out next day menu: " + e.getMessage());
        }
    }

    private void viewEmployeeSelection() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Error viewing employee selection: " + e.getMessage());
        }
    }

    private void viewRecommendations() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Error viewing recommendations: " + e.getMessage());
        }
    }

    private void viewDiscardedItems() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println(response);

            System.out.println("Choose an option from the following:");
            System.out.println("1.) Delete an item from discarded list\n2.) Get employee feedback for an item");

            int selectedOption = Integer.parseInt(stdIn.readLine());

            if (selectedOption == 1) {
                out.println("1");
                ClientUtils.SendInput("Enter the ID of the item to delete from the menu: ", "numeric");
                System.out.println("Item has been deleted");
                viewDiscardedItems();
            } else if (selectedOption == 2) {
                out.println("2");
                ClientUtils.SendInput("Enter Item ID to roll out feedback for: ", "numeric");
                System.out.println("Roll out " + ClientUtils.readServerResponse());
                viewDiscardedItems();
            } else if (selectedOption == 3) {
                System.out.println("Returning back to menu...");
            } else {
                System.out.println("Enter a valid option");
                viewDiscardedItems();
            }
        } catch (IOException e) {
            System.err.println("Error viewing discarded items: " + e.getMessage());
        }
    }

}
