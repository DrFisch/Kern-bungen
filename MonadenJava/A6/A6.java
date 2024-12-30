package A6;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class A6 {
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
                

        //mit streams
        double summeAlter = persons.stream().map(Person::age).reduce(0,Integer::sum);
        double durchschnittsalter= summeAlter/persons.size();
        System.out.printf("%.2f%n",durchschnittsalter);

        // iterativ
        int sum=0;
        for(Person p : persons){
            sum+=p.age();
        }
        double durchschnittIterativ=((double)sum)/persons.size();
        System.out.printf("%.2f%n",durchschnittIterativ);

        // noch eine andere möglichkeit mit mapToInt()
        double averageAgeFunctional = persons.stream()
                                     .mapToInt(Person::age)
                                     .average()
                                     .orElse(0.0); // Falls die Liste leer ist, wird 0.0 zurückgegeben.

        System.out.printf("Funktionales Durchschnittsalter: %.2f%n", averageAgeFunctional);
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