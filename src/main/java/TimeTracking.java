package your_package_name; // <-- CHANGE THIS to match your project's package

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Feat101 – Session Time Tracking.
 * Tracks how long a gym member's session lasts and warns them
 * when they are running low on remaining time.
 */
public class TimeTracking implements Comparable<TimeTracking> {

    /** Default session length in minutes. */
    public static final int DEFAULT_SESSION_MINUTES = 120;

    /** Warning thresholds (minutes remaining). */
    private static final int[] WARNING_THRESHOLDS = {30, 15, 5};

    private final String memberId;
    private final int sessionDurationMinutes;
    private final Clock clock;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;

    /** One flag per threshold so each warning is shown only once. */
    private final boolean[] warningShown = new boolean[WARNING_THRESHOLDS.length];
    private boolean expiryShown;

    // ---------- Constructors ----------

    public TimeTracking(String memberId) {
        this(memberId, DEFAULT_SESSION_MINUTES, Clock.systemDefaultZone());
    }

    public TimeTracking(String memberId, int sessionDurationMinutes) {
        this(memberId, sessionDurationMinutes, Clock.systemDefaultZone());
    }

    /** Package-private / test constructor allowing a fixed Clock. */
    public TimeTracking(String memberId, int sessionDurationMinutes, Clock clock) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId must not be null or blank");
        }
        if (sessionDurationMinutes <= 0) {
            throw new IllegalArgumentException("sessionDurationMinutes must be > 0");
        }
        if (clock == null) {
            throw new IllegalArgumentException("clock must not be null");
        }
        this.memberId = memberId;
        this.sessionDurationMinutes = sessionDurationMinutes;
        this.clock = clock;
    }

    // ---------- Session lifecycle ----------

    /** Called from the check-in flow. */
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

    /** Called from the check-out flow. */
    public void endSession() {
        if (!active) {
            throw new IllegalStateException("No active session for member " + memberId);
        }
        this.endTime = LocalDateTime.now(clock);
        this.active = false;
    }

    // ---------- Remaining time ----------

    /** Precise remaining time (never negative). */
    public Duration calculateRemainingTime() {
        if (!active || startTime == null) {
            return Duration.ZERO;
        }
        Duration elapsed = Duration.between(startTime, LocalDateTime.now(clock));
        Duration remaining = Duration.ofMinutes(sessionDurationMinutes).minus(elapsed);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }

    /** Convenience: remaining time in whole minutes. */
    public long getRemainingTime() {
        return calculateRemainingTime().toMinutes();
    }

    // ---------- Warnings ----------

    /**
     * Display warnings at 30, 15, and 5 minutes remaining (and on expiry).
     * Safe to call repeatedly (e.g. from a scheduler) — each warning fires once.
     */
    public void displayWarning() {
        if (!active) {
            return;
        }

        Duration remaining = calculateRemainingTime();

        // Expiry first.
        if (remaining.isZero() && !expiryShown) {
            System.out.printf("[WARNING] Member %s: session expired — please check out.%n", memberId);
            expiryShown = true;
        }

        long minutes = remaining.toMinutes();
        for (int i = 0; i < WARNING_THRESHOLDS.length; i++) {
            int threshold = WARNING_THRESHOLDS[i];
            if (threshold >= sessionDurationMinutes) {
                continue; // impossible for a short session
            }
            if (!warningShown[i] && minutes > 0 && minutes <= threshold) {
                System.out.printf("[WARNING] Member %s: %d minutes remaining.%n",
                        memberId, minutes);
                warningShown[i] = true;
            }
        }
    }

    // ---------- Duration for reporting ----------

    /** Total elapsed minutes (finished sessions use endTime; active sessions use now). */
    public long getSessionDurationMinutes() {
        if (startTime == null) {
            return 0;
        }
        LocalDateTime end = (endTime != null) ? endTime : LocalDateTime.now(clock);
        return Duration.between(startTime, end).toMinutes();
    }

    // ---------- Getters ----------

    public String getMemberId()          { return memberId; }
    public boolean isActive()            { return active; }
    public LocalDateTime getStartTime()  { return startTime; }
    public LocalDateTime getEndTime()    { return endTime; }
    public int getSessionDurationLimit() { return sessionDurationMinutes; }

    // ---------- Ordering: least time remaining first ----------

    @Override
    public int compareTo(TimeTracking other) {
        return Long.compare(this.getRemainingTime(), other.getRemainingTime());
    }

    @Override
    public String toString() {
        return String.format(
                "TimeTracking{member='%s', active=%s, remaining=%d min, start=%s}",
                memberId, active, getRemainingTime(), startTime);
    }
}
