package A4.A4Si;

import java.util.function.*;

public class A4Si {
    public static void main(String[] args) {
    
        var lazyObject = new Lazy(SomeExpensiveObject::new);
        lazyObject.get(); // Erst hier wird neue Instanz v. Typ erzeugt
    }
}
class Lazy<T>{
    Supplier<T> supp;
    public Lazy(Supplier<T> supp){
        this.supp=supp;
    }
    public T get(){
        return supp.get();
    }
}
class SomeExpensiveObject{

}
class Penis{
    
}
