package A4;

import java.util.function.Function;
import java.util.function.Supplier;

public class A4 {
    public static void main(String[] args) {
        var lazyObject = new Lazy<Integer,Integer>(x->new Integer(x));
        var x=lazyObject.get(2);
        System.out.println(x.toString());
        
    }
}
class SomeExpensiveObject{
    public String toString(){
        return "Ich bin ein Objekt :)";
    }
}
class Lazy<T,R>{
    private Function<T,R> s;
    public Lazy(Function<T,R> s){
        this.s =s;
    }
    public R get(T i){
        return s.apply(i);
    }
    
    
}
