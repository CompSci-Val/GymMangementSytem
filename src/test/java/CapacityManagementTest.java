import java.util.Arrays;

public class CapacityManagementTest {

    public static void main(String[] args) {
        CapacityManagement capacity = new CapacityManagement();

        check("Initial occupancy is zero", capacity.getCurrentOccupancy() == 0);
        check("Gym initially has space", capacity.checkCapacity());

        for (int i = 0; i < 20; i++) {
            capacity.updateOccupancy(true);
        }

        check("Occupancy reaches 20", capacity.getCurrentOccupancy() == 20);
        check("Gym is full at 20", !capacity.checkCapacity());

        capacity.updateOccupancy(true);
        check("Occupancy cannot exceed 20", capacity.getCurrentOccupancy() == 20);

        capacity.updateOccupancy(false);
        check("Occupancy decreases after exit", capacity.getCurrentOccupancy() == 19);
        check("Gym has space after one member exits", capacity.checkCapacity());

        check("Wait time is the shortest remaining session",
                capacity.calculateWaitTime(Arrays.asList(25, 10, 40)) == 10);
        check("Empty remaining-time list returns zero",
                capacity.calculateWaitTime(Arrays.asList()) == 0);

        System.out.println("CapacityManagement tests completed.");
    }

    private static void check(String testName, boolean passed) {
        System.out.println((passed ? "PASS: " : "FAIL: ") + testName);
    }
}
