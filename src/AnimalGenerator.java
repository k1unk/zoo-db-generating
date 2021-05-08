import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class AnimalGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();
        ArrayList<String> types = Utils.fileDataToArray("resources/animal_types.txt");
        Collections.shuffle(types);
        ArrayList<String> names = Utils.fileDataToArray("resources/animal_names.txt");
        Collections.shuffle(names);

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM cage");
        ArrayList<String> cages_id = new ArrayList<>();
        while (rs.next()) {
            cages_id.add(rs.getString(1));
        }
        Collections.shuffle(cages_id);

        if (quantity > cages_id.size()) {
            System.out.println("quantity must be less then cages_id.size");
            return;
        }

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Animal (cage_id, type, name, cold_resistant)  \n" +
                    "\tVALUES ('" +
                    cages_id.get(i) + "', '" +
                    types.get(i % types.size()) + "', '" +
                    names.get(i % names.size()) + "', '" +
                    isColdResistant() + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }

    public static String isColdResistant() {
        if (Utils.random.nextBoolean()) return "true";
        else return "false";
    }
}
