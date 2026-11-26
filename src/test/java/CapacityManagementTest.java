
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CapacityManagementTest {

    @Test
    void testInitialOccupancyIsZero() {
        CapacityManagement capacity = new CapacityManagement();

        assertEquals(0, capacity.getCurrentOccupancy());
    }

    @Test
    void testGymInitiallyHasSpace() {
        CapacityManagement capacity = new CapacityManagement();

        assertTrue(capacity.checkCapacity());
    }

    @Test
    void testOccupancyIncreasesWhenMemberEnters() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(true);

        assertEquals(1, capacity.getCurrentOccupancy());
    }

    @Test
    void testGymBecomesFullAtMaximumCapacity() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(true);
        capacity.updateOccupancy(true);

        assertEquals(2, capacity.getCurrentOccupancy());
        assertFalse(capacity.checkCapacity());
    }

    @Test
    void testOccupancyCannotExceedMaximumCapacity() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(true);
        capacity.updateOccupancy(true);
        capacity.updateOccupancy(true);

        assertEquals(2, capacity.getCurrentOccupancy());
    }

    @Test
    void testOccupancyDecreasesWhenMemberExits() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(true);
        capacity.updateOccupancy(true);
        capacity.updateOccupancy(false);

        assertEquals(1, capacity.getCurrentOccupancy());
        assertTrue(capacity.checkCapacity());
    }

    @Test
    void testOccupancyCannotBecomeNegative() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(false);

        assertEquals(0, capacity.getCurrentOccupancy());
    }

    @Test
    void testCalculateShortestWaitTime() {
        CapacityManagement capacity = new CapacityManagement();

        assertEquals(
            10,
            capacity.calculateWaitTime(Arrays.asList(25, 10, 40))
        );
    }

    @Test
    void testEmptyWaitTimesReturnZero() {
        CapacityManagement capacity = new CapacityManagement();

        assertEquals(
            0,
            capacity.calculateWaitTime(Collections.emptyList())
        );
    }

    @Test
    void testNullWaitTimesReturnZero() {
        CapacityManagement capacity = new CapacityManagement();

        assertEquals(0, capacity.calculateWaitTime(null));
    }

    @Test
    void testWaitlistReturnsMembersInOrder() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.addToWaitlist(new WaitlistEntry("6201"));
        capacity.addToWaitlist(new WaitlistEntry("6202"));

        WaitlistEntry first = capacity.getNextMember();
        WaitlistEntry second = capacity.getNextMember();

        assertNotNull(first);
        assertNotNull(second);
        assertEquals("6201", first.getMemberId());
        assertEquals("6202", second.getMemberId());
    }

    @Test
    void testCannotAdmitWaitlistedMemberWhenGymIsFull() {
        CapacityManagement capacity = new CapacityManagement();

        capacity.updateOccupancy(true);
        capacity.updateOccupancy(true);

        capacity.addToWaitlist(new WaitlistEntry("6201"));

        assertNull(capacity.getNextMember());
    }
}
