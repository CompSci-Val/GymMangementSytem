
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TimeTracking implements Comparable<TimeTracking> {

    public static final int DEFAULT_SESSION_Seconds = 180;// for sake of demo 

    private static final int[] WARNING_THRESHOLDS = {105, 60, 30, 10};
    private final String memberId;
    private final int sessionDurationSeconds;
    private final Clock clock;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;
    private final boolean[] warningShown = new boolean[WARNING_THRESHOLDS.length];
    private boolean expiryShown;

    public TimeTracking(String memberId) {
        this(memberId, DEFAULT_SESSION_Seconds, Clock.systemDefaultZone());
    }

    public TimeTracking(String memberId, int sessionDurationSeconds) {
        this(memberId, sessionDurationSeconds, Clock.systemDefaultZone());
    }

    public TimeTracking(String memberId, int sessionDurationSeconds, Clock clock) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId must not be null or blank");
        }
        if (sessionDurationSeconds <= 0) {
            throw new IllegalArgumentException("sessionDurationSeconds must be > 0");
        }
        if (clock == null) {
            throw new IllegalArgumentException("clock must not be null");
        }
        this.memberId = memberId;
        this.sessionDurationSeconds = sessionDurationSeconds;
        this.clock = clock;
    }

    public void startSession() {
        if (active) {
            throw new IllegalStateException("Session already active for member " + memberId);
        }
        this.startTime = LocalDateTime.now(clock);
        this.endTime = null;
        this.active = true;
        Arrays.fill(warningShown, false);
        this.expiryShown = false;
    }

    public void endSession() {
        if (!active) {
            throw new IllegalStateException("No active session for member " + memberId);
        }
        this.endTime = LocalDateTime.now(clock);
        this.active = false;
    }

    /**
     * Precise remaining time (never negative).
     */
    public Duration calculateRemainingTime() {
        if (!active || startTime == null) {
            return Duration.ZERO;
        }
        Duration elapsed = Duration.between(startTime, LocalDateTime.now(clock));
        Duration remaining = Duration.ofSeconds(sessionDurationSeconds).minus(elapsed);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }

    public long getRemainingTime() {
        return calculateRemainingTime().toSeconds();
    }

    public void displayWarning() {
        if (!active) {
            return;
        }

        Duration remaining = calculateRemainingTime();

        if (remaining.isZero() && !expiryShown) {
            System.out.printf("[WARNING] Member %s: session expired — please check out.%n", memberId);
            expiryShown = true;
        }

        long Seconds = remaining.toSeconds();
        for (int i = 0; i < WARNING_THRESHOLDS.length; i++) {
            int threshold = WARNING_THRESHOLDS[i];
            if (threshold >= sessionDurationSeconds) {
                continue; // impossible for a short session
            }
            if (!warningShown[i] && Seconds > 0 && Seconds <= threshold) {
                System.out.printf("[WARNING] Member %s: %d Seconds remaining.%n",
                        memberId, Seconds);
                warningShown[i] = true;
            }
        }
    }

    public long getSessionDurationSeconds() {
        if (startTime == null) {
            return 0;
        }
        LocalDateTime end = (endTime != null) ? endTime : LocalDateTime.now(clock);
        return Duration.between(startTime, end).toSeconds();
    }

    public String getMemberId() {
        return memberId;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getSessionDurationLimit() {
        return sessionDurationSeconds;
    }

    @Override
    public int compareTo(TimeTracking other) {
        return Long.compare(this.getRemainingTime(), other.getRemainingTime());
    }

    @Override
    public String toString() {
        return String.format(
                "TimeTracking{member='%s', active=%s, remaining=%d Seconds, start=%s}",
                memberId, active, getRemainingTime(), startTime);
    }
}
