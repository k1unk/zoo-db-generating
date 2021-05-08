import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Utils {

    static Random random = new Random();
    static final String SPACE = " ";

    public static Connection getConnection() {
        final String USER = "postgres";
        final String PASSWORD = "postgres";
        final String URL = "jdbc:postgresql://localhost:5432/zoo";

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void executeUpdate(Connection connection, String str) {
        try {
            final Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    static ArrayList<String> fileDataToArray(String path) {
        ArrayList<String> list = new  ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), "UTF-8"))) {
            String sub;
            while ((sub = br.readLine()) != null) {
                list.add(sub);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
