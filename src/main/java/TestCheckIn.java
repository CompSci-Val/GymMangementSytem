
public class TestCheckIn {

    public static void main(String[] args) {

        // Create the member database
        MemberDatabase database = new MemberDatabase();

        // Display registered members
        System.out.println("REGISTERED MEMBERS");

        for (Member member : database.getMembers()) {
            System.out.println(member.getId() + " - " + member.getName());
        }

        // Test member lookup
        System.out.println("\nTESTING MEMBER LOOKUP");

        Member testMember = database.getMemberById(6200001);

        if (testMember != null) {
            System.out.println("Member found: " + testMember.getName());
                 } else {
            System.out.println("Member not found.");
        }
    }
}
