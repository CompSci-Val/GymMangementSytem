//Purpose: Allow members to view trainer availability and manage bookings.
//Method	What the method should do
//viewTrainers()	Display available trainers.
//checkAvailability()	Check whether a trainer is available at the requested time.
//bookTrainer()	Create a booking for an available trainer.
//cancelBooking()	Cancel an existing booking.
//viewBookings()	Display a member's existing bookings.
import java.util.ArrayList;
public class TrainerBooking {
    private ArrayList<Trainer> trainers;

    public TrainerBooking() {
        // Constructor to initialize the TrainerBooking system
        this.trainers = new ArrayList<>();
    }
    // Method to view available trainers
    public void viewTrainers() {
        // Implementation to display available trainers
        return trainers;
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
        for(Trainer trainer :trainers){
            if( trainer isAvaliable())
     
            {system.out.println("Trainer "+" "+Trainer.getname()+"is availible for booking");}



        }
    }

    // Method to cancel a booking
    public void cancelBooking(int bookingId) {
        // Implementation to cancel an existing booking
    }

    // Method to view a member's bookings
    public void viewBookings(int memberId) {
        // Implementation to display a member's existing bookings
    }
}
