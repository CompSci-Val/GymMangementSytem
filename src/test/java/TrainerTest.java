public class TrainerTest {

    public static void main(String[] args) {
        Trainer trainer = new Trainer("T001", "John Brown", "Weight Training");

        check("Trainer ID is stored", "T001".equals(trainer.getTrainerID()));
        check("Trainer name is stored", "John Brown".equals(trainer.getName()));
        check("Trainer specialization is stored",
                "Weight Training".equals(trainer.getSpecialization()));
        check("Trainer is available by default", trainer.isAvailable());

        trainer.setAvailable(false);
        check("Trainer can be marked unavailable", !trainer.isAvailable());

        trainer.setAvailable(true);
        check("Trainer can be marked available", trainer.isAvailable());

        trainer.displayTrainer();
        System.out.println("Trainer tests completed.");
    }

    private static void check(String testName, boolean passed) {
        System.out.println((passed ? "PASS: " : "FAIL: ") + testName);
    }
}
