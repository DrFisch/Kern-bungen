package A11.A11Si;

import java.util.Iterator;
import java.util.function.*;

public class A11Si {
    public static void main(String[] args) {
        var sequence = new Sequence<>(10, i ->(double) (i+1)*(i+1));
        for (var s : sequence)
            System.out.println(s);
    }
}
class Sequence<T> implements Iterable<T>{
    int laenge;
    Function<Integer,T> func;
    public Sequence(int laenge, Function<Integer,T> func){
        this.func=func;
        this.laenge=laenge;
    }
    
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            int index=0;

            @Override
            public boolean hasNext(){
                return index<laenge;
            }

            @Override
            public T next(){
                return func.apply(index++);
            }
        };
    }
}
