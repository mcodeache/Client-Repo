import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ChefHandler {
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;

    public ChefHandler(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
    }

    public void handleUserOperations() throws IOException {
        Map<String, Runnable> menuOptions = new HashMap<>();
        menuOptions.put("1", this::viewFoodMenu);
        menuOptions.put("2", this::rollOutNextDayMenu);
        menuOptions.put("3", this::viewEmployeeSelection);
        menuOptions.put("4", this::viewRecommendations);
        menuOptions.put("5", this::viewDiscardedItems);
        menuOptions.put("6", () -> System.out.println("Exiting"));

        while (true) {
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
                System.out.println("Enter the ID of the item to delete from the menu");
                out.println(stdIn.readLine());
                System.out.println("Item has been deleted");
                viewDiscardedItems();
            } else if (selectedOption == 2) {
                out.println("2");
                System.out.println("Enter Item ID to roll out feedback for");
                out.println(stdIn.readLine());
                System.out.println("Roll out " + in.readLine());
                viewDiscardedItems();
            } else if (selectedOption == 3) {
                System.out.println("Returning back to menu...");
            } else {
                System.out.println("Enter a valid option");
                viewDiscardedItems();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewRecommendations() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewFoodMenu() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println("Food Menu: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rollOutNextDayMenu() {
        try {
            System.out.print("Enter number of items for Breakfast: ");
            int breakfastItems = Integer.parseInt(stdIn.readLine());
            out.println(breakfastItems);
            StringBuilder breakfast = new StringBuilder();
            for (int i = 0; i < breakfastItems; i++) {
                System.out.print("Enter Breakfast item ID: ");
                breakfast.append(stdIn.readLine()).append(",");
            }
            out.println(breakfast);

            System.out.print("Enter number of items for Lunch: ");
            int lunchItems = Integer.parseInt(stdIn.readLine());
            out.println(lunchItems);
            StringBuilder lunch = new StringBuilder();
            for (int i = 0; i < lunchItems; i++) {
                System.out.print("Enter Lunch item ID: ");
                lunch.append(stdIn.readLine()).append(",");
            }
            out.println(lunch);

            System.out.print("Enter number of items for Dinner: ");
            int dinnerItems = Integer.parseInt(stdIn.readLine());
            out.println(dinnerItems);
            StringBuilder dinner = new StringBuilder();
            for (int i = 0; i < dinnerItems; i++) {
                System.out.print("Enter Dinner item ID: ");
                dinner.append(stdIn.readLine()).append(",");
            }
            out.println(dinner);

            String response = in.readLine();
            System.out.println("Roll Out Menu Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewEmployeeSelection() {
        try {
            String response = ClientUtils.readServerResponse();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
