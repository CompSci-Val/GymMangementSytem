public class Trainer {
    private int id;
    private String name;
    private String specialization;
    private boolean isAvailable;

    public Trainer(int id, String name) {
        this.id = id;
        this.name = name;
        this.isAvailable = true; // Default availability
    }
    public Trainer(int id, String name, String specialization, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.isAvailable = isAvailable;
    }
  public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
   
     

    public String getSpecialization() {
        return specialization;
    }
    public boolean isAvailable(String dateTime) {
        // Implement logic to check if the trainer is available at the given dateTime
    
        // For demonstration purposes, we'll assume the trainer is always available
        return isAvailable;
    }

    public void EditAvailability(boolean availability) {
        this.isAvailable = availability;

    }


}