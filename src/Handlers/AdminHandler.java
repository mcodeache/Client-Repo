package Handlers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import Utils.ClientUtils;
import Exceptions.CustomExceptionHandler;

public class AdminHandler {
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;
    private Map<String, Runnable> menuOptions;

    public AdminHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
        initializeMenuOptions();
    }

    private void initializeMenuOptions() {
        menuOptions = new HashMap<>();
        menuOptions.put("1", this::viewMenu);
        menuOptions.put("2", this::addItem);
        menuOptions.put("3", this::updateItem);
        menuOptions.put("4", this::deleteItem);
        menuOptions.put("5", () -> System.out.println("Logging out..."));
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
                    if ("5".equals(choice)) {
                        return;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                }
            } catch (IOException e) {
                System.err.println("Error handling user operations: " + e.getMessage());
            }
        }
    }

    private void viewMenu() {
        try {
            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error viewing menu: " + e.getMessage());
        }
    }

    private void addItem() {
        try {
            ClientUtils.SendInput("Enter item name: ", "alphabetic");
            ClientUtils.SendInput("Enter item price: ", "numeric");
            ClientUtils.SendInput("Enter Item Type (Breakfast, Lunch, Dinner): ", "alphabetic");
            ClientUtils.SendInput("Enter Diet type (Vegetarian, Non-Veg, Other): ", "alphabetic");
            ClientUtils.SendInput("Enter the Spice level for the specified food (Hot/Medium/Mild): ", "alphabetic");
            ClientUtils.SendInput("Enter the region of the food (North Indian, South Indian, Other): ", "alphabetic");

            System.out.print("Is the item sweet (Yes/No): ");
            String sweetTooth = stdIn.readLine();
            if (sweetTooth.toLowerCase().equals("yes")) {
                out.println(1);
            } else if (sweetTooth.toLowerCase().equals("no")) {
                out.println(0);
            }

            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error adding item: " + e.getMessage());
        }
    }

    private void updateItem() {
        try {
            ClientUtils.SendInput("Enter item name to update: ", "alphabetic");
            ClientUtils.SendInput("Enter new price: ", "numeric");
            ClientUtils.SendInput("Enter Yes/No for Availability: ", "alphabetic");

            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    private void deleteItem() {
        try {
            ClientUtils.SendInput("Enter item Id to delete: ", "numeric");
            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error deleting item: " + e.getMessage());
        }
    }
}
