import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void testBookingIDIsStored() {
        LocalDateTime dateTime =
                LocalDateTime.of(2026, 11, 25, 10, 0);

        Booking booking =
                new Booking("B001", 6201, "T001", dateTime);

        assertEquals("B001", booking.getBookingID());
    }

    @Test
    void testMemberIDIsStored() {
        LocalDateTime dateTime =
                LocalDateTime.of(2026, 11, 25, 10, 0);

        Booking booking =
                new Booking("B001", 6201, "T001", dateTime);

        assertEquals(6201, booking.getMemberID());
    }

    @Test
    void testTrainerIDIsStored() {
        LocalDateTime dateTime =
                LocalDateTime.of(2026, 11, 25, 10, 0);

        Booking booking =
                new Booking("B001", 6201, "T001", dateTime);

        assertEquals("T001", booking.getTrainerID());
    }

    @Test
    void testBookingDateTimeIsStored() {
        LocalDateTime dateTime =
                LocalDateTime.of(2026, 11, 25, 10, 0);

        Booking booking =
                new Booking("B001", 6201, "T001", dateTime);

        assertEquals(dateTime, booking.getBookingDateTime());
    }

    @Test
    void testDefaultBookingStatusIsConfirmed() {
        Booking booking =
                new Booking("B001", 6201, "T001",
                        LocalDateTime.now());

        assertEquals("Confirmed", booking.getStatus());
    }

    @Test
    void testBookingStatusCanBeCancelled() {
        Booking booking =
                new Booking("B001", 6201, "T001",
                        LocalDateTime.now());

        booking.setStatus("Cancelled");

        assertEquals("Cancelled", booking.getStatus());
    }
}