import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class WorkersGenerator {
    public static void generate(int quantity) {
        Connection connection = Utils.getConnection();
        StringBuilder str = new StringBuilder();
        ArrayList<String> names = Utils.fileDataToArray("resources/worker_names.txt");
        Collections.shuffle(names);
        ArrayList<String> streets = Utils.fileDataToArray("resources/worker_streets.txt");
        Collections.shuffle(streets);
        String[] specialities = new String[] {"cook", "doctor", "manager", "cleaner", "cashier"};

        for (int i = 0; i < quantity; i++) {
            str.append("INSERT INTO Worker (name, lastname, speciality, address, date_of_birth, phone_number) \n" +
                    "\tVALUES ('" +
                    names.get(i%names.size()).split(Utils.SPACE)[1] + "', '" +
                    names.get(i%names.size()).split(Utils.SPACE)[0] + "', '" +
                    specialities[Utils.random.nextInt(specialities.length)] + "', '" +
                    streets.get(i%streets.size()) + "', '" +
                    getRandomDate() + "', '" +
                    getRandomPhoneNumber() + "'); \n");
        }

        Utils.executeUpdate(connection, str.toString());
        Utils.close(connection);
    }

    public static String getRandomDate() {
        LocalDate startDate = LocalDate.of(1950, 1, 1); //start date
        long start = startDate.toEpochDay();

        LocalDate endDate = LocalDate.of(2003, 1, 1); //end date
        long end = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay).toString();
    }

    public static String getRandomPhoneNumber() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            result.append(Utils.random.nextInt(10));
        }
        return result.toString();
    }


}
