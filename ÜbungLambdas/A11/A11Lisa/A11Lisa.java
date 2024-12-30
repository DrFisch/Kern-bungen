package A11.A11Lisa;

import java.util.Iterator;
import java.util.function.Function;

public class A11Lisa {
    public static void main(String[] args) {
       var sequence = new Sequence<>(10, i -> (double)(i+1)*(i+1));
        for (var s : sequence)
            System.out.println(s);
    }
}
class Sequence<T> implements Iterable<T>{
    private int anzahl;
    private Function<Integer,T> func;

    public Sequence(int anzahl, Function<Integer,T> func){
        this.anzahl=anzahl;
        this.func=func;
    }


    public Iterator<T> iterator(){
        return new Iterator<T>(){
            int cnt=0;
            public boolean hasNext(){
                return cnt<anzahl;
            }
            public T next(){
                return func.apply(cnt++);
            }
        };
    }
}
