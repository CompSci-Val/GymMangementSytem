import java.util.ArrayList;
import java.util.List;

public class CapacityManagement {

    private final int MAX_CAPACITY = 2; //for sake of demo 

    private int currentOccupancy;
    private List<WaitlistEntry> waitlist;

    public CapacityManagement() {
        currentOccupancy = 0;
        waitlist = new ArrayList<>();
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public boolean checkCapacity() {
        return currentOccupancy < MAX_CAPACITY;
    }

    public void updateOccupancy(boolean entering) {
        if (entering) {
            if (checkCapacity()) {
                currentOccupancy++;
                System.out.println("Member entered successfully.");
            } else {
                System.out.println("Gym is at full capacity.");
                
            }
        } else if (currentOccupancy > 0) {
            currentOccupancy--;
            System.out.println("Member has exited the gym.");
        }
    }

    public void addToWaitlist(WaitlistEntry entry) {
        if (entry != null) {
            waitlist.add(entry);
            System.out.println("Member added to waiting list.");
        }
    }

    public int calculateWaitTime(List<Integer> remainingTimes) {
        if (remainingTimes == null || remainingTimes.isEmpty()) {
            return 0;
        }

        int shortestTime = remainingTimes.get(0);

        for (int time : remainingTimes) {
            if (time < shortestTime) {
                shortestTime = time;
            }
        }

        return shortestTime;
    }

    public WaitlistEntry getNextMember() {
        if (checkCapacity() && !waitlist.isEmpty()) {
            return waitlist.remove(0);
        }

        return null;
    }

    public void displayWaitlist() {
        if (waitlist.isEmpty()) {
            System.out.println("Waiting list is empty.");
        } else {
            for (WaitlistEntry entry : waitlist) {
                System.out.println(entry.getMemberId());
            }
        }
    }

    public void displayOccupancy() {
        System.out.println("Current Occupancy: "
                + currentOccupancy + "/" + MAX_CAPACITY);
    }
}
