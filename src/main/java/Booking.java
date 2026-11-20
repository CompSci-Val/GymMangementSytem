public class Booking {
    public int Bookingid;
    public String memberName;
    public int memberId;    
    public String trainerName;
    public int trainerId;
    public String dateTime;

    public Booking(int Bookingid, int memberId, int trainerId, String dateTime) {
        this.Bookingid = Bookingid;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.dateTime = dateTime;
    }
    public Booking(String memberName, int memberId, String trainerName, int trainerId, String dateTime) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.trainerName = trainerName;
        this.trainerId = trainerId;
        this.dateTime = dateTime;
    }
   public int getBookingId() {
        return Bookingid;
    }

    public int getMemberId() {
        return memberId;
    }
    public String getMemberName() {
        return memberName;
    }
    public String getTrainerName() {
        return trainerName;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public String getDateTime() {
        return dateTime;
    }
}