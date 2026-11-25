import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Supporting Class: Attendance
 * Stores the check-in and check-out times for a member.
 */
public class Attendance {
    
    private String memberId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    // Constructor creates a new record at the moment of check-in
    public Attendance(String memberId) {
        this.memberId = memberId;
        this.checkInTime = LocalDateTime.now();
        this.checkOutTime = null;
    }

    // Call this method when the member leaves
    public void recordCheckOut() {
        this.checkOutTime = LocalDateTime.now();
    }

    // Calculates how long the member stayed (in seconds)
    public long getTotalTimeSpentSeconds() {
        if (checkOutTime == null) {
            // Member is still in the gym
            return Duration.between(checkInTime, LocalDateTime.now()).toSeconds();
        }
        return Duration.between(checkInTime, checkOutTime).toSeconds();
    }

    // --- Getters ---
    public String getMemberId() { return memberId; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }

    @Override
    public String toString() {
        return "Attendance{memberId='" + memberId + "', checkIn=" + checkInTime + ", checkOut=" + checkOutTime + "}";
    }
}