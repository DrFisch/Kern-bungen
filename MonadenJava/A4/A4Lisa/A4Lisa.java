package A4.A4Lisa;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class A4Lisa {
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

        record FlatPerson(String name, int birthyear, int addresses){}
        FlatPerson[] arrPersons=persons.stream().map(p->new FlatPerson(
            p.firstName()+" "+p.lastName(),
            LocalDate.now().getYear()-p.age(),
            p.addresses().size()
        )).toArray(FlatPerson[]::new);
        
       Arrays.stream(arrPersons).forEach(System.out::println);

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