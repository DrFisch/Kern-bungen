package A1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





public class A1 {
    public static void main(String[] args) {
        // Adressen für Max Mustermann
        Address maxPrimary = new Address("Primary Address", "Musterstraße 1", "Musterstadt", "12345");
        Address maxSecondary = new Address("Secondary Address", "Nebenstraße 2", "Musterstadt", "12345");
        
        // Adressen für Chris May
        Address chrisPrimary = new Address("Primary Address", "Hauptstraße 1", "Beispielstadt", "54321");
        Address chrisSecondary = new Address("Secondary Address", "Nebenstraße 2", "Beispielstadt", "54321");
        Address chrisSecret = new Address("Secret Address", "Geheimgasse 3", "Beispielstadt", "54321");

        // Adressen für Steve Clover
        Address stevePrimary = new Address("Primary Address", "Kleeweg 4", "Kleestadt", "67890");
        Address steveSecondary = new Address("Secondary Address", "Eichenweg 5", "Kleestadt", "67890");
        Address steveSecret = new Address("Secret Address", "Verborgenstraße 6", "Kleestadt", "67890");

        // Erstellen der Personen mit den jeweiligen Adressen
        Person max = new Person("Max", "Mustermann", 19, new Address[]{maxPrimary, maxSecondary});
        Person chris = new Person("Chris", "May", 21, new Address[]{chrisPrimary, chrisSecondary, chrisSecret});
        Person steve = new Person("Steve", "Clover", 25, new Address[]{stevePrimary, steveSecondary, steveSecret});

        // Liste der Personen
        List<Person> persons = new ArrayList<>(Arrays.asList(max, chris, steve));

        // Ausgabe der Personen
        persons.forEach(System.out::println);
    }
}

class Address {
    private String description;
    private String location;
    private String town;
    private String postalCode;

    public Address(String description, String location, String town, String postalCode) {
        this.description = description;
        this.location = location;
        this.town = town;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return description + ": " + location + ", " + town + ", " + postalCode + "\n";
    }
}

class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Address[] addresses;

    public Person(String firstName, String lastName, int age, Address[] addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + age + " Jahre)\n" + 
               "Adressen: " + Arrays.toString(addresses) + "\n";
    }
}
