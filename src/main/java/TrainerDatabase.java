import java.util.ArrayList;
import java.util.Date;

public class TrainerDatabase{
    public static ArrayList<Trainer> trainer;


    public TrainerDatabase(){
        trainer = new ArrayList<>();


        //sample trainer for demonstration
        trainer.add(new Trainer("5512", "Ajorney Lawrence"));

        trainer.add(new Trainer("7023", "Tori-Ann Valentine"));

        trainer.add(new Trainer("1234", "Kerresa McGann"));
    }
    public void addTrainers(Trainer trainer){
             this.trainer.add(trainer);
            }
        
     public Trainer getTrainerById(String id) {
        // Implement logic to retrieve a trainer by their ID
          for (Trainer trainers : trainer) {
            if (trainers.getTrainerID() == id) {
                return trainers;
            }
        }
        return null;
    }

  public ArrayList<Trainer> getTrainers() {
    return trainer;
}
    
}
