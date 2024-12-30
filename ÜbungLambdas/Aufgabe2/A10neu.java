import java.util.ArrayList;
import java.util.List;

public class A10neu {
    public static void main(String[] args) {
        View view1 = new View("View1");
        View view2 = new View("View2");
        Model model = new Model();

        model.getObservers().add(view1);
        model.getObservers().add(view2);

        model.setName("Alex");
        model.setAlter(21);
    }
}

class Model {
    List<Observer> observers = new ArrayList<>();
    private String name;
    private int alter;

    public List<Observer> getObservers() {
        return observers;
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers(name);
    }

    public void setAlter(int alter) {  // Hier war der Fehler mit "alt" behoben
        this.alter = alter;
        notifyObservers(alter);
    }

    public void notifyObservers(Object obj) {
        for (Observer o : observers) {
            o.update(obj);
        }
    }
}

interface Observer {
    void update(Object neuerWert);
}

class View implements Observer {
    String name;

    public View(String name) {
        this.name = name;
    }

    @Override
    public void update(Object neuerWert) {
        System.out.println(name + " hat einen neuen Wert vom Model: " + neuerWert);
    }
}
