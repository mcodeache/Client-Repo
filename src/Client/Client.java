package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Handlers.AdminHandler;
import Handlers.ChefHandler;
import Handlers.EmployeeHandler;
import Utils.ClientUtils;
import Exceptions.CustomExceptionHandler;

public class Client {
    private static final int MAX_ATTEMPTS = 3;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try (Socket socket = createSocket();
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            ClientUtils.initialize(in, out, stdIn);
            int attempts = 0;
            boolean authenticated = false;

            while (attempts < MAX_ATTEMPTS && !authenticated) {
                String[] credentials = getUserInput(stdIn);
                String username = credentials[0];
                String password = credentials[1];

                out.println(username);
                out.println(password);

                String response = in.readLine();
                System.out.println(response);

                if ("Authentication successful".equals(response)) {
                    authenticated = true;
                    handleRole(in, out, stdIn);
                } else {
                    attempts++;
                }
            }

            if (!authenticated) {
                System.out.println("Maximum authentication attempts reached. Please try again later.");
            }

        } catch (IOException e) {
            CustomExceptionHandler.handleException(e);
        }
    }

    private String[] getUserInput(BufferedReader stdIn) {
        String[] credentials = new String[2];
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.print("Enter username: ");
                credentials[0] = stdIn.readLine();
                System.out.print("Enter password: ");
                credentials[1] = stdIn.readLine();

                if (!credentials[0].isEmpty() && !credentials[1].isEmpty()) {
                    break;
                } else {
                    System.out.println("Username and password cannot be empty. Please try again.");
                }
            } catch (IOException e) {
                CustomExceptionHandler.handleException(e);
            }
            attempts++;
        }

        if (attempts >= MAX_ATTEMPTS) {
            System.out.println("Maximum attempts reached. Please try again later.");
            System.exit(1);
        }

        return credentials;
    }

    private void handleRole(BufferedReader in, PrintWriter out, BufferedReader stdIn) {
        try {
            String role = in.readLine();
            switch (role.toLowerCase()) {
                case "admin":
                    handleAdmin(out, in, stdIn);
                    break;
                case "chef":
                    handleChef(out, in, stdIn);
                    break;
                case "employee":
                    handleEmployee(out, in, stdIn);
                    break;
                default:
                    System.out.println("Unknown role.");
                    break;
            }
        } catch (IOException e) {
            CustomExceptionHandler.handleException(e);
        }
    }

    private void handleAdmin(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        try {
            AdminHandler adminHandler = new AdminHandler(out, in, stdIn);
            adminHandler.handleUserOperations();
        } catch (Exception e) {
            CustomExceptionHandler.handleException(e);
        }
    }

    private void handleChef(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        ChefHandler chefHandler = new ChefHandler(out, in, stdIn);
        chefHandler.handleUserOperations();
    }

    private void handleEmployee(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        try {
            EmployeeHandler employeeHandler = new EmployeeHandler(out, in, stdIn);
            employeeHandler.handleUserOperations();
        } catch (Exception e) {
            CustomExceptionHandler.handleException(e);
        }
    }

    private Socket createSocket() throws IOException {
        return new Socket("localhost", 12345);
    }
}
