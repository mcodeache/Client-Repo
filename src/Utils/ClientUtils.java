package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class ClientUtils {
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader stdIn;

    private static final Map<String, Predicate<String>> validationMap = new HashMap<>();

    static {
        validationMap.put("alphanumeric", ClientUtils::isAlphanumeric);
        validationMap.put("numeric", ClientUtils::isNumeric);
        validationMap.put("alphabetic", ClientUtils::isAlphabetic);
        validationMap.put("string", value -> value != null && !value.trim().isEmpty());
        validationMap.put("boolean", ClientUtils::isBoolean);
    }

    public static void initialize(BufferedReader input, PrintWriter output, BufferedReader userInput) {
        in = input;
        out = output;
        stdIn = userInput;
    }

    public static String readServerResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            if (line.contains("END_OF_MESSAGE")) {
                break;
            }
            response.append(line).append("\n");
        }
        return response.toString().trim();
    }

    public static void SendInput(String promptMessage, String expectedFormat) throws IOException {
        String input;
        while (true) {
            System.out.print(promptMessage);
            input = stdIn.readLine();

            if (validateValue(input, expectedFormat)) {
                out.println(input);
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid " + expectedFormat + " value.");
            }
        }
    }

    public static boolean validateValue(String value, String expectedFormat) {
        Predicate<String> validator = validationMap.get(expectedFormat.toLowerCase());
        return validator != null && validator.test(value);
    }

    private static boolean isAlphanumeric(String value) {
        return value.matches("[a-zA-Z0-9]+");
    }

    private static boolean isNumeric(String value) {
        return value.matches("[0-9]+");
    }

    private static boolean isAlphabetic(String value) {
        return value.matches("[a-zA-Z]+");
    }

    private static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("no");
    }

    private static boolean isDataPresent(String tableName, String fieldName, Object fieldValue) throws IOException {
        out.println(tableName);
        out.println(fieldName);
        out.println(fieldValue);

        String isDataValid = in.readLine().toLowerCase();
        if (isDataValid.equals("yes")){
            return true;
        }
        return false;
    }

}
