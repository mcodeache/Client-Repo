import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientUtils {
    private static BufferedReader in;
    private static PrintWriter out;

    public static void initialize(BufferedReader input, PrintWriter output) {
        in = input;
        out = output;
    }

    public static String readServerResponse() throws IOException {
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
