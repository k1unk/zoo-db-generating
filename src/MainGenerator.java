import java.sql.SQLException;

public class MainGenerator {

    public static void main(String[] args) throws SQLException {
        WorkersGenerator.generate(100);
        CageGenerator.generate(100);
        AnimalGenerator.generate(50);
        MealGenerator.generate(50);
        FeedingGenerator.generate(300);
        MedicalCheckupGenerator.generate(100);
        VisitsGenerator.generate(100);
    }
}
