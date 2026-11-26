import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TimeTrackingTest {

    @Test
    void testSessionStartsSuccessfully() {
        TimeTracking session = new TimeTracking("6201");

        session.startSession();

        assertTrue(session.isActive());
    }

    @Test
    void testSessionEndsSuccessfully() {
        TimeTracking session = new TimeTracking("6201");

        session.startSession();
        session.endSession();

        assertFalse(session.isActive());
    }

    @Test
    void testSessionHasRemainingTime() {
        TimeTracking session = new TimeTracking("6201");

        session.startSession();

        assertTrue(session.getRemainingTime() > 0);
    }
}
