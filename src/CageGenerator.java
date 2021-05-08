import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class CageGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM worker WHERE speciality LIKE 'cleaner'");
        ArrayList<String> cleaners_id = new ArrayList<>();
        while (rs.next()) {
            cleaners_id.add(rs.getString(1));
        }
        Collections.shuffle(cleaners_id);

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Cage (worker_id, size_x, size_y, size_z, is_warm) \n" +
                    "\tVALUES ('" +
                    cleaners_id.get(i%cleaners_id.size()) + "', '" +
                    (Utils.random.nextInt(15) + 1) + "', '" +
                    (Utils.random.nextInt(15) + 1) + "', '" +
                    (Utils.random.nextInt(5) + 1) + "', '" +
                    isWarm() + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }

    public static String isWarm() {
        if (Utils.random.nextBoolean()) return "true";
        else return "false";
    }
}
