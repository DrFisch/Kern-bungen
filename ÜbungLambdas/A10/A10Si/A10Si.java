package A10.A10Si;

import java.util.ArrayList;
import java.util.List;

public class A10Si {
    public static void main(String[] args) {
        var v1 = new View(1); var v2 = new View(2);
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
interface Observer{
    <T>void update(String fieldname,T obj);
}
class View implements Observer{
    int index;
    public View(int index){
        this.index=index;
    }

    public <T> void update(String fieldName,T obj){
        System.out.println("View: "+index+" wurde über eine Änderung notifiziert. \n"+fieldName+": "+obj+"\n");
    }
}

class Model{
    private int age;
    private String name;
    List<Observer> observers=new ArrayList<Observer>();

    public Model(){
        
    }

    public void notifyObservers(String fieldname,Object obj){
        for (Observer observer : observers) {
            observer.update(fieldname, obj);
        }
    }

    public void setAge(int age){
        this.age=age;
        notifyObservers("Alter",age);
    }
    public void setName(String name){
        this.name=name;
        notifyObservers("Name",name);
    }

    public List<Observer> getObservers(){
        return observers;
    }

}
