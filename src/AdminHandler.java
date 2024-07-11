import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
                e.printStackTrace();
            }
        }
    }

    private void viewMenu() {
        try {
            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error viewing menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addItem() {
        try {
            System.out.print("Enter item name: ");
            String itemName = stdIn.readLine();
            out.println(itemName);

            System.out.print("Enter item price: ");
            String itemPrice = stdIn.readLine();
            out.println(itemPrice);

            System.out.println("Enter Item Type (Breakfast, Lunch, Dinner):");
            String itemType = stdIn.readLine();
            out.println(itemType);

            System.out.println("Enter Diet type (Vegetarian, Non-Veg, Other):");
            String dietType = stdIn.readLine();
            out.println(dietType);

            System.out.println("Enter the Spice level for the specified food (Hot/Medium/Mild):");
            String spiceLevel = stdIn.readLine();
            out.println(spiceLevel);

            System.out.println("Enter the region of the food (North Indian, South Indian, Other):");
            String preference = stdIn.readLine();
            out.println(preference);

            System.out.println("Is the item sweet (Yes/No):");
            String sweetTooth = stdIn.readLine();
            out.println(sweetTooth);

            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error adding item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateItem() {
        try {
            System.out.print("Enter item name to update: ");
            String itemName = stdIn.readLine();
            out.println(itemName);

            System.out.print("Enter new price: ");
            String newItemPrice = stdIn.readLine();
            out.println(newItemPrice);

            System.out.println("Enter Yes/No for Availability:");
            String availability = stdIn.readLine();
            out.println(availability);

            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteItem() {
        try {
            System.out.print("Enter item name to delete: ");
            String itemName = stdIn.readLine();
            out.println(itemName);

            String serverResponse = ClientUtils.readServerResponse();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Error deleting item: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
