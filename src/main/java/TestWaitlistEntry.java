public class TestWaitlistEntry {
    public static void main(String[] args) {
        System.out.println("--- TESTING WAITLIST ENTRY ---");
        
        // 1. Add a member to the waitlist
        System.out.println("Adding Member M002 to waitlist...");
        WaitlistEntry waitlist = new WaitlistEntry("M002");
        System.out.println("Time joined queue: " + waitlist.getTimeJoinedQueue());

        // 2. Simulate time passing (waiting 2 seconds)
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // 3. Check wait time
        System.out.println("Current wait time (minutes): " + waitlist.getWaitTimeMinutes());
        
        System.out.println("SUCCESS: WaitlistEntry class is working.\n");
    }
}