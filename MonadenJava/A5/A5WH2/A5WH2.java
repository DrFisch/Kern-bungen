package A5.A5WH2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A5WH2 {
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
        record FlatPerson(String name, LocalDate birthday, int addresses){}
        FlatPerson[] flatPersons= persons.stream().map(x-> 
        new FlatPerson(
            x.firstName()+" " + x.lastName(), 
            LocalDate.now().minusYears(x.age()),
            x.addresses().length)).toArray(FlatPerson[]::new);
            System.out.println("Unsortiert:");
        System.out.println(Arrays.toString(flatPersons));

        // int[] a= {1,2,3};
        // Arrays.stream(a).boxed()

        //sort a)
        var sorted1=List.of(flatPersons).stream().sorted(new java.util.Comparator<FlatPerson>() {
            @Override
            public int compare(FlatPerson o1, FlatPerson o2) {
                return o1.name().compareTo(o2.name());
            };
        }).toArray();
        System.out.println("Sorted1:");
        System.out.println(Arrays.toString(sorted1));

        //sort b)
        var sorted2 = Arrays.stream(flatPersons).sorted((a,b)->a.name().compareTo(b.name())).toArray();
        System.out.println("Sorted2:");
        System.out.println(Arrays.toString(sorted2));

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