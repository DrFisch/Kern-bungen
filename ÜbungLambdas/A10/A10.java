package A10;

import java.util.ArrayList;
import java.util.List;

public class A10 {
    public static void main(String[] args) {
        var v1 = new View(1); 
        var v2 = new View(2);
var model = new Model();
model.getObservers().add(v1);
model.getObservers().add(v2);
model.setName("Alex");
// View '1' received new value 'Alex’!
// View '2' received new value 'Alex‘!
model.setAge(21);
// View '1' received new value '21’!
// View '2' received new value '21‘!
    }
}
class Model{
    private List<View> observers=new ArrayList<>();
    private int age;
    private String name;
    
    public List<View> getObservers() {
        return observers;
    }
    public void add(View v){
        observers.add(v);
    }
    public void setAge(int age){
        this.age=age;
        notifyObservers("Alter: ", age);
    }
    public void setName(String name){
        this.name=name;
        notifyObservers("Name: ", name);
    }
    public void notifyObservers(String propName, Object value){
        for (View view : observers) {
            view.update(propName, value);
        }
    }
}
class View implements Observer{
    int value;
    public View(int i){
        this.value=i;
    }
    public void update(String propName, Object value){
        System.out.println("Die Werte von "+ this.value +" haben sich geändert!\n"+propName+value);
    }
}
interface Observer{
    void update(String propName, Object value);
}