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

private Member(int id, String name, String email, String phoneNumber, Date dateOfBirth) {
// Constructor to initialize member attributes
// This constructore stores the member's information in the class variables
    this.id = id;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
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




}