//Purpose: Allow members to view trainer availability and manage bookings.
//Method	What the method should do
//viewTrainers()	Display available trainers.
//checkAvailability()	Check whether a trainer is available at the requested time.
//bookTrainer()	Create a booking for an available trainer.
//cancelBooking()	Cancel an existing booking.
//viewBookings()	Display a member's existing bookings.
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;

public class TrainerBooking {
    private ArrayList<Trainer> trainers;
    private ArrayList<Booking> bookings;
    private Random random = new Random();

    public TrainerBooking() {
        // Constructor to initialize the TrainerBooking system
        this.trainers = new ArrayList<>();
    }

    // Method to view available trainers
    public void viewTrainers() {
        // Implementation to display available trainers
        System.out.println("Available Trainers:");
        for (Trainer trainer : trainers) {  
            System.out.println("ID: " + trainer.getTrainerID() + ", Name: " + trainer.getName() + ", Specialization: " + trainer.getSpecialization());
        }
        //return trainers;

    }

    // Method to check trainer availability
    public boolean checkAvailability(String trainerId, String dateTime) {
        for (Trainer trainer : trainers) {
           if (trainer.getTrainerID().equals(trainerId) && trainer.isAvailable()) {
                return true;
            }
        }
        // Implementation to check if the trainer is available at the requested time
        return false; // Placeholder return value
    }
    private String generateBookingID() {
    return "B" +(1000 + random.nextInt(9000));
    }

    // Method to book a trainer
    public void bookTrainer(String memberId, String trainerId, LocalDateTime dateTime) {
        // Implementation to create a booking for an available trainer
        String bookingID = generateBookingID(); // Generate a random booking ID
        for(Trainer trainer :trainers){
            if( trainer.isAvailable() )
     
            {System.out.println("Trainer "+" "+trainer.getName()+"is availible for booking\n");}
            bookings.add(new Booking(bookingID , Integer.parseInt(memberId), trainerId, dateTime));
            System.out.println("trainer session was sucessfully booked\n");
            




        }
    }

    // Method to cancel a booking
    public void cancelBooking(String bookingId) {
        // Implementation to cancel an existing booking
        for (Booking booking : bookings) {
            if (booking.getBookingID() == bookingId) {
                bookings.remove(booking);
                System.out.println("Booking with ID " + bookingId + " has been canceled.");
                return;
            }
        }
        System.out.println("Booking with ID " + bookingId + " not found.");

    }

    // Method to view a member's bookings
    public void viewBookings(int memberId) {
        // Implementation to display a member's existing bookings
        for (Booking booking : bookings) {
            if (booking.getMemberID() == memberId) {
                System.out.println("Booking ID: " + booking.getMemberID() + ", Trainer: " + booking.getTrainerID() + ", DateTime: " + booking.getBookingDateTime());
            }
        }
    }
}
