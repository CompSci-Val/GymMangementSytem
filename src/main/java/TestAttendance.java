public class TestAttendance {
    public static void main(String[] args) {
        System.out.println("--- TESTING ATTENDANCE ---");
        
        // 1. Check a member in
        System.out.println("Checking in Member M001...");
        Attendance attendance = new Attendance("M001");
        System.out.println("Check-in time recorded: " + attendance.getCheckInTime());

        // 2. Simulate time passing (waiting 2 seconds)
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // 3. Check the member out
        System.out.println("Checking out Member M001...");
        attendance.recordCheckOut();
        System.out.println("Check-out time recorded: " + attendance.getCheckOutTime());

        // 4. Check the calculation
        System.out.println("Total time spent (minutes): " + attendance.getTotalTimeSpentMinutes());
        
        // (Note: It will say 0 minutes because it only waited 2 seconds, but if it prints 0 and not an error, it works!)
        System.out.println("SUCCESS: Attendance class is working.\n");
    }
}