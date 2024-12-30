package A2;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class A2 {
    public static void main(String[] args) {
        // Erstellen der Adressen für Personen
        Address maxPrimary = new Address("Primary Address", "Teststreet 1", "Hof", "95028");
        Address maxSecondary = new Address("Secondary Address", "Teststreet 2", "Hof", "95028");
        
        Address chrisPrimary = new Address("Primary Address", "Teststreet 19", "Hof", "95030");
        Address chrisSecondary = new Address("Secondary Address", "Teststreet 20", "Hof", "95028");
        Address chrisSecret = new Address("Secret Address", "Secretstreet 1", "Bayreuth", "95444");

        Address stevePrimary = new Address("Primary Address", "Teststreet 19", "Hof", "95030");
        Address steveSecondary = new Address("Secondary Address", "Teststreet 20", "Hof", "95028");
        Address steveSecret = new Address("Secret Address", "Secretstreet 1", "Bayreuth", "95444");

        // Erstellen der Personen-Objekte mit Listen von Adressen
        Person max = new Person("Max", "Mustermann", 19, Arrays.asList(maxPrimary, maxSecondary));
        Person chris = new Person("Chris", "May", 21, Arrays.asList(chrisPrimary, chrisSecondary, chrisSecret));
        Person steve = new Person("Steve", "Clover", 25, Arrays.asList(stevePrimary, steveSecondary, steveSecret));

        List<Person> persons = List.of(max, chris, steve);

        // Aufrufen der printPersons-Methode
        printPersons("Alle Personen", persons);
    }

    private static void printPersons(String header, List<Person> persons) {
        System.out.println(header);
        
        persons.forEach(person -> {
            // Ausgabe des Namens und Alters
            String fullName = person.getFirstName().orElse("N/A") + " " + person.getLastName().orElse("N/A");
            System.out.print(fullName + " (" + person.getAge() + ") [");

            // Adressen verarbeiten und formatieren
            String addresses = person.getAddresses().stream()
                .map(address -> address.getDescription().orElse("N/A") + " (" +
                                address.getLocation().orElse("N/A") + ", " +
                                address.getTown().orElse("N/A") + ", " +
                                address.getPostalCode().orElse("N/A") + ")")
                .reduce((a, b) -> a + ", " + b) // Verkettet Adressen mit Kommas
                .orElse("Keine Adresse");

            System.out.println(addresses + "]");
        });
    }
}

// Address-Klasse mit Optional-Werten
class Address {
    private Optional<String> description;
    private Optional<String> location;
    private Optional<String> town;
    private Optional<String> postalCode;

    public Address(String description, String location, String town, String postalCode) {
        this.description = Optional.ofNullable(description);
        this.location = Optional.ofNullable(location);
        this.town = Optional.ofNullable(town);
        this.postalCode = Optional.ofNullable(postalCode);
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getLocation() {
        return location;
    }

    public Optional<String> getTown() {
        return town;
    }

    public Optional<String> getPostalCode() {
        return postalCode;
    }
}

// Person-Klasse mit Optional und Liste von Address-Objekten
class Person {
    private String firstName;
    private String lastName;
    private int age;
    private List<Address> addresses;

    public Person(String firstName, String lastName, int age, List<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addresses = addresses != null ? addresses : List.of();
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    public int getAge() {
        return age;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
