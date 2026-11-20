
import java.util.ArrayList;
import java.util.Date;

public class MemberDatabase {
   // public Members member;

    public static ArrayList<Member> members;

    public MemberDatabase() {

        members = new ArrayList<>();

        // Sample members for demonstration
        members.add(new Member(6200001, "Coyesha Valentine",
                "Coyesha@gmail.com", "18764535765",
                new Date(105, 4, 15)));

        members.add(new Member(6200002, "Daniel Macwean",
                "Daniel@gmail.com", "18764535766",
                new Date(105, 5, 15)));

        members.add(new Member(6200003, "Bryan Seiveright",
                "Bryan@gmail.com", "18764535767",
                new Date(105, 6, 15)));
    }

    // Add a new member
    public void addMember(Member member) {
        members.add(member);
    }

    // Find a member using their ID
    public static Member getMemberById(int memberId) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        return null;
    }

    // Return all members
    public ArrayList<Member> getMembers() {
        return members;
    }//end of getMembers
}//end of MemberDatabase
