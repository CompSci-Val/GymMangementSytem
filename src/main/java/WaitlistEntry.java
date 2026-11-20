import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Supporting Class: WaitlistEntry
 * Stores details of a member waiting to enter the gym.
 */
public class WaitlistEntry {
    
    private String memberId;
    private LocalDateTime timeJoinedQueue;

    public WaitlistEntry(String memberId) {
        this.memberId = memberId;
        this.timeJoinedQueue = LocalDateTime.now();
    }

    // How long has this person been waiting?
    public long getWaitTimeMinutes() {
        return Duration.between(timeJoinedQueue, LocalDateTime.now()).toMinutes();
    }

    // --- Getters ---
    public String getMemberId() { return memberId; }
    public LocalDateTime getTimeJoinedQueue() { return timeJoinedQueue; }

    @Override
    public String toString() {
        return "WaitlistEntry{memberId='" + memberId + "', waited=" + getWaitTimeMinutes() + " mins}";
    }
}