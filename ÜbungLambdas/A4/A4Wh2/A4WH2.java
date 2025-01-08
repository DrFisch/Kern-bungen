package A4.A4Wh2;

import java.util.function.Supplier;

public class A4WH2 {
    public static void main(String[] args) {
        var lazyobject = new Lazy(SomeExpensiveObject::new);
        lazyobject.get();
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