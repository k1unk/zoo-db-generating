import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class VisitsGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM worker WHERE speciality LIKE 'cashier'");
        ArrayList<String> cashiers_id = new ArrayList<>();
        while (rs.next()) {
            cashiers_id.add(rs.getString(1));
        }
        Collections.shuffle(cashiers_id);

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Visits (ticket_price, worker_id) \n" +
                    "\tVALUES ('" +
                    (100 + 100 * Utils.random.nextInt(4)) + "', '" +
                    cashiers_id.get(Utils.random.nextInt(cashiers_id.size())) + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }
}
