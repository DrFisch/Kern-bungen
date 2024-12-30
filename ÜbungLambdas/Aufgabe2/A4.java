

import java.util.function.Supplier;

public class A4 {
    public static void main(String[] args) {
        var lazyObject = new Lazy(SomeExpensiveObject::new);
    System.out.println(lazyObject.get()); // Erst hier wird neue Instanz v. Typ erzeugt
    }
    
}
class SomeExpensiveObject { /* */ }

class Lazy<T>{
    Supplier<T> ctor;
    public Lazy(Supplier<T> ctor){
        this.ctor=ctor;
    }
    public T get(){
        
        return ctor.get();
    }
}