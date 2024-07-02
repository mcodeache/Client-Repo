import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class CustomExceptionHandler {

    public static void handleException(Exception e) {
        if (e instanceof SQLException) {
            handleSQLException((SQLException) e);
        } else if (e instanceof IOException) {
            handleIOException((IOException) e);
        } else {
            handleGenericException(e);
        }
    }

    private static void handleSQLException(SQLException e) {
        System.err.println("Database error: " + e.getMessage());
    }

    private static void handleIOException(IOException e) {
//        System.out.println("Can't connect to server at the moment");

    }

//    private static void handleUnknownHostException(UnknownHostException e) {
//        System.out.println("Server is under maintainence, Please connect after sometime");
//    }

    private static void handleGenericException(Exception e) {
        System.err.println("An unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}
