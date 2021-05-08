import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MedicalCheckupGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM worker WHERE speciality LIKE 'doctor'");
        ArrayList<String> doctors_id = new ArrayList<>();
        while (rs.next()) {
            doctors_id.add(rs.getString(1));
        }
        Collections.shuffle(doctors_id);

        rs = st.executeQuery("SELECT id FROM animal");
        ArrayList<String> animals_id = new ArrayList<>();
        while (rs.next()) {
            animals_id.add(rs.getString(1));
        }

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Medical_checkup (worker_id, date, is_successful, animal_id)  \n" +
                    "\tVALUES ('" +
                    doctors_id.get(Utils.random.nextInt(doctors_id.size())) + "', '" +
                    getRandomDate() + "', '" +
                    isSuccessful() + "', '" +
                    animals_id.get(i%animals_id.size()) + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }
    static String getRandomDate(){
        Random random = new Random();
        int minDay = (int) LocalDate.of(2021, 4, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2021, 4, 20).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

        return randomBirthDate.toString();
    }

    public static String isSuccessful() {
        if (Utils.random.nextInt(100)>2) return "true";
        else return "false";
    }
}
