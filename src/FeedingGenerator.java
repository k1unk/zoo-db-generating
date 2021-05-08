import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class FeedingGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM worker WHERE speciality LIKE 'cook'");
        ArrayList<String> cooks_id = new ArrayList<>();
        while (rs.next()) {
            cooks_id.add(rs.getString(1));
        }
        Collections.shuffle(cooks_id);

        rs = st.executeQuery("SELECT id FROM meal");
        ArrayList<String> meals_id = new ArrayList<>();
        while (rs.next()) {
            meals_id.add(rs.getString(1));
        }

        rs = st.executeQuery("SELECT id FROM animal");
        ArrayList<String> animal_id = new ArrayList<>();
        while (rs.next()) {
            animal_id.add(rs.getString(1));
        }

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Feeding (worker_id, meal_id, time, animal_id)  \n" +
                    "\tVALUES ('" +
                    cooks_id.get(Utils.random.nextInt(cooks_id.size())) + "', '" +
                    meals_id.get(Utils.random.nextInt(meals_id.size())) + "', '" +
                    getRandomTime() + "', '" +
                    animal_id.get(i%animal_id.size()) + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }
    static String getRandomTime(){
        long rangeBegin = Timestamp.valueOf("2021-04-08 00:00:00").getTime();
        long rangeEnd = Timestamp.valueOf("2021-04-10 23:59:59").getTime();
        long diff = rangeEnd - rangeBegin + 1;
        Timestamp rand = new Timestamp(rangeBegin + (long)(Math.random() * diff));
        return rand.toString().split("\\.")[0];
    }
}
