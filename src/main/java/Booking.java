import java.time.LocalDateTime;

public class Booking {

    private String bookingID;
    private int memberID;
    private String trainerID;
    private LocalDateTime bookingDateTime;
    private String status;

    public Booking(String bookingID, int memberID,
                   String trainerID,
                   LocalDateTime bookingDateTime) {
        this.bookingID = bookingID;
        this.memberID = memberID;
        this.trainerID = trainerID;
        this.bookingDateTime = bookingDateTime;
        this.status = "Confirmed";
    }

    public String getBookingID() {
        return bookingID;
    }


    public int getMemberID() {
        return memberID;
    }
   
    public String getTrainerID() {
        return trainerID;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayBooking() {
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Member ID: " + memberID);
        System.out.println("Trainer ID: " + trainerID);
        System.out.println("Date and Time: " + bookingDateTime);
        System.out.println("Status: " + status);
    }
}
