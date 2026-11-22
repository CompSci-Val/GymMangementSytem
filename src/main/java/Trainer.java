public class Trainer {

    private String trainerID;
    private String name;
    private String specialization;
    private boolean available;

    public Trainer(String trainerID, String name,
                   String specialization) {
        this.trainerID = trainerID;
        this.name = name;
        this.specialization = specialization;
        this.available = true;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayTrainer() {
        System.out.println("Trainer ID: " + trainerID);
        System.out.println("Name: " + name);
        System.out.println("Specialization: " + specialization);
        System.out.println("Available: " + available);
    }
}
