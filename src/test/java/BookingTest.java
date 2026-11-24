import java.time.LocalDateTime;

public class BookingTest {

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.of(2026, 11, 25, 10, 0);

        Booking booking = new Booking("B001", "M001", "T001", dateTime);

        check("Booking ID is stored", "B001".equals(booking.getBookingID()));
        check("Member ID is stored", "M001".equals(booking.getMemberID()));
        check("Trainer ID is stored", "T001".equals(booking.getTrainerID()));
        check("Booking date and time are stored",
                dateTime.equals(booking.getBookingDateTime()));
        check("New booking status is Confirmed",
                "Confirmed".equals(booking.getStatus()));

        booking.setStatus("Cancelled");
        check("Booking status can be changed",
                "Cancelled".equals(booking.getStatus()));

        booking.displayBooking();
        System.out.println("Booking tests completed.");
    }

    private static void check(String testName, boolean passed) {
        System.out.println((passed ? "PASS: " : "FAIL: ") + testName);
    }
}
