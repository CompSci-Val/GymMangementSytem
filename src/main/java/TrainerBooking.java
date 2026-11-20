//Purpose: Allow members to view trainer availability and manage bookings.
//Method	What the method should do
//viewTrainers()	Display available trainers.
//checkAvailability()	Check whether a trainer is available at the requested time.
//bookTrainer()	Create a booking for an available trainer.
//cancelBooking()	Cancel an existing booking.
//viewBookings()	Display a member's existing bookings.
import java.util.ArrayList;
import java.util.Random;

public class TrainerBooking {
    private ArrayList<Trainer> trainers;
    private ArrayList<Booking> bookings;
    private Random random = new Random();

    public TrainerBooking() {
        // Constructor to initialize the TrainerBooking system
        this.trainers = new ArrayList<>();
    }

     public Trainer getTrainerById(int id) {
        // Implement logic to retrieve a trainer by their ID
          for (Trainer trainer : trainers) {
            if (trainer.getId() == id) {
                return trainer;
            }
        }
        return null;
    }
    // Method to view available trainers
    public void viewTrainers() {
        // Implementation to display available trainers
        System.out.println("Available Trainers:");
        for (Trainer trainer : trainers) {  
            System.out.println("ID: " + trainer.getId() + ", Name: " + trainer.getName() + ", Specialization: " + trainer.getSpecialization());
        }
        //return trainers;

    }

    // Method to check trainer availability
    public boolean checkAvailability(int trainerId, String dateTime) {
        for (Trainer trainer : trainers) {
            if (trainer.getId() == trainerId && trainer.isAvailable(dateTime)) {
                return true;
            }
        }
        // Implementation to check if the trainer is available at the requested time
        return false; // Placeholder return value
    }

    // Method to book a trainer
    public void bookTrainer(int memberId, int trainerId, String dateTime) {
        // Implementation to create a booking for an available trainer
        int bookinId = random.nextInt(1000); // Generate a random booking ID
        for(Trainer trainer :trainers){
            if( trainer.isAvailable(dateTime) )
     
            {System.out.println("Trainer "+" "+trainer.getName()+"is availible for booking\n");}
            bookings.add(new Booking(MemberDatabase.getMemberById(memberId).getName(),memberId, trainer.getName(), trainerId, dateTime));
            System.out.println("trainer session was sucessfully booked\n");
            




        }
    }

    // Method to cancel a booking
    public void cancelBooking(int bookingId) {
        // Implementation to cancel an existing booking
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
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
            if (booking.getMemberId() == memberId) {
                System.out.println("Booking ID: " + booking.getBookingId() + ", Trainer: " + booking.getTrainerName() + ", DateTime: " + booking.getDateTime());
            }
        }
    }
}
