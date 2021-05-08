import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class MealGenerator {
    public static void generate(int quantity) {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();
        ArrayList<String> names = Utils.fileDataToArray("resources/meal_names.txt");
        Collections.shuffle(names);

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Meal (food_name, calories, price) \n" +
                    "\tVALUES ('" +
                    names.get(i % names.size()) + "', '" +
                    (Utils.random.nextInt(300) + 100) + "', '" +
                    (Utils.random.nextInt(300) + 100) + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }

}
