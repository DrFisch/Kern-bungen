package A5;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class A5 {
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
    
        List<Person> personsolder20=persons.stream().filter(p->p.age()>20).collect(Collectors.toList());
        
        record FlatPerson(String name, LocalDate birthday, int addresses){}
        var personsSchoen= persons.stream().map(x->new FlatPerson(
            x.firstName()+""+x.lastName(),
            LocalDate.now().minusYears(x.age()),
            x.addresses().length))
            .toArray(FlatPerson[]::new);
        // Ab hier Übung 5
        // b) mit sort-Funktion und mit Lambda-Funktion
        var personsSorted= List.of(personsSchoen).stream().sorted((a,b)->b.name().compareTo(a.name)).collect(Collectors.toList());
        printpersons("Sortierte", personsSorted);

        // a) mit sort-Funktion und anonymer Funktion als Komparator
        var personssorted2=List.of(personsSchoen).stream().sorted(new java.util.Comparator<FlatPerson>() {
            @Override
            public int compare(FlatPerson a, FlatPerson b) {
                return b.name().compareTo(a.name());
            }
        }).collect(Collectors.toList());
        printpersons("Sortiert mit Klasse", personssorted2);
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