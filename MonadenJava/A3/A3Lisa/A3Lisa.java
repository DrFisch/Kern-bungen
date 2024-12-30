package A3.A3Lisa;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class A3Lisa {
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

        // Iterativ
        List<Person> personsOlder20=new ArrayList<Person>();
        for (Person person : persons) {
            if(person.age()>20){
                personsOlder20.add(person);
            }
        }

        // Filter
        List<Person> personsOlder20Filter = persons.stream()
        .filter(person -> person.age()>20).collect(Collectors.toList());

        // Map 
        List<Person> personsolder20Map= persons.stream().map(p -> (p.age()>20)? p : null).collect(Collectors.toList());

        // FlatMap
        List<Person> personsOlder20FlatMap=persons.stream().flatMap(p -> (p.age()>20) ? Stream.of(p) : Stream.empty() ).collect(Collectors.toList());

        printPersons("Hier sehen sie alle Personen älter als 20", personsolder20Map);
    }
    private static void printPersons(String header,List<Person> persons){
        System.out.println(header);
        persons.stream().forEach(System.out::println);
    }
}

record Person(String firstName, String lastName, int age, 
                List<Address> addresses) {
}

record Address(String description,String location, 
                String town, String postalcode) {
}