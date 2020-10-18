package com.training.addressbookcsv;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

public class ContactPerson extends AddressBook {
    // Functions
    private static int NumOfPerson;

    public ContactPerson(String firstName, String lastName, String city, String state, int zip, String phoneNum, String email) {
        super(firstName, lastName, city, state, zip, phoneNum, email);
    }
    static void CreateContact(int numOfPerson, ArrayList<AddressBook> addArray) {
        for(int i=0;i<numOfPerson;i++){
            AddressBook add = AddContact();
            int count = (int) addArray.stream().filter(j->j.equals(add)).count();
            if(count>0)
                System.out.println("Dupicate Contacts not allowed");
            else
                addArray.add(add);
        }
    }
    static void EditContact(ArrayList<AddressBook> addArray) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the First Name Of Contact to Update ");
        String fn=sc.next();
        int pos=-1;
        for(int i=0;i<addArray.size();i++)
        {
            System.out.println(addArray.get(i).firstName);
            if(addArray.get(i).firstName.equals(fn))
                pos=i;
        }
        if(pos==-1)
            System.out.println("Not Found Contact Person");
        else {
            System.out.println("Choose the option to edit");
            System.out.println("1.Edit Last name");
            System.out.println("2.Edit State");
            System.out.println("3.Edit City");
            System.out.println("4.Edit Zip");
            System.out.println("5.Edit Phone Number");
            System.out.println("6.Edit Email");
            System.out.println("7.Exit");
            int option = sc.nextInt();
            if (option == 1)
                addArray.get(pos).lastName = (sc.next());
            else if (option == 2)
                addArray.get(pos).state = (sc.next());
            else if (option == 3)
                addArray.get(pos).city = (sc.next());
            else if (option == 4)
                addArray.get(pos).zip = (sc.nextInt());
            else if (option == 5)
                addArray.get(pos).phoneNum = (sc.next());
            else if (option == 6)
                addArray.get(pos).email = (sc.next());
            else
                return;
        }
    }
    static void DeleteContact(ArrayList<AddressBook> addArray) {
        int NumOfPerson=addArray.size();
        Scanner sc=new Scanner(System.in);
        if(addArray.size()==0)
        {
            System.out.println("No Element in address Book ");
        }
        else {
            System.out.println("Enter the First Name Of Contact to Delete ");
            String fn = sc.next();
            for (int i = 0; i < addArray.size(); i++) {
                if (addArray.get(i).firstName.equals(fn)) {
                    addArray.remove(i);
                    NumOfPerson--;
                    System.out.println("Contact Delete Successfully");
                }
            }
        }
        sc.close();
    }
    public static void displayContactPerson(ArrayList<AddressBook> list){
        for(int i=0;i<list.size();i++)
        {
            System.out.println("First Name : " + list.get(i).firstName);
            System.out.println("Last Name : "  + list.get(i).lastName);
            System.out.println("City : " + list.get(i).city);
            System.out.println("State : " + list.get(i).state);
            System.out.println("Zip Code : " + list.get(i).zip);
            System.out.println("Email : " + list.get(i).email);
            System.out.println("Phone Number : " + list.get(i).phoneNum);
            System.out.println();
        }
    }

    public static ArrayList<AddressBook> addContactPerson(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Number Of Persons");
        NumOfPerson=sc.nextInt();
        ArrayList<AddressBook> AddArray=new ArrayList<>();

        CreateContact(NumOfPerson,AddArray);

        for(int i=0;i<NumOfPerson;i++){
            System.out.println(AddArray.get(i).firstName);
        }
        return AddArray;
    }

}
