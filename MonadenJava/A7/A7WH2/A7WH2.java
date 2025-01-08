package A7.A7WH2;

import java.util.Arrays;
import java.util.List;

public class A7WH2 {
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

        var jederHatPrimaerAdresse=persons.stream()
        .allMatch(person -> Arrays.stream(person.addresses())
        .anyMatch(y->"Primary Address".equals(y.description())));
        System.out.println(jederHatPrimaerAdresse);

        var einerHatSecretAddress=persons.stream()
        .anyMatch(person -> Arrays.stream(person.addresses())
        .anyMatch(y->"Secret Address".equals(y.description())));
        System.out.println(einerHatSecretAddress);
    }
}
record Person(String firstName, String lastName, int age, Address[] addresses){
    @Override
    public String toString(){
        return firstName() +" "+ lastName()+" "+age()+" "+Arrays.toString(addresses());
    }
}

record Address(String description, String location, String town, String postalcode){
    @Override
    public String toString(){
        return description()+" "+location()+", "+town()+", "+postalcode()+"\n";
    }
}