package A10;

import java.util.Optional;

public class A10OhneOptionals {
    public static void main(String[] args) {
        Course2 course = new Course2(new Lecturer2(new Name2("John", "Doe")));
        String firstName = course.getLecturer()
                         .flatMap(Lecturer2::getName)
                         .map(name -> "Vorname: " + name.getFirstName())
                         .orElse("Unknown");
        System.out.println(firstName);
    }
}
class Course2 {
    private Lecturer2 lecturer; // Keine Optionals notwendig

    public Course2(Lecturer2 lecturer) {
        this.lecturer = lecturer;
    }

    public Optional<Lecturer2> getLecturer() {
        return Optional.ofNullable(lecturer);
    }
}

class Lecturer2 {
    private Name2 name; // Keine Optionals notwendig

    public Lecturer2(Name2 name) {
        this.name = name;
    }

    public Optional<Name2> getName() {
        return Optional.ofNullable(name);
    }
}

class Name2 {
    private String firstName;
    private String lastName;

    public Name2(String firstName, String lastName) {
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
