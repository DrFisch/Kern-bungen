package A5.A5Lisa;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class A5Lisa {
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
        
        // a) anonyme Funktion
        FlatPerson[] arrSortedPersons= Arrays.stream(arrPersons)
        .sorted(new Comparator<FlatPerson>() {
            @Override
            public int compare(FlatPerson a, FlatPerson b){
                return a.name().compareTo(b.name());
            }
        })
        .toArray(FlatPerson[]::new);

        System.out.println("Unsorted:");
        Arrays.stream(arrPersons).forEach(System.out::println);
        System.out.println("Sorted:");
        Arrays.stream(arrSortedPersons).forEach(System.out::println);

       // b)Lambda Fkt
        List<FlatPerson> sortedList=Arrays
        .stream(arrPersons)
        .sorted((a,b)->a.name().compareTo(b.name())).toList();

        System.out.println("unsorted:");
        Arrays.stream(arrPersons).forEach(System.out::println);
        System.out.println("sorted:");
        sortedList.forEach(System.out::println);

        Function<Integer,Boolean> penis = (x)->x==2;
        System.out.println(penis.apply(2));

        Supplier<Person> supp=()->new Person(null, null, 0, null);
        System.out.println(supp.get());

        BiFunction<String,String,Person> personengenerator=
        Person::new;
        System.out.println(personengenerator.apply("peniskopf", "HD"));


    }
    private static void printPersons(String header,List<Person> persons){
        System.out.println(header);
        persons.stream().forEach(System.out::println);
    }
}

record Person(String firstName, String lastName, int age, 
                List<Address> addresses) {
                    public Person(String firstName, String lastName) {
                        this(firstName, lastName, 0, List.of()); // Standardwerte für age und addresses
                    }
}

record Address(String description,String location, 
                String town, String postalcode) {
}