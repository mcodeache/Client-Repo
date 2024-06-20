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
            String menuOption = in.readLine();
            System.out.println(menuOption);

            System.out.print("Enter your choice :");
            String choice = stdIn.readLine();

            switch (choice) {
                case "a":
                    viewFoodMenu();
                    break;
                case "b":
                    rollOutNextDayMenu();
                    break;
                case "c":
                    viewMonthlyReport();
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void viewFoodMenu() throws IOException {
        out.println("view_food_menu");
        String response = in.readLine();
        System.out.println("Food Menu: " + response);
    }

    private void rollOutNextDayMenu() throws IOException {
        out.println("roll_out_next_day_menu");
        System.out.println("Select for Breakfast:");
        String breakfast = stdIn.readLine();
        out.println(breakfast);

        System.out.println("Select for Lunch:");
        String lunch = stdIn.readLine();
        out.println(lunch);

        System.out.println("Select for Dinner:");
        String dinner = stdIn.readLine();
        out.println(dinner);

        String response = in.readLine();
        System.out.println("Roll Out Menu Response: " + response);
    }

    private void viewMonthlyReport() throws IOException {
        out.println("view_monthly_report");
        String response = in.readLine();
        System.out.println("Monthly Report: " + response);
    }
}
