package A2.A2Lisa;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class A2Lisa {
    public static void main(String[] args) {
        Person max =new Person("Max","Mustermann",19,List.of(
            new Address("Primary Address", "Teststreet 1", "Hof", "95028"),
            new Address("Secondary Address", "Teststreet 2", "Hof", "95028")
        ));
        Person chris =new Person("Chris","May",21,List.of(
            new Address("Primary Address", "Teststreet 19", "Hof", "95030"),
            new Address("Secondary Address", "Teststreet 20", "Hof", "95028"),
            new Address("Secret Address", "Secretstreet 1", "Bayreuth", "95444")
        ));
        Person steve =new Person("Steve","Clover",25,List.of(
            new Address("Primary Address", "Teststreet 19", "Hof", "95030"),
            new Address("Secondary Address", "Teststreet 20", "Hof", "95028"),
            new Address("Secret Address", "Secretstreet 1", "Bayreuth", "95444")
        ));

        
        List<Person> persons = List.of(max,chris,steve);
        
        printPersons("Hier sehen sie alle Personen :)", persons);
    }
    private static void printPersons(String header,List<Person> persons){
        persons.stream().forEach(System.out::println);
    }
}

record Person(String firstName, String lastName, int age, 
                List<Address> addresses) {
}

record Address(String description,String location, 
                String town, String postalcode) {
}