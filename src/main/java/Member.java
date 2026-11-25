import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.Date;

public class Member {


   

private int id;
private String name;
private String email;
private String phoneNumber;
private Date dateOfBirth;
private String password;
public Member(int id, String name, String email, String phoneNumber, Date dateOfBirth, String password) {
// Constructor to initialize member attributes
// This constructore stores the member's information in the class variables
    this.id = id;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.password = password;
}

public int getId() {
    return id;}


public String getName() {
    return name;}

public String getEmail() {
    return email;}

public String getPhoneNumber() {
    return phoneNumber;}    
public Date getDateOfBirth() {
    return dateOfBirth;}
public String getpassword(){
    return password;
}

public void setId(int id) {
    this.id = id;}

public void setName(String name) {
    this.name = name;}
public void setEmail(String email) {    
    this.email = email;}            
public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;}    
public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;}
public void setPassword(String password)
{this.password=password;}
 public String calculateAge() {
    Date currentDate = new Date();
    int age = currentDate.getYear() - dateOfBirth.getYear();
    if (currentDate.getMonth() < dateOfBirth.getMonth() || 
        (currentDate.getMonth() == dateOfBirth.getMonth() && currentDate.getDate() < dateOfBirth.getDate())) {
        age--;
    }
    return String.valueOf(age);



}//end of calculateAge
public boolean login(int enteredId, String enteredPassword) {
    return this.id == enteredId &&
           this.password.equals(enteredPassword);
}


}//end of Member class