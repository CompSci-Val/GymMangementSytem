
import java.util.LinkedList;
import java.util.Queue; 


public class CheckInandOut {

    // Global queue to store members currently in the gym
    public Member member;
    public static Queue<Member> memberQueue = new LinkedList<>();
    //private static CapacityManagement capacity;    
    
    
 
    public static Member findMember(int ID) {
        // Search for the member in the database or list of members
        // Return the member object if found, otherwise return null
        // For demonstration purposes, let's assume we have a simple list of members
        return MemberDatabase.getMemberById(ID);
    }
    public static boolean isInside(int ID) {
        // Check if the member is currently inside the gym
        for (Member member : memberQueue) {
            if (member.getId() == ID) {
                return true;
            }
        }
        return false;
    }
    
  


    // Method to check in a member
    public static void checkIn(Member member) {
        if (!memberQueue.contains(member)) {
            memberQueue.add(member);
            System.out.println("Member " + member.getName() + " checked in.");
           // capacity.updateOccupancy(true);
        } else {
            System.out.println("Member " + member.getName() + " is already checked in.");
            
        }
    }
    // Method to check out a member
    public static void checkOut(Member member) {
        if (memberQueue.contains(member)) {
            memberQueue.remove(member);
            System.out.println("Member " + member.getName() + " checked out.");
           // Menu.capacity.updateOccupancy(false);
        } else {
            System.out.println("Member " + member.getName() + " is not checked in.");
        }
    




    }
}




