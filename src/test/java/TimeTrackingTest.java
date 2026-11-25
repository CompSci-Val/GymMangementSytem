package your_package_name; // <-- CHANGE THIS to match  project's package

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class TimeTrackingTest {

    private MutableClock clock;
    private TimeTracking tracker;

    @BeforeEach
    void setUp() {
        clock = new MutableClock(Instant.parse("2026-11-19T10:00:00Z"), ZoneOffset.UTC);
        tracker = new TimeTracking("M001", 60, clock);
        tracker.startSession();
    }

    @Test
    void remainingTimeDecreasesAsTimePasses() {
        clock.advance(Duration.ofSeconds(20));
        assertEquals(Duration.ofSeconds(40), tracker.calculateRemainingTime());
        assertEquals(40, tracker.getRemainingTime());
    }

    @Test
    void remainingTimeNeverGoesNegative() {
        clock.advance(Duration.ofSeconds(90));
        assertEquals(Duration.ZERO, tracker.calculateRemainingTime());
    }

    @Test
    void startingTwiceThrows() {
        assertThrows(IllegalStateException.class, tracker::startSession);
    }

    @Test
    void endSessionRecordsDuration() {
        clock.advance(Duration.ofSeconds(25));
        tracker.endSession();
        assertFalse(tracker.isActive());
        assertEquals(25, tracker.getSessionDurationSeconds());
    }

    @Test
    void endingWithoutStartingThrows() {
        TimeTracking t = new TimeTracking("M999", 60, clock);
        assertThrows(IllegalStateException.class, t::endSession);
    }

    @Test
    void warningsFireAtThresholds() {
        // Note: we don't assert on System.out; this test just checks no exception.
        clock.advance(Duration.ofSeconds(31));
        tracker.displayWarning(); // ~29 min left  -> 30-min warning
        clock.advance(Duration.ofSeconds(15));
        tracker.displayWarning(); // ~14 min left  -> 15-min warning
        clock.advance(Duration.ofSeconds(10));
        tracker.displayWarning(); // ~4 min left   -> 5-min warning
    }
}