package com.training.addressbookcsv;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {
    private static HashMap<String, ArrayList<AddressBook>> addressBookMap = new HashMap<>();
    private static final Scanner sc =new Scanner(System.in);


    private static String path = "C:\\Users\\I524735\\IdeaProjects\\AddressBook-System";
    private static String directory = "Address Book Directory";
    private  static String Json_directory = "Address Book Json Directory";

    private static void deleteContact() {
        System.out.println(addressBookMap.keySet());
        System.out.println("Enter the name of the address book you want to edit");
        String name1 = sc.next();
        Iterator<String> itr1 = addressBookMap.keySet().iterator();
        ArrayList<AddressBook> listtmp1 = new ArrayList<>();
        while(itr1.hasNext())
        {
            String tmp = itr1.next();
            if(tmp.equals(name1))
                listtmp1 = addressBookMap.get(name1);
        }
        ContactPerson.DeleteContact(listtmp1);
        addressBookMap.put(name1, listtmp1);
    }

    private static void editContact() {
        System.out.println(addressBookMap.keySet());
        System.out.println("Enter the name of the address book you want to edit");
        String name1 = sc.next();
        Iterator<String> itr1 = addressBookMap.keySet().iterator();
        ArrayList<AddressBook> listtmp1 = new ArrayList<>();
        while(itr1.hasNext())
        {
            String tmp = itr1.next();
            if(tmp.equals(name1))
                listtmp1 = addressBookMap.get(name1);
        }
        ContactPerson.EditContact(listtmp1);
        addressBookMap.put(name1, listtmp1);
        System.out.println("Contact updated succesfully");
    }

    private static void addContact() {
        System.out.println(addressBookMap.keySet());
        System.out.println("Enter the name of the address book you want to add contact");
        String name = sc.next();
        Iterator<String> itr = addressBookMap.keySet().iterator();
        addressBookMap.put(name, ContactPerson.addContactPerson());
        System.out.println("Contact added successfully");
    }

    private static void addAddressBook() {
        System.out.println("Enter the name of the address book");
        String addressBookName = sc.next();
        ArrayList<AddressBook> list = new ArrayList<>();
        addressBookMap.put(addressBookName, list);
    }
    private static void displayContact() {
        System.out.println("Enter the name of the address book");
        String AddressBookName = sc.next();
        Iterator<String> itr3 = addressBookMap.keySet().iterator();
        ArrayList<AddressBook> list= new ArrayList<>();
        while(itr3.hasNext())
        {
            String tmp = itr3.next();
            if(tmp.equals(AddressBookName))
                list = addressBookMap.get(AddressBookName);
        }

        ContactPerson.displayContactPerson(list);
    }

    private static void viewByState() {
        System.out.println("Enter the state to view contacts");
        String state = sc.next();
        List<AddressBook> c = addressBookMap.get(state);
        for(int j=0 ;j < c.size();j++) {
            System.out.println(" Name "+c.get(j).firstName+" "+c.get(j).lastName);
        }
    }

    private static void viewByCity() {
        System.out.println("Enter the city to view contacts");
        String city = sc.next();
        List<AddressBook> c = addressBookMap.get(city);
        for(int j=0 ;j < c.size();j++) {
            System.out.println(c.get(j).city);
            System.out.println(" Name "+c.get(j).firstName+" "+c.get(j).lastName);
        }
    }

    private static void searchByCity() {
        System.out.println("Enter the state to search contacts");
        String city =sc.next();
        if(addressBookMap.isEmpty())
        {
            System.out.println("No AddressBook Exists, add new AddressBook First");
            System.exit(0);
        }
        for(Map.Entry<String, ArrayList<AddressBook>> ab : addressBookMap.entrySet()) {
            List<AddressBook> c = ab.getValue().stream().filter(i->i.city.equals(i.city)).collect(Collectors.toList());
            if(c.size() == 0)
                System.out.println("No entry with city name in addressbook "+ab.getKey());

            else
                for(int j=0 ;j< c.size();j++) {
                    System.out.println("AddressBook "+ab.getKey()+" Name "+c.get(j).firstName+" "+c.get(j).lastName);
                }
        }
    }

    private static void searchByState() {
        System.out.println("Enter the city to search contacts");
        String State = sc.next();
        if(addressBookMap.isEmpty())
        {
            System.out.println("No AddressBook Exists, add new AddressBook First");
            System.exit(0);
        }
        for(Map.Entry<String, ArrayList<AddressBook>> ab : addressBookMap.entrySet()) {

            List<AddressBook> c = ab.getValue().stream().filter(i->i.state.equals(i.state)).collect(Collectors.toList());

            if(c.size() == 0)
                System.out.println("No entry with state name in addressbook "+ab.getKey());

            else
                for(int j=0 ;j< c.size();j++)
                    System.out.println("AddressBook "+ab.getKey()+" Name "+c.get(j).firstName+" "+c.get(j).lastName);
        }
    }

    private static void viewSortedContactsInAddressBook() {
        System.out.println("Enter the address book to view its sorted contacts");
        String AddressBookName = sc.next();
        if(addressBookMap.get(AddressBookName) == null)
        {
            System.out.println("No addressBook with this name, enter correct address book");
            return;
        }
        addressBookMap.get(AddressBookName).stream().sorted((n1,n2) -> n1.firstName.compareTo(n2.firstName)).
                map(i->i.toString()).forEach(y-> System.out.println(y));
    }

    public static void viewSortedContactsByCityInAddressBook(String AddressBookName) {
        if(addressBookMap.get(AddressBookName) == null)
        {
            System.out.println("No addressBook with this name, enter correct address book");
            return;
        }
        addressBookMap.get(AddressBookName).stream().sorted(Comparator.comparing(AddressBook :: getCity)).
                map(i->i.toString()).forEach(y-> System.out.println(y));
    }

    public static void viewSortedContactsByStateInAddressBook(String AddressBookName) {
        if(addressBookMap.get(AddressBookName) == null)
        {
            System.out.println("No addressBook with this name, enter correct address book");
            return;
        }
        addressBookMap.get(AddressBookName).stream().sorted(Comparator.comparing(AddressBook :: getState)).
                map(i->i.toString()).forEach(y-> System.out.println(y));
    }

    public static void viewSortedContactsByZipInAddressBook(String AddressBookName) {
        if(addressBookMap.get(AddressBookName) == null)
        {
            System.out.println("No addressBook with this name, enter correct address book");
            return;
        }
        addressBookMap.get(AddressBookName).stream().sorted(Comparator.comparing(AddressBookName :: getZip)).
                map(i->i.toString()).forEach(y-> System.out.println(y));
    }

    public static void writeToAFile() throws IOException {
        Path directoryLoc = Paths.get(path + "\\addressbook\\" + directory);
        if (Files.notExists(directoryLoc)) {
            Files.createDirectory(directoryLoc);
        }

        Path fileLoc = Paths.get(directoryLoc + "\\Name" + ".txt");
        if (Files.notExists(fileLoc))
            Files.createFile(fileLoc);

        StringBuffer bufferList = new StringBuffer();
        addressBookMap.values().forEach(details -> {
            String data = details.toString().concat("\n");
            bufferList.append(data);
        });
        try {
            Files.write(fileLoc, bufferList.toString().getBytes());
            System.out.println("Contact added to the file successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromAFile(){
        Path directoryLoc1 = Paths.get(path + "\\addressbook\\" + directory);
        Path fileLoc1 = Paths.get(directoryLoc1 + "\\Name" + ".txt");
        try {
            System.out.println("The contacts in the all the address books are");
            Files.lines(fileLoc1).map(line -> line.trim()).forEach(line -> System.out.println(line));
        }
        catch(Exception e) {
            e.getMessage();
        }
    }

    public static void writeToACSV() throws IOException {
        System.out.println("Enter the name of the address book to add to csv file");
        ArrayList<AddressBook> listCsv = addressBookMap.get(sc.next());
        Path directoryLoc2 = Paths.get(path + "\\csvFile\\" + directory);
        if (Files.notExists(directoryLoc2)) {
            Files.createDirectory(directoryLoc2);
        }

        Path fileLoc2 = Paths.get(directoryLoc2 + "\\File" + ".csv");
        if (Files.notExists(fileLoc2))
            Files.createFile(fileLoc2);

        try (Writer writer = Files.newBufferedWriter(Paths.get(fileLoc2.toUri()));) {
            StatefulBeanToCsv<ArrayList<AddressBook>> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

            try {
                beanToCsv.write(addressBookMap.get(listCsv));
            } catch (CsvDataTypeMismatchException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFromACSV() throws IOException {
        Path pathLoc = Paths.get(path + "\\addressbook\\" + directory);
        Path fileLoc11 = Paths.get(pathLoc + "\\File" + ".csv");
        try (Reader reader = Files.newBufferedReader(Paths.get(fileLoc11.toUri()));) {

            CsvToBean<AddressBook> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(AddressBook.class).withIgnoreLeadingWhiteSpace(true).build();

            Iterator<AddressBook> AddressBookIterator = csvToBean.iterator();

            while (AddressBookIterator.hasNext()) {
                AddressBook contact = AddressBookIterator.next();
                System.out.println("Firstname : " + contact.firstName);
                System.out.println("Lastname : " + contact.lastName);
                System.out.println("City : " + contact.city);
                System.out.println("State : " + contact.state);
                System.out.println("Zip : " + contact.zip);
                System.out.println("Phone number : " + contact.phoneNum);
                System.out.println("Email : " + contact.email);
                System.out.println("**********************************");
            }
        }

    }

    private static void readFromAJSON() throws IOException {
        Path pathLocJsonread = Paths.get(path + "\\addressbook\\" + Json_directory);
        if (Files.notExists(pathLocJsonread))
            Files.createDirectory(pathLocJsonread);

        String SAMPLE_JSON_FILE1 = path + "\\addressbook\\" + Json_directory + "\\file_json"
                +".json";
        Gson gson1 = new Gson();

        BufferedReader br = new BufferedReader(new FileReader(SAMPLE_JSON_FILE1));
        AddressBook[] contact = gson1.fromJson(br, AddressBook[].class);
        List<AddressBook> contactList = Arrays.asList(contact);
        for (AddressBook a : contactList) {
            System.out.println("Firstname : " + a.firstName);
            System.out.println("Lastname : " + a.lastName);
            System.out.println("City : " + a.city);
            System.out.println("State : " + a.state);
            System.out.println("Zip : " + a.zip);
            System.out.println("Phone number : " + a.phoneNum);
            System.out.println("Email : " + a.email);
            System.out.println("**********************************");
        }
    }

    private static void writeToAJSON() throws IOException {
        System.out.println("Enter the name of the address book to add to csv file");
        ArrayList<AddressBook> listGson = addressBookMap.get(sc.next());
        Path pathLocJson = Paths.get(path + "\\addressbook\\" + Json_directory);
        if (Files.notExists(pathLocJson))
            Files.createDirectory(pathLocJson);

        String SAMPLE_JSON_FILE = path + "\\eclipse-workspace\\AddressBookLib\\" + Json_directory + "\\Json directory"
                + ".json";
        Gson gson = new Gson();
        String json = gson.toJson(listGson);
        FileWriter writer = new FileWriter(SAMPLE_JSON_FILE);
        writer.write(json);
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Address Book");
        int choice = 1;
        while(choice!=0)
        {
            System.out.println("Enter 1 to Create Address Book \n2 edit in Address Book \n3. delete in Address Book\n"
                    +"4. Add in Address Book  \n5. display Address Book \n 6.Search By City \n 7.Search By State \n" +
                    " 8.View By City \n 9.View By State \n 10.View Alphabetically sorted contacts in a particular address book " +
                    "\n 11.View Alphabetically sorted contacts in a particular address book by city \n" +
                    "12.View Alphabetically sorted contacts in a particular address book by State " +
                    "\n13.View Alphabetically sorted contacts in a particular address book by Zip " +
                    "\n14.Write To a File \n15.Read From a File \n16.Write to CSV \n17.Read From CSV " +
                    "\n18.Write to a JSON \n19.Read From a JSON0 to exit");
            choice = sc.nextInt();
            switch(choice) {
                case 1 :
                    addAddressBook();
                    break;
                case 2 :
                    editContact();
                    break;
                case 3:
                    deleteContact();
                    break;
                case 4:
                    addContact();
                    break;
                case 5:
                    displayContact();
                case 6:
                    searchByCity();
                    break;
                case 7:
                    searchByState();
                    break;
                case 8:
                    viewByCity();
                    break;
                case 9:
                    viewByState();
                    break;
                case 10:
                    viewSortedContactsInAddressBook();
                    break;
                case 11:
                    viewSortedContactsByCityInAddressBook();
                    break;
                case 12:
                    viewSortedContactsByStateInAddressBook();
                    break;
                case 13:
                    viewSortedContactsByZipInAddressBook();
                    break;
                case 14:
                    writeToAFile();
                    break;
                case 15:
                    readFromAFile();;
                    break;
                case 16:
                    writeToACSV();
                    break;
                case 17:
                    readFromACSV();
                    break;
                case 18:
                    writeToAJSON();
                    break;
                case 19:
                    readFromAJSON();
                    break;

                default :
                    System.out.println("Invalid Input ");
                    break;
            }
        }
    }

}
