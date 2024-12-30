package A4;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Übung 4: Erzeugen Sie ein neues Array, welches Personen mit folgender alternativer
// Struktur fasst (iterativ und funktional) – Pseudocode: [{name = "Max Mustermann",
// birthday = ..., addresses = 3}

public class A4 {
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
        //printpersons("Personen über 20", personsolder20);
        record FlatPerson(String name, LocalDate birthday, int addresses){}
        FlatPerson[] personsSchoen= persons.stream().map(x->new FlatPerson(
            x.firstName()+""+x.lastName(),
            LocalDate.now().minusYears(x.age()),
            x.addresses().length))
            .toArray(FlatPerson[]::new);
        System.out.println(Arrays.toString(personsSchoen));
        
    }
}


record Person(String firstName, String lastName, int age, Address[] addresses) {
}
record Address(String description, String location, String town, String postalcode){}