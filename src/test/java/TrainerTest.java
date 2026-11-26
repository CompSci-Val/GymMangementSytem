
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TrainerTest {

    @Test
    void testTrainerIDIsStored() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        assertEquals("T001", trainer.getTrainerID());
    }

    @Test
    void testTrainerNameIsStored() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        assertEquals("John Brown", trainer.getName());
    }

    @Test
    void testTrainerSpecializationIsStored() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        assertEquals("Weight Training", trainer.getSpecialization());
    }

    @Test
    void testTrainerIsAvailableByDefault() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        assertTrue(trainer.isAvailable());
    }

    @Test
    void testTrainerCanBeMarkedUnavailable() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        trainer.setAvailable(false);

        assertFalse(trainer.isAvailable());
    }

    @Test
    void testTrainerCanBeMarkedAvailableAgain() {
        Trainer trainer = new Trainer(
            "T001", "John Brown", "Weight Training"
        );

        trainer.setAvailable(false);
        trainer.setAvailable(true);

        assertTrue(trainer.isAvailable());
    }

    @Test
    void testTwoArgumentConstructorCreatesUnavailableTrainer() {
        Trainer trainer = new Trainer("T002", "Jane Brown");

        assertEquals("T002", trainer.getTrainerID());
        assertEquals("Jane Brown", trainer.getName());
        assertFalse(trainer.isAvailable());
    }
}
