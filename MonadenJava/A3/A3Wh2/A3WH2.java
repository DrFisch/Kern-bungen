package A3.A3Wh2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class A3WH2 {
    public static void main(String[] args) {
        Person max = new Person("Max","Mustermann",19, new Address[]{
            new Address("Primary Address","Musterstraße 1","Musterstadt","12345"),
            new Address("Secondary Address","Nebenstraße 2","Musterstadt","12345")
        });
        Person chris= new Person("Chris","May",21, new Address[]{
            new Address("Primary Address","Hauptstraße 1","Beispielstadt","54321"),
            new Address("Secondary Address","Nebenstraße 2","Beispielstadt","54321"),
            new Address("Secret Address","Geheimgasse 3","Beispielstadt","54321")
        });
        Person steve = new Person("Steve","Clover",25, new Address[]{
            new Address("Primary Address","Kleeweg 4","Kleestadt","67890"),
            new Address("Secondary Address","Eichenweg 5","Kleestadt","67890"),
            new Address("Secret Address","Verborgenstraße 6","Kleestadt","67890")
        });
        List<Person> persons=List.of(max,chris,steve);
       
        //
        var personsOlder20 = persons.stream().filter(x->x.age()>20).collect(Collectors.toList());

        // Iterativ
        List<Person> personsOlder20Iterativ=new ArrayList<>();
        for(var p : persons){
            if(p.age()>20){
                personsOlder20Iterativ.add(p);
            }
        }

        //map
        List<Person> personsOlder20Map = persons.stream()
        .map(x->x.age()>20?x:null).toList();

        printPersons("test", personsOlder20Map);
    }




    static void printPersons(String header, List<Person> persons){
        // System.out.println(header);
        // persons.stream().forEach(System.out::println);
        System.out.println(header);
        for (Person p : persons) {
            System.out.println(p);
        }
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
