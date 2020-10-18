package com.training.addressbookcsv;
import java.util.Scanner;

public class AddressBook {
    String firstName;
    String lastName;
    String city;
    String state;
    int zip;
    String phoneNum;
    String email;


    public AddressBook(String firstName, String lastName, String city, String state, int zip, String phoneNum, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNum = phoneNum;
        this.email = email;
    }
    static AddressBook AddContact(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter First Name ");
        String firstName=sc.next();

        System.out.println("Enter Last Name ");
        String lastName=sc.next();

        System.out.println("Enter City ");
        String city=sc.next();

        System.out.println("Enter State ");
        String state=sc.next();

        System.out.println("Enter Zip Code ");
        Integer zip=sc.nextInt();

        System.out.println("Enter phone Number ");
        String phoneNum=sc.next();

        System.out.println("Enter Email ");
        String email=sc.next();

        return new AddressBook(firstName,lastName,city,state,zip,phoneNum,email);
    }
}
