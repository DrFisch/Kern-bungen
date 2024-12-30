package A2.A2Neu;

import java.util.List;
import java.util.Arrays;


record Person(String firstName, String lastName, int age, Address[] addresses) {
    @Override
    public String toString(){
        // String addressesString="";
        // for (Address address : addresses) {
        //     addressesString+=address;
        // }
        String addressesString =Arrays.stream(addresses).map(a -> a.description()+", "+ a.location()+", "+a.town()+", "+ a.postalcode()).reduce((a,b)->a+", "+b ).orElse("");

        //return "Vorname: "+ firstName+" ,Nachname: "+lastName+" ,Alter: "+age+" ,Addressen: "+ Arrays.toString(addresses);
        return "Vorname: "+ firstName+" ,Nachname: "+lastName+" ,Alter: "+age+" ,Addressen: "+ addressesString;

    }
}
record Address(String description, String location, String town, String postalcode){}
public class A2neu {
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
        printpersons("hallo",persons);
    }
    private static void printpersons(String header, List<? extends Object> persons){
        System.out.println(header);
        for (var person:persons){
            System.out.println(person);
        }
        System.out.println();
    }
}