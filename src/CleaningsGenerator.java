import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CleaningsGenerator {
    public static void generate(int quantity) throws SQLException {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM cage");
        ArrayList<String> cages_id = new ArrayList<>();
        while (rs.next()) {
            cages_id.add(rs.getString(1));
        }

        rs = st.executeQuery("SELECT id FROM worker WHERE speciality LIKE 'cleaner'");
        ArrayList<String> workers_id = new ArrayList<>();
        while (rs.next()) {
            workers_id.add(rs.getString(1));
        }

        for (int i = 0; i < quantity; i++) {
            String time = getRandomTime();
            str.append("INSERT INTO Cleanings (cage_id, worker_id, start_time, end_time)  \n" +
                    "\tVALUES ('" +
                    cages_id.get(Utils.random.nextInt(cages_id.size())) + "', '" +
                    workers_id.get(Utils.random.nextInt(workers_id.size())) + "', '" +
                    time.split("~")[0] + "', '" +
                    time.split("~")[1] + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }

    static String getRandomTime() {
        StringBuilder result = new StringBuilder();
        long rangeBegin = Timestamp.valueOf("2021-04-08 00:00:00").getTime();
        long rangeEnd = Timestamp.valueOf("2021-04-10 23:59:59").getTime();
        long diff = rangeEnd - rangeBegin + 1;
        Timestamp rand = new Timestamp(rangeBegin + (long) (Math.random() * diff));

        result.append(rand.toString().split("\\.")[0]);
        result.append("~");

        rand.setTime(rand.getTime() + TimeUnit.MINUTES.toMillis(10 +
                Utils.random.nextInt(50)));
        result.append(rand.toString().split("\\.")[0]);

        return result.toString();
    }
}
