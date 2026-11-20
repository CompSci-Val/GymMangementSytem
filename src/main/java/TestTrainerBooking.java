
public class TestTrainerBooking {

    public static void main(String[] args) {

        // Create trainer database
        TrainerDatabase database = new TrainerDatabase();

        // Display registered trainers
        System.out.println("Registered Trainers");

        for (Trainer trainer : database.getTrainers()) {
            System.out.println(
                trainer.getId() + " " + trainer.getName()
            );
        }

        // Test searching for a trainer
        System.out.println("\nTesting Trainer Search");

        Trainer trainer = database.getTrainerById(1234);

        if (trainer != null) {
            System.out.println("Trainer found: " + trainer.getName());
        } else {
            System.out.println("Trainer not found.");
        }

        // Test an invalid trainer ID
        System.out.println("\nTesting Invalid Trainer ID");

        Trainer invalidTrainer = database.getTrainerById(9999);

        if (invalidTrainer == null) {
            System.out.println("Invalid trainer ID rejected.");
        }
    }
}


