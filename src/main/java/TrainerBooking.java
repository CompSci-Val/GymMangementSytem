//Purpose: Allow members to view trainer availability and manage bookings.
//Method	What the method should do
//viewTrainers()	Display available trainers.
//checkAvailability()	Check whether a trainer is available at the requested time.
//bookTrainer()	Create a booking for an available trainer.
//cancelBooking()	Cancel an existing booking.
//viewBookings()	Display a member's existing bookings.
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class TrainerBooking {
    private static final int SESSION_Second = 60; // each session blocks the trainer for 1 hour

    private ArrayList<Trainer> trainers;
    private ArrayList<Booking> bookings;
    private Random random = new Random();
    

    public TrainerBooking() {
        // Share the list from TrainerDatabase (create the TrainerDatabase first!)
        this.trainers = (TrainerDatabase.trainer != null) ? TrainerDatabase.trainer : new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Method to view available trainers
    public void viewTrainers() {
        System.out.println("Available Trainers:");
        boolean any = false;
        for (Trainer trainer : trainers) {
            if (trainer.isAvailable()) {
                String spec = (trainer.getSpecialization() != null) ? trainer.getSpecialization() : "General";
                System.out.println("ID: " + trainer.getTrainerID() + ", Name: " + trainer.getName()
                        + ", Specialization: " + spec);
                any = true;
            }
        }
        if (!any) {
            System.out.println("No trainers are available right now.");
        }
    }

    private Trainer findTrainer(String trainerId) {
        for (Trainer trainer : trainers) {
            if (trainer.getTrainerID().equals(trainerId)) {
                return trainer;
            }
        }
        return null;
    }

    // Method to check trainer availability at the requested time
    public boolean checkAvailability(String trainerId, LocalDateTime dateTime) {
        Trainer trainer = findTrainer(trainerId);
        if (trainer == null || !trainer.isAvailable()) {
            return false;
        }
        // Not available if they already have a session within an hour of this time
        for (Booking booking : bookings) {
            if (booking.getTrainerID().equals(trainerId)) {
                long gap = Math.abs(Duration.between(booking.getBookingDateTime(), dateTime).toMinutes());
                if (gap < SESSION_Second) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bookingExists(String id) {
        for (Booking booking : bookings) {
            if (booking.getBookingID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private String generateBookingID() {
        String id;
        do {
            id = "B" + (1000 + random.nextInt(9000));
        } while (bookingExists(id));
        return id;
    }

    // Method to book a trainer
    public void bookTrainer(String memberId, String trainerId, LocalDateTime dateTime) {
        Trainer trainer = findTrainer(trainerId);
        if (trainer == null) {
            System.out.println("Trainer " + trainerId + " is not found ");
            return;}


        if(!checkAvailability(trainerId, dateTime)) {
            System.out.println("Trainer " + trainer.getName() + " is not available at that time.");
            return;
        }

        int memberNumber;
        try {
            memberNumber = Integer.parseInt(memberId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID.");
            return;
        }

        String bookingID = generateBookingID();
        bookings.add(new Booking(bookingID, memberNumber, trainerId, dateTime));
        System.out.println("Trainer session successfully booked with " + trainer.getName()
                + ". Booking ID: " + bookingID);
    }

    // Method to cancel a booking
    public void cancelBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingID().equalsIgnoreCase(bookingId)) {
                bookings.remove(booking);
                System.out.println("Booking with ID " + bookingId + " has been canceled.");
                return; // stop right after removing so the list isn't changed mid-loop
            }
        }
        System.out.println("Booking with ID " + bookingId + " not found.");
    }

    // Method to view a member's bookings
    public void viewBookings(int memberId) {
        boolean any = false;
        for (Booking booking : bookings) {
            if (booking.getMemberID() == memberId) {
                Trainer trainer = findTrainer(booking.getTrainerID());
                String trainerName = (trainer != null) ? trainer.getName() : booking.getTrainerID();
                System.out.println("Booking ID: " + booking.getBookingID()
                        + ", Trainer: " + trainerName
                        + ", DateTime: " + booking.getBookingDateTime()
                        + ", Status: " + booking.getStatus());
                any = true;
            }
        }
        if (!any) {
            System.out.println("No bookings found for member " + memberId + ".");
        }
    }
}


