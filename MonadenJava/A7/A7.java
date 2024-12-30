package A7;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class A7 {
    public static void main(String[] args) {
        Person max = new Person("Max","Mustermann",19,
            new Address[]{
            new Address("Primary Address", "Musterstraße 1", "Musterstadt", "12345"),
            new Address("Secondary Address", "Nebenstraße 2", "Musterstadt", "12345")});
        Person chris=new Person("Chris","May",21,
            new Address[]{
            new Address("Primary Address", "Hauptstraße 1", "Beispielstadt", "54321"),
            new Address("Secondary Address", "Nebenstraße 2", "Beispielstadt", "54321"),
            new Address("Secret Address", "Geheimgasse 3", "Beispielstadt", "54321")});
        Person steve=new Person("Steve","Clover",25,
            new Address[]{
            new Address("Primary Address", "Kleeweg 4", "Kleestadt", "67890"),
            new Address("Secondary Address", "Eichenweg 5", "Kleestadt", "67890"),
            new Address("Secret Address", "Verborgenstraße 6", "Kleestadt", "67890")});
        List<Person> persons=List.of(max,chris,steve);
                
        // a) mindestens eine Primäradresse hat
        var allHavePrimaryAddress = persons.stream()
    .allMatch(person -> Arrays.stream(person.addresses())
    .anyMatch(y -> "Primary Address".equals(y.description())));
    
        System.out.println("Jede Person hat mindestens eine Primäradresse: " + allHavePrimaryAddress);
                

        // b) ob mindestens eine Person mindestens eine Secret Address hat
        //boolean oneHaveSecretAddress =persons.stream()
        
    }
    private static void printpersons(String header, List<? extends Object> persons){
        System.out.println(header);
        for (var person:persons){
            System.out.println(person);
        }
        System.out.println();
    }
}
record Person(String firstName, String lastName, int age, Address[] addresses) {}
record Address(String description, String location, String town, String postalcode){}


