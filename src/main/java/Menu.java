import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main menu for the Gym Management System.
 * One shared Scanner is used for all input (don't create other Scanners on System.in).
 */
public class Menu {

    private static final Scanner input = new Scanner(System.in);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "gym123";

   // private static final String STAFF_USERNAME = "staff";
    //private static final String STAFF_PASSWORD = "staff123";
    private static MemberDatabase memberDb;
    private static Member currentMember;
    private static String role;
    public static CapacityManagement capacity;
    private static TrainerBooking trainerBooking;
    private static CheckInandOut  CIO;
    private static List<Integer> remaining = new ArrayList<>();

    // Active gym sessions (memberId -> timer) and the full attendance history
    private static final Map<String, TimeTracking> sessions = new LinkedHashMap<>();
    private static final List<Attendance> attendanceLog = new ArrayList<>();
    // IDs currently on the waiting list (stops the same member joining twice)
    private static final Set<String> waitingIds = new HashSet<>();
    private String pleasework;

    public static void main(String[] args) {
        // The databases fill their static lists in their constructors, so create them first
        //if sign in not valid it exits program
        
        memberDb = new MemberDatabase();
       
        new TrainerDatabase();
        startWarningChecker();

        // Trainers made with the 2-argument constructor start as unavailable, so switch them on
        for (Trainer t : TrainerDatabase.trainer) {
            t.setAvailable(true);
        }

        capacity = new CapacityManagement();
        trainerBooking = new TrainerBooking();
        
        boolean running = true;
        while (running) {
          role =login();
        boolean loggedIn = true;
        while (loggedIn){
            
            if (role.equals("member")) {
        PersonalMenu();
             }      
             else{
                printMainMenu();
            
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    memberMenu();
                    break;
                case 2:
                    scanID();
                    break;
                case 3:
                    capacityMenu();
                    break;
                case 4:
                    viewTimeTracking();
                    break;
                case 5:
                    trainerMenu();
                    break;
                case 6:
                    viewAttendance();
                    break;
                case 7:
                    System.out.println("signing out...");
                    loggedIn=false;
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }}
        

    }
    }
    private static boolean signIn() {
       final String ADMIN_USER = "admin";
        final String ADMIN_PASS = "gym123";

        System.out.println("===== GYM MANAGEMENT SYSTEM LOGIN =====");

         System.out.print("Username: ");
        String username = input.nextLine().trim();

    System.out.print("Password: ");
    String password = input.nextLine().trim();

    if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
        System.out.println("Login successful!");
        return true;
    }

    System.out.println("Invalid username or password.");
    return false;
}

    // ------------------------------------------------------------------
    // MENUS
    // ------------------------------------------------------------------
    private static String login() {
    System.out.println("========================================");
    System.out.println("       GYM MANAGEMENT SYSTEM");
    System.out.println("========================================");
    System.out.println("Please sign in.");

    while (true) {
        String username = readNonEmpty("Username: ");
        String password = readNonEmpty("Password: ");

        if (username.equals(ADMIN_USERNAME)
                && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Welcome, Admin and Staff!");
            return "admin";
        }

        //if (username.equals(STAFF_USERNAME)
             //   && password.equals(STAFF_PASSWORD)) {
            //System.out.println("Welcome, Staff!");
           // return "staff";
        //}
         // Member login
        try {
            int memberId = Integer.parseInt(username);

            Member member = MemberDatabase.getMemberById(memberId);

            if (member != null && member.login(memberId, password)) {
                currentMember = member;
                System.out.println("Welcome, " + member.getName() + "!");
                return "member";
            }

        } catch (NumberFormatException e) {
            // Username was not a member ID
        }

        System.out.println("Invalid username or password. Try again.");
    }
}
    private static void printMainMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         GYM MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println(" 1. Member Management");
        System.out.println(" 2. Check In / Check Out");
        System.out.println(" 3. Capacity & Waiting List");
        System.out.println(" 4. Session Time Tracking");
        System.out.println(" 5. Trainer Booking");
        System.out.println(" 6. Attendance Records");
        System.out.println(" 7. Sign out");
        System.out.println(" 0. Exit");
        System.out.println("----------------------------------------");
    }
    

    private static void memberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- MEMBER MANAGEMENT ---");
            System.out.println(" 1. View all members");
            System.out.println(" 2. Search member by ID");
            System.out.println(" 3. Add a new member");
            System.out.println(" 0. Back");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    viewAllMembers();
                    break;
                case 2:
                    searchMember();
                    break;
                case 3:
                    addMember();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void capacityMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();

            System.out.println("--- CAPACITY & WAITING LIST ---");
            System.out.println(" 1. View current occupancy");
            System.out.println(" 2. View waiting list");
            System.out.println(" 3. Admit next member from waiting list");
            System.out.println(" 0. Back");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    capacity.displayOccupancy();
                    break;
                case 2:
                    viewWaitlist();
                    break;
                case 3:
                    if (role.equals("member")){System.out.println("Only Admin ia allowed to acess this");}
                    else{
                    if (!admitNextFromWaitlist()) {
                        System.out.println("No one admitted (waiting list is empty or the gym is full).");
                    }}
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void PersonalMenu(){
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- MEMBER Menu---");
            System.out.println(" 1. Check in");
            System.out.println(" 2. Check out");
            System.out.println(" 3. Book Trainer ");
            System.out.println(" 4. View session time remaining");

            System.out.println(" 0. signOut");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    doCheckIn(currentMember);
                    break;
                case 2:
                  doCheckOut(currentMember);
                    break;
                case 3:
                  trainerMenu();
                    break;
                case 4:
                viewTimeTracking();
                break;
               
                
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    

    private static void trainerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- TRAINER BOOKING ---");
            System.out.println(" 1. View available trainers");
            System.out.println(" 2. Book a trainer");
            System.out.println(" 3. Cancel a booking");
            System.out.println(" 4. View a member's bookings");
            System.out.println(" 0. Back");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    trainerBooking.viewTrainers();
                    break;
                case 2:
                    bookTrainer();
                    break;
                case 3:
                    String bookingId = readNonEmpty("Booking ID to cancel: ");
                    trainerBooking.cancelBooking(bookingId);
                    break;
                case 4:
                 if (role.equals("member")) {
                   trainerBooking.viewBookings(currentMember.getId());
         } else {
                    int memberId = readInt("Member ID: ");
                    trainerBooking.viewBookings(memberId);}
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ------------------------------------------------------------------
    // MEMBERS
    // ------------------------------------------------------------------

    private static void viewAllMembers() {
        System.out.println();
        System.out.println("--- ALL MEMBERS ---");
        for (Member m : memberDb.getMembers()) {
            printMember(m);
        }
    }

    private static void searchMember() {
        int id = readInt("Enter member ID: ");
        Member member = MemberDatabase.getMemberById(id);
        if (member == null) {
            System.out.println("Member not found.");
        } else {
            printMember(member);
        }
    }

    private static void printMember(Member m) {
        System.out.println("ID:      " + m.getId());
        System.out.println("Name:    " + m.getName());
        System.out.println("Email:   " + m.getEmail());
        System.out.println("Phone:   " + m.getPhoneNumber());
        System.out.println("Age:     " + m.calculateAge());
        System.out.println("Status:  " + (CheckInandOut.isInside(m.getId()) ? "In the gym" : "Not in the gym"));
        System.out.println("----------------------------------------");
    }

    private static void addMember() {
        int id = readInt("New member ID: ");
        if (MemberDatabase.getMemberById(id) != null) {
            System.out.println("A member with that ID already exists.");
            return;
        }
        String name = readNonEmpty("Full name: ");
        String email = readNonEmpty("Email: ");
        String phone = readNonEmpty("Phone number: ");
        Date dob = readDate("Date of birth (yyyy-MM-dd): ");
        String password = readNonEmpty("Create password: ");
        memberDb.addMember(new Member(id, name, email, phone, dob ,password));
        System.out.println("Member " + name + " added successfully.");
    }

    // ------------------------------------------------------------------
    // CHECK IN / CHECK OUT
    // ------------------------------------------------------------------

    private static void scanID() {
    int id = readInt("Scan / enter member ID: ");
    Member member = CheckInandOut.findMember(id);

    if (member == null) {
        System.out.println("Invalid ID. Member not found.");
    } 
    else if (CheckInandOut.isInside(id)) {
        doCheckOut(member);
    } 
    else if (!capacity.checkCapacity()) {
        handleFullGym(member);
    } 
    else {
        doCheckIn(member);
    }
}

    public static void handleFullGym(Member member) {
        String key = String.valueOf(member.getId());
        System.out.println("The gym is currently full.");

        if (waitingIds.contains(key)) {
            System.out.println(member.getName() + " is already on the waiting list.");
            return;
        }
        
        System.out.println("The current wait time is " + capacity.calculateWaitTime(remaining) + " Seconds");
        String answer = readLine("Join the waiting list? (y/n): ");
         
        if (answer.equalsIgnoreCase("y")) {
            capacity.addToWaitlist(new WaitlistEntry(key));
            waitingIds.add(key);
        }
    }

    private static void doCheckIn(Member member) {
        String key = String.valueOf(member.getId());

        CheckInandOut.checkIn(member);
        capacity.updateOccupancy(true);
        attendanceLog.add(new Attendance(key));

        TimeTracking session = new TimeTracking(key);
        session.startSession();
        sessions.put(key, session);

        System.out.println("Session started. Time allowed: " + (TimeTracking.DEFAULT_SESSION_Seconds)/60+ " minutes.");
    }

    private static void doCheckOut(Member member) {
        String key = String.valueOf(member.getId());

        CheckInandOut.checkOut(member);
        capacity.updateOccupancy(false);

        // Close this member's open attendance record
        for (int i = attendanceLog.size() - 1; i >= 0; i--) {
            Attendance a = attendanceLog.get(i);
            if (a.getMemberId().equals(key) && a.getCheckOutTime() == null) {
                a.recordCheckOut();
                System.out.println("Time spent in gym: " + a.getTotalTimeSpentSeconds() + " Seconds.");
                break;
            }
        }

        // Stop their timer
        TimeTracking session = sessions.remove(key);
        if (session != null && session.isActive()) {
            session.endSession();
        }

        // A spot just opened up, so let the next waiting member in
        admitNextFromWaitlist();
    }

    // ------------------------------------------------------------------
    // CAPACITY / WAITLIST
    // ------------------------------------------------------------------

    private static boolean admitNextFromWaitlist() {
        WaitlistEntry next = capacity.getNextMember();
        if (next == null) {
            return false;
        }

        waitingIds.remove(next.getMemberId());
        Member member = MemberDatabase.getMemberById(Integer.parseInt(next.getMemberId()));
        if (member == null) {
            return false;
        }

        System.out.println("Next on the waiting list: " + member.getName()
                + " (waited " + next.getWaitTimeSeconds() + " min)");
        doCheckIn(member);
        return true;
    }

    private static void viewWaitlist() {
        System.out.println("Member IDs on the waiting list (first in line at the top):");
        capacity.displayWaitlist();

        if (!waitingIds.isEmpty()) {
            for (TimeTracking t : sessions.values()) {
                if (t.isActive()) {
                    remaining.add((int) t.getRemainingTime());
                }
            }
            System.out.println("Estimated wait for the next free spot: "
                    + capacity.calculateWaitTime(remaining) + " Seconds");
        }
    }

    // ------------------------------------------------------------------
    // TIME TRACKING
    // ------------------------------------------------------------------

    private static void viewTimeTracking() {
        List<TimeTracking> active = new ArrayList<>();
        for (TimeTracking t : sessions.values()) {
            if (t.isActive()) {
                active.add(t);
            }
        }

        if (active.isEmpty()) {
            System.out.println("Nobody is currently in the gym.");
            return;
        }

        Collections.sort(active); // least time remaining first
        System.out.println();
        System.out.println("--- MEMBERS IN THE GYM (least time remaining first) ---");
        for (TimeTracking t : active) {
            System.out.printf("%-22s ID %s | %d seconds left | in for %d seconds%n",
                    nameOf(t.getMemberId()), t.getMemberId(),
                    t.getRemainingTime(), t.getSessionDurationSeconds());
            t.displayWarning(); // prints 45 / 30 / 5 seconds  and expiry warnings once each
        }
    }
    private static void startWarningChecker() {
    ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });

    scheduler.scheduleAtFixedRate(() -> {
        for (TimeTracking t : new ArrayList<>(sessions.values())) {
            if (t.isActive()) {
                t.displayWarning();
            }
        }
    }, 1, 1, TimeUnit.SECONDS);
}
    // ------------------------------------------------------------------
    // ATTENDANCE
    // ------------------------------------------------------------------

    private static void viewAttendance() {
        if (attendanceLog.isEmpty()) {
            System.out.println("No attendance records yet.");
            return;
        }

        System.out.println();
        System.out.println("--- ATTENDANCE RECORDS ---");
        for (Attendance a : attendanceLog) {
            String out = (a.getCheckOutTime() != null)
                    ? a.getCheckOutTime().format(DATE_TIME_FORMAT)
                    : "still inside";
            System.out.printf("%s (%s) | in: %s | out: %s | %d seconds%n",
                    nameOf(a.getMemberId()), a.getMemberId(),
                    a.getCheckInTime().format(DATE_TIME_FORMAT), out,
                    a.getTotalTimeSpentSeconds());
        }
    }

    // ------------------------------------------------------------------
    // TRAINER BOOKING
    // ------------------------------------------------------------------

    private static void bookTrainer() {
        int memberId = readInt("Member ID: ");
        if (MemberDatabase.getMemberById(memberId) == null) {
            System.out.println("Member not found.");
            return;
        }

        trainerBooking.viewTrainers();
        String trainerId = readNonEmpty("Trainer ID: ");

        LocalDateTime when = readDateTime("Session date and time (yyyy-MM-dd HH:mm): ");

        if (when.isBefore(LocalDateTime.now())) {
            System.out.println("The booking time must be in the future.");
            return;
        }

        trainerBooking.bookTrainer(String.valueOf(memberId), trainerId, when);
    }

    // ------------------------------------------------------------------
    // INPUT HELPERS
    // ------------------------------------------------------------------

    private static String nameOf(String memberId) {
        try {
            Member m = MemberDatabase.getMemberById(Integer.parseInt(memberId));
            return (m != null) ? m.getName() : "Unknown";
        } catch (NumberFormatException e) {
            return "Unknown";
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Date readDate(String prompt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        while (true) {
            String line = readLine(prompt);
            try {
                Date date = format.parse(line);
                if (date.after(new Date())) {
                    System.out.println("Date of birth cannot be in the future.");
                } else {
                    return date;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date. Use the format yyyy-MM-dd (e.g. 2005-04-15).");
            }
        }
    }

    private static LocalDateTime readDateTime(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return LocalDateTime.parse(line, DATE_TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use yyyy-MM-dd HH:mm (e.g. 2026-12-01 14:30).");
            }
        }
    }
}
