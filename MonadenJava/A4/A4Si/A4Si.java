package A4.A4Si;

import java.util.List;
import java.util.stream.Stream;
import java.time.LocalDate;
import java.util.ArrayList;


public class A4Si {
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
        record FlatPerson (String name, LocalDate birthday, int addresses){}

        // iterativ

        List<FlatPerson> flachePersonen=new ArrayList<FlatPerson>();
        for(Person p: persons){
            flachePersonen.add(new FlatPerson(p.firstName()+" "+p.lastName(), LocalDate.now().minusYears(p.age()), p.addresses().size()));
        }
        System.out.println("Iterativ:");
        flachePersonen.forEach(System.out::println);
        // funktional

        List<FlatPerson> alternateList=persons.stream().map(p->new FlatPerson(
            p.firstName()+" "+p.lastName(),
            LocalDate.now().minusYears(p.age()), 
            p.addresses().size()
            )).toList();
            System.out.println("Funktional:");
        alternateList.forEach(System.out::println);
        
        printPersons("Alle Personen älter 20", persons);
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
