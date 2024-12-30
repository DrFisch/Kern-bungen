import java.util.ArrayList;
import java.util.List;

// Observer Interface
interface Observer {
    void update(String propertyName, Object newValue);
}

// Concrete Observer (View)
class View implements Observer {
    private int id;

    public View(int id) {
        this.id = id;
    }

    @Override
    public void update(String propertyName, Object newValue) {
        System.out.println("View '" + id + "' received new value '" + newValue + "' for " + propertyName + "!");
    }
}

// Subject (Model)
class Model {
    private List<Observer> observers = new ArrayList<>();
    private String name;
    private int age;

    // Register a new observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Notify all observers about a change
    public void notifyObservers(String propertyName, Object newValue) {
        for (Observer observer : observers) {
            observer.update(propertyName, newValue);
        }
    }

    // Set a new name and notify observers
    public void setName(String name) {
        this.name = name;
        notifyObservers("Name", name);
    }

    // Set a new age and notify observers
    public void setAge(int age) {
        this.age = age;
        notifyObservers("Age", age);
    }

    // Get the list of observers (optional, for debugging)
    public List<Observer> getObservers() {
        return observers;
    }
}

// Main class to test the functionality
public class A10 {
    public static void main(String[] args) {
        View v1 = new View(1);
        View v2 = new View(2);
        Model model = new Model();

        // Register observers
        model.addObserver(v1);
        model.addObserver(v2);

        // Set values in the model
        model.setName("Alex");
        model.setAge(21);
    }
}
