package A10;

import java.util.Optional;

public class A10 {
    public static void main(String[] args) {
        // Beispiel um Nullpointer exception zu vermeiden:
        Course course = new Course(new Lecturer(new Name("John", "Doe")));
        String firstName = course.getLecturer()
                         .flatMap(Lecturer::getName)
                         .map(name -> "Vorname: " + name.getFirstName())
                         .orElse("Unknown");
        System.out.println(firstName);

        Course kursMitNullpointerex=new Course();
        System.out.println(kursMitNullpointerex.getLecturer());
        System.out.println(kursMitNullpointerex.getClass());
    }
}
class Course { 
    private  Optional<Lecturer> lecturer; 

    public Course(Lecturer lecturer){
        this.lecturer=Optional.ofNullable(lecturer);
    }
    public Course(){

    }

    public Optional<Lecturer> getLecturer(){
        return lecturer;
    }
}
class Lecturer { 
    private Optional<Name> name; 

    public Lecturer(Name name){
        this.name = Optional.ofNullable(name);
    }

    public Optional<Name> getName(){
        return name;
    }
}
class Name {
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}