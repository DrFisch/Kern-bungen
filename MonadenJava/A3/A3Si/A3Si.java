package A3.A3Si;

import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;


public class A3Si {
    public static void main(String[] args) {
        Person max=new Person("Max", "Mustermann", 19, 
            List.of(
                new Address("Primary Address","Teststreet 1","Hof","95028"),
                new Address("Secondary Address","Teststreet 2","Hof","95028")
              ));
        Person chris=new Person("Chris", "May", 21, 
            List.of(
                new Address("Primary Address","Teststreet 19","Hof","95030"),
                new Address("Secondary Address","Teststreet 20","Hof","95028"),
                new Address("Secret Address","Secretstreet 1","Bayreuth","95444")
            ));
        Person steve=new Person("Steve", "Clover", 25, 
            List.of(
                new Address("Primary Address","Teststreet 19","Hof","95030"),
                new Address("Secondary Address","Teststreet 20","Hof","95028"),
                new Address("Secret Address","Secretstreet 1","Bayreuth","95444")
            ));
        List<Person> persons=List.of(max,chris,steve);


        // persons.stream().filter(x->x.age()>20).forEach(x->printPersons(x));;
        // Filter
        List<Person> personsolder20 =persons.stream().filter(x->x.age()>20).toList();

        // Map
        List<Person> personsOlder20Map = persons.stream().map(x->x.age()>20? x :null).filter(x ->x!=null).toList();

        List<Person> personsOlder20FlatMap= persons.stream().flatMap(x->x.age()>20?Stream.of(x):Stream.empty()).toList();

        printPersons("Alle Personen älter 20", personsOlder20FlatMap);
    }
    private static void printPersons(String header, List<Person> persons){
        System.out.println(header);
        for (Person p : persons) {
            printPersons(p);
        }
    }
    private static void printPersons( Person person){
        
            System.out.println(person);
        
    }
}
record Person(String firstName,String lastName, int age,List<Address>addresses){
    @Override
    public String toString(){
        return firstName() + " "+ lastName() +" ("+age()+") "+ addresses();
    }
}
record Address(String description, String location, String town, String postalcode){
    @Override
    public String toString(){
        return description() +" ("+location()+", "+postalcode()+ " "+town()+")";
    }
}
